package simulation.components.light;

import java.util.ArrayList;
import java.util.List;

import simulation.agent.Agent;
import simulation.agent.TimeServer;

public class LightController implements Agent {
	private List<TrafficLight> lights = new ArrayList<>();
	private TimeServer time;

	public LightController(TimeServer time, TrafficLight one, TrafficLight two) {
		this.time = time;
		two.setState(two.getRedState());
		lights.add(one);
		lights.add(two);
	}

	public void run(){
		TrafficLight one = lights.get(0);
		TrafficLight two = lights.get(1);
		System.out.println(time.currentTime());
		//1st light is green
		if(time.currentTime() == 0.0){
			time.enqueue(one.getGreenTime() + time.currentTime(), this);
			System.out.println(one);
			System.out.println(two);
		} else if(one.getCurrentState() == one.getGreenState()){
			one.next();
			time.enqueue(one.getYellowTime() + time.currentTime(), this);			
			System.out.println(one);
			System.out.println(two);
		} else if(one.getCurrentState() == one.getYellowState()){
			//1st light is yellow
			one.next(); //go to red light
			two.next(); //go to green light
			//wake up after two's green light is expired
			time.enqueue(two.getGreenTime() + time.currentTime(), this);
			System.out.println(one);
			System.out.println(two);
		} else if(two.getCurrentState() == two.getGreenState()){
			//if 2nd light is green then go to yellow
			two.next();
			//wake up after yellow time is complete
			time.enqueue(two.getYellowTime() + time.currentTime(), this);
			System.out.println(one);
			System.out.println(two);
		} else if(two.getCurrentState() == two.getYellowState()){
			//if 2nd light is yellow then go to red light
			two.next(); //go to red light
			one.next();//go to green light
			//wake up after 1st light's green time is complete
			time.enqueue(one.getGreenTime() + time.currentTime(), this);
			System.out.println(one);
			System.out.println(two);
		}		
	}
	/*
	 * public void addLight(TrafficLight light){ lights.add(light); }
	 */
	
	public List<TrafficLight> getLights(){
		return lights;
	}
}
