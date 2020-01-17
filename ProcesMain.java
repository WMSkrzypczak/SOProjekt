import java.util.Scanner;
import java.util.ArrayList;

public class ProcesMain {

    private ArrayList<Process> processList;
    private int processNumber;

    public static void main (String[] args) {
        ProcesMain Simulation = new ProcesMain();
        Simulation.addProcessEx();
        FCFS fcfs = new FCFS(Simulation.processList);
        fcfs.algorithm();
        Simulation.drawGantt(fcfs.diagram);
    }

    ProcesMain () {
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
}