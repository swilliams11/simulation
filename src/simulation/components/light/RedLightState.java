package simulation.components.light;

public class RedLightState implements LightState {
	TrafficLight trafficLight;
	public RedLightState(TrafficLight trafficLight){
		this.trafficLight = trafficLight;
	}
	
	@Override
	public void next(){
		trafficLight.setState(trafficLight.getGreenState());
	}
	
	@Override
	public String toString() {
		return "RED";
	}
}
