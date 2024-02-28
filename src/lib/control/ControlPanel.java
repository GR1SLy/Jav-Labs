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
    JButton _startButton, _stopButton, _timeButton, _objectsButton;
    private SimTimer _timer;
    private JToggleButton _infoButton;
    private boolean _showTime, _showInfo;
    private JPanel _startPanel, _stopPanel, _infoPanel, _timePanel, _objectsPanel;
    private Habitat _habitat;

    {
        _showTime = true;
        _showInfo = true;

        setLayout(new GridLayout(3, 2));

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

        _objectsPanel = new JPanel();
        _objectsPanel.setLayout(new FlowLayout());
        _objectsPanel.add(_objectsButton);

        add(_startPanel);
        add(_stopPanel);
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
