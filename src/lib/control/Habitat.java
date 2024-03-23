package lib.control;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import lib.ai.BaseAI;
import lib.ai.DevAI;
import lib.ai.ManAI;
import lib.employee.*;

public class Habitat extends JFrame {
    private class Pair {
        public Employee emp1;
        public LinkedList<Employee> emp2;
        public Pair() { emp1 = null; emp2 = null; }
        public boolean isEmpty() { return emp1 == null && emp2 == null; }
    }
    private int _x, _y, _width, _height;

    private LinkedList<Employee> _employeeList;
    private HashMap<Integer, Pair> _employeeBirthTime;
    private TreeSet<Integer> _employeeID;

    private DevAI _devAI;
    private ManAI _manAI;
    private boolean _devAIFlag = true, _manAIFlag = true;

    private JPanel _cardPanel, _mainPanel, _workingPanel, _timerPanel, _graphicsPanel;
    private JLabel _timerLabel;
    private MenuPanel _menuPanel;
    private JButton _backToMenuButton;
    private MenuBar _menuBar;
    ControlPanel _controlPanel;

    private Timer _timer;
    private int _timerX = 1, _currentTime = 0;
    private boolean _timerFlag = true;
    private boolean _isRunning = false;

    {
        setTitle("SIMULATION");
        setLayout(new BorderLayout());

        _cardPanel = new JPanel();
        _cardPanel.setLayout(new CardLayout());
        add(_cardPanel, BorderLayout.CENTER);

        _menuPanel = new MenuPanel(this);
        _cardPanel.add(_menuPanel);

        _mainPanel = new JPanel();
        _mainPanel.setLayout(new BorderLayout());
        _cardPanel.add(_mainPanel);

        //<---------Back To Menu Button--------->
        _backToMenuButton = new JButton("Back to menu");
        _backToMenuButton.addActionListener(e -> {
            switchCard();
            _menuPanel.requestFocus();
        });
        _backToMenuButton.setFocusable(false);
        _mainPanel.add(_backToMenuButton, BorderLayout.PAGE_END);

        //<---------Working Panel--------->
        _workingPanel = new JPanel();
        _workingPanel.setLayout(new BorderLayout());
        _mainPanel.add(_workingPanel, BorderLayout.CENTER);

        _controlPanel = new ControlPanel(this);
        _workingPanel.add(_controlPanel, BorderLayout.PAGE_END);

        _menuBar = new MenuBar(this);

        //<---------Timer--------->
        _timerPanel = new JPanel();
        _timerPanel.setLayout(new FlowLayout());
        _timerPanel.setBorder(BorderFactory.createTitledBorder("TIMER"));
        _workingPanel.add(_timerPanel, BorderLayout.PAGE_START);

        _timerLabel = new JLabel("Simulation hasn't started yet");
        _timerLabel.setVerticalAlignment(JLabel.TOP);
        _timerPanel.add(_timerLabel);

        //<---------Graphics Panel--------->
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

        //<---------Collections--------->
        _employeeList = new LinkedList<>();
        _employeeID = new TreeSet<>();
        _employeeBirthTime = new HashMap<>();

        //<---------AI--------->
        _devAI = new DevAI(_employeeList, _graphicsPanel);
        _manAI = new ManAI(_employeeList, _graphicsPanel);
    }

    public Habitat(int x, int y, int width, int height) {
        super();
        _x = x;
        _y = y;
        _width = width;
        _height = height;
    }

