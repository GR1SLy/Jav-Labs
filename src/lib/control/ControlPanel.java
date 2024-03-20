package lib.control;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import java.awt.FlowLayout;
import java.awt.GridLayout;

public class ControlPanel extends JPanel {
    JButton _startButton, _stopButton, _timeButton, _stopDevAIButton, _stopManAIButton, _objectsButton;
    JToggleButton _infoButton;
    private boolean _showInfo;
    boolean _devAI, _manAI;
    private JPanel _startPanel, _stopPanel, _stopDevAIPanel, _stopManAIPanel, _infoPanel, _timePanel, _objectsPanel;
    private Habitat _habitat;

    {
        _devAI = _manAI = true;
        _showInfo = false;

        setLayout(new GridLayout(4, 2));

        _startButton = new JButton("Start");
        _startButton.addActionListener(e -> {
                _habitat.startSimulation();
                _startButton.setEnabled(false);
                _stopButton.setEnabled(true);
        });
        _startButton.setFocusable(false);

        _startPanel = new JPanel();
        _startPanel.setLayout(new FlowLayout());
        _startPanel.add(_startButton);

        _stopButton = new JButton("Stop");
        _stopButton.setEnabled(false);
        _stopButton.addActionListener(e -> {
            _habitat.stopSimulation();
            _startButton.setEnabled(true);
                _stopButton.setEnabled(false);
            if (_showInfo) showInfoPane();
            else _habitat.clear();
        });
        _stopButton.setFocusable(false);

        _stopPanel = new JPanel();
        _stopPanel.setLayout(new FlowLayout());
        _stopPanel.add(_stopButton);

        _stopDevAIButton = new JButton("Stop developer's AI");
        _stopDevAIButton.addActionListener(e -> {
                _devAI = !_devAI;
                if (_devAI) _stopDevAIButton.setText("Stop developer's AI"); else _stopDevAIButton.setText("Resume developer's AI");
                _habitat.changeDevAIStatus(_devAI);
        });
        _stopDevAIButton.setFocusable(false);

        _stopDevAIPanel = new JPanel();
        _stopDevAIPanel.setLayout(new FlowLayout());
        _stopDevAIPanel.add(_stopDevAIButton);

        _stopManAIButton = new JButton("Stop manager's AI");
        _stopManAIButton.addActionListener(e -> {
                _manAI = !_manAI;
                if (_manAI) _stopManAIButton.setText("Stop manager's AI"); else _stopManAIButton.setText("Resume manager's AI");
                _habitat.changeManAIStatus(_manAI);
        });
        _stopManAIButton.setFocusable(false);

        _stopManAIPanel = new JPanel();
        _stopManAIPanel.setLayout(new FlowLayout());
        _stopManAIPanel.add(_stopManAIButton);

        _infoButton = new JToggleButton("Show Information");
        _infoButton.addActionListener(e -> {
                _showInfo = !_showInfo;
                System.out.println("Show info: " + _showInfo);
        });
        _infoButton.setFocusable(false);
        _infoButton.setSelected(!_showInfo);

        _infoPanel = new JPanel();
        _infoPanel.setLayout(new FlowLayout());
        _infoPanel.add(_infoButton);

        _timeButton = new JButton("Hide Timer");
        _timeButton.addActionListener(e -> {
            if (_habitat.showTimer()) _timeButton.setText("Hide  Timer"); else _timeButton.setText("Show Timer");
        });
        _timeButton.setFocusable(false);

        _timePanel = new JPanel();
        _timePanel.setLayout(new FlowLayout());
        _timePanel.add(_timeButton);

        _objectsButton = new JButton("Current objects");
        _objectsButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(_habitat, _habitat.getObjects(), "CURRENT OBJECTS", JOptionPane.INFORMATION_MESSAGE);
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

    public ControlPanel(Habitat habitat) {
        super();
        _habitat = habitat;
    }

    private void showInfoPane() {
        int choose = JOptionPane.showConfirmDialog(_habitat, _habitat + "\nStop simulation?", "CANCELED", JOptionPane.YES_NO_OPTION);
                System.out.println(choose);
                switch(choose) {
                    case 0 -> _habitat.clear();
                    case 1 -> _startButton.doClick();
                }
    }
}
