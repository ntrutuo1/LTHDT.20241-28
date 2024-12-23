package Model;

import java.util.*;

/**
 * Lớp RRScheduler triển khai thuật toán Round Robin (RR) cho lập lịch CPU.
 * Round Robin chia thời gian xử lý thành các khoảng thời gian (quantum) bằng nhau.
 */
public class RRScheduler extends Scheduler {
    private double quantum;                // Thời gian quantum (khoảng thời gian tối đa cho mỗi tiến trình).
    private List<GanttEntry> ganttChart;   // Danh sách Gantt Chart biểu diễn quá trình thực thi tiến trình.
    private boolean isSimulated;           // Cờ đánh dấu xem mô phỏng đã chạy hay chưa.

    /**
     * Constructor khởi tạo RRScheduler với danh sách tiến trình và thời gian quantum.
     *
     * @param processList Danh sách tiến trình cần lập lịch.
     * @param quantum     Thời gian quantum cho thuật toán Round Robin.
     */
    public RRScheduler(List<Process> processList, double quantum) {
        super(processList);          // Gọi constructor của lớp cha Scheduler.
        this.quantum = quantum;      // Gán giá trị quantum.
        this.ganttChart = new ArrayList<>();  // Khởi tạo danh sách Gantt Chart.
        this.isSimulated = false;    // Ban đầu chưa thực hiện mô phỏng.
    }

    /**
     * Tính toán các chỉ số như thời gian chờ và thời gian hoàn thành cho các tiến trình.
     * Hàm này đảm bảo mô phỏng được thực thi trước khi tính các chỉ số.
     */
    @Override
    public void calculateMetrics() {
        if (!isSimulated) {
            runSimulation();  // Chạy mô phỏng nếu chưa được thực hiện.
        }
    }

    /**
     * Tạo và trả về Gantt Chart biểu diễn tiến trình được thực thi trong khoảng thời gian cụ thể.
     *
     * @return Danh sách GanttEntry biểu diễn Gantt Chart.
     */
    @Override
    public List<GanttEntry> generateGanttChart() {
        if (!isSimulated) {
            runSimulation();  // Chạy mô phỏng nếu chưa được thực hiện.
        }
        return ganttChart;  // Trả về danh sách Gantt Chart.
    }

    /**
     * Thực hiện mô phỏng thuật toán Round Robin.
     * - Xử lý các tiến trình theo thời gian quantum.
     * - Quản lý tiến trình trong hàng đợi ready queue.
     * - Tính toán thời gian chờ và thời gian hoàn thành.
     */
    private void runSimulation() {
        // Tạo bản sao danh sách tiến trình để tránh ảnh hưởng đến dữ liệu gốc.
        List<Process> processesCopy = new ArrayList<>();
        for (Process p : processList) {
            Process newP = new Process(p.getProcessId(), p.getArrivalTime(), p.getBurstTime(), p.getPriority());
            processesCopy.add(newP);
        }

        // Sắp xếp tiến trình theo thời gian đến tăng dần.
        processesCopy.sort(Comparator.comparingDouble(Process::getArrivalTime));

        Queue<Process> readyQueue = new LinkedList<>();  // Hàng đợi sẵn sàng.
        double currentTime = 0;  // Thời gian hiện tại.
        int index = 0;           // Chỉ số để duyệt danh sách tiến trình.
        List<Process> completed = new ArrayList<>();  // Danh sách tiến trình đã hoàn thành.

        // Vòng lặp chính của thuật toán Round Robin.
        while (true) {
            // Đưa tiến trình đến vào hàng đợi sẵn sàng.
            while (index < processesCopy.size() && processesCopy.get(index).getArrivalTime() <= currentTime) {
                readyQueue.add(processesCopy.get(index));
                index++;
            }

            // Nếu hàng đợi rỗng nhưng vẫn còn tiến trình, cập nhật thời gian hiện tại.
            if (readyQueue.isEmpty()) {
                if (index < processesCopy.size()) {
                    currentTime = processesCopy.get(index).getArrivalTime();
                    continue;
                } else {
                    break;  // Kết thúc khi không còn tiến trình nào.
                }
            }

            // Lấy tiến trình từ hàng đợi sẵn sàng.
            Process process = readyQueue.poll();

            // Tính thời gian xử lý (time slice).
            double timeSlice = Math.min(quantum, process.getRemainingTime());

            // Thêm tiến trình vào Gantt Chart.
            ganttChart.add(new GanttEntry(process.getProcessId(), currentTime, currentTime + timeSlice, timeSlice));
            currentTime += timeSlice;  // Cập nhật thời gian hiện tại.
            process.setRemainingTime(process.getRemainingTime() - timeSlice);  // Giảm thời gian còn lại.

            // Đưa các tiến trình mới vào hàng đợi nếu thời gian hiện tại cho phép.
            while (index < processesCopy.size() && processesCopy.get(index).getArrivalTime() <= currentTime) {
                readyQueue.add(processesCopy.get(index));
                index++;
            }

            // Nếu tiến trình chưa hoàn thành, thêm lại vào hàng đợi.
            if (process.getRemainingTime() > 0) {
                readyQueue.add(process);
            } else {  
                // Tính toán thời gian chờ và thời gian hoàn thành cho tiến trình hoàn thành.
                double finishTime = currentTime;
                double arrival = process.getArrivalTime();
                double burst = process.getBurstTime();
                double waitingTime = finishTime - arrival - burst;
                double turnaroundTime = waitingTime + burst;

                process.setWaitingTime(waitingTime);
                process.setTurnaroundTime(turnaroundTime);
                completed.add(process);  // Thêm vào danh sách hoàn thành.
            }

            // Kết thúc vòng lặp nếu không còn tiến trình nào trong hàng đợi và danh sách.
            if (index >= processesCopy.size() && readyQueue.isEmpty()) {
                break;
            }
        }

        // Cập nhật thời gian chờ và thời gian hoàn thành vào danh sách gốc.
        for (Process compP : completed) {
            for (Process originalP : processList) {
                if (originalP.getProcessId() == compP.getProcessId()) {
                    originalP.setWaitingTime(compP.getWaitingTime());
                    originalP.setTurnaroundTime(compP.getTurnaroundTime());
                    break;
                }
            }
        }

        isSimulated = true;  // Đánh dấu mô phỏng đã hoàn thành.
    }
}
