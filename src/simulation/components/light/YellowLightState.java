package simulation.components.light;

public class YellowLightState implements LightState {
	TrafficLight trafficLight;
	public YellowLightState(TrafficLight trafficLight){
		this.trafficLight = trafficLight;
	}
	
	@Override
	public void next(){
		trafficLight.setState(trafficLight.getRedState());
	}
	
	@Override
	public String toString() {
		return "YELLOW";
	}
}
