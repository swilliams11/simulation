package simulation.components.light;

import java.util.ArrayList;
import java.util.List;

import simulation.agent.Agent;

public class LightController implements Agent {
	private List<TrafficLight> lights = new ArrayList<>();
	
	public void run(){
		
	}
	
	public void addLight(TrafficLight light){
		lights.add(light);
	}
}
