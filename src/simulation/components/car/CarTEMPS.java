package simulation.components.car;

import org.junit.Assert;

import simulation.agent.TimeServer;
import simulation.agent.TimeServerLinked;
import simulation.components.light.TrafficLight;
//import junit.framework.Assert;
import junit.framework.TestCase;

public class CarTEMPS extends TestCase {
   TimeServer time = new TimeServerLinked();
  public CarTEMPS(String name) {
    super(name);
  }
  
  public void testConstructorAndAttributes() {
    Car v1 = new CarImpl(time);
    //test that default values get used if no values passed as contructor args
    Assert.assertEquals(CarSettings.LENGTH_DEFAULT.value(), v1.getLength(), .05);
    Assert.assertEquals(CarSettings.MAX_VELOCITY_DEFAULT.value(), v1.getMaxVelocity(), .05);
    Assert.assertEquals(CarSettings.BREAK_DISTANCE_DEFAULT.value(), v1.getBrakeDistance(), .05);
    Assert.assertEquals(CarSettings.STOP_DISTANCE_DEFAULT.value(), v1.getStopDistance(), .05);
    
    //double length, double maxVelocity, double brakeDistance, double stopDistance, TimeServer time
    Car v2 = new CarImpl(7.0, 30.0, 10.0, 5.0, time );
    Assert.assertEquals(7.0, v2.getLength(), .05);
    Assert.assertEquals(30.0, v2.getMaxVelocity(), .05);
    Assert.assertEquals(10.0, v2.getBrakeDistance(), .05);
    Assert.assertEquals(5.0, v2.getStopDistance(), .05);
    
    v2 = new CarImpl(7.0, 30.0, 10.0, 5.0, time );
    Assert.assertEquals(7.0, v2.getLength(), .05);
    Assert.assertEquals(30.0, v2.getMaxVelocity(), .05);
    Assert.assertEquals(10.0, v2.getBrakeDistance(), .05);
    Assert.assertEquals(5.0, v2.getStopDistance(), .05);
    
    //check that default values are set when you exceed the maximum default setting 
    v2 = new CarImpl(12.0, 50.0, 12.0, 7.0, time );
    Assert.assertEquals(CarSettings.LENGTH_DEFAULT.value(), v1.getLength(), .05);
    Assert.assertEquals(CarSettings.MAX_VELOCITY_DEFAULT.value(), v1.getMaxVelocity(), .05);
    Assert.assertEquals(CarSettings.BREAK_DISTANCE_DEFAULT.value(), v1.getBrakeDistance(), .05);
    Assert.assertEquals(CarSettings.STOP_DISTANCE_DEFAULT.value(), v1.getStopDistance(), .05);
  }
  
  /*
   * This is no longer a valid test. The distance to is calculated in the Road.
  public void testDistanceToAndRun(){
	  //the default position is 100
	  TrafficLight light = new TrafficLight(time);
	  Car v1 = new Car(time);
	  v1.distanceTo(light);
	  
	  Assert.assertEquals(v1.getDistanceToObstacle(), 100.0, .05);
	  Assert.assertEquals(v1.position(), 0.0, .05);
	  v1.run();//time 0
	  //after time zero runs then the next position is 10
	  Assert.assertEquals(v1.position(), 10.0, .05);
	  v1.run();//time 1
	  //the car moved 10 meters in 1 second so new distance is 90
	  Assert.assertEquals(v1.getDistanceToObstacle(), 90.0, .05);
	  //after time 1 runs then the next position is 20
	  Assert.assertEquals(v1.position(), 20.0, .05);
  }*/

  /* this was changed to use a random number in the car number.
  public void testToString(){
	  Car v1 = new CarImpl(time);
	  TrafficLight light = new TrafficLight(time);
	  System.out.println(v1);
	  //v1.distanceTo(light);
	  //String car = "Car: current time=0.0, current position=0.0, current road=null, obstacle=[TrafficLight: current position=100.0, current state=GREEN]\n"
	  //		  + ", distance to obstacle =100.0, current velocity=0.0, stop distance=5.0, brake distance=10.0, max velocity=10.0, car length=5.0";
	  
	  String car = "Car: current time=0.0, current position=0.0, current road=null, obstacle=[TrafficLight: current position=100.0, current state=GREEN]\n"
			  + ", distance to obstacle =1.7976931348623157E308, current velocity=0.0, stop distance=5.0, brake distance=10.0, max velocity=10.0, car length=5.0";
	  
	  v1.toString();
	  Assert.assertTrue(v1.toString().equals(car));
  }*/
}
