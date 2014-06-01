package simulation.components.light;

public enum IntersectionLength {
	MIN(30.0),
	MAX(180.0);	
	
	private final double value;
	
	IntersectionLength(double value){
		this.value = value;
	}
	
	public double value(){ return value;}
}
