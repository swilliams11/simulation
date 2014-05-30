package simulation.components.road;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import simulation.agent.TimeServer;
import simulation.components.car.Car;
import simulation.components.light.TrafficDirection;

public class Road implements CarAcceptor {

	private List<Car> cars;
	private CarAcceptor nextRoad;
	private double roadLength; // meters
	private String name;
	private TrafficDirection direction;

	TimeServer time;

	public Road(TimeServer time, CarAcceptor next, String name) {
		roadLength = 100;
		this.time = time;
		cars = new LinkedList<>();
		nextRoad = next;
		this.name = name;
	}

	public Road(TimeServer time, CarAcceptor next, String name, double length) {
		this(time, next, name);
		this.roadLength = length;
	}

	public double distanceToObstacle(double fromPosition) {
		double obstaclePosition = this.distanceToCarBack(fromPosition);
		if (obstaclePosition == Double.POSITIVE_INFINITY) {
			double distanceToEnd = this.roadLength - fromPosition;
			obstaclePosition = nextRoad.distanceToObstacle(fromPosition
					- this.roadLength);
			// if(obstaclePosition == 0)
			// obstaclePosition = distanceToEnd;
		}
		if (nextRoad instanceof CarSink)
			return Math.abs(obstaclePosition - fromPosition);
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

	/*
	 * Accept the car if the front of car is greater than the current road
	 * length. If the (non-Javadoc)
	 * 
	 * @see simulation.components.CarAcceptor#accept(simulation.components.Car,
	 * double)
	 */
	@Override
	public boolean accept(Car c, double carFrontPosition) {
		if (cars.contains(c))
			cars.remove(c);
		if (carFrontPosition > roadLength) {
			if (nextRoad.accept(c, carFrontPosition - roadLength)) {
				c.setCurrentRoad(nextRoad);
				return true;
			} else {
				cars.add(c);
				c.setFrontPosition(roadLength);
				return false;
				// c.setCurrentRoad(nextRoad);
				// return nextRoad.accept(c, carFrontPosition - roadLength);
			}
		} else {
			c.setCurrentRoad(this);
			c.setFrontPosition(carFrontPosition);
			cars.add(c);
			return true;
		}
	}

	public void setNext(CarAcceptor next) {
		this.nextRoad = next;
	}

	public CarAcceptor nextRoad() {
		return this.nextRoad;
	}

	public String name() {
		return name;
	}

	@Override
	public int count() {
		return cars.size();
	}

	@Override
	public TrafficDirection getDirection() {
		return direction;
	}

	public void setDirection(TrafficDirection direction) {
		this.direction = direction;
	}

}
