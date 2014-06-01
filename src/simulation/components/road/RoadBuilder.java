package simulation.components.road;

import simulation.agent.TimeServer;
import simulation.components.light.TrafficDirection;

public class RoadBuilder {
	private TimeServer time;
	private CarAcceptor nextRoad;
	private double roadLength;	//meters
	private String name;
	private TrafficDirection direction;
	
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
	
	public RoadBuilder setTrafficDirection(TrafficDirection direction){
		this.direction = direction;
		return this;
	}
		
	public CarAcceptor build(){
		//this value is set with a random number in Road		
		if(time == null)
			throw new IllegalArgumentException("Set TimeServer before building.");
		//if(nextRoad == null)
		//	throw new IllegalArgumentException("Set the next road before building.");
		/*if(name == null)
			throw new IllegalArgumentException("Set the road name before building.");*/
		if(nextRoad != null && roadLength != 0.0){
			return new Road(time, nextRoad, name, roadLength);
		}
		if(roadLength != 0.0){
			Road road = new Road(time, direction);
			road.setRoadLength(roadLength);
			return road;
		} else {
			return new Road(time, direction);
		}
		
	}
}
