package Model;

/**
 * Lớp Process đại diện cho một tiến trình trong hệ thống lập lịch CPU.
 * Lưu trữ thông tin về tiến trình như thời gian đến, thời gian burst, ưu tiên và các chỉ số khác.
 */
public class Process {
    private double arrivalTime;       // Thời gian tiến trình đến hệ thống.
    private int processId;            // ID của tiến trình.
    private double burstTime;         // Thời gian CPU cần để hoàn thành tiến trình.
    private int priority;          // Mức độ ưu tiên của tiến trình (nếu có).
    private double waitingTime;       // Thời gian tiến trình chờ trước khi được xử lý.
    private double turnaroundTime;    // Tổng thời gian từ khi tiến trình đến đến khi hoàn thành.
    private double remainingTime;     // Thời gian còn lại của tiến trình (dùng cho RR).

    /**
     * Constructor khởi tạo Process với các giá trị cơ bản.
     *
     * @param processId    ID của tiến trình.
     * @param arrivalTime  Thời gian tiến trình đến hệ thống.
     * @param burstTime    Thời gian CPU cần để xử lý tiến trình.
     * @param priority     Độ ưu tiên của tiến trình (dùng cho thuật toán ưu tiên).
     */
    public Process(int processId, double arrivalTime, double burstTime, int priority) {
        this.processId = processId;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.remainingTime = burstTime; // Thời gian còn lại ban đầu bằng thời gian burst.
    }

    /**
     * Trả về thời gian tiến trình đến hệ thống.
     *
     * @return Thời gian đến (arrival time).
     */
    public double getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Thiết lập thời gian tiến trình đến hệ thống.
     *
     * @param arrivalTime Thời gian đến mới của tiến trình.
     */
    public void setArrivalTime(double arrivalTime) {
        this.arrivalTime = arrivalTime;
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
     * Thiết lập ID cho tiến trình.
     *
     * @param processId ID mới cho tiến trình.
     */
    public void setProcessId(int processId) {
        this.processId = processId;
    }

    /**
     * Trả về thời gian burst của tiến trình.
     *
     * @return Thời gian burst.
     */
    public double getBurstTime() {
        return burstTime;
    }

    /**
     * Thiết lập thời gian burst mới cho tiến trình.
     *
     * @param burstTime Thời gian burst mới.
     */
    public void setBurstTime(double burstTime) {
        this.burstTime = burstTime;
    }

    /**
     * Trả về độ ưu tiên của tiến trình.
     *
     * @return Độ ưu tiên.
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Thiết lập độ ưu tiên mới cho tiến trình.
     *
     * @param priority Độ ưu tiên mới.
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * Trả về thời gian chờ của tiến trình.
     *
     * @return Thời gian chờ.
     */
    public double getWaitingTime() {
        return waitingTime;
    }

    /**
     * Thiết lập thời gian chờ cho tiến trình.
     *
     * @param waitingTime Thời gian chờ mới.
     */
    public void setWaitingTime(double waitingTime) {
        this.waitingTime = waitingTime;
    }

    /**
     * Trả về thời gian hoàn thành (turnaround time) của tiến trình.
     *
     * @return Thời gian hoàn thành.
     */
    public double getTurnaroundTime() {
        return turnaroundTime;
    }

    /**
     * Thiết lập thời gian hoàn thành cho tiến trình.
     *
     * @param turnaroundTime Thời gian hoàn thành mới.
     */
    public void setTurnaroundTime(double turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    /**
     * Trả về thời gian còn lại của tiến trình (dùng trong Round Robin).
     *
     * @return Thời gian còn lại.
     */
    public double getRemainingTime() {
        return remainingTime;
    }

    /**
     * Thiết lập thời gian còn lại cho tiến trình.
     *
     * @param remainingTime Thời gian còn lại mới.
     */
    public void setRemainingTime(double remainingTime) {
        this.remainingTime = remainingTime;
    }
}
