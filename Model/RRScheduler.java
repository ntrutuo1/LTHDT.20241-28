package Model;

import java.util.ArrayList;
import java.util.List;

public class RRScheduler {

    /**
     * Phương thức tính toán thời gian chờ cho tất cả tiến trình.
     * Sử dụng thuật toán Round Robin để xử lý các tiến trình theo quantum.
     * @param processes Danh sách các tiến trình cần xử lý.
     * @param quantum Thời gian quantum cho mỗi lần thực thi.
     */
    static void calcWaitingTime(List<Process> processes, int quantum) {
        int timePassed = 0;  // Thời gian đã trôi qua
        boolean isSimulated;;  // Cờ để kiểm tra xem tất cả tiến trình đã hoàn thành chưa
        List<GanttEntry> ganttChart = new ArrayList<>();  // Danh sách chứa các mục trong bảng Gantt

        // Vòng lặp tiếp tục cho đến khi tất cả tiến trình hoàn thành
        do {
        	isSimulated = true;  // Mặc định tất cả tiến trình đã hoàn thành

            // Kiểm tra xem tất cả tiến trình còn lại cần được xử lý không
            for (Process process : processes) {
                if (process.getRemainingTime() > 0) {
                	isSimulated = false;  // Nếu có tiến trình chưa hoàn thành, thay đổi cờ done
                    break;
                }
            }

            // Duyệt qua các tiến trình và thực thi chúng theo Round Robin
            for (Process process : processes) {
                if (process.getRemainingTime() > 0) {
                    // Tạo một GanttEntry cho lần thực thi này
                    GanttEntry entry = new GanttEntry();
                    entry.setProcessId(process.getProcessId());  // Lưu ID của tiến trình
                    entry.setStartTime(timePassed);  // Lưu thời gian bắt đầu thực thi

                    // Nếu tiến trình cần thời gian xử lý lớn hơn quantum, cắt bớt và tiếp tục
                    if (process.getRemainingTime() > quantum) {
                        timePassed += quantum;  // Cộng thêm quantum vào thời gian đã trôi qua
                        process.setRemainingTime(process.getRemainingTime() - quantum);  // Cập nhật thời gian còn lại
                    } else {
                        // Nếu tiến trình xử lý xong, cộng phần thời gian còn lại vào tổng thời gian
                        timePassed += process.getRemainingTime();
                        process.setWaitingTime(timePassed - process.getBurstTime());  // Tính toán thời gian chờ của tiến trình
                        process.setRemainingTime(0);  // Đánh dấu tiến trình đã hoàn thành
                    }

                    // Lưu thời gian kết thúc và thời gian chờ của tiến trình trong GanttEntry
                    entry.setEndTime(timePassed);
                    entry.setWaitingTime(entry.getStartTime() - process.getArrivalTime());  // Tính thời gian chờ của tiến trình
                    ganttChart.add(entry);  // Thêm GanttEntry vào bảng Gantt
                }
            }
        } while (!isSimulated);  // Tiếp tục cho đến khi tất cả tiến trình hoàn thành

        // In bảng Gantt và tính toán thời gian trung bình
        printGanttChart(ganttChart);
        calculateAvgTimes(processes);
    }

    /**
     * Phương thức in ra bảng Gantt.
     * Có thể thêm mã ở đây để hiển thị chi tiết bảng Gantt.
     * @param ganttChart Danh sách các mục trong bảng Gantt.
     */
    static void printGanttChart(List<GanttEntry> ganttChart) {
        // In ra bảng Gantt, có thể định dạng hiển thị thêm
        System.out.println("Bảng Gantt:");
        for (GanttEntry entry : ganttChart) {
            System.out.println(entry.toString());  // In thông tin của từng GanttEntry
        }
    }

    /**
     * Phương thức tính toán và in ra thời gian trung bình chờ và quay vòng cho tất cả tiến trình.
     * @param processes Danh sách các tiến trình cần tính toán.
     */
    static void calculateAvgTimes(List<Process> processes) {
        int totalWaitingTime = 0;  // Tổng thời gian chờ của tất cả tiến trình
        int totalTurnaroundTime = 0;  // Tổng thời gian quay vòng của tất cả tiến trình
        int n = processes.size();  // Số lượng tiến trình

        // Tính toán tổng thời gian chờ và quay vòng cho tất cả tiến trình
        for (Process process : processes) {
            totalWaitingTime += process.getWaitingTime();  // Cộng thời gian chờ
            totalTurnaroundTime += (process.getWaitingTime() + process.getBurstTime());  // Cộng thời gian quay vòng
        }

        // Tính và in ra thời gian trung bình
        System.out.println("\nThời gian chờ trung bình = " + (float) totalWaitingTime / n);
        System.out.println("Thời gian quay vòng trung bình = " + (float) totalTurnaroundTime / n);
    }
}
