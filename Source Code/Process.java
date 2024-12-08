//This is an empty class
public class Process {
	private int processId = 0;
	private double arrivalTime = 0;
	private double burstTime = 0;
	private double completionTime = 0;
	private boolean isCompleted = false;
	private int priority = 0;
	
	public int getProcessId() {
		return processId;
	}

	public void setProcessId(int processId) {
		this.processId = processId;
	}

	public double getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(double arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public double getBurstTime() {
		return burstTime;
	}

	public void setBurstTime(double burstTime) {
		this.burstTime = burstTime;
	}

	public double getCompletionTime() {
		return completionTime;
	}

	public void setCompletionTime(double completionTime) {
		this.completionTime = completionTime;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public Process() {
		super();
	}
	
	public void markAsCompleted() {
		
	}
	
	public String getProcessDetails() {
		return null;
	}
}

