import java.util.ArrayList;
import java.util.List;

public class SJN implements Calculator {
	private List<Process> sortedProcesses = new ArrayList<Process>();
	
	public Process findMinBurstTimeAvailable(List<Process> processes, double time) {
		double min = Double.MAX_VALUE;
		Process temp = null;
		for (Process process : processes) {
			if(!process.isCompleted()) {
				if (process.getArrivalTime() <= time) {
					if (min > process.getBurstTime()) {
						min = process.getBurstTime();
						temp = process;
					}
				}
			}
		} 
		return temp;
	}
	
	public void runAlgorithm() {
		double currentTime = 0;
		Process temp = new Process();
		while (findMinBurstTimeAvailable(sortedProcesses, currentTime) != null) {
			temp = findMinBurstTimeAvailable(sortedProcesses, currentTime);
			temp.setCompleted(true);
			currentTime += temp.getBurstTime();
			temp.setCompletionTime(currentTime);
		}
	}	
}
