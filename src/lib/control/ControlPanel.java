package lib.control;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import lib.employee.*;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {
    JButton _startButton, _stopButton, _timeButton, _stopDevAIButton, _stopManAIButton, _objectsButton;
    private SimTimer _timer;
    JToggleButton _infoButton;
    private boolean _showTime, _showInfo, _devAI, _manAI;
    private JPanel _startPanel, _stopPanel, _stopDevAIPanel, _stopManAIPanel, _infoPanel, _timePanel, _objectsPanel;
    private Habitat _habitat;

    {
        _showTime = _showInfo = _devAI = _manAI = true;

        setLayout(new GridLayout(4, 2));

        _startButton = new JButton("Start");
        _startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!_timer.isRunning()) {
                    _timer.start();
                }
            }
        });
        _startButton.setFocusable(false);

        _startPanel = new JPanel();
        _startPanel.setLayout(new FlowLayout());
        _startPanel.add(_startButton);

        _stopButton = new JButton("Stop");
        _stopButton.setEnabled(false);
        _stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (_timer.isRunning()) {

                    long totalTime = _timer.pause();

                    int devCount = Developer.getCount(), manCount = Manager.getCount(), empCount = Employee.getCount();

                    System.out.println("\n\n=================\nSimulation paused\n=================\n\nSimulation time: " + totalTime + 
                    "\nCurrent statistics:\nDevelopers generated: " + devCount + 
                    "\nManagers generated: " + manCount + "\nTotal Employees generated: " + empCount);
                    ModalWindow modalWindow = new ModalWindow(_timer);
                    if (_showInfo) modalWindow.showWindow(totalTime, devCount, manCount, empCount);
                    else _timer.stop();
                }
            }
        });
        _stopButton.setFocusable(false);

        _stopPanel = new JPanel();
        _stopPanel.setLayout(new FlowLayout());
        _stopPanel.add(_stopButton);

        _stopDevAIButton = new JButton("Stop developer's AI");
        _stopDevAIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _devAI = !_devAI;
                if (_devAI) _stopDevAIButton.setText("Stop developer's AI"); else _stopDevAIButton.setText("Resume developer's AI");
            }
        });
        _stopDevAIButton.setFocusable(false);

        _stopDevAIPanel = new JPanel();
        _stopDevAIPanel.setLayout(new FlowLayout());
        _stopDevAIPanel.add(_stopDevAIButton);

        _stopManAIButton = new JButton("Stop manager's AI");
        _stopManAIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _manAI = !_manAI;
                if (_manAI) _stopManAIButton.setText("Stop manager's AI"); else _stopManAIButton.setText("Resume manager's AI");
            }
        });
        _stopManAIButton.setFocusable(false);

        _stopManAIPanel = new JPanel();
        _stopManAIPanel.setLayout(new FlowLayout());
        _stopManAIPanel.add(_stopManAIButton);

        _infoButton = new JToggleButton("Show Information");
        _infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _showInfo = !_showInfo;
                System.out.println("Show info: " + _showInfo);
            }
        });
        _infoButton.setFocusable(false);

        _infoPanel = new JPanel();
        _infoPanel.setLayout(new FlowLayout());
        _infoPanel.add(_infoButton);

        _timeButton = new JButton("Hide Timer");
        _timeButton.addActionListener(new ActionListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void actionPerformed(ActionEvent e) {
                _showTime = !_showTime;
                _habitat._timerLabel.setVisible(_showTime);
                System.out.println("Show timer: " + _showTime);
                if (_showTime) _timeButton.setLabel("Hide Timer"); else _timeButton.setLabel("Show Timer");
            }
        });
        _timeButton.setFocusable(false);

        _timePanel = new JPanel();
        _timePanel.setLayout(new FlowLayout());
        _timePanel.add(_timeButton);

        _objectsButton = new JButton("Current objects");
        _objectsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _habitat.showCurrentObjects();
            }
        });
        _objectsButton.setFocusable(false);

        _objectsPanel = new JPanel();
        _objectsPanel.setLayout(new FlowLayout());
        _objectsPanel.add(_objectsButton);

        add(_startPanel);
        add(_stopPanel);
        add(_stopDevAIPanel);
        add(_stopManAIPanel);
        add(_infoPanel);
        add(_timePanel);
        add(_objectsPanel);

    }

    void setHabitat(Habitat hbt) {
        _timer = new SimTimer();
        _timer.setHabitat(hbt);
        _habitat = hbt;
    }
}
