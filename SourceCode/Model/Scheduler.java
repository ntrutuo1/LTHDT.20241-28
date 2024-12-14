package Model;

import java.util.ArrayList;
import java.util.List;

public abstract class Scheduler {
    protected List<Process> processList;

    public Scheduler(List<Process> processList) {
        this.processList = processList;
    }

    public void addProcess(Process p) {
        this.processList.add(p);
    }

    public List<Process> getProcessList() {
        return processList;
    }

    public abstract void calculateMetrics();

    public abstract List<GanttEntry> generateGanttChart();

    // Static method to create a default process list
    public static List<Process> createDefaultProcessList() {
        List<Process> defaultProcesses = new ArrayList<>();
        defaultProcesses.add(new Process(1, 0, 80, 1));   // Process ID: 1
        defaultProcesses.add(new Process(2, 20, 60, 2));  // Process ID: 2
        defaultProcesses.add(new Process(3, 40, 65, 3));  // Process ID: 3
        defaultProcesses.add(new Process(4, 60, 120, 4)); // Process ID: 4
        defaultProcesses.add(new Process(5, 80, 30, 5));  // Process ID: 5
        defaultProcesses.add(new Process(6, 90, 90, 6));  // Process ID: 6
        defaultProcesses.add(new Process(7, 120, 25, 7)); // Process ID: 7
        defaultProcesses.add(new Process(8, 240, 40, 8)); // Process ID: 8
        defaultProcesses.add(new Process(9, 260, 90, 9)); // Process ID: 9
        defaultProcesses.add(new Process(10, 380, 75, 10)); // Process ID: 10
        return defaultProcesses;
    }
}
