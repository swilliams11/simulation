package simulation.components.light;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import simulation.agent.TimeServer;
import simulation.agent.TimeServerLinked;
import simulation.components.SimulationSettings;
import simulation.components.car.Car;
import simulation.components.car.CarBuilder;
import simulation.components.road.CarAcceptor;
import simulation.components.road.CarSink;
import simulation.components.road.RoadBuilder;

public class LightControllerTest {
	TimeServer time = new TimeServerLinked();
	TrafficLight light2 = new TrafficLight(time);
	TrafficLight light1 = new TrafficLight(time);
	LightController lc = new LightController(time, light1, light2);
	/*CarAcceptor road1 = new RoadBuilder()
		.setNextRoad(light2)
		.setRoadLength(20.0)
		.setRoadLength(50)
		.setRoadName("Light2").setTimeServer(time).build();	
	Car c1, c2;*/
	CarSink sink = new CarSink();
	
	
	@Before
	public void setUp() throws Exception {
		light1.setNext(sink);
		/*c1 = new CarBuilder(time, road1).setRoad(road1).build();
		c2 = new CarBuilder(time, road1).build();*/
		light2.setNext(sink);
	}

	@Test
	public void testRun() {
		assertTrue(light1.getGreenTime() >= TrafficLightSettings.GREEN_MIN.value());
		assertTrue(light1.getGreenTime() <= TrafficLightSettings.GREEN_MAX.value());
		assertTrue(light1.getYellowTime() >= TrafficLightSettings.YELLOW_MIN.value());
		assertTrue(light1.getYellowTime() <= TrafficLightSettings.YELLOW_MAX.value());
		
		assertTrue(light2.getGreenTime() >= TrafficLightSettings.GREEN_MIN.value());
		assertTrue(light2.getGreenTime() <= TrafficLightSettings.GREEN_MAX.value());
		assertTrue(light2.getYellowTime() >= TrafficLightSettings.YELLOW_MIN.value());
		assertTrue(light2.getYellowTime() <= TrafficLightSettings.YELLOW_MAX.value());
		
		SimulationSettings.setRunTime(300);
		SimulationSettings.setTimeStep(1);
		
		//time.enqueue(0, carsource);
		time.enqueue(0,lc);
		time.run(300);
		
		//System.out.println(light1.getGreenTime() + " " + light1.getYellowTime());
		//System.out.println(light2.getGreenTime() + " " + light2.getYellowTime());
	}

}
