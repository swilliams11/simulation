package simulation.components.car;

public enum CarGeneratorDelay {
	DELAY_MIN(2),
	DELAY_MAX(25);
	
	private final double value;
	
	CarGeneratorDelay(double value){
		this.value = value;
	}
	
	public double value(){ return value;}
}
