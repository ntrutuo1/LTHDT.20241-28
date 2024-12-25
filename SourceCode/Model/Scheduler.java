package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Lớp trừu tượng Scheduler là lớp cơ sở cho các thuật toán lập lịch CPU.
 * Cung cấp các phương thức và thuộc tính chung để quản lý danh sách tiến trình.
 */
public abstract class Scheduler {
    // Danh sách tiến trình được quản lý bởi thuật toán lập lịch.
    protected List<Process> processList;

    /**
     * Constructor khởi tạo Scheduler với danh sách tiến trình.
     *
     * @param processList Danh sách các tiến trình cần lập lịch.
     */
    public Scheduler(List<Process> processList) {
        this.processList = processList;
    }

    /**
     * Thêm một tiến trình mới vào danh sách tiến trình.
     *
     * @param p Tiến trình cần thêm.
     */
    public void addProcess(Process p) {
        this.processList.add(p);
    }

    /**
     * Trả về danh sách các tiến trình hiện tại.
     *
     * @return Danh sách tiến trình.
     */
    public List<Process> getProcessList() {
        return processList;
    }

    /**
     * Phương thức trừu tượng để tính toán các chỉ số của tiến trình như:
     * - Thời gian chờ (waiting time).
     * - Thời gian hoàn thành (turnaround time).
     * Các lớp con phải triển khai phương thức này.
     */
    public abstract void calculateMetrics();

    /**
     * Phương thức trừu tượng để tạo Gantt Chart biểu diễn tiến trình thực thi theo thời gian.
     * Các lớp con phải triển khai phương thức này.
     *
     * @return Danh sách các GanttEntry biểu diễn Gantt Chart.
     */
    public abstract List<GanttEntry> generateGanttChart();

    /**
     * Tạo danh sách tiến trình mặc định cho mục đích mô phỏng.
     * Mỗi tiến trình được định nghĩa với ID, thời gian đến, thời gian burst, và mức độ ưu tiên.
     *
     * @return Danh sách các tiến trình mặc định.
     */
    public static List<Process> createDefaultProcessList() {
        List<Process> defaultProcesses = new ArrayList<>();

        // Thêm các tiến trình mặc định vào danh sách.
        defaultProcesses.add(new Process(1, 0, 80, 1));     // Process 1: Arrive at 0, Burst 80, Priority 1
        defaultProcesses.add(new Process(2, 20, 60, 2));    // Process 2: Arrive at 20, Burst 60, Priority 2
        defaultProcesses.add(new Process(3, 40, 65, 3));    // Process 3: Arrive at 40, Burst 65, Priority 3
        defaultProcesses.add(new Process(4, 60, 120, 4));   // Process 4: Arrive at 60, Burst 120, Priority 4
        defaultProcesses.add(new Process(5, 80, 30, 5));    // Process 5: Arrive at 80, Burst 30, Priority 5
        defaultProcesses.add(new Process(6, 90, 90, 6));    // Process 6: Arrive at 90, Burst 90, Priority 6
        defaultProcesses.add(new Process(7, 120, 25, 7));   // Process 7: Arrive at 120, Burst 25, Priority 7
        defaultProcesses.add(new Process(8, 240, 40, 8));   // Process 8: Arrive at 240, Burst 40, Priority 8
        defaultProcesses.add(new Process(9, 260, 90, 9));   // Process 9: Arrive at 260, Burst 90, Priority 9
        defaultProcesses.add(new Process(10, 380, 75, 10)); // Process 10: Arrive at 380, Burst 75, Priority 10

        return defaultProcesses; // Trả về danh sách tiến trình mặc định.
    }
}
