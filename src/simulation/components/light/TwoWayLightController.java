package simulation.components.light;

import java.util.ArrayList;
import java.util.List;

import simulation.agent.Agent;

public class TwoWayLightController implements Agent {
	private List<TrafficLight> lights = new ArrayList<>();
	private String name = "Light" + (int)Math.random() * 1000;
	
	@Override
	public void run() {
		
	}

	
}
