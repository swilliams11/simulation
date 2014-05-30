package simulation.components.light;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import simulation.agent.TimeServer;
import simulation.agent.TimeServerLinked;
import simulation.components.car.Car;
import simulation.components.car.CarBuilder;
import simulation.components.road.CarAcceptor;
import simulation.components.road.CarSink;
import simulation.components.road.RoadBuilder;

public class TrafficLightTest {
	TimeServer time = new TimeServerLinked();
	TrafficLight light2 = new TrafficLight(time);
	TrafficLight light1; 
	CarAcceptor road1 = new RoadBuilder()
		.setNextRoad(light2)
		.setRoadLength(20.0)
		.setRoadLength(50)
		.setRoadName("Light2").setTimeServer(time).build();
	CarSink sink = new CarSink();
	Car c1, c2;
	
	@Before
	public void setUp() throws Exception {
		light1 = new TrafficLight(time);
		c1 = new CarBuilder(time, road1).setRoad(road1).build();
		c2 = new CarBuilder(time, road1).build();
		light2.setNext(sink);
	}

	//constuctor
	@Test
	public void testTrafficLightTimeServer() {
		try{
			new TrafficLight(null);
		} catch (IllegalArgumentException e){
			assertTrue(true);
		}
		try{
			new TrafficLight(time);
			assertTrue(true);
		} catch (IllegalArgumentException e){
			assertTrue(false);
		}
		//initial state should be green
		assertEquals(light1.getCurrentState(), light1.getGreenState());
		//check the green light time
		for(int i = 0; i < 500; i++ ){
			light1 = new TrafficLight(time);
			if(light1.getGreenTime() >= TrafficLightSettings.GREEN_MIN.value()
				&& light1.getGreenTime() <= TrafficLightSettings.GREEN_MAX.value())
				assertTrue(true);
			else
				assertTrue(false);
			//System.out.println(light1.getGreenTime());
		}
		
		//check the yellow light time
		for(int i = 0; i < 500; i++ ){
			light1 = new TrafficLight(time);
			if(light1.getYellowTime() >= TrafficLightSettings.YELLOW_MIN.value()
				&& light1.getYellowTime() <= TrafficLightSettings.YELLOW_MAX.value())
				assertTrue(true);
			else
				assertTrue(false);
			//System.out.println(light1.getYellowTime());
		}
	}

	/*@Test
	public void testTrafficLightTimeServerDouble() {
		fail("Not yet implemented");
	}*/

	/*@Test
	public void testSetState() {
	}*/

	/*@Test
	public void testGetGreenState() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetYellowState() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRedState() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCurrentState() {
		fail("Not yet implemented");
	}*/

	/*@Test
	public void testToString() {
		
		fail("Not yet implemented");
	}*/

	@Test
	public void testAccept() {	
		//road1 length is 50 meters
		//test distanceTo for one car on the road
		//current state of light should be green
		assertEquals(light2.getCurrentState()
				,light2.getGreenState());
		//road1 
		assertTrue(road1.accept(c2, 55.0)); 
		assertEquals(0.0, road1.count(), 0.0);
		assertEquals(1.0, light2.count(), 0.0);
		//move car2 out of the the traffic light road
		light2.accept(c2, 55);
		assertEquals(0, light2.count(), 0.0);
		
		light2.next(); //change the state to yellow
		assertEquals(light2.getCurrentState()
				,light2.getYellowState());
		assertTrue(road1.accept(c2, 45.0));
		assertEquals(1.0, road1.count(), 0.0);
		assertEquals(0.0, light2.count(), 0.0);
		assertTrue(road1.accept(c2, 55)); //move the car to the traffic light
		assertEquals(0.0, road1.count(), 0.0);
		assertEquals(1.0, light2.count(), 0.0);
		
		light2.accept(c2, 55);
		//make sure the car is not in the intersection
		assertEquals(0, light2.count(), 0.0); 
		
		//check the red light state
		light2.next(); //change the state to yellow
		assertEquals(light2.getCurrentState()
				,light2.getRedState());
		assertTrue(road1.accept(c2, 45.0));
		assertEquals(1.0, road1.count(), 0.0);
		assertEquals(0.0, light2.count(), 0.0);
		assertFalse(road1.accept(c2, 55)); //move the car to the traffic light
		assertEquals(1.0, road1.count(), 0.0);
		assertEquals(0.0, light2.count(), 0.0);
		
		//light2.accept(c2, 55);
		//make sure the car is not in the intersection
		//assertEquals(0, light2.count(), 0.0);
		}

	@Test
	public void testDistanceToObstacle() {
		//TrafficLight road length is 50 meters
		//road1.accept(c1, 55);
		assertEquals(light2.getCurrentState()
				,light2.getGreenState());
		light2.accept(c1, 10);	
		assertEquals(40.0, light2.distanceToObstacle(10.0), 0.0);
		
		//test the the light gives the correct distance when there are two cars on it;
		light2.accept(c2,  40.0);
		light2.accept(c1, 10);
		assertEquals(25.0, light2.distanceToObstacle(10.0), 0.0);
		assertEquals(10.0, light2.distanceToObstacle(40.0), 0.0);
	}

	@Test
	public void testLightTransition() {		
		assertEquals(light1.getCurrentState(), light1.getGreenState());
		light1.next();
		assertEquals(light1.getCurrentState(), light1.getYellowState());
		light1.next();
		assertEquals(light1.getCurrentState(), light1.getRedState());
		light1.next();
		assertEquals(light1.getCurrentState(), light1.getGreenState());
	}

	/*
	@Test
	public void testCount() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDirection() {
		fail("Not yet implemented");
	}
*/
}
