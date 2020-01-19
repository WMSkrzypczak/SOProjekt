import java.util.Scanner;
import java.util.ArrayList;
import java.util.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Main {

    private ArrayList<Process> processList;
    private int processNumber;

    public static void main (String[] args) {
        Main Simulation = new Main();
        Simulation.askForStartingData();
        Simulation.askForAlgorithm();
    }

    Main () {
        ArrayList<Process> processess = new ArrayList<>();
        this.processList = processess;
        this.processNumber = 0;
    }

    private void askForStartingData () {
        System.out.println("Jakich danych chcesz użyć? 1/2");
        System.out.println("1: Domyslne");
        System.out.println("2: Wlasne");
        Scanner scan = new Scanner (System.in);
        int choice = scan.next().charAt(0);
        switch(choice){
            case '1':
            addDefaultData();
            break;

            case '2':
            addProcessEx();
            break;

            default:
            System.out.println("Wcisnij 1/2");
            choice = scan.next().charAt(0);
            break;
        }
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
        this.processNumber++;
        System.out.println("Proces numer: " + this.processNumber);
        Scanner scan = new Scanner (System.in);
        System.out.println("Podaj czas wejścia procesu: ");
        int enTime = scan.nextInt();

        System.out.println("Podaj czas wykonania procesu: ");
        int exTime = scan.nextInt();

        System.out.println("Podaj priorytet procesu: ");
        int pr = scan.nextInt();

        int tag = this.processNumber;
        addProcessToList(enTime, exTime, pr, tag);
        boolean choice = askForProcess();
        while(choice == true) {
            this.processNumber++;
            System.out.println("Proces numer: " + this.processNumber);
            System.out.println("Podaj czas wejścia procesu: ");
            enTime = scan.nextInt();

            System.out.println("Podaj czas wykonania procesu: ");
            exTime = scan.nextInt();

            System.out.println("Podaj priorytet procesu: ");
            pr = scan.nextInt();

            tag = this.processNumber;

            addProcessToList(enTime, exTime, pr, tag);
            choice = askForProcess();
        }
    }

    private void addDefaultData () {
        try {
            int enTime;
            int exTime;
            int pr;

            for(int i = 1; i <= 10; i++) {
                enTime = Integer.parseInt(Files.readAllLines(Paths.get("dane.txt")).get((i*4)-3));
                exTime = Integer.parseInt(Files.readAllLines(Paths.get("dane.txt")).get((i*4)-2));
                pr = Integer.parseInt(Files.readAllLines(Paths.get("dane.txt")).get((i*4)-1));
                addProcessToList(enTime, exTime, pr, i);
            }
        } catch (IOException ioe) {
            System.out.println("Unable to read from file.");
        }
    }

    private void askForAlgorithm () {
        System.out.println("Jakiego algorytmu chcesz użyć?");
        System.out.println("1: FCFS");
        System.out.println("2: SJF");
        System.out.println("3. RR");
        System.out.println("4. Priority aging");
        Scanner scan = new Scanner (System.in);
        int choice = scan.next().charAt(0);
        switch(choice) {
            case '1':
            runFCFS (this.processList);
            break;

            case '2':
            runSJF (this.processList);
            break;

            case '3':
            runRR (this.processList);
            break;

            case '4':
            runPA (this.processList);
            break;

            default:
            System.out.println("Wcisnij 1/2/3/4");
            choice = scan.next().charAt(0);
            break;
        }
    }

    private void runFCFS (ArrayList<Process> prList) {
        FCFS fcfs = new FCFS(prList);
        fcfs.algorithm();
        drawGantt(fcfs.diagram);
        finalData(fcfs.finishedQueue);
        printDoc(fcfs.diagram, fcfs.finishedQueue, "fcfs");
    }
    
    private void runSJF (ArrayList<Process> prList) {
        SJF sjf = new SJF(prList);
        sjf.algorithm();
        drawGantt(sjf.diagram);
        finalData(sjf.finishedQueue);
        printDoc(sjf.diagram, sjf.finishedQueue, "sjf");
    }

    private void runRR (ArrayList<Process> prList) {
        System.out.println("Podaj kwant czasu: ");
        Scanner scan = new Scanner (System.in);
        int qTime = scan.nextInt();
        RR rr = new RR(prList, qTime);
        rr.algorithm();
        drawGantt(rr.diagram);
        finalData(rr.finishedQueue);
        printDoc(rr.diagram, rr.finishedQueue, "rr");
    }

    private void runPA (ArrayList<Process> prList) {
        PA pa = new PA(prList);
        pa.algorithm();
        drawGantt(pa.diagram);
        finalData(pa.finishedQueue);
        printDoc(pa.diagram, pa.finishedQueue, "pa");
    }

    private void finalData (ArrayList<Process> prList) {
        double allWaitingTime = 0;
        double allMaxTime = 0;
        double amountOfProcessess = prList.size();

        for(int i = 0; i < prList.size(); i++) {
            allWaitingTime = allWaitingTime + prList.get(i).waitingTime;
            allMaxTime = allMaxTime + prList.get(i).getMaxTime();
        }

        double avgWaitingTime = allWaitingTime/amountOfProcessess;
        double avgMaxTime = allMaxTime/amountOfProcessess;

        System.out.println("Sredni czas oczekiwania: " + avgWaitingTime);
        System.out.println("Sredni czas cyklu przetwarzania: " + avgMaxTime);
        System.out.println(" ");

        for(int i = 0; i < prList.size(); i++) {
            System.out.println("--------");
            System.out.println("Proces nr: " + prList.get(i).prTag);
            System.out.println(" ");
            System.out.println("Czas wejscia: " + prList.get(i).enterTime);
            System.out.println("Czas wykonania: " + prList.get(i).executeTime);
            System.out.println("Priorytet: " + prList.get(i).priority);
            System.out.println(" ");
            System.out.println("Czas wyjscia: " + (prList.get(i).exitTime-1));
            System.out.println("Czas oczekiwania: " + prList.get(i).waitingTime);
            System.out.println("Czas cyklu przetwarzania: " + prList.get(i).getMaxTime());
        }
        System.out.println("--------");
    }

    private void drawGantt (ArrayList<Integer> gantt) {
        System.out.println(" ");
        System.out.println("Diagram Gantta: ");
        for (int i = 0; i < gantt.size(); i++) {
            System.out.print(gantt.get(i) + " ");
        }
        System.out.println(" ");
        System.out.println(" ");
    }

    private void printDoc (ArrayList<Integer> gantt, ArrayList<Process> prList, String type) {
        try (
        FileWriter fw = new FileWriter("./" + type + "Out.txt");
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw)){
            out.println(" ");
            out.println("Diagram Gantta: ");
            for (int i = 0; i < gantt.size(); i++) {
                out.print(gantt.get(i) + " ");
            }   
            out.println(" ");
            out.println(" ");

            double allWaitingTime = 0;
            double allMaxTime = 0;
            double amountOfProcessess = prList.size();

            for(int i = 0; i < prList.size(); i++) {
                allWaitingTime = allWaitingTime + prList.get(i).waitingTime;
                allMaxTime = allMaxTime + prList.get(i).getMaxTime();
            }

            double avgWaitingTime = allWaitingTime/amountOfProcessess;
            double avgMaxTime = allMaxTime/amountOfProcessess;

            out.println("Sredni czas oczekiwania: " + avgWaitingTime);
            out.println("Sredni czas cyklu przetwarzania: " + avgMaxTime);
            out.println(" ");

            for(int i = 0; i < prList.size(); i++) {
                out.println("--------");
                out.println("Proces nr: " + prList.get(i).prTag);
                out.println(" ");
                out.println("Czas wejscia: " + prList.get(i).enterTime);
                out.println("Czas wykonania: " + prList.get(i).executeTime);
                out.println("Priorytet: " + prList.get(i).priority);
                out.println(" ");
                out.println("Czas wyjscia: " + (prList.get(i).exitTime-1));
                out.println("Czas oczekiwania: " + prList.get(i).waitingTime);
                out.println("Czas cyklu przetwarzania: " + prList.get(i).getMaxTime());
            }
            out.println("--------");
        }
        catch (IOException ioe) {
            System.err.println("Doc cannot be created.");
        }
    }
        
}