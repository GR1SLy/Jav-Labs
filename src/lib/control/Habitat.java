package lib.control;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import lib.employee.*;


public class Habitat extends JFrame {
    private int _x, _y;
    private ArrayList<Employee> _employeeArray;
    private JPanel _workingPanel, _timerPanel, _graphicsPanel;
    private ControlPanel _controlPanel;
    JLabel _timerLabel;
    {
        setTitle("SIMULATION");
        setLayout(new BorderLayout());

        _workingPanel = new JPanel();
        _workingPanel.setLayout(new BorderLayout());
        add(_workingPanel, BorderLayout.CENTER);

        _timerPanel = new JPanel();
        _timerPanel.setLayout(new FlowLayout());
        _timerPanel.setBorder(BorderFactory.createTitledBorder("TIMER"));
        _workingPanel.add(_timerPanel, BorderLayout.PAGE_START);

        _timerLabel = new JLabel("Simulation hasn't started yet");
        _timerLabel.setVerticalAlignment(JLabel.TOP);
        _timerPanel.add(_timerLabel);

        _x = _y = 0;
        _employeeArray = new ArrayList<Employee>();

        _graphicsPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Employee employee : _employeeArray) {
                    employee.draw(g);
                }
            }
        };
        _workingPanel.add(_graphicsPanel, BorderLayout.CENTER);

        _controlPanel = new ControlPanel();
        _workingPanel.add(_controlPanel, BorderLayout.PAGE_END);

    }

    /**
     * 
     * @param x - frame width
     * @param y - frame height
     * @param developerGenerateTime
     * @param managerGenerateTime
     * @param developerGenerateChance
     * @param managerGeneratePercent
     */
    public Habitat(final int x, final int y, final int developerGenerateTime, 
    final int managerGenerateTime, final int developerGenerateChance, final int managerGeneratePercent) {
        _x = x;
        _y = y;
        Manager.setGenerateTime(managerGenerateTime);
        Manager.setGeneratePercent(managerGeneratePercent);
        Developer.setGenerateTime(developerGenerateTime);
        Developer.setGenerateChance(developerGenerateChance);
    }

    public void createFrame() {
        setBounds(200, 200, _x, _y);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent event) {
                char keyChar = event.getKeyChar();
                System.out.println("Key pressed: " + keyChar);
                switch (keyChar) {
                    case 'B', 'b', 'И', 'и' -> _controlPanel._startButton.doClick();
                    case 'E', 'e', 'У', 'у' -> _controlPanel._stopButton.doClick();
                    case 'T', 't', 'Е', 'е' -> _controlPanel._timeButton.doClick();
                } 
            }
        });

        _controlPanel.setHabitat(this);
        
        setVisible(true);
    }

    void moveEmployees() {
        for (Employee emp : _employeeArray) {
            emp.move();
        }
    }

    void setTimer(final String timerSeconds) { _timerLabel.setText(timerSeconds); }

    void clear() { _employeeArray.clear(); Employee.clear(); Developer.clear(); Manager.clear(); }

    /**
     * Creates new employees
     * @param time - time from start of simulation
     */
    public void update(final long time) {
        System.out.println("Trying to generate employee:\nCurrent time: " + time);
        
        if (new Developer().generate(time)) {
            _employeeArray.add(new Developer(_x, _y));
            System.out.println("New Developer generated!\nCurrent count: " + Developer.getCount());
        }
        
        if (new Manager().generate(time)) {
            _employeeArray.add(new Manager(_x, _y));
            System.out.println("New Manager generated!\nCurrent count: " + Manager.getCount());
        }
        
        System.out.println("Current employees count: " + Employee.getCount());
        
        _graphicsPanel.repaint();
    }

    static int findUpdateTime(int devTime, int manTime) {
        if(devTime == manTime) return devTime;

        int minTime;
        if (devTime < manTime) minTime = devTime;
        else minTime = manTime;

        for (int i = minTime; i > 0; --i) if (devTime % i == 0 && manTime % i == 0) return i;
        return -1;
    }
    
}
