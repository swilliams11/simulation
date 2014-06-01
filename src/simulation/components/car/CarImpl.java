package simulation.components.car;

import simulation.agent.Agent;
import simulation.agent.TimeServer;
import simulation.components.Simulatable;
import simulation.components.SimulationSettings;
import simulation.components.road.CarAcceptor;
import simulation.components.road.CarSink;

public class CarImpl implements Car {
	// meters/second
	private double maxVelocity = (int)(Math.random() * (CarSettings.MAX_VELOCITY_MAX.value() - CarSettings.MAX_VELOCITY_MIN.value()) + CarSettings.MAX_VELOCITY_MIN.value());
	// If distance to nearest obstacle is <= brakeDistance,
	// then the car will start to slow down (in meters)
	private double brakeDistance = (int)(Math.random() * (CarSettings.BREAK_DISTANCE_MAX.value() - CarSettings.BREAK_DISTANCE_MIN.value()) + CarSettings.BREAK_DISTANCE_MIN.value());
	// If distance to nearest obstacle is == stopDistance,
	// then the car will stop (in meters)
	private double stopDistance = (int)(Math.random() * (CarSettings.STOP_DISTANCE_MAX.value() - CarSettings.STOP_DISTANCE_MIN.value()) + CarSettings.STOP_DISTANCE_MIN.value());
	private double length = (int)(Math.random() * (CarSettings.LENGTH_MAX.value() - CarSettings.LENGTH_MIN.value()) + CarSettings.LENGTH_MIN.value());
	private double distanceToObstacle = 0; //Double.MAX_VALUE;
	private double frontPosition;
	private double timeStep;
	private double currentVelocity;
	//private Simulatable obstacle;
	private TimeServer time;
	private CarAcceptor road;
	private int carnumber = (int)(Math.random() * 1000);
	private java.awt.Color color = new java.awt.Color((int)Math.ceil(Math.random()*255),(int)Math.ceil(Math.random()*255),(int)Math.ceil(Math.random()*255));
	  	
	public CarImpl(TimeServer time){
		this.time = time;
	}
	
	public CarImpl(double length, double maxVelocity, double brakeDistance, double stopDistance, TimeServer time) {
		this.time = time;
		
		if(maxVelocity >= CarSettings.MAX_VELOCITY_MIN.value() && maxVelocity <= CarSettings.MAX_VELOCITY_MAX.value()){
			this.maxVelocity = maxVelocity;
		} else {
			this.maxVelocity = CarSettings.MAX_VELOCITY_DEFAULT.value();
		}
		
		if(brakeDistance >= CarSettings.BREAK_DISTANCE_MIN.value() && brakeDistance <= CarSettings.BREAK_DISTANCE_MAX.value()){
			this.brakeDistance = brakeDistance;
		} else {
			this.brakeDistance = CarSettings.BREAK_DISTANCE_DEFAULT.value();
		}
		
		if(stopDistance >= CarSettings.STOP_DISTANCE_MIN.value() && stopDistance <= CarSettings.STOP_DISTANCE_MAX.value()){
			this.stopDistance = stopDistance;
		} else {
			this.stopDistance = CarSettings.STOP_DISTANCE_DEFAULT.value();
		}
		
		if(length >= CarSettings.LENGTH_MIN.value() && length <= CarSettings.LENGTH_MAX.value()){
			this.length = length;
		} else {
			this.length = CarSettings.LENGTH_DEFAULT.value();
		}
		
	}

	/* (non-Javadoc)
	 * @see simulation.components.Car#voidDistanceTo()
	 */
	@Override
	public double voidDistanceTo() { return distanceToObstacle; }
	
	/* (non-Javadoc)
	 * @see simulation.components.Car#distanceTo(simulation.components.Simulatable)
	 */
	/*
	@Override
	public void distanceTo(Simulatable obj){
		distanceToObstacle =  obj.position() - frontPosition;
		if(distanceToObstacle <= stopDistance){
			distanceToObstacle = stopDistance;
			frontPosition = obstacle.position() - stopDistance;
		}
		obstacle = obj;
	}*/
	
