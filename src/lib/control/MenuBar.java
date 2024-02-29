package lib.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

public class MenuBar extends JMenuBar {
    private JMenu _headMenu, _timerMenu;
    private JMenuItem _startItem, _stopItem, _showTimerItem, _showInfoItem;
    private JSeparator _separator;
    private boolean _showTimer, _showInfo;
    private Habitat _habitat;

    {
        _showTimer = _showInfo = true;

        _headMenu = new JMenu("Control");
        _timerMenu = new JMenu("Timer");

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

        _separator = new JSeparator();
        _headMenu.add(_separator);
        _headMenu.add(_timerMenu);
        add(_headMenu);
    }

    public MenuBar(Habitat hbt) {
        _habitat = hbt;
    }
}
