package MODEL;
public class GanttEntry {
private double processId;
//thoi gian bat dau va ket thuc
private double startTime;
private double endTime;
//constructor khoi tao 
public GanttEntry(double processId, double startTime, double endTime) {
	this.processId=processId;
	this.startTime=startTime;
	this.endTime=endTime;
	
}
	public double getProcessId() {
	return processId;
}


public void setProcessId(double processId) {
	this.processId = processId;
}


public double getStartTime() {
	return startTime;
}


public void setStartTime(double startTime) {
	this.startTime = startTime;
}


public double getEndTime() {
	return endTime;
}


public void setEndTime(double endTime) {
	this.endTime = endTime;
}


	public GanttEntry() {
		// TODO Auto-generated constructor stub
	}

}
