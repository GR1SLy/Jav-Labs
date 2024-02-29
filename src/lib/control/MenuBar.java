package lib.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

public class MenuBar extends JMenuBar {
    private JMenu _headMenu, _timerMenu, _AIMenu;
    private JMenuItem _startItem, _stopItem, _showTimerItem, _showInfoItem, _devAIItem, _manAIItem;
    private JSeparator _separator1, _separator2;
    private boolean _showTimer, _showInfo, _devAI, _manAI;
    private Habitat _habitat;

    {
        _showTimer = _showInfo = _devAI = _manAI = true;

        _headMenu = new JMenu("Control");
        _timerMenu = new JMenu("Timer");
        _AIMenu = new JMenu("AI");

        _startItem = new JMenuItem("Start");
        _startItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _habitat._controlPanel._startButton.doClick();
            }
        });
        _timerMenu.add(_startItem);

        
        _stopItem = new JMenuItem("Stop");
        _stopItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _habitat._controlPanel._stopButton.doClick();
            }
        });
        _timerMenu.add(_stopItem);
        
        _headMenu.add(_timerMenu);
        _separator1 = new JSeparator();
        _headMenu.add(_separator1);

        _showTimerItem = new JMenuItem("Hide Timer");
        _showTimerItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _showTimer = !_showTimer;
                if (_showTimer) _showTimerItem.setText("Hide Timer"); else _showTimerItem.setText("Show Timer");
                _habitat._controlPanel._timeButton.doClick();
            }
        });
        _headMenu.add(_showTimerItem);

        _showInfoItem = new JMenuItem("Hide Info");
        _showInfoItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _showInfo = !_showInfo;
                if (_showInfo) _showInfoItem.setText("Hide Info"); else _showInfoItem.setText("Show Info");
                _habitat._controlPanel._infoButton.doClick();
            }
        });
        _headMenu.add(_showInfoItem);

        _separator2 = new JSeparator();
        _headMenu.add(_separator2);

        _devAIItem = new JMenuItem("Stop developer's AI");
        _devAIItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _devAI = !_devAI;
                if (_devAI) _devAIItem.setText("Stop developer's AI"); else _devAIItem.setText("Resume developer's AI");
                _habitat._controlPanel._stopDevAIButton.doClick();
            }
        });
        _AIMenu.add(_devAIItem);

        _manAIItem = new JMenuItem("Stop developer's AI");
        _manAIItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _manAI = !_manAI;
                if (_manAI) _manAIItem.setText("Stop manager's AI"); else _manAIItem.setText("Resume manager's AI");
                _habitat._controlPanel._stopManAIButton.doClick();
            }
        });
        _AIMenu.add(_manAIItem);

        _headMenu.add(_AIMenu);

        add(_headMenu);
    }

    public MenuBar(Habitat hbt) {
        _habitat = hbt;
    }
}
