package simulation.components.segment;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import simulation.agent.TimeServer;
import simulation.agent.TimeServerLinked;
import simulation.components.light.TrafficLight;
import simulation.components.road.CarAcceptor;
import simulation.components.road.CarSink;
import simulation.components.road.CarSource;
import simulation.components.road.Road;

public class SimpleBuilderTest {
	TimeServer time = new TimeServerLinked();
	SimpleBuilder simple = new SimpleBuilder(time);
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testBuild() {
		simple.build();
		//default number of roads is 2
		//default number of columns is 3
		//test that it has two east/west roads and 3 north/south roads
		assertEquals(3, simple.numofeastwestlights);
		assertEquals(2, simple.numofnorthsouthlights);
		
		assertEquals(2, simple.numOfEastWestRoads);
		assertEquals(3, simple.numOfNorthSouthRoads);
		
		assertEquals(4, simple.numOfEastWestRoadSeg);
		assertEquals(3, simple.numOfNorthSouthRoadSeg);
		
		assertEquals(2, simple.eastwestroads.size());
		assertEquals(3, simple.northsouthroads.size());
		
		for(CarAcceptor c : simple.eastwestroads)
			assertTrue( simple.eastwestroads.get(0) instanceof CarSource);
		
		for(CarAcceptor c : simple.northsouthroads)
			assertTrue( simple.northsouthroads.get(0) instanceof CarSource);
		
		int counter = simple.numOfEastWestRoadSeg + simple.numofeastwestlights + 2;
		for(CarAcceptor c : simple.eastwestroads)
			for(int i = 0; i < counter; i++ ){
				switch(i){
				case 0:
					assertTrue(simple.eastwestroads.get(0) instanceof CarSource);
					break;
				case 1:
					assertTrue(simple.eastwestroads.get(0).getNext() instanceof Road);
					break;
				case 2:
					assertTrue(simple.eastwestroads.get(0).getNext().getNext() instanceof TrafficLight);
					break;
				case 3:
					assertTrue(simple.eastwestroads.get(0).getNext().getNext().getNext() instanceof Road);
					break;
				case 4:
					assertTrue(simple.eastwestroads.get(0).getNext().getNext().getNext().getNext() 
							instanceof TrafficLight);
					break;
				case 5:
					assertTrue(simple.eastwestroads.get(0).getNext().getNext().getNext().getNext().getNext() 
							instanceof Road);
					break;
				case 6:
					assertTrue(simple.eastwestroads.get(0).getNext().getNext().getNext().getNext().getNext().getNext()
							instanceof TrafficLight);
					break;
				case 7:
					assertTrue(simple.eastwestroads.get(0).getNext().getNext().getNext().getNext().getNext().getNext()
							.getNext()
							instanceof Road);
					break;
				case 8:
					CarAcceptor d = simple.eastwestroads.get(0).getNext().getNext().getNext().getNext().getNext().getNext()
							.getNext().getNext();
					assertTrue(d instanceof CarSink);
					assertTrue(d.getNext() == null);
					
					break;
				}
			}
		
		
		/*for(CarAcceptor c : simple.northsouthroads)			
			assertTrue( simple.northsouthroads.get(0) instanceof CarSource);*/
	}
}
