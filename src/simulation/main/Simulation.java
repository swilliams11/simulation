package simulation.main;

import simulation.agent.Agent;
import simulation.agent.TimeServer;
import simulation.agent.TimeServerLinked;
import simulation.components.Simulatable;
import simulation.components.SimulationSettings;
import simulation.components.car.Car;
import simulation.components.car.CarBuilder;
import simulation.components.car.CarImpl;
import simulation.components.light.TrafficLight;
import simulation.components.road.CarAcceptor;
import simulation.components.road.CarSink;
import simulation.components.road.CarSource;
import simulation.components.road.RoadBuilder;

public class Simulation {

	public static void main(String[] args) {
		TimeServer time = new TimeServerLinked();
		TrafficLight light = new TrafficLight(time);
		RoadBuilder roadBuilder = new RoadBuilder();

		SimulationSettings.setRunTime(50);
		SimulationSettings.setTimeStep(1);

		CarAcceptor sink = new CarSink();
		CarAcceptor road1 = roadBuilder.setNextRoad(sink).setRoadLength(200)
				.setRoadName("Route66").setTimeServer(time).build();
		CarSource carsource = new CarSource(time, road1);
		// Agent a = new CarImpl(0.0, 15.0, 0, 0, time);
		CarBuilder carBuilder = new CarBuilder(time, road1);
		Car car = carBuilder.setBrakeDistance(0).setLength(0).setMaxVelocity(0)
				.setStopDistance(0).setBrakeDistance(0).setRoad(road1).build();
		Car car2 = carBuilder.setBrakeDistance(0).setLength(0).setMaxVelocity(25)
				.setStopDistance(0).setBrakeDistance(0).setRoad(road1).build();
		//time.enqueue(5,car2);
		//time.enqueue(0, car);
		
		time.enqueue(0, carsource);
		// time.enqueue(0,light);
		time.run(50);

	}
}
