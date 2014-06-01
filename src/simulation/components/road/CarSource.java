package simulation.components.road;

import java.text.DecimalFormat;
import java.util.Random;

import simulation.agent.Agent;
import simulation.agent.TimeServer;
import simulation.components.SimulationSettings;
import simulation.components.car.Car;
import simulation.components.car.CarBuilder;
import simulation.components.car.CarGeneratorDelay;
import simulation.components.car.CarSettings;
import simulation.components.light.TrafficDirection;

public class CarSource implements Agent, CarAcceptor {
	TimeServer time;
	CarBuilder builder;
	CarAcceptor initialRoad;
	DecimalFormat df = new DecimalFormat("#.0");
	DecimalFormat dfSingle = new DecimalFormat("#.0");
	Random random = new Random();
	double multiplier = 10.0;
	double generatorDelay;

	public CarSource(TimeServer time){
		if (time == null)
			throw new IllegalArgumentException("TimeServer arg is null.");
		this.time = time;
		builder = new CarBuilder(time, initialRoad);

		double randomValue = CarGeneratorDelay.DELAY_MIN.value()
				+ (CarGeneratorDelay.DELAY_MAX.value() - CarGeneratorDelay.DELAY_MIN
						.value()) * random.nextDouble();
		generatorDelay = Double.parseDouble(dfSingle.format(randomValue));

	}
	
	public CarSource(TimeServer time, CarAcceptor road) {
		this(time);

		if (road == null)
			throw new IllegalArgumentException("CarAcceptor arg is null.");

		initialRoad = road;
	}

	/*
	 * Enqueue the car and the CarSource.
	 * (non-Javadoc)
	 * @see simulation.agent.Agent#run()
	 */
	@Override
	public void run() {
		time.enqueue(SimulationSettings.timeStep() + time.currentTime(), buildCar());
		time.enqueue(generatorDelay + time.currentTime(), this);
	}

	/*
	 * Build the car with random values. Add it to the road.
	 */
	public Car buildCar() {
		/*Car car = builder.setBrakeDistance(brakeDistance()).setLength(length())
				.setMaxVelocity(maxVelocity()).setStopDistance(stopDistance())
				.setRoad(initialRoad).build();*/
		Car car = builder.setRoad(initialRoad).build();
		initialRoad.accept(car, 0);
		//time.enqueue(0, car);
		return car;
	}

	/*
	 * Return a random number between the min and max brake distance
	 */
	public double brakeDistance() {
		double rand = Double.parseDouble(df.format(Math.random()));
		double dist = CarSettings.BREAK_DISTANCE_MIN.value() + rand;
		if (dist > CarSettings.BREAK_DISTANCE_MAX.value())
			return CarSettings.BREAK_DISTANCE_MAX.value();
		else
			return dist;
	}

	public double stopDistance() {
		double randomValue = CarSettings.STOP_DISTANCE_MIN.value()
				+ (CarSettings.STOP_DISTANCE_MAX.value() - CarSettings.STOP_DISTANCE_MIN
						.value()) * random.nextDouble();
		return Double.parseDouble(dfSingle.format(randomValue));
	}

	public double maxVelocity() {
		double randomValue = CarSettings.MAX_VELOCITY_MIN.value()
				+ (CarSettings.MAX_VELOCITY_MAX.value() - CarSettings.MAX_VELOCITY_MIN
						.value()) * random.nextDouble();
		return Double.parseDouble(dfSingle.format(randomValue));
	}

	public double length() {
		double randomValue = CarSettings.LENGTH_MIN.value()
				+ (CarSettings.LENGTH_MAX.value() - CarSettings.LENGTH_MIN
						.value()) * random.nextDouble();
		return Double.parseDouble(dfSingle.format(randomValue));
	}

	@Override
	public boolean accept(Car c, double frontPosition) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double distanceToObstacle(double fromPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setNext(CarAcceptor next) {
		this.initialRoad = next;		
	}
	
	@Override
	public CarAcceptor getNext(){ return initialRoad;}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TrafficDirection getDirection() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
