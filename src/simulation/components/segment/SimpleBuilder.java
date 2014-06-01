package simulation.components.segment;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import simulation.agent.TimeServer;
import simulation.components.SimulationSettings;
import simulation.components.light.LightController;
import simulation.components.light.TrafficDirection;
import simulation.components.light.TrafficLight;
import simulation.components.road.CarAcceptor;
import simulation.components.road.CarSink;
import simulation.components.road.CarSource;
import simulation.components.road.Road;
import simulation.components.road.RoadBuilder;

public class SimpleBuilder {
	TimeServer time;
	List<CarAcceptor> eastwestroads;
	List<CarAcceptor> northsouthroads;
	List<LightController> lightControllers;
	List<TrafficLight[]> lightListEW;
	List<TrafficLight[]> lightListNS;

	CarAcceptor[] roadsArray;
	// CarAcceptor [] northsouthroadsarray;
	TrafficLight[] lightsArray;
	TrafficLight[] lightsArrayEW;
	TrafficLight[] lightsArrayNS;

	RoadBuilder rb = new RoadBuilder();
	CarSink csink;
	CarSource csource;
	int numOfEastWestRoads = SimulationSettings.getGridrows();
	int numOfNorthSouthRoads = SimulationSettings.getGridcols();
	int numOfEastWestRoadSeg = SimulationSettings.getGridcols() + 1;
	int numOfNorthSouthRoadSeg = SimulationSettings.getGridrows() + 1;
	int numofeastwestlights = SimulationSettings.getGridcols();
	int numofnorthsouthlights = SimulationSettings.getGridrows();
	int numOfLightControllers = (SimulationSettings.getGridcols() * SimulationSettings
			.getGridrows());

	public SimpleBuilder(TimeServer time) {
		this.time = time;
	}

	public void build() {
		eastwestroads = new ArrayList<>();
		northsouthroads = new ArrayList<>();
		lightControllers = new ArrayList<>(numOfLightControllers);
		lightListEW = new ArrayList<>();
		lightListNS = new ArrayList<>();
		buildRoads(TrafficDirection.EAST_WEST, numOfEastWestRoadSeg,
				numofeastwestlights, numOfEastWestRoads);
		buildRoads(TrafficDirection.NORTH_SOUTH, numOfNorthSouthRoadSeg,
				numofnorthsouthlights, numOfNorthSouthRoads);
		buildLightControllers();
	}

	/*
	 * Builds the whole road with carsource, road segments, lights and car sinks.
	 * This builds the builds the components and assembles them.
	 */
	private void buildRoads(TrafficDirection direction, int numOfRoads,
			int numOfLights, int roads) {
		for (int i = 0; i < roads; i++) {
			buildComponents(direction, numOfRoads, numOfLights);
			assembleComponents(numOfRoads, numOfLights);
			addSourceToMasterRoadList(direction);
		}
	}

	private void buildComponents(TrafficDirection direction, int numOfRoads,
			int numOfLights) {
		csink = new CarSink();
		buildRoadSegments(direction, numOfRoads);
		buildLights(direction, numOfLights);
		csource = new CarSource(time);
		time.enqueue(0, csource);
	}

	/*
	 * Assembles components from start to finish.
	 */
	private void assembleComponents(int numOfRoads, int numOfLights) {
		csource.setNext(roadsArray[0]);
		for (int i = 0; i < numOfLights; i++) {
			roadsArray[i].setNext(lightsArray[i]);
		}
		for (int i = 0; i < numOfLights; i++) {
			lightsArray[i].setNext(roadsArray[i + 1]);
		}
		roadsArray[numOfRoads - 1].setNext(csink);
	}

	private void addSourceToMasterRoadList(TrafficDirection direction) {
		switch (direction) {
		case EAST_WEST:
			eastwestroads.add(csource);
			break;
		case NORTH_SOUTH:
			northsouthroads.add(csource);
			break;
		}
	}

	private void buildRoadSegments(TrafficDirection direction, int numOfRoads) {
		roadsArray = new Road[numOfRoads];
		// List eastwestroadsarray = new ArrayList(3);
		for (int i = 0; i < numOfRoads; i++) {
			roadsArray[i] = rb.setTimeServer(time)
					.setTrafficDirection(direction).build();
		}
	}

	private void buildLights(TrafficDirection direction, int numOfRoadLights) {
		lightsArray = new TrafficLight[numOfRoadLights];
		for (int i = 0; i < numOfRoadLights; i++) {
			lightsArray[i] = new TrafficLight(time, direction);
		}

		switch (direction) {
		case EAST_WEST:
			lightListEW.add(lightsArray);
			break;
		case NORTH_SOUTH:
			lightListNS.add(lightsArray);
			break;
		}
		
	}

	
	/*
	 * private void buildLightControllers() { // int numOfLightControllers =
	 * SimulationSettings.getGridcols() * // SimulationSettings.getGridrows();
	 * LightController controller = null; for (int i = 0; i <
	 * numOfLightControllers; i++) { for (int j = 0; j <
	 * this.numofeastwestlights; j++){ controller = new LightController(time,
	 * lightsArrayEW[j], lightsArrayNS[i]); } lightControllers.add(controller);
	 * } }
	 */
	private void buildLightControllers() {
		LightController controller = null;
		int nscounter = 0;
		
		for(TrafficLight[] ewl : lightListEW){
			for (int i = 0; i < ewl.length; i++){			
				controller = new LightController(time, ewl[i], lightListNS.get(i)[nscounter]);
				lightControllers.add(controller);
				time.enqueue(0, controller);
			}			
			nscounter++;
		}
	}

	public List<LightController> getLightControllers() {
		return lightControllers;
	}

	public List<TrafficLight[]> getLightListEW() {
		return lightListEW;
	}

	public List<TrafficLight[]> getLightListNS() {
		return lightListNS;
	}

	public CarAcceptor[] getRoadsArray() {
		return roadsArray;
	}

	public List<CarAcceptor> getEastwestroads() {
		return eastwestroads;
	}

	public List<CarAcceptor> getNorthsouthroads() {
		return northsouthroads;
	}
	
	
	
	
}
