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
        // Tạo một bản sao của processList để thực thi mà không làm thay đổi các đối tượng gốc
        List<Process> processesCopy = new ArrayList<>();
        for (Process p : processList) {
            Process copy = new Process(
                    p.getProcessId(),
                    p.getArrivalTime(),
                    p.getBurstTime(),
                    p.getPriority()
            );
            processesCopy.add(copy);
        }

        // Sắp xếp danh sách theo thời điểm đến
        processesCopy.sort(Comparator.comparingDouble(Process::getArrivalTime));

        // Hàng đợi sẵn sàng
        Queue<Process> readyQueue = new LinkedList<>();
        double currentTime = 0.0;
        int index = 0; // Dùng để lấy tiếp những process sẽ đến trong tương lai
        List<Process> completed = new ArrayList<>();

        while (true) {
            // Đưa tất cả tiến trình đến trước hoặc bằng currentTime vào hàng đợi
            while (index < processesCopy.size() && processesCopy.get(index).getArrivalTime() <= currentTime) {
                readyQueue.add(processesCopy.get(index));
                index++;
            }

            // Nếu hàng đợi rỗng nhưng vẫn còn tiến trình, nhảy thời gian đến arrival của tiến trình kế tiếp
            if (readyQueue.isEmpty()) {
                if (index < processesCopy.size()) {
                    currentTime = processesCopy.get(index).getArrivalTime();
                    continue;
                } else {
                    // Không còn tiến trình nào trong danh sách
                    break;
                }
            }

            // Lấy tiến trình đầu tiên khỏi hàng đợi
            Process process = readyQueue.poll();

            // Tính toán xem sẽ chạy tiến trình trong thời gian timeSlice = min(quantum, remainingTime)
            double timeSlice = Math.min(quantum, process.getRemainingTime());
            double startTime = currentTime;
            double endTime = currentTime + timeSlice;

            // Ghi nhận vào Gantt Chart
            ganttChart.add(new GanttEntry(
                    process.getProcessId(),
                    startTime,
                    endTime,
                    process.getWaitingTime() // Tại đây có thể biểu diễn waitingTime tạm thời
            ));

            // Cập nhật thời gian hiện tại và remaining time
            currentTime = endTime;
            process.setRemainingTime(process.getRemainingTime() - timeSlice);

            // Nếu tiến trình còn thời gian, cho nó quay lại hàng đợi
            if (process.getRemainingTime() > 0) {
                // Trong thời gian process chạy, có thể có process mới đến
                // Ta cần thêm những process mới đó vào readyQueue
                while (index < processesCopy.size()
                        && processesCopy.get(index).getArrivalTime() <= currentTime) {
                    readyQueue.add(processesCopy.get(index));
                    index++;
                }
                // Thêm tiến trình chưa hoàn thành này về cuối hàng đợi
                readyQueue.add(process);
            } else {
                // Nếu tiến trình đã hoàn thành
                double finishTime = currentTime;
                double arrival = process.getArrivalTime();
                double burst = process.getBurstTime();
                double waitingTime = finishTime - arrival - burst;
                double turnaroundTime = finishTime - arrival;

                process.setWaitingTime(waitingTime);
                process.setTurnaroundTime(turnaroundTime);
                completed.add(process);
            }

            // Nếu không còn tiến trình mới và hàng đợi cũng rỗng, dừng
            if (index >= processesCopy.size() && readyQueue.isEmpty()) {
                break;
            }
        }

        // Cập nhật kết quả tính toán (Waiting Time, Turnaround Time) cho danh sách process gốc
        for (Process comp : completed) {
            for (Process original : processList) {
                if (original.getProcessId() == comp.getProcessId()) {
                    original.setWaitingTime(comp.getWaitingTime());
                    original.setTurnaroundTime(comp.getTurnaroundTime());
                    break;
                }
            }
        }

        isSimulated = true; // Đánh dấu đã mô phỏng xong
    }
}
