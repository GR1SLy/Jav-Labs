package lib.ai;

import java.util.LinkedList;

import javax.swing.JPanel;

import lib.employee.Employee;
import lib.employee.Manager;

public class ManAI extends BaseAI {
    
     private static int $count = 0;

    private String _threadName = "ManAI-" + $count;

    public ManAI(LinkedList<Employee> employees, JPanel graphicsPanel) {
        super(employees, graphicsPanel);
        $count++;
        setName(_threadName);
    }

    @Override
    synchronized void process() {
        for (Employee man : _employees) {
            if (man instanceof Manager) {
                man.move(_isRunning);
            }
        }
    }
}
