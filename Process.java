public class Process {
    
    public float status;
    
    public int enterTime;
    public int executeTime;
    public int priority;
    public int prTag;

    public int exitTime;
    public int waitingTime;
    public int maxTime;
    public int remainTime;

    Process(int enTime, int exeTime, int pr, int tag) {
        this.enterTime = enTime;
        this.executeTime = exeTime;
        this.priority = pr;
        this.prTag = tag;

        this.status = 0;
        this.exitTime = 0;
        this.waitingTime = 0;
        this.maxTime = 0;
        this.remainTime = exeTime;
    }

    public void increaseWaitingTime () {
        this.waitingTime++;
    }

    public void decreaseRemainTime () {
        this.remainTime--;
    }

    public void setExitTime (int stop) {
        this.exitTime = stop;
    }

    public int getMaxTime () {
        this.maxTime = this.exitTime - this.enterTime;
        return this.maxTime;
    }
}