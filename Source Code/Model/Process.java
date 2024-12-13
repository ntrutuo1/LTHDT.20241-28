package Model;
public class Process {
	private double arrivalTime;//thoi gian den tt
    private double processId;//id tt
    private double burstTime;//thoi gian xu ly tt
    private double priority;//uu tien cua tien trinh 
    private double waitingTime;//thoi gian cho
    private double turnaroundTime;//thoi gian quay vong cua tien trinh
    private double remainingTime;//thoi gian con lai cua tien trinh
 //khoi tao mot tien trinh(constructor)
    public Process(double processId, double arrivalTime, double burstTime, double priority) {
        this.processId = processId;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.remainingTime = burstTime; //toan bo tien trinh van can duoc xu li
    }
    
	public double getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(double arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public double getProcessId() {
		return processId;
	}

	public void setProcessId(double processId) {
		this.processId = processId;
	}

	public double getBurstTime() {
		return burstTime;
	}

	public void setBurstTime(double burstTime) {
		this.burstTime = burstTime;
	}

	public double getPriority() {
		return priority;
	}

	public void setPriority(double priority) {
		this.priority = priority;
	}

	public double getWaitingTime() {
		return waitingTime;
	}

	public void setWaitingTime(double waitingTime) {
		this.waitingTime = waitingTime;
	}

	public double getTurnaroundTime() {
		return turnaroundTime;
	}

	public void setTurnaroundTime(double turnaroundTime) {
		this.turnaroundTime = turnaroundTime;
	}

	public double getRemainingTime() {
		return remainingTime;
	}

	public void setRemainingTime(double remainingTime) {
		this.remainingTime = remainingTime;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
