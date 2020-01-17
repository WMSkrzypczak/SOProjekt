import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SJF {

    private ArrayList<Process> sjfProcessess;
    public ArrayList<Integer> diagram;
    public int time;

    SJF (ArrayList<Process> sjfPr) {
        ArrayList<Integer> diagram = new ArrayList<>();
        this.diagram = diagram;
        this.time=1;
        this.sjfProcessess = sjfPr;
    }

    public void algorithm() {
        ArrayList<Process> holdQueue = new ArrayList<>();
        ArrayList<Process> activeQueue = new ArrayList<>();
        boolean processCheck;
        int finishedCheck = 0;
        int inProcessTime;
        while(true) {
            if(activeQueue.size() > 0) {
                inProcessTime = (this.time - activeQueue.get(0).enterTime) - activeQueue.get(0).waitingTime;
                if(inProcessTime == activeQueue.get(0).executeTime) {
                    activeQueue.get(0).status = 3;
                    activeQueue.remove(0);
                    finishedCheck++;
                }
            }

            if (finishedCheck == this.sjfProcessess.size()) {
                break;
            }

            processCheck = true;

            for(int i = 0; i < this.sjfProcessess.size(); i++) {
                if(this.sjfProcessess.get(i).enterTime == this.time) {
                    this.sjfProcessess.get(i).status = 1;
                    holdQueue.add(this.sjfProcessess.get(i));
                }
            }
            
            for(int i = 0; i < this.sjfProcessess.size(); i++) {
                if(this.sjfProcessess.get(i).status == 2) {
                    processCheck = false;
                }
            }  

            if(processCheck == true) {
                if(holdQueue.size() > 1) {
                    Collections.sort(holdQueue, new Comparator<Process>()
                    {
                        public int compare(Process p1, Process p2)
                            {
                                return Integer.valueOf(p1.executeTime).compareTo(p2.executeTime);
                            }
                    });
                } 
            }

            if((activeQueue.size() == 0)&&(holdQueue.size() > 0)) {
                holdQueue.get(0).status = 2;
                activeQueue.add(holdQueue.get(0));
                holdQueue.remove(0);
            }

            for(int i = 0; i < holdQueue.size(); i++) {
                holdQueue.get(i).increaseWaitingTime();
            }

            if(activeQueue.size() == 0) {
                this.diagram.add(0);
            } else {
                this.diagram.add(activeQueue.get(0).prTag);
            }

            this.time++;
        }
    }
}