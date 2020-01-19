import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FCFS {

    private ArrayList<Process> fcfsProcessess;
    public ArrayList<Process> finishedQueue;
    public ArrayList<Integer> diagram;
    public int time;

    FCFS (ArrayList<Process> fcfsPr) {
        ArrayList<Process> finishedQueue = new ArrayList<>();
        this.finishedQueue = finishedQueue;
        ArrayList<Integer> diagram = new ArrayList<>();
        this.diagram = diagram;
        this.time = 0;
        this.fcfsProcessess = fcfsPr;
    }

    public void algorithm() {
        ArrayList<Process> holdQueue = new ArrayList<>();
        ArrayList<Process> activeQueue = new ArrayList<>();
        int finishedCheck = 0;
        while(true) {

            if(activeQueue.size() > 0) {
                activeQueue.get(0).decreaseRemainTime();
                if(activeQueue.get(0).remainTime == 0) {
                    activeQueue.get(0).status = 3;
                    activeQueue.get(0).setExitTime(this.time);
                    this.finishedQueue.add(activeQueue.get(0));
                    activeQueue.remove(0);
                }
            }

            if (this.finishedQueue.size() == this.fcfsProcessess.size()) {
                break;
            }

            for(int i = 0; i < this.fcfsProcessess.size(); i++) {
                if(this.fcfsProcessess.get(i).enterTime == this.time) {
                    this.fcfsProcessess.get(i).status = 1;
                    holdQueue.add(this.fcfsProcessess.get(i));
                }
            }  

            if(holdQueue.size() > 1) {
                Collections.sort(holdQueue, new Comparator<Process>()
                {
                    public int compare(Process p1, Process p2)
                        {
                            return Integer.valueOf(p1.enterTime).compareTo(p2.enterTime);
                        }
                });
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

        Collections.sort(this.finishedQueue, new Comparator<Process>()
        {
            public int compare(Process p1, Process p2)
                {
                    return Integer.valueOf(p1.prTag).compareTo(p2.prTag);
                }
        });
    }
}