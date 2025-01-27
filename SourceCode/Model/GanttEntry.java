package Model;

/**
 * Lớp GanttEntry đại diện cho một mục trong Gantt Chart.
 * Mỗi mục bao gồm ID của tiến trình, thời gian bắt đầu, thời gian kết thúc, và thời gian chờ.
 */
public class GanttEntry {
    private int processId;    // ID của tiến trình.
    private double startTime; // Thời gian bắt đầu thực thi tiến trình.
    private double endTime;   // Thời gian kết thúc thực thi tiến trình.
    private double waitingTime; // Thời gian chờ trước khi tiến trình bắt đầu thực thi.

    /**
     * Constructor không tham số để tạo một đối tượng GanttEntry trống.
     */
    public GanttEntry() {
    }

    /**
     * Constructor khởi tạo một đối tượng GanttEntry với giá trị cụ thể.
     *
     * @param processId   ID của tiến trình.
     * @param startTime   Thời gian bắt đầu thực thi tiến trình.
     * @param endTime     Thời gian kết thúc thực thi tiến trình.
     * @param waitingTime Thời gian chờ trước khi tiến trình bắt đầu thực thi.
     */
    public GanttEntry(int processId, double startTime, double endTime, double waitingTime) {
        this.processId = processId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.waitingTime = waitingTime;    
    }

    /**
     * Trả về ID của tiến trình.
     *
     * @return ID của tiến trình.
     */
    public int getProcessId() {
        return processId;
    }

    /**
     * Thiết lập ID của tiến trình.
     *
     * @param processId ID của tiến trình cần thiết lập.
     */
    public void setProcessId(int processId) {
        this.processId = processId;
    }

    /**
     * Trả về thời gian bắt đầu của tiến trình.
     *
     * @return Thời gian bắt đầu của tiến trình.
     */
    public double getStartTime() {
        return startTime;
    }

    /**
     * Thiết lập thời gian bắt đầu của tiến trình.
     *
     * @param startTime Thời gian bắt đầu cần thiết lập.
     */
    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    /**
     * Trả về thời gian kết thúc của tiến trình.
     *
     * @return Thời gian kết thúc của tiến trình.
     */
    public double getEndTime() {
        return endTime;
    }

    /**
     * Thiết lập thời gian kết thúc của tiến trình.
     *
     * @param endTime Thời gian kết thúc cần thiết lập.
     */
    public void setEndTime(double endTime) {
        this.endTime = endTime;
    }

    /**
     * Trả về thời gian chờ của tiến trình.
     *
     * @return Thời gian chờ của tiến trình.
     */
    public double getWaitingTime() {
        return waitingTime;
    }

    /**
     * Thiết lập thời gian chờ của tiến trình.
     *
     * @param waitingTime Thời gian chờ cần thiết lập.
     */
    public void setWaitingTime(double waitingTime) {
        this.waitingTime = waitingTime;
    }

    /**
     * Tính toán và thiết lập thời gian chờ dựa trên thời gian bắt đầu và thời gian đến.
     *
     * @param arrivalTime Thời gian đến của tiến trình.
     */
    public void calculateWaitingTime(double arrivalTime) {
        this.waitingTime = startTime - arrivalTime;
    }

    @Override
    public String toString() {
        return "GanttEntry{" +
               "processId=" + processId +
               ", startTime=" + startTime +
               ", endTime=" + endTime +
               ", waitingTime=" + waitingTime +
               '}';
    }
    
}
