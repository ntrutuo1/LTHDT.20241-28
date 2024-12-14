package Model;

import java.util.List;
import java.util.ArrayList;

public class FCFSScheduler extends Scheduler {

    public FCFSScheduler(List<Process> processList) {
        super(processList);
    }

    @Override
    public List<GanttEntry> generateGanttChart() {
        List<GanttEntry> gantt = new ArrayList<>();
        double now = 0;
        for (Process process : processList) {
            if (now < process.getArrivalTime()) {
                now = process.getArrivalTime();
            }
            gantt.add(new GanttEntry(process.getProcessId(), now, now + process.getBurstTime()));
            now += process.getBurstTime();
        }
        return gantt;
    }

    @Override
    public void calculateMetrics() {
        double now = 0;
        // Giả sử processList đã sort theo arrivalTime (hoặc thêm bước sort nếu cần)
        processList.sort((p1, p2) -> Double.compare(p1.getArrivalTime(), p2.getArrivalTime()));
        for (Process process : processList) {
            if (now < process.getArrivalTime()) {
                now = process.getArrivalTime();
            }
            double waitingTime = now - process.getArrivalTime();
            double turnaroundTime = waitingTime + process.getBurstTime();

            process.setWaitingTime(waitingTime);
            process.setTurnaroundTime(turnaroundTime);

            now += process.getBurstTime();
        }
    }
}
