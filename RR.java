import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RR {

    private ArrayList<Process> rrProcessess;
    public ArrayList<Process> finishedQueue;
    public ArrayList<Integer> diagram;
    public int time;
    public int qTime;

    RR (ArrayList<Process> rrPr, int qTime) {
        ArrayList<Process> finishedQueue = new ArrayList<>();
        this.finishedQueue = finishedQueue;
        ArrayList<Integer> diagram = new ArrayList<>();
        this.diagram = diagram;
        this.time = 1;
        this.rrProcessess = rrPr;
        this.qTime = qTime;
    }

    public void algorithm() {
        ArrayList<Process> holdQueue = new ArrayList<>();
        ArrayList<Process> activeQueue = new ArrayList<>();
        int counter = 1;
        while(true) {

            for(int i = 0; i < holdQueue.size(); i++) {
                holdQueue.get(i).increaseWaitingTime();
            }

            if(activeQueue.size() > 0) {
                activeQueue.get(0).decreaseRemainTime();
                if(activeQueue.get(0).remainTime == 0) {
                    activeQueue.get(0).status = 3;
                    activeQueue.get(0).setExitTime(this.time);
                    this.finishedQueue.add(activeQueue.get(0));
                    activeQueue.remove(0);
                }

                if (counter == this.qTime) {
                    activeQueue.get(0).status = 1;
                    holdQueue.add(activeQueue.get(0));
                    activeQueue.remove(0);
                }
            }

            if (this.finishedQueue.size() == this.rrProcessess.size()) {
                break;
            }

            for(int i = 0; i < this.rrProcessess.size(); i++) {
                if(this.rrProcessess.get(i).enterTime == this.time) {
                    this.rrProcessess.get(i).status = 1;
                    holdQueue.add(this.rrProcessess.get(i));
                }
            }

            if(holdQueue.size() > 1) {
                Collections.sort(holdQueue, new Comparator<Process>()
                {
                    public int compare(Process p1, Process p2)
                        {
                            return Integer.valueOf(p1.remainTime).compareTo(p2.remainTime);
                        }
                });
            }
            counter++;
            if((activeQueue.size() == 0)&&(holdQueue.size() > 0)) {
                holdQueue.get(0).status = 2;
                activeQueue.add(holdQueue.get(0));
                counter=1;
                holdQueue.remove(0);
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