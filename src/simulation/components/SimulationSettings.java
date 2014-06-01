package simulation.components;

import simulation.agent.TimeServer;
import simulation.components.light.TrafficPattern;

public class SimulationSettings {
	private static double timeStep = .01;
	private static double runTime = 1000.0;
	private static TimeServer time;
	private static TrafficPattern trafficPattern = TrafficPattern.ALTERNATING;
	private static double carGeneratorDelay;
	private static int gridrows = 2;
	private static int gridcols = 3;
	private static double minRoadLength = 200.0;
	private static double maxRoadLength = 500.0;
		
	public SimulationSettings(){}
	
	public static void setTimeStep(double step){ timeStep = step;	}
	
	public static double timeStep() { return timeStep;}
	
	public static void setTrafficPattern(TrafficPattern pattern){
		trafficPattern = pattern;
	}
	
	public static TrafficPattern trafficPattern(){
		return trafficPattern;
	}
	
	public static void setRunTime(double runTime1 ){
		runTime = runTime1;
	}
	
	public static double runTime(){
		return runTime;
	}

	public static double carGeneratorDelay() {
		return carGeneratorDelay;
	}

	public static void setCarGeneratorDelay(double carGeneratorDelay) {
		SimulationSettings.carGeneratorDelay = carGeneratorDelay;
	}

	public static int getGridrows() {
		return gridrows;
	}

	public static void setGridrows(int gridrows) {
		SimulationSettings.gridrows = gridrows;
	}

	public static int getGridcols() {
		return gridcols;
	}

	public static void setGridcols(int gridcols) {
		SimulationSettings.gridcols = gridcols;
	}

	public static double getMinRoadLength() {
		return minRoadLength;
	}

	public static void setMinRoadLength(double minRoadLength) {
		SimulationSettings.minRoadLength = minRoadLength;
	}

	public static double getMaxRoadLength() {
		return maxRoadLength;
	}

	public static void setMaxRoadLength(double maxRoadLength) {
		SimulationSettings.maxRoadLength = maxRoadLength;
	}
	
	
	
	
	
}
