import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    private ArrayList<Process> processList;
    private int processNumber;

    public static void main (String[] args) {
        Main Simulation = new Main();
        Simulation.addProcessEx();
        Simulation.askForAlgorithm();
    }

    Main () {
        ArrayList<Process> processess = new ArrayList<>();
        this.processList = processess;
        this.processNumber = 0;
    }

    private void addProcessToList (int enTime, int exTime, int pr, int tag) {
        processList.add(new Process(enTime, exTime, pr, tag));
    }

    private boolean askForProcess () {
        System.out.println("Czy chcesz wprowadzić nowy proces? y/n");
        Scanner scan = new Scanner (System.in);
        int choice = scan.next().charAt(0);

        while(true){
            switch(choice){
                case 'y':
                return true;

                case 'n':
                return false;

                default:
                System.out.println("Wcisnij y/n");
                choice = scan.next().charAt(0);
                break;
            }
        }
    }

    private void addProcessEx () {
        boolean choice = askForProcess();
        while(choice == true) {
            this.processNumber++;
            Scanner scan = new Scanner (System.in);
            System.out.println("Podaj czas wejścia procesu: ");
            int enTime = scan.nextInt();

            System.out.println("Podaj czas wykonania procesu: ");
            int exTime = scan.nextInt();

            System.out.println("Podaj priorytet procesu: ");
            int pr = scan.nextInt();

            int tag = this.processNumber;
            int i = tag-1;

            addProcessToList(enTime, exTime, pr, tag);
            choice = askForProcess();
        }
    }

    private void drawGantt (ArrayList<Integer> gantt) {
        for (int i = 0; i < gantt.size(); i++) {
            System.out.println(i+1 + "ms " + gantt.get(i));
        }
    }

    private void askForAlgorithm () {
        System.out.println("Jakiego algorytmu chcesz użyć?");
        System.out.println("1: FCFS");
        System.out.println("2: SJF");
        System.out.println("3. RR");
        Scanner scan = new Scanner (System.in);
        int choice = scan.next().charAt(0);
        switch(choice){
            case '1':
            runFCFS (this.processList);
            break;

            case '2':
            runSJF (this.processList);
            break;

            case '3':
            runRR (this.processList);
            break;
            default:
            System.out.println("Wcisnij 1/2/3");
            choice = scan.next().charAt(0);
            break;
        }
    }

    private void runFCFS (ArrayList<Process> prList) {
        FCFS fcfs = new FCFS(prList);
        fcfs.algorithm();
        drawGantt(fcfs.diagram);
    }
    
    private void runSJF (ArrayList<Process> prList) {
        SJF sjf = new SJF(prList);
        sjf.algorithm();
        drawGantt(sjf.diagram);
    }

    private void runRR (ArrayList<Process> prList) {
        System.out.println("Podaj kwant czasu: ");
        Scanner scan = new Scanner (System.in);
        int qTime = scan.nextInt();
        RR rr = new RR(prList, qTime);
        rr.algorithm();
        drawGantt(rr.diagram);
    }
        
}