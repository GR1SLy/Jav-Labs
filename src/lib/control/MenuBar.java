package lib.control;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

public class MenuBar extends JMenuBar {
    private JMenu _headMenu, _timerMenu, _AIMenu, _fileMenu;
    private JMenuItem _startItem, _stopItem, _showTimerItem, 
                      _showInfoItem, _showObjectsItem, _devAIItem, 
                      _manAIItem, _saveItem, _loadItem, _cfgItem,
                      _consoleItem;
    private JSeparator _separator1, _separator2;
    private boolean _showTimer, _showInfo, _devAI, _manAI;
    private Habitat _habitat;

    {
        _showTimer = _devAI = _manAI = true;
        _showInfo = false;

        _headMenu = new JMenu("Control");
        _timerMenu = new JMenu("Timer");
        _AIMenu = new JMenu("AI");

        _startItem = new JMenuItem("Start");
        _startItem.addActionListener(e -> {
                _habitat.getControlPanel().getStartButton().doClick();
        });
        _timerMenu.add(_startItem);

        
        _stopItem = new JMenuItem("Stop");
        _stopItem.addActionListener(e -> {
                _habitat.getControlPanel().getStopButton().doClick();
        });
        _timerMenu.add(_stopItem);
        
        _headMenu.add(_timerMenu);
        _separator1 = new JSeparator();
        _headMenu.add(_separator1);

        _showTimerItem = new JMenuItem("Hide Timer");
        _showTimerItem.addActionListener(e -> {
                _showTimer = !_showTimer;
                if (_showTimer) _showTimerItem.setText("Hide Timer"); else _showTimerItem.setText("Show Timer");
                _habitat.getControlPanel().getTimeButton().doClick();
        });
        _headMenu.add(_showTimerItem);

        _showInfoItem = new JMenuItem("Show Info");
        _showInfoItem.addActionListener(e -> {
                _showInfo = !_showInfo;
                if (_showInfo) _showInfoItem.setText("Hide Info"); else _showInfoItem.setText("Show Info");
                _habitat.getControlPanel().getInfoButton().doClick();
        });
        _headMenu.add(_showInfoItem);

        _showObjectsItem = new JMenuItem("Current objects");
        _showObjectsItem.addActionListener(e -> {
            _habitat.getControlPanel().getObjectsButton().doClick();
        });
        _headMenu.add(_showObjectsItem);

        _separator2 = new JSeparator();
        _headMenu.add(_separator2);

        _devAIItem = new JMenuItem("Stop developer's AI");
        _devAIItem.addActionListener(e -> {
                _devAI = !_devAI;
                if (_devAI) _devAIItem.setText("Stop developer's AI"); else _devAIItem.setText("Resume developer's AI");
                _habitat.getControlPanel().getStopDevAIButton().doClick();
        });
        _AIMenu.add(_devAIItem);

        _manAIItem = new JMenuItem("Stop developer's AI");
        _manAIItem.addActionListener(e -> {
                _manAI = !_manAI;
                if (_manAI) _manAIItem.setText("Stop manager's AI"); else _manAIItem.setText("Resume manager's AI");
                _habitat.getControlPanel().getStopManAIButton().doClick();
        });
        _AIMenu.add(_manAIItem);

        _headMenu.add(_AIMenu);


        _fileMenu = new JMenu("File");

        _saveItem = new JMenuItem("Save");
        _saveItem.addActionListener(e -> {
                Serializer.chooseSaveFile();
                _habitat.serialize();
        });
        _fileMenu.add(_saveItem);

        _loadItem = new JMenuItem("Load");
        _loadItem.addActionListener(e -> {
                Serializer.chooseLoadFile();
                _habitat.deserialize();
        });
        _fileMenu.add(_loadItem);

        _cfgItem = new JMenuItem("Save config");
        _cfgItem.addActionListener(e -> {
                ConfigOperator.chooseSaveFile();
        });
        _fileMenu.add(_cfgItem);

        _consoleItem = new JMenuItem("Console");
        _consoleItem.addActionListener(e -> {
                _habitat.showConsole();
        });
        _fileMenu.add(_consoleItem);

        add(_fileMenu);

        add(_headMenu);


    }

    public MenuBar(Habitat habitat) {
        _habitat = habitat;
    }
}
