package simulation.components.light;

public enum TrafficLightSettings {
	GREEN_MIN(30.0),
	GREEN_MAX(180.0),
	YELLOW_MIN(4.0),
	YELLOW_MAX(5.0);
	
	private final double value;
	
	TrafficLightSettings(double value){
		this.value = value;
	}
	
	public double value(){ return value;}
}
