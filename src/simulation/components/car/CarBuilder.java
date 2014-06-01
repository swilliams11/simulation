package simulation.components.car;

import simulation.agent.TimeServer;
import simulation.components.road.CarAcceptor;

public class CarBuilder {
	private TimeServer time;
	private double maxVelocity;
	// If distance to nearest obstacle is <= brakeDistance,
	// then the car will start to slow down (in meters)
	private double brakeDistance;
	// If distance to nearest obstacle is == stopDistance,
	// then the car will stop (in meters)
	private double stopDistance;
	private double length; // in meters
	private CarAcceptor road;
	
	public CarBuilder(){
		
	}
	
	public CarBuilder(TimeServer time, CarAcceptor road){
		this.time = time;
		this.road = road;
	}
	
	public CarBuilder setTimeServer(TimeServer time){
		this.time = time;
		return this;
	}	
	
	public CarBuilder setMaxVelocity(double maxVelocity){
		this.maxVelocity = maxVelocity;
		return this;
	}	
	
	public CarBuilder setBrakeDistance(double brakeDistance){
		this.brakeDistance = brakeDistance;
		return this;
	}
	
	public CarBuilder setStopDistance(double stopDistance){
		this.stopDistance = stopDistance;
		return this;
	}
		
	public CarBuilder setLength(double length){
		this.length = length;
		return this;
	}
	
	public CarBuilder setRoad(CarAcceptor road){
		this.road = road;
		return this;
	}
	
	public Car build(){	
		//Car temp = new CarImpl(length, maxVelocity, brakeDistance, stopDistance, time);
		Car temp = new CarImpl(time);
		temp.setCurrentRoad(road);
		return temp;
	}
}
