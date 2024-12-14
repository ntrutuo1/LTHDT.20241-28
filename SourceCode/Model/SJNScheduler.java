package Model;

import java.util.*;

public class SJNScheduler extends Scheduler {
    private List<GanttEntry> ganttChart;
    private boolean isSimulated;

    public SJNScheduler(List<Process> processList) {
        super(processList);
        this.ganttChart = new ArrayList<>();
        this.isSimulated = false;
    }

    @Override
    public void calculateMetrics() {
        if (!isSimulated) {
            runSimulation();
        }
    }

    @Override
    public List<GanttEntry> generateGanttChart() {
        if (!isSimulated) {
            runSimulation();
        }
        return ganttChart;
    }

    private void runSimulation() {
        // Create a copy of processList to avoid modifying the original list
        List<Process> processesCopy = new ArrayList<>();
        for (Process p : processList) {
            Process newP = new Process(p.getProcessId(), p.getArrivalTime(), p.getBurstTime(), p.getPriority());
            processesCopy.add(newP);
        }

        // Sort by arrival time
        processesCopy.sort(Comparator.comparingDouble(Process::getArrivalTime));
        double now = 0;
        List<Process> completed = new ArrayList<>();

        while (!processesCopy.isEmpty()) {
            final double finalNow = now;
            List<Process> available = new ArrayList<>();
            for (Process p : processesCopy) {
                if (p.getArrivalTime() <= finalNow) {
                    available.add(p);
                } else {
                    break;
                }
            }

            if (available.isEmpty()) {
                now++;
                continue;
            }

            Process shortest = available.stream()
                    .min(Comparator.comparingDouble(Process::getBurstTime))
                    .orElse(null);

            if (shortest != null) {
                double start = now;
                double finish = now + shortest.getBurstTime();
                now = finish;

                double waitingTime = start - shortest.getArrivalTime();
                double turnaroundTime = waitingTime + shortest.getBurstTime();

                shortest.setWaitingTime(waitingTime);
                shortest.setTurnaroundTime(turnaroundTime);

                ganttChart.add(new GanttEntry(shortest.getProcessId(), start, finish));

                processesCopy.remove(shortest);
                completed.add(shortest);
            }
        }

        // Update original processList with metrics
        for (Process compP : completed) {
            for (Process originalP : processList) {
                if (originalP.getProcessId() == compP.getProcessId()) {
                    originalP.setWaitingTime(compP.getWaitingTime());
                    originalP.setTurnaroundTime(compP.getTurnaroundTime());
                    break;
                }
            }
        }

        isSimulated = true;
    }
}
