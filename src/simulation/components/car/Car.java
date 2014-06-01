package simulation.components.car;

import simulation.agent.Agent;
import simulation.components.Simulatable;
import simulation.components.road.CarAcceptor;

public interface Car extends Agent{

	public abstract double voidDistanceTo();

	/**
	 * This should removed.  
	 * Calculate the distance to another object.
	 * @param car
	 */
	//public abstract void distanceTo(Simulatable obj);

	public abstract double position();

	public abstract double backPosition();

	public abstract double getMaxVelocity();

	public abstract double getBrakeDistance();

	public abstract double getStopDistance();

	public abstract double getLength();

	public abstract double getFrontPosition();

	public abstract double getDistanceToObstacle();

	public abstract void setCurrentRoad(CarAcceptor r);

	public abstract void setFrontPosition(double frontPosition);
	
	public abstract CarAcceptor getCurrentRoad();
	
	public abstract java.awt.Color getColor();

}