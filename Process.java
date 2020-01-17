public class Process {
    
    public int prTag;
    public float status;
    
    public int enterTime;
    public int executeTime;
    public int priority;

    public int exitTime;
    public int waitingTime;
    public int maxTime;

    Process(int enTime, int exeTime, int pr, int tag) {
        this.enterTime = enTime;
        this.executeTime = exeTime;
        this.priority = pr;
        this.prTag = tag;

        this.status = 0;
        this.exitTime = 0;
        this.waitingTime = 0;
        this.maxTime = 0;
    }

    public void increaseWaitingTime () {
        this.waitingTime++;
    }

    public void increaseMaxTime () {
        this.maxTime++;
    }
    public void setExitTime (int stop) {
        this.exitTime = stop;
    }
}