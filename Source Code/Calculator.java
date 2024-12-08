import java.util.List;

public interface Calculator {
	private double minArrivalTime(List<Process> processes) {
		double min = Double.MAX_VALUE;
		for (Process process : processes) {
			if (min > process.getArrivalTime()) {
				min = process.getArrivalTime();
			}
		} 
		return min;
	}
	
	private double maxCompletionTime(List<Process> processes) {
		double max = -1;
		for (Process process : processes) {
			if (max < process.getCompletionTime()) {
				max = process.getCompletionTime();
			}
		} 
		return max;
	}
	
	default double calculateWaitingTime(List<Process> processes) {
		double waitingTime = 0;
		for (Process process : processes) {
			waitingTime += (process.getCompletionTime() - process.getArrivalTime() - process.getBurstTime());
		}
		waitingTime /= (double)processes.size();
		return waitingTime;
	}
	
	default double calculateTurnaroundTime(List<Process> processes) {
		double turnaroundTime = 0;
		for (Process process : processes) {
			turnaroundTime += (process.getCompletionTime() - process.getArrivalTime());
		} 
		turnaroundTime /= (double)processes.size();
		return turnaroundTime;
	}
	
	default double calculateutilization(List<Process> processes) {
		double numerator = 0, denominator = maxCompletionTime(processes) - minArrivalTime(processes);
		double utilization = 0;
		for (Process process : processes) {
			numerator += process.getBurstTime();
		}
		utilization = numerator / denominator;
		return utilization;
	}
}
