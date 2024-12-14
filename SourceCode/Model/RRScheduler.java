package Model;
import java.util.*;

public class RRScheduler extends Scheduler {
    private double quantum;
    private List<GanttEntry> ganttChart;
    private boolean isSimulated;

    public RRScheduler(List<Process> processList, double quantum) {
        super(processList);
        this.quantum = quantum;
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
        List<Process> processesCopy = new ArrayList<>();
        for (Process p : processList) {
            Process newP = new Process(p.getProcessId(), p.getArrivalTime(), p.getBurstTime(), p.getPriority());
            processesCopy.add(newP);
        }

        processesCopy.sort(Comparator.comparingDouble(Process::getArrivalTime));

        Queue<Process> readyQueue = new LinkedList<>();
        double currentTime = 0;
        int index = 0;
        List<Process> completed = new ArrayList<>();

        while (true) {
            while (index < processesCopy.size() && processesCopy.get(index).getArrivalTime() <= currentTime) {
                readyQueue.add(processesCopy.get(index));
                index++;
            }

            if (readyQueue.isEmpty()) {
                if (index < processesCopy.size()) {
                    currentTime = processesCopy.get(index).getArrivalTime();
                    continue;
                } else {
                    break;
                }
            }

            Process process = readyQueue.poll();
            double timeSlice = Math.min(quantum, process.getRemainingTime());

            ganttChart.add(new GanttEntry(process.getProcessId(), currentTime, currentTime + timeSlice));
            currentTime += timeSlice;
            process.setRemainingTime(process.getRemainingTime() - timeSlice);

            while (index < processesCopy.size() && processesCopy.get(index).getArrivalTime() <= currentTime) {
                readyQueue.add(processesCopy.get(index));
                index++;
            }

            if (process.getRemainingTime() > 0) {
                readyQueue.add(process);
            } else {
                double finishTime = currentTime;
                double arrival = process.getArrivalTime();
                double burst = process.getBurstTime();
                double waitingTime = finishTime - arrival - burst;
                double turnaroundTime = waitingTime + burst;

                process.setWaitingTime(waitingTime);
                process.setTurnaroundTime(turnaroundTime);
                completed.add(process);
            }

            if (index >= processesCopy.size() && readyQueue.isEmpty()) {
                break;
            }
        }

        // Cập nhật dữ liệu vào processList gốc
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
