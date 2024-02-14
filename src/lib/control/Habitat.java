package lib.control;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import lib.employee.*;


public class Habitat extends JFrame {
    private int _x, _y;
    private Vector<Employee> _employeeVector;
    private JPanel _timerPanel;
    private KeyAction _keyAction;
    JLabel _timerLabel;
    boolean _labelHidden;
    {
        setTitle("SIMULATION");
        setLayout(new BorderLayout());

        _timerPanel = new JPanel();
        _timerPanel.setLayout(new FlowLayout());
        _timerPanel.setBorder(BorderFactory.createTitledBorder("TIMER"));
        add(_timerPanel, BorderLayout.PAGE_START);

        _timerLabel = new JLabel("Simulation hasn't started yet");
        _timerLabel.setVerticalAlignment(JLabel.TOP);
        _timerPanel.add(_timerLabel);

        _labelHidden = false;
        _x = _y = 0;
        _employeeVector = new Vector<Employee>();
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
        
        _keyAction = new KeyAction();
        addKeyListener(_keyAction);
        _keyAction.setHabitat(this);
        
        setVisible(true);
    }

    void setTimer(final String timerSeconds) { _timerLabel.setText(timerSeconds); }

    void clear() { _employeeVector.clear(); Employee.clear(); Developer.clear(); Manager.clear(); }

    /**
     * Creates new employees
     * @param time - time from start of simulation
     */
    public void update(final long time) {
        System.out.println("Trying to generate employee:\nCurrent time: " + time);

        if (new Developer().generate(time)) {
            _employeeVector.addElement(new Developer());
            System.out.println("New Developer generated!\nCurrent count: " + Developer.getCount());
        }

        if (new Manager().generate(time)) {
            _employeeVector.addElement(new Manager());
            System.out.println("New Manager generated!\nCurrent count: " + Manager.getCount());
        }

        System.out.println("Current employees count: " + Employee.getCount());
        System.out.println(_employeeVector);
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
