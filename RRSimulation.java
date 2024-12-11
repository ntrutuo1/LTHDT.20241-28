import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

class Process {
    String pid;
    int arrivalTime;
    int burstTime;
    int remainingTime;
    int completionTime;
    int waitingTime;
    int turnaroundTime;

    public Process(String pid, int arrivalTime, int burstTime) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }
}

class RRScheduler {
    private int quantum;
    private ArrayList<Process> processes;
    private ArrayList<String> executionOrder;
    private int currentTime;

    public RRScheduler(int quantum) {
        this.quantum = quantum;
        this.processes = new ArrayList<>();
        this.executionOrder = new ArrayList<>();
        this.currentTime = 0;
    }

    public void addProcess(String pid, int arrivalTime, int burstTime) {
        processes.add(new Process(pid, arrivalTime, burstTime));
    }

    public void execute() {
        Queue<Process> readyQueue = new LinkedList<>();
        processes.sort((p1, p2) -> Integer.compare(p1.arrivalTime, p2.arrivalTime));

        while (processes.stream().anyMatch(p -> p.remainingTime > 0)) {
            for (Process process : processes) {
                if (process.arrivalTime <= currentTime && process.remainingTime > 0 && !readyQueue.contains(process)) {
                    readyQueue.add(process);
                }
            }

            if (!readyQueue.isEmpty()) {
                Process currentProcess = readyQueue.poll();
                int executionTime = Math.min(currentProcess.remainingTime, quantum);
                executionOrder.add("[Time " + currentTime + "] -> Process " + currentProcess.pid);
                currentTime += executionTime;
                currentProcess.remainingTime -= executionTime;

                if (currentProcess.remainingTime > 0) {
                    readyQueue.add(currentProcess);
                } else {
                    currentProcess.completionTime = currentTime;
                }
            } else {
                currentTime++;
            }
        }

        for (Process process : processes) {
            process.turnaroundTime = process.completionTime - process.arrivalTime;
            process.waitingTime = process.turnaroundTime - process.burstTime;
        }
    }

    public void printMetrics() {
        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;

        System.out.println("Execution Order:");
        for (String step : executionOrder) {
            System.out.println(step);
        }

        for (Process process : processes) {
            totalWaitingTime += process.waitingTime;
            totalTurnaroundTime += process.turnaroundTime;
        }

        System.out.printf("Average Waiting Time: %.2f\n", totalWaitingTime / processes.size());
        System.out.printf("Average Turnaround Time: %.2f\n", totalTurnaroundTime / processes.size());
    }
}

public class RRSimulation {
    public static void main(String[] args) {
        RRScheduler scheduler = new RRScheduler(4);

        scheduler.addProcess("P1", 0, 8);
        scheduler.addProcess("P2", 1, 4);
        scheduler.addProcess("P3", 2, 9);
        scheduler.addProcess("P4", 3, 5);

        scheduler.execute();
        scheduler.printMetrics();
    }
}
