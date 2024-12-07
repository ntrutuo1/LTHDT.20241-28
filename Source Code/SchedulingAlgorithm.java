import java.util.List;

/**
 * Abstract class representing a general scheduling algorithm. 
 * This class defines the structure and common functionalities 
 * required for various CPU scheduling algorithms.
 */
public abstract class SchedulingAlgorithm {
    // Protected so that subclasses can access/modify this if needed
    protected String algorithmName;

    /**
     * Constructor to set the algorithm's name.
     * @param algorithmName The name of the scheduling algorithm.
     */
    public SchedulingAlgorithm(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    /**
     * Runs the scheduling algorithm on the provided list of processes.
     * Each subclass must implement its own logic for how processes 
     * are scheduled (e.g., FCFS, SJN, RR).
     *
     * @param processes The list of processes to schedule.
     */
    public abstract void runAlgorithm(List<Process> processes);

    /**
     * Calculates the average waiting time for the set of processes 
     * after the algorithm has run. The calculation details (e.g., 
     * summing total waiting times and dividing by number of processes) 
     * can be common, but the values depend on when processes complete.
     *
     * @return The calculated average waiting time.
     */
    public abstract double calculateWaitingTime();

    /**
     * Calculates the average turnaround time for the set of processes.
     * Turnaround time is typically completionTime - arrivalTime.
     *
     * @return The calculated average turnaround time.
     */
    public abstract double calculateTurnaroundTime();

    /**
     * Calculates the CPU utilization after the scheduling is performed.
     * CPU utilization might be computed from total busy time 
     * versus the overall time span.
     *
     * @return The CPU utilization as a percentage (0 to 100) 
     *         or a fraction (0 to 1), depending on implementation.
     */
    public abstract double calculateUtilization();

    /**
     * Returns the name of the scheduling algorithm.
     * @return The algorithm name.
     */
    public String getAlgorithmName() {
        return algorithmName;
    }

    /**
     * Optionally sets the name of the algorithm if needed.
     * @param algorithmName The new algorithm name.
     */
    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }
}
