package simulation.components.light;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import simulation.agent.Agent;
import simulation.agent.TimeServer;
import simulation.components.car.Car;
import simulation.components.road.CarAcceptor;
import simulation.components.road.CarSink;

public class TrafficLight implements CarAcceptor{
	//if current state is Yellow then NS is Red
	//if the current state is Red then NS is Green
	//if the current state is Green then NS is Red
	private LightState currentState; //this is always the east west direction
	private LightState greenState = new GreenLightState(this);
	private LightState yellowState = new YellowLightState(this);
	private LightState redState = new RedLightState(this);
	private TimeServer time;
	private double roadLength;
	private List<Car> cars = new LinkedList<>();
	private String name = "Light-" + (int)(Math.random() * 1000);
	private CarAcceptor nextRoad;
	private TrafficDirection direction;
	private double greenTime;
	private double yellowTime;
	private Random random = new Random();
	DecimalFormat df = new DecimalFormat("#");
	
	public TrafficLight(TimeServer time){
		if(time == null)
			throw new IllegalArgumentException("TimeServer arg is null");
		
		this.time = time;
		greenState = new GreenLightState(this);
		yellowState = new YellowLightState(this);
		redState = new RedLightState(this);
		currentState = greenState;
		//System.out.println(random.nextDouble());
		double randomValue = TrafficLightSettings.GREEN_MIN.value()
				+ (TrafficLightSettings.GREEN_MAX.value() - TrafficLightSettings.GREEN_MIN
						.value()) * random.nextDouble();
		greenTime =  Double.parseDouble(df.format(randomValue));
		//System.out.println(random.nextDouble());
		randomValue = TrafficLightSettings.YELLOW_MIN.value()
				+ (TrafficLightSettings.YELLOW_MAX.value() - TrafficLightSettings.YELLOW_MIN
						.value()) * random.nextDouble();
		yellowTime =  Double.parseDouble(df.format(randomValue));
		roadLength = 50.0;
	}
	
	/*
	 * Changes the state of the traffic light.
	 */
	public void next(){
		currentState.next();
	}	
	
	@Override
	public boolean accept(Car c, double carFrontPosition) {
		//current state represents E/W direction only; 
		//N/S is Red when E/W is green and yellow.
		if((currentState == greenState || currentState == yellowState)) {
			if(cars.contains(c))
				cars.remove(c); 
			if (carFrontPosition > roadLength) {
				c.setCurrentRoad(nextRoad);
				return nextRoad.accept(c, carFrontPosition - roadLength);	      
		    } else {
		      c.setCurrentRoad(this);
		      c.setFrontPosition(carFrontPosition);
		      cars.add(c);
		      return true;
		    }
		} else {			
			return false;
		}
	}

	@Override
	public double distanceToObstacle(double fromPosition) {
		double obstaclePosition = this.distanceToCarBack(fromPosition);
		double distanceToEnd = this.roadLength - fromPosition;
		if (obstaclePosition == Double.POSITIVE_INFINITY) {			
			obstaclePosition = nextRoad.distanceToObstacle(fromPosition
					- this.roadLength);
			// if(obstaclePosition == 0)
			// obstaclePosition = distanceToEnd;
		}
		if (obstaclePosition == 0)
			return distanceToEnd;
		else
			return obstaclePosition - fromPosition;
	}

	private double distanceToCarBack(double fromPosition) {
		double carBackPosition = Double.POSITIVE_INFINITY;
		for (Car c : cars)
			if (c.backPosition() >= fromPosition
					&& c.backPosition() < carBackPosition)
				carBackPosition = c.backPosition();
		return carBackPosition;
	}
	
	public void setState(LightState state){
		if(state == null)
			throw new IllegalArgumentException("LightState arg is null.");
		this.currentState = state;
	}
	
	public LightState getGreenState() {
		return greenState;
	}

	public LightState getYellowState() {
		return yellowState;
	}
	
	public LightState getRedState() {
		return redState;
	}
	
	public LightState getCurrentState(){
		return currentState;
	}
	
	@Override
	public String toString() {
		return name + ": current state=" + currentState + ", # of cars=" + cars.size();
	}	

	@Override
	public String name() {
		return name;
	}

	@Override
	public void setNext(CarAcceptor next) {
		if(next == null)
			throw new IllegalArgumentException("CarAcceptor arg is null.");
		nextRoad = next;	
	}

	@Override
	public int count() {
		return cars.size();
	}	
	
	@Override
	public TrafficDirection getDirection() {
		return direction;
	}

	public double getGreenTime() {
		return greenTime;
	}
	
	public double getYellowTime() {
		return yellowTime;
	}
}
