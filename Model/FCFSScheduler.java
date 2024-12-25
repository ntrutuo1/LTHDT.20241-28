package Model;

import java.util.List;
import java.util.ArrayList;

/**
 * FCFSScheduler (First-Come-First-Serve) là lớp con của Scheduler.
 * Triển khai thuật toán lập lịch FCFS: tiến trình đến trước sẽ được xử lý trước.
 */
public class FCFSScheduler extends Scheduler {

    /**
     * Constructor khởi tạo FCFSScheduler với danh sách tiến trình đầu vào.
     *
     * @param processList Danh sách tiến trình cần lập lịch.
     */
    public FCFSScheduler(List<Process> processList) {
        super(processList); // Gọi constructor của lớp cha Scheduler.
    }

    /**
     * Tạo Gantt Chart dựa trên thuật toán FCFS.
     * Tiến trình được xếp vào Gantt Chart theo thứ tự đến của chúng.
     *
     * @return Danh sách các GanttEntry, mỗi mục biểu diễn một tiến trình trong Gantt Chart.
     */
    @Override
    public List<GanttEntry> generateGanttChart() {
        List<GanttEntry> gantt = new ArrayList<>(); // Danh sách lưu Gantt chart.
        double now = 0; // Thời gian hiện tại.

        // Duyệt qua từng tiến trình trong danh sách.
        for (Process process : processList) {
            // Nếu thời gian hiện tại nhỏ hơn thời gian đến của tiến trình, cập nhật "now".
            if (now < process.getArrivalTime()) {
                now = process.getArrivalTime();
            }

            // Tạo GanttEntry cho tiến trình.
            GanttEntry entry = new GanttEntry(process.getProcessId(), now, now + process.getBurstTime(), 0);
            
            // Tính toán thời gian chờ cho GanttEntry.
            entry.calculateWaitingTime(process.getArrivalTime());

            // Thêm GanttEntry vào danh sách.
            gantt.add(entry);

            // Cập nhật thời gian hiện tại sau khi tiến trình hoàn thành.
            now += process.getBurstTime();
        }
        return gantt; // Trả về danh sách Gantt Chart.
    }

    /**
     * Tính toán các chỉ số như thời gian chờ và thời gian hoàn thành cho từng tiến trình.
     * - Waiting Time: Thời gian tiến trình chờ từ lúc đến cho tới khi được thực thi.
     * - Turnaround Time: Tổng thời gian từ lúc tiến trình đến cho đến khi hoàn thành.
     */
    @Override
    public void calculateMetrics() {
        double now = 0; // Thời gian hiện tại.

        // Sắp xếp danh sách tiến trình theo thời gian đến (arrival time) tăng dần.
        processList.sort((p1, p2) -> Double.compare(p1.getArrivalTime(), p2.getArrivalTime()));

        // Duyệt qua từng tiến trình trong danh sách.
        for (Process process : processList) {
            // Nếu thời gian hiện tại nhỏ hơn thời gian đến của tiến trình, cập nhật "now".
            if (now < process.getArrivalTime()) {
                now = process.getArrivalTime();
            }

            // Tính thời gian chờ của tiến trình.
            double waitingTime = now - process.getArrivalTime();

            // Tính thời gian hoàn thành của tiến trình (waiting time + burst time).
            double turnaroundTime = waitingTime + process.getBurstTime();

            // Gán các giá trị thời gian chờ và hoàn thành cho tiến trình.
            process.setWaitingTime(waitingTime);
            process.setTurnaroundTime(turnaroundTime);

            // Cập nhật thời gian hiện tại sau khi tiến trình được xử lý xong.
            now += process.getBurstTime();
        }
    }
}
