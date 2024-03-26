package lib.control;

import java.util.Scanner;

import lib.employee.Manager;

public class Console extends Thread{

    private Scanner _scanner;

    private Habitat _habitat;

    {
        _scanner = new Scanner(System.in);
    }

    public Console(Habitat hbt) {
        super();
        _habitat = hbt;
    }

    @Override
    public void run() {
        while (true) {
            if (_scanner.hasNext()) {
                String line = _scanner.nextLine();
                checkCommand(line);
            }
        }
    }

    private void checkCommand(String command) {
        switch (command) {
            case "manager rm" -> _habitat.fireManagers();
            case "manager add" -> {
                System.out.print("Count: ");
                try { 
                    int n = Integer.parseInt(_scanner.nextLine());
                    if (n <= 0) System.out.println("Count must be a positive integer");
                    else _habitat.hireManagers(n); 
                }
                catch (NumberFormatException e) { System.out.println("Count must be a positive integer"); }
            }
            case "manager count" -> System.out.println(Manager.getCount());
            case "ai velocity" -> {
                System.out.print("Velocity: ");
                try { 
                    int v = Integer.parseInt(_scanner.nextLine());
                    if (v <= 0) System.out.println("Velocity must be a positive integer");
                    else _habitat.setAIV(v); 
                }
                catch (NumberFormatException e) { System.out.println("Velocity must be a positive integer"); }
            }
            case "ai time" -> {
                System.out.print("Time: ");
                try { 
                    int t = Integer.parseInt(_scanner.nextLine());
                    if (t <= 0) System.out.println("Time must be a positive integer");
                    else _habitat.setAIN(t); 
                }
                catch (NumberFormatException e) { System.out.println("Time must be a positive integer"); }
            }
            case "sim start" -> {
                if (_habitat.isRunning()) System.out.println("Simulation is already running");
                else _habitat._controlPanel._startButton.doClick();
            }
            case "sim stop" -> {
                if (!_habitat.isRunning()) System.out.println("Simulation is already cancelled");
                else _habitat._controlPanel._stopButton.doClick();
            }
            case "serialize" -> _habitat.serialize();
            case "serialize dir" -> Serializer.chooseSaveFile();
            case "deserialize" -> _habitat.deserialize();
            case "deserialize dir" -> Serializer.chooseLoadFile();
            case "cfg dir" -> ConfigOperator.chooseSaveFile();
            case "clear" -> System.out.println("\033[H\033[2J");
            case "help" -> getCommands();
            default -> System.err.println("Unknown command: " + command + "\nType help for more information");
        }
    }

    private void getCommands() {
        System.out.println("Current commands:" + 
                           "\nmanager:" + 
                                "\n\trm - delete all managers" + 
                                "\n\tadd - create N new managers" + 
                                "\n\tcount - get count of managers" +
                           "\nai:" + 
                                "\n\tvelocity - set movement speed for objects" + 
                                "\n\ttime - set time of change movement for developers" + 
                           "\nsim:" + 
                                "\n\tstart - start simulation" + 
                                "\n\tstop - stop simulation" +
                           "\nserialize:" +
                                "\n\t- serialize all objects" +
                                "\n\tdir - set directory for save" +
                           "\ndeserialize" +
                                "\n\t- deserialize all objects" +
                                "\n\tdir - set directory for load" + 
                           "\ncfg dir - set configuration directory" +
                           "\nclear - clear console");
    }
}

/*
 * Commands:
 * manager rm .
 * manager add N
 * manager count
 * ai velocity V
 * ai time T
 * sim start
 * sim stop
 * serialize
 * deserialize
 */
