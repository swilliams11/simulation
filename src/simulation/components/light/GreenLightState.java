package simulation.components.light;

public class GreenLightState implements LightState {
	private TrafficLight trafficLight;
	
	public GreenLightState(TrafficLight state){
		this.trafficLight = state;
	}
	
	@Override
	public void next(){
		trafficLight.setState(trafficLight.getYellowState());
	}

	@Override
	public String toString() {
		return "GREEN";
	}
	
	
}