	/* (non-Javadoc)
	 * @see simulation.components.Car#position()
	 */
	@Override
	public double position(){
		return frontPosition;
	}
	
	/* (non-Javadoc)
	 * @see simulation.components.Car#backPosition()
	 */
	@Override
	public double backPosition() {
	    return frontPosition-length;
	}
	
	@Override
	public void run() {
		distanceToObstacle = road.distanceToObstacle(frontPosition);
		//distanceTo(obstacle);
		currentVelocity = (maxVelocity / (brakeDistance - stopDistance))
				* (distanceToObstacle - stopDistance);
		currentVelocity = Math.abs(currentVelocity);//added this because it was negative
		currentVelocity = Math.max(0.0, currentVelocity);
		currentVelocity = Math.min(maxVelocity, currentVelocity);
		
		//double nextFrontPosition = frontPosition + velocity * time;
		if(distanceToObstacle == 0)
			frontPosition = frontPosition + currentVelocity * SimulationSettings.timeStep();
		else if((frontPosition + distanceToObstacle) < (frontPosition + currentVelocity))
			frontPosition = frontPosition + distanceToObstacle * SimulationSettings.timeStep();
		else
			frontPosition = frontPosition + currentVelocity * SimulationSettings.timeStep();
		
		System.out.println(this);
		//did the road accept the car; this will alway be true is this correct?
		if(road.accept(this, frontPosition)){
			//if the current road is not an instance of CarSink then 
			//continue moving the car
			if(!(road instanceof CarSink))				
				time.enqueue(SimulationSettings.timeStep() + time.currentTime(), this);
		}
		//return nextFrontPosition;
	}
	
	@Override
	public String toString() {
		return "Car-" + carnumber + ": current time=" + time.currentTime() + ", current position=" + frontPosition
				//+ ", current road=" + ((road != null)? road.name() : null)
				+ ", current road=[" + road + "]"
				+ ", obstacle=[]\n"
				+ ", distance to obstacle =" + distanceToObstacle
				+ ", current velocity=" + currentVelocity
				+ ", stop distance=" + stopDistance + ", brake distance=" + brakeDistance
				+ ", max velocity=" + maxVelocity + ", car length=" + length;
	}

	/* (non-Javadoc)
	 * @see simulation.components.Car#getMaxVelocity()
	 */
	@Override
	public double getMaxVelocity() {
		return maxVelocity;
	}

	/* (non-Javadoc)
	 * @see simulation.components.Car#getBrakeDistance()
	 */
	@Override
	public double getBrakeDistance() {
		return brakeDistance;
	}

	/* (non-Javadoc)
	 * @see simulation.components.Car#getStopDistance()
	 */
	@Override
	public double getStopDistance() {
		return stopDistance;
	}

	/* (non-Javadoc)
	 * @see simulation.components.Car#getLength()
	 */
	@Override
	public double getLength() {
		return length;
	}

	/* (non-Javadoc)
	 * @see simulation.components.Car#getFrontPosition()
	 */
	@Override
	public double getFrontPosition() {
		return frontPosition;
	}	
	
	/* (non-Javadoc)
	 * @see simulation.components.Car#getDistanceToObstacle()
	 */
	@Override
	public double getDistanceToObstacle() {
		return distanceToObstacle;
	}	
	
	/* (non-Javadoc)
	 * @see simulation.components.Car#setCurrentRoad(simulation.components.Road)
	 */
	@Override
	public void setCurrentRoad(CarAcceptor r){
		road = r;
	}
	
	@Override
	public CarAcceptor getCurrentRoad(){
		return road;
	}
	
	/* (non-Javadoc)
	 * @see simulation.components.Car#setFrontPosition(double)
	 */
	@Override
	public void setFrontPosition(double frontPosition){
		this.frontPosition = frontPosition;
	}
	
	public java.awt.Color getColor() {
	    return color;
	  }
}
