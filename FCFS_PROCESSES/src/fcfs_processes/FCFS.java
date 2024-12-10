package fcfs_processes;
import java.util.List;
public class FCFS {
public void runFCFS(List<Processes> processesList) {
double currentTime = 0;
System.out.println("KẾT QUẢ CHẠY THUẬT TOÁN FCFS");
System.out.println("---------------------------------------------");
System.out.printf("%-10s%-15s%-15s%-15s%-15s%-15s\n","Process", "Arrival Time", "Burst Time","Completion Time", "Turnaround Time", "Waiting Time");
for (Processes process : processesList) {
    if (currentTime < process.arrTime) {
	 currentTime = process.arrTime;
	 }
	 process.cptTime = currentTime + process.burstTime; //thoigian hoan thanh
	 double turnaroundTime = process.cptTime - process.arrTime; //time quay vong
	 double waitingTime = turnaroundTime - process.burstTime;  //time cho
     process.complete = 1; //hoan thanh tien trinh
	 currentTime = process.cptTime; //cap nhat thoi gian
	 System.out.printf("%-10.0f%-15.1f%-15.1f%-15.1f%-15.1f%-15.1f\n",process.prsID, process.arrTime, process.burstTime,process.cptTime, turnaroundTime, waitingTime);
}
}
}
