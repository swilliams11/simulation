package simulation.components.road;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import simulation.agent.TimeServer;
import simulation.agent.TimeServerLinked;
import simulation.components.car.Car;
import simulation.components.car.CarImpl;

public class RoadTest {
	TimeServer time = new TimeServerLinked();
	CarAcceptor carSink = new CarSink();
	RoadBuilder roadBuilder = new RoadBuilder();
	Car c1;
	Car c2;
	CarAcceptor road1; 
	CarAcceptor road2;
	CarAcceptor road3;
	
	@Before
	public void setUp() throws Exception {
		c1 = new CarImpl(time);
		c2 = new CarImpl(time);
		road2 = roadBuilder.setRoadLength(200.0)
				.setRoadName("Route66").setTimeServer(time).build();
		road1 = roadBuilder.setRoadLength(200.0)
				.setRoadName("Route69").setTimeServer(time).build();
		road3 = roadBuilder.setRoadLength(100.0)
				.setRoadName("Route294").setTimeServer(time).build();
		road2.setNext(carSink);
		road1.setNext(road2);
		road3.setNext(carSink);
	}
	

	@Test
	public void testCarOnOneRoad(){
		assertEquals("Route294", road3.name());
		assertTrue(road3.accept(c1, 0));
		assertEquals(0.0, road3.distanceToObstacle(0), 0.0);
		assertTrue(road3.accept(c1, 50.0)); //move car forward 50 meters
		assertEquals(50.0, road3.distanceToObstacle(50.0), 0.0);
	}
	
	
	@Test
	public void testRoadTimeServerRoad() {
		//constructor test	
		assertEquals("Route66", road2.name());
		assertEquals("Route69", road1.name());
	}
		

	@Test
	public void testDistanceToObstacle() {
		//no cars on the road; default length of car is 5m
		//On road segment with another road segment
		//if there are no obstacles on the road then distanceTo()
		//returns the length of the road.
		assertEquals(200.0, road1.distanceToObstacle(0), 0.0);
		road1.accept(c1, 25.0);
		
		//obstacle front is 25, length is 5m, so back position is 20m
		//distance fromPosition is 10 so distanceTo should be 10m.
		assertEquals(10.0, road1.distanceToObstacle(10), 0.0);
		
		road1.accept(c2, 75.0);
		assertEquals(40.0, road1.distanceToObstacle(30.0), 0.0);
	}

	
	@Test
	public void testAccept() {	
		assertTrue(road1.accept(c1, 25));
		assertTrue(road1.accept(c1, 50));
		assertEquals(1, road1.count());
		assertTrue(road1.accept(c2, 5));
		assertEquals(2, road1.count());
		assertTrue(road1.accept(c1, 250));
		assertEquals(1, road1.count());
		assertEquals(1,road2.count());
		assertTrue(road2.accept(c2, 500));//car sink should have the car
		assertEquals(1, carSink.count());
		
		
	}
}
