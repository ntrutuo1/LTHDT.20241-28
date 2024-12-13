package Model;
import java.util.*;

public class RRScheduler extends Scheduler {
    private double quantum; // Định nghĩa quantum cho thuật toán Round Robin là double

    // Constructor khởi tạo với quantum
    public RRScheduler(double quantum) {
        this.quantum = quantum;
    }

    @Override
    public void calculateMetrics() {
        Queue<Process> readyQueue = new LinkedList<>(processList); // Tạo hàng đợi tiến trình
        double currentTime = 0; // Thời gian hiện tại ban đầu là 0
        while (!readyQueue.isEmpty()) {
            Process process = readyQueue.poll(); // Lấy tiến trình đầu tiên từ hàng đợi
            if (process.getArrivalTime() > currentTime) {
                currentTime = process.getArrivalTime(); // Cập nhật nếu tiến trình đến sau thời gian hiện tại
            }

            double timeSlice = Math.min(quantum, process.getRemainingTime()); // Tính slice thời gian nhỏ nhất giữa quantum và thời gian còn lại
            process.setRemainingTime(process.getRemainingTime() - timeSlice); // Cập nhật thời gian còn lại của tiến trình
            currentTime += timeSlice; // Cập nhật thời gian hiện tại

            if (process.getRemainingTime() > 0) {
                readyQueue.add(process); // Nếu tiến trình chưa hoàn thành, thêm lại vào hàng đợi
            } else {
                process.setWaitingTime(currentTime - process.getArrivalTime() - process.getBurstTime());
                process.setTurnaroundTime(process.getWaitingTime() + process.getBurstTime());
            }
        }
    }

    @Override
    public List<GanttEntry> generateGanttChart() {
        List<GanttEntry> ganttChart = new ArrayList<>();
        Queue<Process> readyQueue = new LinkedList<>(processList); // Tạo hàng đợi tiến trình
        double currentTime = 0; // Thời gian hiện tại ban đầu là 0

        while (!readyQueue.isEmpty()) {
            Process process = readyQueue.poll(); // Lấy tiến trình đầu tiên từ hàng đợi
            if (process.getArrivalTime() > currentTime) {
                currentTime = process.getArrivalTime(); // Cập nhật nếu tiến trình đến sau thời gian hiện tại
            }

            double timeSlice = Math.min(quantum, process.getRemainingTime()); // Tính slice thời gian nhỏ nhất
            ganttChart.add(new GanttEntry(process.getProcessId(), currentTime, currentTime + timeSlice)); // Thêm vào gantt chart
            process.setRemainingTime(process.getRemainingTime() - timeSlice); // Cập nhật thời gian còn lại
            currentTime += timeSlice; // Cập nhật thời gian hiện tại
            if (process.getRemainingTime() > 0) {
                readyQueue.add(process); // Nếu tiến trình chưa hoàn thành, thêm lại vào hàng đợi
            }
        }
        return ganttChart; // Trả về bảng gantt
    }
}
