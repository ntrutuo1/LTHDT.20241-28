package Model;

import java.util.*;

/**
 * Lớp SJNScheduler triển khai thuật toán Shortest Job Next (SJN).
 * SJN chọn tiến trình có thời gian burst ngắn nhất trong các tiến trình đã đến để thực thi trước.
 */
public class SJNScheduler extends Scheduler {
    private List<GanttEntry> ganttChart; // Danh sách Gantt Chart để lưu trữ các khoảng thời gian thực thi tiến trình.
    private boolean isSimulated;         // Cờ đánh dấu xem mô phỏng đã chạy hay chưa.

    /**
     * Constructor khởi tạo SJNScheduler với danh sách tiến trình đầu vào.
     *
     * @param processList Danh sách tiến trình cần lập lịch.
     */
    public SJNScheduler(List<Process> processList) {
        super(processList);         // Gọi constructor của lớp cha Scheduler.
        this.ganttChart = new ArrayList<>(); // Khởi tạo danh sách Gantt Chart.
        this.isSimulated = false;   // Ban đầu chưa thực hiện mô phỏng.
    }

    /**
     * Tính toán các chỉ số như thời gian chờ và thời gian hoàn thành cho tiến trình.
     * Phương thức đảm bảo mô phỏng được chạy trước khi tính toán.
     */
    @Override
    public void calculateMetrics() {
        if (!isSimulated) {
            runSimulation(); // Chạy mô phỏng nếu chưa thực hiện.
        }
    }

    /**
     * Tạo và trả về Gantt Chart biểu diễn tiến trình thực thi theo thuật toán SJN.
     *
     * @return Danh sách GanttEntry biểu diễn Gantt Chart.
     */
    @Override
    public List<GanttEntry> generateGanttChart() {
        if (!isSimulated) {
            runSimulation(); // Chạy mô phỏng nếu chưa thực hiện.
        }
        return ganttChart; // Trả về Gantt Chart đã tạo.
    }

    /**
     * Thực hiện mô phỏng thuật toán SJN:
     * - Chọn tiến trình có thời gian burst ngắn nhất trong số các tiến trình đã đến.
     * - Tính toán thời gian chờ và thời gian hoàn thành.
     * - Lưu lại kết quả vào danh sách Gantt Chart.
     */
    private void runSimulation() {
        // Tạo bản sao của processList để tránh ảnh hưởng đến danh sách gốc.
        List<Process> processesCopy = new ArrayList<>();
        for (Process p : processList) {
            Process newP = new Process(p.getProcessId(), p.getArrivalTime(), p.getBurstTime(), p.getPriority());
            processesCopy.add(newP);
        }

        // Sắp xếp tiến trình theo thời gian đến (arrival time) tăng dần.
        processesCopy.sort(Comparator.comparingDouble(Process::getArrivalTime));
        double now = 0; // Thời gian hiện tại.
        List<Process> completed = new ArrayList<>(); // Danh sách tiến trình đã hoàn thành.

        // Vòng lặp chính để chọn và thực thi tiến trình.
        while (!processesCopy.isEmpty()) {
            final double finalNow = now;

            // Lọc các tiến trình đã đến hệ thống tại thời điểm hiện tại.
            List<Process> available = new ArrayList<>();
            for (Process p : processesCopy) {
                if (p.getArrivalTime() <= finalNow) {
                    available.add(p);
                } else {
                    break; // Dừng khi gặp tiến trình chưa đến.
                }
            }

            // Nếu không có tiến trình nào khả dụng, tăng thời gian hiện tại.
            if (available.isEmpty()) {
                now++;
                continue;
            }

            // Chọn tiến trình có thời gian burst ngắn nhất từ danh sách khả dụng.
            Process shortest = available.stream()
                    .min(Comparator.comparingDouble(Process::getBurstTime)) // Chọn tiến trình có burst time ngắn nhất.
                    .orElse(null);

            if (shortest != null) {
                double start = now;                       // Thời gian bắt đầu thực thi.
                double finish = now + shortest.getBurstTime(); // Thời gian kết thúc thực thi.
                now = finish;                             // Cập nhật thời gian hiện tại.

                // Tính toán thời gian chờ và thời gian hoàn thành.
                double waitingTime = start - shortest.getArrivalTime();
                double turnaroundTime = waitingTime + shortest.getBurstTime();

                // Gán các giá trị thời gian cho tiến trình.
                shortest.setWaitingTime(waitingTime);
                shortest.setTurnaroundTime(turnaroundTime);

                // Thêm tiến trình vào Gantt Chart.
                GanttEntry entry = new GanttEntry(shortest.getProcessId(), start, finish, turnaroundTime);
                entry.calculateWaitingTime(shortest.getArrivalTime());
                ganttChart.add(entry);

                // Xóa tiến trình đã thực thi khỏi danh sách.
                processesCopy.remove(shortest);
                completed.add(shortest); // Thêm vào danh sách tiến trình hoàn thành.
            }
        }

        // Cập nhật thời gian chờ và thời gian hoàn thành vào danh sách tiến trình gốc.
        for (Process compP : completed) {
            for (Process originalP : processList) {
                if (originalP.getProcessId() == compP.getProcessId()) {
                    originalP.setWaitingTime(compP.getWaitingTime());
                    originalP.setTurnaroundTime(compP.getTurnaroundTime());
                    break;
                }
            }
        }

        isSimulated = true; // Đánh dấu mô phỏng đã hoàn thành.
    }
}
