package simulation.components.road;

import java.util.LinkedList;
import java.util.List;

import simulation.components.car.Car;
import simulation.components.light.TrafficDirection;

public class CarSink implements CarAcceptor {

	private List<Car> cars = new LinkedList<>();
	private String name;
	private double roadLength = 0;
	private TrafficDirection direction;
	
	@Override
	public boolean accept(Car c, double frontPosition) {
		cars.remove(c);		
		return cars.add(c);
	}

	@Override
	public double distanceToObstacle(double fromPosition) {
		return 0;
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void setNext(CarAcceptor c){
		throw new UnsupportedOperationException("Car sink cannot have a next road.");
	}

	@Override
	public int count() { return cars.size();	}
	
	@Override
	public TrafficDirection getDirection() { return direction;	}
	
	public void setDirection(TrafficDirection direction) { this.direction = direction;} 
	
	public CarAcceptor getNext(){ return null;}
}
