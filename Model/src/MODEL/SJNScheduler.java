package MODEL;
import java.util.List;
import java.util.*;
import java.util.ArrayList;
public class SJNScheduler extends Scheduler {
    @Override
    public void calculateMetrics() {
        //sap xep tien trinh theo thoi gian den 
        processList.sort(Comparator.comparingDouble(Process::getArrivalTime));
        double now = 0; //thoi gian hien tai
        while (!processList.isEmpty()) {
            //tim tien trinh ngan nhat de thuc hien
        	final double finalnow = now;
            Process shortest = processList.stream()
                .filter(p -> p.getArrivalTime() <= finalnow) //dung finalnow trong filter
                .min(Comparator.comparingDouble(Process::getBurstTime))
                .orElse(null);
            // thuc hien tien trinh ngan nhat
            if (shortest != null) {
                processList.remove(shortest);
                shortest.setWaitingTime(now - shortest.getArrivalTime()); //thoi gian cho
                shortest.setTurnaroundTime(shortest.getWaitingTime() + shortest.getBurstTime());//thoi gian quay vong
                now += shortest.getBurstTime();
            } else {
                now++; //khong co tien trinh nao dung thi tang thoi gian len
            }
        }
    }

    @Override
    public List<GanttEntry> generateGanttChart() {
        List<GanttEntry> ganttChart = new ArrayList<>();
        List<Process> sortedProcesses = new ArrayList<>(processList);
        //sap xep tien trinh theo thoi gian den
        sortedProcesses.sort(Comparator.comparingDouble(Process::getArrivalTime));
        double now = 0;
        while (!sortedProcesses.isEmpty()) {
            final double finalnow = now;
            Process shortest = sortedProcesses.stream()
                .filter(p -> p.getArrivalTime() <= finalnow)
                .min(Comparator.comparingDouble(Process::getBurstTime))
                .orElse(null);
            if (shortest != null) {
                sortedProcesses.remove(shortest);
                ganttChart.add(new GanttEntry(shortest.getProcessId(), (int)now, (int)(now + shortest.getBurstTime()))); // Cập nhật bảng gantt (cast sang int nếu cần)
                now += shortest.getBurstTime();
            } else {
                now++;
            }
        }
        return ganttChart;
    }
}
