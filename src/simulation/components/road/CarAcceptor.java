package simulation.components.road;

import simulation.components.car.Car;
import simulation.components.light.TrafficDirection;

public interface CarAcceptor {
	public boolean accept(Car c, double frontPosition);
	public double distanceToObstacle(double fromPosition);
	public String name();
	public void setNext(CarAcceptor next);
	public int count();
	public TrafficDirection getDirection();
}
