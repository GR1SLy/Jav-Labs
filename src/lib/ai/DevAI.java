package lib.ai;

import java.util.LinkedList;

import javax.swing.JPanel;

import lib.employee.Developer;
import lib.employee.Employee;

public class DevAI extends BaseAI {

    private static int $count = 0;

    private String _threadName = "DevAI-" + $count;

    public DevAI(LinkedList<Employee> employees, JPanel graphicsPanel) {
        super(employees, graphicsPanel);
        $count++;
        setName(_threadName);
    }

    @Override
    synchronized void process() {
        for (Employee dev : _employees) {
            if (dev instanceof Developer) {
                dev.move(_isRunning);
            }
        }
    }
    
}
