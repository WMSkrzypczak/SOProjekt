import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FCFS {

    private ArrayList<Process> fcfsProcessess;
    public ArrayList<Integer> diagram;
    public int time;

    FCFS (ArrayList<Process> fcfsPr) {
        ArrayList<Integer> diagram = new ArrayList<>();
        this.diagram = diagram;
        this.time=1;
        this.fcfsProcessess = fcfsPr;
    }

    public void algorithm() {
        ArrayList<Process> holdQueue = new ArrayList<>();
        ArrayList<Process> activeQueue = new ArrayList<>();
        boolean processCheck;
        int finishedCheck = 0;
        int inProcessTime;
        while(true) {
            processCheck = true;
            for(int i = 0; i < this.fcfsProcessess.size(); i++) {
                if(this.fcfsProcessess.get(i).enterTime == this.time) {
                    this.fcfsProcessess.get(i).status = 1;
                    holdQueue.add(this.fcfsProcessess.get(i));
                }
            }
            
            for(int i = 0; i < this.fcfsProcessess.size(); i++) {
                if(this.fcfsProcessess.get(i).status == 2) {
                    processCheck = false;
                }
            }  

            if(processCheck == true) {
                if(holdQueue.size() > 1) {
                    Collections.sort(holdQueue, new Comparator<Process>()
                    {
                        public int compare(Process p1, Process p2)
                            {
                                return Integer.valueOf(p2.enterTime).compareTo(p1.enterTime);
                            }
                    });
                } 
            }

            for(int i = 0; i < holdQueue.size(); i++) {
                holdQueue.get(i).increaseWaitingTime();
            }

            if((activeQueue.size() == 0)&&(holdQueue.size() > 0)) {
                holdQueue.get(0).status = 2;
                activeQueue.add(holdQueue.get(0));
                holdQueue.remove(0);
            }

            if(activeQueue.size() == 0) {
                this.diagram.add(0);
            } else {
                this.diagram.add(activeQueue.get(0).prTag);
            }

            if(activeQueue.size() > 0) {
                inProcessTime = (this.time - activeQueue.get(0).enterTime) - activeQueue.get(0).waitingTime;
                if(inProcessTime == activeQueue.get(0).executeTime) {
                    activeQueue.get(0).status = 3;
                    activeQueue.remove(0);
                    finishedCheck++;
                }
            }

            if (finishedCheck == this.fcfsProcessess.size()) {
                break;
            }
            this.time++;
        }
    }
}