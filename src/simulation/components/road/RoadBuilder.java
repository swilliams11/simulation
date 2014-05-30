package simulation.components.road;

import simulation.agent.TimeServer;

public class RoadBuilder {
	private TimeServer time;
	private CarAcceptor nextRoad;
	private double roadLength;	//meters
	private String name;
	
	public RoadBuilder setTimeServer(TimeServer time){
		this.time = time;
		return this;
	}
	
	public RoadBuilder setNextRoad(CarAcceptor next){ 
		nextRoad = next;
		return this;
	}
	
	public RoadBuilder setRoadLength(double length){
		roadLength = length;
		return this;
	}
	
	public RoadBuilder setRoadName(String name){
		this.name = name;
		return this;
	}
		
	public CarAcceptor build(){
		if(roadLength == 0.0)
			roadLength = 100.0;
		if(time == null)
			throw new IllegalArgumentException("Set TimeServer before building.");
		//if(nextRoad == null)
		//	throw new IllegalArgumentException("Set the next road before building.");
		if(name == null)
			throw new IllegalArgumentException("Set the road name before building.");
		
		return new Road(time, nextRoad, name, roadLength);
	}
}
