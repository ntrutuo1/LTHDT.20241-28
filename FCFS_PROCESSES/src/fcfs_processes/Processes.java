package fcfs_processes;

public class Processes {
	double arrTime;
    double prsID;
    double burstTime;
    double cptTime;
    double complete ;
    // Constructor
    public Processes(double prsID, double arrTime, double burstTime) {
        this.prsID = prsID;
        this.arrTime = arrTime;
        this.burstTime = burstTime;
        this.cptTime = 0;
        this.complete = 0;
    }
    
    public double getArrTime() {
		return arrTime;
	}

	public void setArrTime(double arrTime) {
		this.arrTime = arrTime;
	}

	public double getPrsID() {
		return prsID;
	}

	public void setPrsID(double prsID) {
		this.prsID = prsID;
	}

	public double getBurstTime() {
		return burstTime;
	}

	public void setBurstTime(double burstTime) {
		this.burstTime = burstTime;
	}

	public double getCptTime() {
		return cptTime;
	}

	public void setCptTime(double cptTime) {
		this.cptTime = cptTime;
	}

	public double getComplete() {
		return complete;
	}

	public void setComplete(double complete) {
		this.complete = complete;
	}

	public void displayProcessDetails() {
        System.out.println("Process ID: " + prsID);
        System.out.println("Arrival Time: " + arrTime);
        System.out.println("Burst Time: " + burstTime);
        System.out.println("Completion Time: " + cptTime);
        System.out.println("completeE (0=No, 1=Yes): " + complete);
        System.out.println("--------------------");
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
