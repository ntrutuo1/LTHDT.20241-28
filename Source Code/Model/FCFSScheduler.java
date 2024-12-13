package Model;
import java.util.List;
import java.util.ArrayList;
public class FCFSScheduler extends Scheduler {
@Override 
public List<GanttEntry> generateGanttChart(){
	List<GanttEntry> gantt = new ArrayList<>();
    double now = 0;
    for (Process process : processList) {
        //neu tien trinh den sau cap nhat thoi gian
        if (now < process.getArrivalTime()) {
            now = process.getArrivalTime();
        }
        //them ganttentry cho tien trinh
        gantt.add(new GanttEntry(process.getProcessId(), now, now + process.getBurstTime()));
        now += process.getBurstTime();
    }
    return gantt;
}
@Override
public void calculateMetrics() {
	//thoi gian ban dau
	double now=0;
	//neu tien trinh khong den luc ban dau thi cap nhat thoi gian ban dau bang thoi gian den 
	for (Process process : processList) {
		if(now < process.getArrivalTime()) {
			now = process.getArrivalTime();
		}
		//tinh thoi gian cho va quay vong 
		 process.setWaitingTime(now - process.getArrivalTime());
         process.setTurnaroundTime(process.getWaitingTime() + process.getBurstTime());
         now += process.getBurstTime(); // Cập nhật thời gian hiện tại sau khi tiến trình hoàn thành
	}
}
	public FCFSScheduler() {
		// TODO Auto-generated constructor stub
	}

}
