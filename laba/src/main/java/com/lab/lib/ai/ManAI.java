package com.lab.lib.ai;

import java.util.LinkedList;

import javax.swing.JPanel;

import com.lab.lib.employee.Employee;
import com.lab.lib.employee.Manager;

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
        for (int i = 0; i < _employees.size(); i++) {
            try {
                Employee emp = _employees.get(i);
                if (emp instanceof Manager) {
                    emp.move(_isRunning);
                }
            } catch (Exception e) {}
        }
    }
}
