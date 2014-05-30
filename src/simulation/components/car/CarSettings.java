package simulation.components.car;

public enum CarSettings {
	MAX_VELOCITY_MIN(10),
	MAX_VELOCITY_MAX(30),
	MAX_VELOCITY_DEFAULT(10),
	LENGTH_MIN(5.0),
	LENGTH_MAX(10.0),
	LENGTH_DEFAULT(5.0),
	STOP_DISTANCE_MIN(.05),
	STOP_DISTANCE_MAX(5.0),
	STOP_DISTANCE_DEFAULT(5.0),
	BREAK_DISTANCE_MIN(9.0),
	BREAK_DISTANCE_MAX(10.0),
	BREAK_DISTANCE_DEFAULT(10.0);
	
	private final double value;
	
	CarSettings(double value){
		this.value = value;
	}
	
	public double value(){ return value;}
}
