package lib.ai;

import java.util.LinkedList;

import javax.swing.JPanel;

import lib.employee.Employee;

public abstract class BaseAI extends Thread {

    private static int $velocity = 10;
    
    protected boolean _isRunning = true;

    protected LinkedList<Employee> _employees;

    protected JPanel _graphicsPanel;

    public BaseAI(LinkedList<Employee> employees, JPanel graphicsPanel) { 
        _employees = employees; 
        _graphicsPanel = graphicsPanel;
    }

    public void pauseAI() {
        _isRunning = false;
    }

    public void resumeAI() {
        synchronized(this) {
            _isRunning = true;
            notify();
        }
    }

    @Override
    public void run() {
        while (true) {
            synchronized(this) {
                if (!_isRunning) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else process();
            }
            _graphicsPanel.repaint();
            try {
                Thread.sleep($velocity);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // System.out.println(getName() + " is working");
        }
    }

    abstract void process();

    public static void setV(int speed) { $velocity = speed; }
}
