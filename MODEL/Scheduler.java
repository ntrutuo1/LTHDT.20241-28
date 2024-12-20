package MODEL;
import java.util.ArrayList;
import java.util.List;
public abstract class Scheduler {
protected ArrayList<Process> processList = new ArrayList<>();
//them tien trinh
public void addProcess(Process a) {
	processList.add(a);
}
//phuong thuc truu tuong tinh toan thong so tien trinh
public abstract void calculateMetrics();
//phuong thuc truu tuong tao bang gantt 
public abstract List<GanttEntry> generateGanttChart();
	public Scheduler() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
