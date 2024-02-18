package lib.control;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import lib.employee.Developer;
import lib.employee.Manager;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {
    JButton _startButton, _stopButton, _timeButton;
    private JToggleButton _infoButton;
    private boolean _showTime, _showInfo, _isStarted;
    private SimTimer _timer;
    private JPanel _startPanel, _stopPanel, _infoPanel, _timePanel;
    private Habitat _habitat;

    {
        _showTime = true;
        _showInfo = true;
        _isStarted = false;

        setLayout(new GridLayout(2, 2));

        _startButton = new JButton("Start");
        _startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!_isStarted) {
                    _timer.start();
                    _isStarted = true;
                }
            }
        });
        _startButton.setFocusable(false);

        _startPanel = new JPanel();
        _startPanel.setLayout(new FlowLayout());
        _startPanel.add(_startButton);

        _stopButton = new JButton("Stop");
        _stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (_isStarted) {
                    _timer.stop();
                    _isStarted = false;
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

        add(_startPanel);
        add(_stopPanel);
        add(_infoPanel);
        add(_timePanel);

    }

    void setHabitat(Habitat hbt) {
        int upt = Habitat.findUpdateTime(Developer.getGenerateTime(), Manager.getGenerateTime());
        _timer = new SimTimer(upt);
        _timer.setHabitat(hbt);
        _habitat = hbt;
    }
}
