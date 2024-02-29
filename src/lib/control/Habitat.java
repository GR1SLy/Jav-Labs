package lib.control;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeSet;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import lib.employee.*;

public class Habitat extends JFrame {
    private class Pair {
        public Employee emp1, emp2;
        public Pair() { emp1 = null; emp2 = null; }
        public boolean isEmpty() { return emp1 == null && emp2 == null; }
    }
    private int _x, _y, _width, _height;
    private LinkedList<Employee> _employeeList;
    private HashMap<Long, Pair> _employeeBirthTime;
    private TreeSet<Integer> _employeeID;
    private JPanel _cardPanel, _mainPanel, _workingPanel, _timerPanel, _graphicsPanel;
    private MenuPanel _menuPanel;
    private JButton _menuButton;
    private MenuBar _menuBar;
    JButton _backToMenuButton;
    ControlPanel _controlPanel;
    JLabel _timerLabel;
    int _updateTime;

    {
        setTitle("SIMULATION");
        setLayout(new BorderLayout());

        _cardPanel = new JPanel();
        _cardPanel.setLayout(new CardLayout());
        add(_cardPanel, BorderLayout.CENTER);

        _menuPanel = new MenuPanel();
        _cardPanel.add(_menuPanel);

        _menuButton = _menuPanel.getStartButton();
        _menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CardLayout)_cardPanel.getLayout()).next(_cardPanel);
                Developer.setGenerateTime(_menuPanel._generateDevTime);
                Developer.setGenerateChance(_menuPanel._generateDevChance);
                Developer.setLifeTime(_menuPanel._devLifeTime);
                Manager.setGenerateTime(_menuPanel._generateManTime);
                Manager.setGeneratePercent(_menuPanel._generateManPercent);
                Manager.setLifeTime(_menuPanel._manLifeTime);
                _updateTime = findUpdateTime(_menuPanel._generateDevTime, _menuPanel._generateManTime) * 1000;

                System.out.println("DevGenTime: " + Developer.getGenerateTime() + "\tManGenTime: " + Manager.getGenerateTime()
                + "\nDevLifeTime: " + Developer.getLifeTime() + "\tManLifeTime: " + Manager.getLifeTime()
                + "\nFrom menu:"
                + "\nDevGenTime: " + _menuPanel._generateDevTime + "\tManGenTime: " + _menuPanel._generateManTime
                + "\nDevLifeTime: " + _menuPanel._devLifeTime + "\tManLifeTime: " + _menuPanel._manLifeTime
                + "\nGenerate Time: " + _updateTime);
                
                _controlPanel._startButton.doClick();
                requestFocus();
            }
        });
        
        _mainPanel = new JPanel();
        _mainPanel.setLayout(new BorderLayout());
        _cardPanel.add(_mainPanel);

        _backToMenuButton = new JButton("Back to menu");
        _backToMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CardLayout)_cardPanel.getLayout()).previous(_cardPanel);
            } 
        });
        _backToMenuButton.setFocusable(false);
        _mainPanel.add(_backToMenuButton, BorderLayout.PAGE_END);

        _workingPanel = new JPanel();
        _workingPanel.setLayout(new BorderLayout());
        _mainPanel.add(_workingPanel, BorderLayout.CENTER);

        _timerPanel = new JPanel();
        _timerPanel.setLayout(new FlowLayout());
        _timerPanel.setBorder(BorderFactory.createTitledBorder("TIMER"));
        _workingPanel.add(_timerPanel, BorderLayout.PAGE_START);

        _timerLabel = new JLabel("Simulation hasn't started yet");
        _timerLabel.setVerticalAlignment(JLabel.TOP);
        _timerPanel.add(_timerLabel);

        _x = _y = 0;
        _employeeList = new LinkedList<Employee>();

        _graphicsPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Employee employee : _employeeList) {
                    employee.draw(g);
                }
            }
        };
        _workingPanel.add(_graphicsPanel, BorderLayout.CENTER);

        _controlPanel = new ControlPanel();
        _workingPanel.add(_controlPanel, BorderLayout.PAGE_END);

        _employeeID = new TreeSet<>();
        _employeeBirthTime = new HashMap<>();
    }

    /**
     * Default constructor
     * @param width = 500
     * @param height = 500
     * @param x = @param y = 0
     */
    public Habitat() {
        _width = 500;
        _height = 500;
    }

    /**
     * Habitat constructor with bounds
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public Habitat(int x, int y, int width, int height) {
        _x = x;
        _y = y;
        _width = width;
        _height = height;
    }

    /**
     * Creates a new frame with start menu
     */
    public void createFrame() {
        setBounds(_x, _y, _width, _height);
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
        setVisible(true);

        _controlPanel.setHabitat(this);
        _menuBar = new MenuBar(this);
        setJMenuBar(_menuBar);
    }

    void moveEmployees() {
        for (Employee employee : _employeeList) {
            employee.move();
        }
    }

    void setTimer(final String timerSeconds) { _timerLabel.setText(timerSeconds); }

    void clear() { _employeeBirthTime.clear(); _employeeID.clear(); _employeeList.clear(); Employee.clear(); Developer.clear(); Manager.clear(); }

    /**
     * Creates new employees
     * @param time - time from start of simulation
     */
    void update(final long time) {
        System.out.println("Trying to generate employee:\nCurrent time: " + time);
        Pair pair = new Pair();
        
        if (new Developer().generate(time)) {
            Rectangle rect = _graphicsPanel.getBounds();
            _employeeList.addLast(new Developer(rect.width, rect.height, time, _employeeID));
            pair.emp1 = _employeeList.getLast();
            System.out.println("New Developer generated!\nCurrent count: " + Developer.getCount());
        }
        
        if (new Manager().generate(time)) {
            Rectangle rect = _graphicsPanel.getBounds();
            _employeeList.addLast(new Manager(rect.width, rect.height, time, _employeeID));
            pair.emp2 = _employeeList.getLast();
            System.out.println("New Manager generated!\nCurrent count: " + Manager.getCount());
        }

        if (!pair.isEmpty()) _employeeBirthTime.put(time, pair);
        
        System.out.println("Current employees count: " + Employee.getCount() + "\tIDs: " + _employeeID);
        
        _graphicsPanel.repaint();
    }

    /**
     * Terminates employees when lifetime is reached
     * @param time - time from start of simulation
     */
    void terminateCheck(final long time) {
        LinkedList<Employee> terminateArray = new LinkedList<Employee>();
        for (Employee employee : _employeeList) {
            if (employee.terminate(time)) {
                System.out.println("Terminating " + employee);
                terminateArray.add(employee);
            }
        }

        long devTime = time - Developer.getLifeTime();
        long manTime = time - Manager.getLifeTime();
        if (_employeeBirthTime.containsKey(devTime)) {
            if (terminateArray.contains(_employeeBirthTime.get(devTime).emp1)) {
                Pair pair = _employeeBirthTime.get(devTime);
                pair.emp1 = null;
                if (pair.isEmpty()) _employeeBirthTime.remove(devTime);
            }
        }
        if (_employeeBirthTime.containsKey(manTime)) {
            if (terminateArray.contains(_employeeBirthTime.get(manTime).emp2)) {
                Pair pair = _employeeBirthTime.get(manTime);
                pair.emp2 = null;
                if (pair.isEmpty()) _employeeBirthTime.remove(manTime);
            }
        }


        for (Employee employee : terminateArray) {
            _employeeList.remove(employee);
            _employeeID.remove(employee.getID());
        }

        if (terminateArray.size() > 0) { System.out.println("Terminating:\n" + terminateArray + "\nFrom:\n" + _employeeList); _graphicsPanel.repaint(); }
    }

    void showCurrentObjects() {
        String employees = "";
        Employee emp1, emp2;
        for (Map.Entry<Long, Pair> entry : _employeeBirthTime.entrySet()) {
            emp1 = entry.getValue().emp1;
            emp2 = entry.getValue().emp2;
            if (emp1 != null) employees += emp1 + "\n";
            if (emp2 != null) employees += emp2 + "\n";
        }
        if (employees == "") employees = "Empty";
        JOptionPane.showMessageDialog(null, employees, "CURRENT OBJECTS", JOptionPane.INFORMATION_MESSAGE);
    }

    private int findUpdateTime(int devTime, int manTime) {
        if(devTime == manTime) return devTime;

        int minTime;
        if (devTime < manTime) minTime = devTime;
        else minTime = manTime;

        for (int i = minTime; i > 0; --i) if (devTime % i == 0 && manTime % i == 0) return i;
        return -1;
    }
}
