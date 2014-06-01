//package project.model;
package simulation.ui;

import java.util.List;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
//import project.util.Animator;











import simulation.agent.Agent;
import simulation.agent.TimeServer;
import simulation.agent.TimeServerLinked;
import simulation.components.car.Car;
import simulation.components.car.CarBuilder;
import simulation.components.light.LightController;
import simulation.components.light.TrafficLight;
import simulation.components.road.CarAcceptor;
import simulation.components.road.Road;
import simulation.components.road.RoadBuilder;
import simulation.components.segment.SimpleBuilder;

/**
 * An example to model for a simple visualization.
 * The model contains roads organized in a matrix.
 * See {@link #Model(AnimatorBuilder, int, int)}.
 */
public class Model extends Observable {
  private TimeServer time = new TimeServerLinked();
  private List<Agent> _agents;
  private Animator _animator;
  private boolean _disposed;
  private double _time;

  /** Creates a model to be visualized using the <code>builder</code>.
   *  If the builder is null, no visualization is performed.
   *  The number of <code>rows</code> and <code>columns</code>
   *  indicate the number of {@link Light}s, organized as a 2D
   *  matrix.  These are separated and surrounded by horizontal and
   *  vertical {@link Road}s.  For example, calling the constructor with 1
   *  row and 2 columns generates a model of the form:
   *  <pre>
   *     |  |
   *   --@--@--
   *     |  |
   *  </pre>
   *  where <code>@</code> is a {@link Light}, <code>|</code> is a
   *  vertical {@link Road} and <code>--</code> is a horizontal {@link Road}.
   *  Each road has one {@link Car}.
   *
   *  <p>
   *  The {@link AnimatorBuilder} is used to set up an {@link
   *  Animator}.
   *  {@link AnimatorBuilder#getAnimator()} is registered as
   *  an observer of this model.
   *  <p>
   */
  public Model(AnimatorBuilder builder, int rows, int columns) {
    if (rows < 0 || columns < 0 || (rows == 0 && columns == 0)) {
      throw new IllegalArgumentException();
    }
    if (builder == null) {
      builder = new NullAnimatorBuilder();
    }
    _agents = new ArrayList<Agent>();
    setup(builder, rows, columns);
    _animator = builder.getAnimator();
    super.addObserver(_animator);
  }

  /**
   * Run the simulation for <code>duration</code> model seconds.
   */
  public void run(double duration) {
    if (_disposed)
      throw new IllegalStateException();
    for (int i=0; i<duration; i++) {
      _time++;
      // iterate through a copy because _agents may change during iteration...
      for (Agent a : _agents.toArray(new Agent[0])) {
        //a.run(_time);
    	  a.run();
      }
      super.setChanged();
      super.notifyObservers();
    }
  }

  /**
   * Throw away this model.
   */
  public void dispose() {
    _animator.dispose();
    _disposed = true;
  }

  /**
   * Construct the model, establishing correspondences with the visualizer.
   */
  private void setup(AnimatorBuilder builder, int rows, int columns) {
	SimpleBuilder simple = new SimpleBuilder(time); 
	simple.build();
	
	List<LightController> lightctl = simple.getLightControllers();
	
	
    List<Road> roads = new ArrayList<Road>();
    //
    TrafficLight[][] intersections = new TrafficLight[rows][columns];
    //LightController[][] intersections = new LightController[rows][columns];
    Boolean reverse;

    // Add Lights
    /*for (int i=0; i<rows; i++) {
      for (int j=0; j<columns; j++) {
        intersections[i][j] = new TrafficLight(time); 
        builder.addLight(intersections[i][j], i, j);
        _agents.add(intersections[i][j]);
      }
    }*/
    
    for (int i=0; i<rows; i++) {
        for (int j=0; j<columns; j++) {
          intersections[i][j] = lightctl.get(i).getLights().get(0); 
          builder.addLight(intersections[i][j], i, j);
          _agents.add(intersections[i][j]);
        }
    }
        
    /*for(LightController l : lightctl){
          intersections[i][j] = l.getLights().get(0);
          intersections[i][j]
          builder.addLight(intersections[i][j], i, j);
          _agents.add(intersections[i][j]);
      }*/
    
    // Add Horizontal Roads
    boolean eastToWest = false;
    for (int i = 0; i<rows; i++) {
      for (int j=0; j<=columns; j++) {
        //Road l = new Road();
    	  Road l = (Road) (new RoadBuilder().setTimeServer(time).build());
        builder.addHorizontalRoad(l, i, j, eastToWest);
        roads.add(l);
      }
      eastToWest = !eastToWest;
    }

    
    /*List<CarAcceptor> li = simple.getEastwestroads();
    boolean eastToWest = false;
    int z = -1, k = -1;
    for(CarAcceptor c : simple.getEastwestroads()){
    	z++;
    	while(c.getNext() != null){
    		if(c instanceof Road){
    			k++;
    			Road l = (Road) c;
    			builder.addHorizontalRoad(l, z, k, eastToWest);
    			roads.add(l);
    			
    		}
    		c = c.getNext();
    }
  }
      eastToWest = !eastToWest;*/
    //}
    
    
    // Add Vertical Roads
    boolean southToNorth = false;
    for (int j=0; j<columns; j++) {
      for (int i=0; i<=rows; i++) {
        //Road l = new Road();
    	Road l = (Road) (new RoadBuilder().setTimeServer(time).build());
        builder.addVerticalRoad(l, i, j, southToNorth);
        roads.add(l);
      }
      southToNorth = !southToNorth;
    }
      
     /* List<CarAcceptor> liz = simple.getNorthsouthroads();
      boolean southToNorth = false;
      z = -1;
      k = -1;
      for(CarAcceptor c : simple.getNorthsouthroads()){
      	z++;
      	while(c.getNext() != null){
      		if(c instanceof Road){
      			k++;
      			Road l = (Road) c;
      			builder.addVerticalRoad(l, z, k, southToNorth);
      			roads.add(l);      			
      		}
      		c = c.getNext();
        //}
      }
    }*/

    // Add Cars
    for (Road l : roads) {
      //Car car = new Car();
    	Car car = new CarBuilder().build();
      _agents.add(car);
      //l.accept(car);
      l.accept(car,0);
    }
  }
}
