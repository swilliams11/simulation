package simulation.components.road;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import simulation.agent.TimeServer;
import simulation.agent.TimeServerLinked;
import simulation.components.car.CarSettings;

public class CarSourceTest {
	TimeServer time = new TimeServerLinked();
	CarAcceptor carSink = new CarSink();
	RoadBuilder roadBuilder = new RoadBuilder();
	CarAcceptor road1; 
	CarAcceptor road2;
	CarAcceptor road3;
	CarSource carSource;
	CarSource carSourceinit;
	@Before
	public void setUp() throws Exception {
		//c1 = new CarImpl(time);
		//c2 = new CarImpl(time);
		
		road1 = roadBuilder.setRoadLength(200.0)
				.setRoadName("Route69").setTimeServer(time).build();
		/*
		road2 = roadBuilder.setRoadLength(200.0)
				.setRoadName("Route66").setTimeServer(time).build();
		road3 = roadBuilder.setRoadLength(100.0)
				.setRoadName("Route294").setTimeServer(time).build();
				*/
		//road2.setNext(carSink);
		road1.setNext(carSink);
		//road3.setNext(carSink);
		carSourceinit = new CarSource(time, road1);
	}

	@Test
	public void testCarSource() {
		try{
			carSource = new CarSource(null, null);
			assertTrue(false);
		} catch(IllegalArgumentException e){
			assertTrue(true);
		}
	}
	
	@Test
	public void testCarSourceNullTime() {
		try{
			carSource = new CarSource(null, road1);
			assertTrue(false);
		} catch(IllegalArgumentException e){
			assertTrue(true);
		}
	}
	
	@Test
	public void testCarSourceNullRoad() {
		try{
			carSource = new CarSource(time, null);
			assertTrue(false);
		} catch(IllegalArgumentException e){
			assertTrue(true);
		}
	}

	@Test
	public void testBuildCar() {
		assertEquals(road1.count(), 0.0, 0.0);
		carSourceinit.buildCar();
		assertEquals(road1.count(), 1.0, 0.0);
		carSourceinit.buildCar();
		assertEquals(road1.count(), 2.0, 0.0);
	}

	@Test
	public void testBrakeDistance() {
		double value = 0.0;
		
		for(int i = 0; i < 500; i++){
			value = carSourceinit.brakeDistance();
			if(value >= CarSettings.BREAK_DISTANCE_MIN.value() 
				&& value <= CarSettings.BREAK_DISTANCE_MAX.value())
				assertTrue(true);
			else 
				assertTrue(false);
			//System.out.println(value);
		}
	}

	@Test
	public void testStopDistance() {
		double value = 0.0;
		
		for(int i = 0; i < 500; i++){
			value = carSourceinit.stopDistance();
			if(value >= CarSettings.STOP_DISTANCE_MIN.value() 
				&& value <= CarSettings.STOP_DISTANCE_MAX.value())
				assertTrue(true);
			else 
				assertTrue(false);
			//System.out.println(value);
		}
	}

	@Test
	public void testMaxVelocity() {
		double value = 0.0;
		
		for(int i = 0; i < 500; i++){
			value = carSourceinit.maxVelocity();
			if(value >= CarSettings.MAX_VELOCITY_MIN.value() 
				&& value <= CarSettings.MAX_VELOCITY_MAX.value())
				assertTrue(true);
			else 
				assertTrue(false);
			//System.out.println(value);
		}
	}

	@Test
	public void testLength() {
		double value = 0.0;
		
		for(int i = 0; i < 500; i++){
			value = carSourceinit.length();
			if(value >= CarSettings.LENGTH_MIN.value() 
				&& value <= CarSettings.LENGTH_MAX.value())
				assertTrue(true);
			else 
				assertTrue(false);
			//System.out.println(value);
		}
	}

}
