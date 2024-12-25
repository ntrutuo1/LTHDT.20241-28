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
                double finishTime = currentTime;
                double arrival = process.getArrivalTime();
                double burst = process.getBurstTime();
                double waitingTime = finishTime - arrival;
                
                ganttChart.add(new GanttEntry(process.getProcessId(), currentTime, currentTime + timeSlice, process.getWaitingTime()));
                currentTime += timeSlice;
                process.setRemainingTime(process.getRemainingTime() - timeSlice);

                // Cập nhật thời gian chờ cho các tiến trình khác trong hàng đợi.
                for (Process p : readyQueue) {
                    p.setWaitingTime(waitingTime);
                }
        
                // Cập nhật Arrival Time của tiến trình.
                if (process.getRemainingTime() > 0) {
                    process.setArrivalTime(currentTime); // Đặt Arrival Time mới.
                    processesCopy.sort(Comparator.comparingDouble(Process::getArrivalTime));

                    for (Process p : processesCopy) {
                        System.out.println(p.getArrivalTime());
                    }
                } else {
                    finishTime = currentTime;
                    arrival = process.getArrivalTime();
                    burst = process.getBurstTime();
                    waitingTime = finishTime - arrival - burst;
                    double turnaroundTime = waitingTime + burst;
        
                    process.setWaitingTime(waitingTime);
                    process.setTurnaroundTime(turnaroundTime);
                    completed.add(process);
                }
        
                if (index >= processesCopy.size() && readyQueue.isEmpty()) {
                    break;
                }
            }
        
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
