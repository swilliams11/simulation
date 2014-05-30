package simulation.components;

import simulation.agent.TimeServer;
import simulation.components.light.TrafficPattern;

public class SimulationSettings {
	private static double timeStep = 1.0;
	private static double runTime;
	private static TimeServer time;
	private static TrafficPattern trafficPattern;
	private static double carGeneratorDelay;
	private SimulationSettings(){}
	
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
	
	
	
}