    public void createFrame() {
        setBounds(_x, _y, _width, _height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()) {
                    case KeyEvent.VK_B -> { if (!_isRunning) _controlPanel._startButton.doClick(); }
                    case KeyEvent.VK_E -> { if (_isRunning) { _controlPanel._stopButton.doClick(); }}
                    case KeyEvent.VK_T -> _controlPanel._timeButton.doClick();
                }
                System.out.println(e.getKeyChar());
            }
        });
        setVisible(true);
        setJMenuBar(_menuBar);

        _devAI.start();
        _manAI.start();
        pauseAI();
    }

    void switchCard() {
        ((CardLayout)_cardPanel.getLayout()).next(_cardPanel);
        requestFocus();
    }

    private void setTimerLabel() { _timerLabel.setText("Time: " + _currentTime); } 

    boolean showTimer() {
        _timerLabel.setVisible(!_timerFlag);
        _timerFlag = !_timerFlag;
        return _timerFlag;
    }

    void startSimulation() {
        _isRunning = true;
        _timer = new Timer();
        resumeAI();
        _timer.schedule(new TimerTask() {
            @Override
            public void run() {
                setTimerLabel();
                _currentTime++;
                update(_currentTime);
            }
        }, 0, 1000 / _timerX);
        _backToMenuButton.setEnabled(false);
    }

    void stopSimulation() {
        _isRunning = false;
        _timer.cancel();
        pauseAI();
        _timerLabel.setText("Simulation canceled");
        _backToMenuButton.setEnabled(true);
    }

    void clear() {
        _employeeList.clear();
        _employeeBirthTime.clear();
        _employeeID.clear();
        Developer.clear();
        Manager.clear();
        Employee.clear();
        _currentTime = 0;
        _graphicsPanel.repaint();
    }

    public void setTimerAcceleration(int X) {
        if (X > 0) _timerX = X;
    }

    private int findID(Random rand) {
        Integer id = rand.nextInt(1000, 9999);
        while (_employeeID.contains(id)) id = rand.nextInt(1000, 9999);
        _employeeID.add(id);
        return id;
    }

    String getObjects() {
        String res = "Current objects:\n";
        Employee emp1;
        LinkedList<Employee> emp2;
        for (Map.Entry<Integer, Pair> entry : _employeeBirthTime.entrySet()) {
            emp1 = entry.getValue().emp1;
            emp2 = entry.getValue().emp2;
            if (emp1 != null) res += emp1 + "\n";
            if (emp2 != null) res += emp2 + "\n";
        }
        return res;
    }

    @Override
    public String toString() {
        return "Simulation statistics:" +
        "\nDeveloper count: " + Developer.getCount() +
        "\nManager count: " + Manager.getCount() + 
        "\nTotal employee count: " + Employee.getCount();
    }

    private void update(final int currentTime) {
        generate();
        terminate();
        _graphicsPanel.repaint();
    }

    private void generate() {
        Rectangle rect = _graphicsPanel.getBounds();
        Random rand = new Random();
        Pair pair = new Pair();
        if (_currentTime % Developer.getGenerateTime() == 0) {
            if (Developer.getGenerateChance() >= rand.nextInt(100)) {
                Employee emp = new Developer(rect.width, rect.height, _currentTime, findID(rand));
                pair.emp1 = emp;
                _employeeList.addLast(emp);
                System.out.println("Created new Developer!\tTotal count: " + Developer.getCount());
            }
        }
        if (_currentTime % Manager.getGenerateTime() == 0) {
            if ((double) Manager.getCount() / (double) Developer.getCount() * 100 < Manager.getGeneratePercent()) {
                LinkedList<Employee> emp2 = new LinkedList<>();
                Employee emp = new Manager(rect.width, rect.height, _currentTime, findID(rand));
                emp2.add(emp);
                pair.emp2 = emp2;
                _employeeList.addLast(emp);
                System.out.println("Created new Manager!\tTotal count: " + Manager.getCount());
            }
        }
        if (!pair.isEmpty()) _employeeBirthTime.put(_currentTime, pair);
    }

    private void terminate() {
        if (_employeeBirthTime.containsKey(_currentTime - Developer.getLifeTime()) && _employeeBirthTime.get(_currentTime - Developer.getLifeTime()).emp1 != null) {
            Employee emp1 = _employeeBirthTime.get(_currentTime - Developer.getLifeTime()).emp1;
            _employeeID.remove(emp1.getID());
            _employeeList.remove(emp1);
            _employeeBirthTime.get(_currentTime - Developer.getLifeTime()).emp1 = null;
            Developer.decCount();
            Employee.decCount();
            if (_employeeBirthTime.get(_currentTime - Developer.getLifeTime()).isEmpty()) _employeeBirthTime.remove(_currentTime - Developer.getLifeTime());
        }
        if (_employeeBirthTime.containsKey(_currentTime - Manager.getLifeTime()) && _employeeBirthTime.get(_currentTime - Manager.getLifeTime()).emp2 != null) {
            LinkedList<Employee> emp = _employeeBirthTime.get(_currentTime - Manager.getLifeTime()).emp2;
            for (Employee emp2 : emp) {
                _employeeID.remove(emp2.getID());
                _employeeList.remove(emp2);
            }
            _employeeBirthTime.get(_currentTime - Manager.getLifeTime()).emp2 = null;
            Manager.decCount();
            Employee.decCount();
            if (_employeeBirthTime.get(_currentTime - Manager.getLifeTime()).isEmpty()) _employeeBirthTime.remove(_currentTime - Manager.getLifeTime());
        }
    }

    private void resumeAI() { 
        if (_devAIFlag) _devAI.resumeAI(); 
        if (_manAIFlag) _manAI.resumeAI(); 
    }

    private void pauseAI() { _devAI.pauseAI(); _manAI.pauseAI(); }

    void changeDevAIStatus(boolean status) { if (!status) _devAI.pauseAI(); else _devAI.resumeAI(); _devAIFlag = status; }

    void changeManAIStatus(boolean status) { if (!status) _manAI.pauseAI(); else _manAI.resumeAI(); _manAIFlag = status; }

    void setAIPriority(int priority) { 
        if (priority == 0) { _devAI.setPriority(Thread.MAX_PRIORITY); _manAI.setPriority(Thread.MIN_PRIORITY); } 
        else if (priority == 1) { _manAI.setPriority(Thread.MAX_PRIORITY); _devAI.setPriority(Thread.MIN_PRIORITY); }
    }

    public void setAIV(int velocity) { BaseAI.setV(velocity); }

    public void setAIN(int N) { Developer.setN(N); }

    void fireManagers() {
        LinkedList<Employee> deathNote = new LinkedList<>();
        for (Employee emp : _employeeList) {
            if (emp instanceof Manager) {
                deathNote.add(emp);
            }
        }
        for (Employee emp : deathNote) {
            _employeeID.remove(emp.getID());
            _employeeList.remove(emp);
            if (_employeeBirthTime.containsKey(emp.getBirthTime())) {
                _employeeBirthTime.get(emp.getBirthTime()).emp2 = null;
            }
        }
        System.out.println("!!Managers have fired!!");
    }

    void hireManagers(int n) {
        LinkedList<Employee> newManagers = new LinkedList<>();
        Rectangle rect = _graphicsPanel.getBounds();
        Random rand = new Random();
        for (int i = 0; i < n; ++i) {
            newManagers.addLast(new Manager(rect.width, rect.height, _currentTime, findID(rand)));
        }
        for (Employee emp : newManagers) {
            _employeeList.addLast(emp);
            _employeeID.add(emp.getID());
            if (_employeeBirthTime.containsKey(_currentTime)) {
                _employeeBirthTime.get(_currentTime).emp2 = newManagers;
            } else {
                Pair pair = new Pair();
                pair.emp2 = newManagers;
                _employeeBirthTime.put(_currentTime, pair);
            }
        }
        System.out.println("!!Created " + n + " new managers!!");
    }
}