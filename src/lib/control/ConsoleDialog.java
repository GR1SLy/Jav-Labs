package lib.control;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.PrintStream;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import lib.employee.Manager;

import java.io.InputStream;
import java.io.OutputStream;

public class ConsoleDialog extends JFrame {

    private Habitat _habitat;
    
    private JPanel _panel;
    private JTextPane _pane;
    private JScrollPane _scroll;
    private Document _doc;

    private int _len = 0, _off = 0;
    private byte[] _cbuf = null;
    private boolean _eol = false, _countNeeded = false, 
                    _velNeeded = false, _timeNeeded = false;
    private Scanner _scanner;

    {
        System.setOut(new PrintStream(new ConsoleOutputStream(this)));
        _scanner = new Scanner(new ConsoleInputStream(this));
        setSize(400, 400);
        setTitle("Console");
        setMinimumSize(new Dimension(400, 400));
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);

        _panel = new JPanel();
        _panel.setLayout(new BorderLayout());
        add(_panel);

        _pane = new JTextPane();
        _doc = _pane.getDocument();
        _pane.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String command = _scanner.nextLine();
                    if (_countNeeded) {
                        try { 
                            int n = Integer.parseInt(command);
                            if (n <= 0) System.out.println("\nCount must be a positive integer");
                            else { System.out.println("\nCreated " + n + " managers"); _habitat.hireManagers(n); }
                        }
                        catch (NumberFormatException ex) { System.out.println("\nCount must be a positive integer"); }
                        _countNeeded = false;
                    } else if (_velNeeded) {
                        try { 
                            int v = Integer.parseInt(command);
                            if (v <= 0) System.out.println("\nVelocity must be a positive integer");
                            else { System.out.println("\nVelocity setted " + v); _habitat.setAIV(v); }
                        }
                        catch (NumberFormatException ex) { System.out.println("\nVelocity must be a positive integer"); }
                        _velNeeded = false;
                    } else if (_timeNeeded) {
                        try { 
                            int t = Integer.parseInt(command);
                            if (t <= 0) System.out.println("\nTime must be a positive integer");
                            else { System.out.println("\nChange time setted " + t); _habitat.setAIN(t); }
                        }
                        catch (NumberFormatException ex) { System.out.println("\nTime must be a positive integer"); }
                        _timeNeeded = false;
                    } else checkCommand(command);
                }
            }
        });
        _scroll = new JScrollPane(_pane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        _panel.add(_scroll, BorderLayout.CENTER);
    }

    public ConsoleDialog(Habitat habitat) {
        super();
        _habitat = habitat;
    }

    public void showDialog() {
        setVisible(true);
    }

    private void checkCommand(String command) {
        switch (command) {
            case "manager rm" -> { System.out.println("\nManagers have been removed"); _habitat.fireManagers(); }
            case "manager add" -> { System.out.print("\nCount: "); _countNeeded = true; }
            case "manager count" -> { System.out.println("\nManager count: " + Manager.getCount()); }
            case "ai velocity" -> { System.out.print("\nVelocity: "); _velNeeded = true; }
            case "ai time" -> { System.out.print("\nTime: "); _timeNeeded = true; }
            case "sim start" -> {
                if (_habitat.isRunning()) System.out.println("Simulation is already running");
                else _habitat._controlPanel._startButton.doClick();
            }
            case "sim stop" -> {
                if (!_habitat.isRunning()) System.out.println("Simulation is already cancelled");
                else _habitat._controlPanel._stopButton.doClick();
            }
            case "serialize" -> { System.out.println("\nObjects have been serialized"); _habitat.serialize(); }
            case "serialize dir" -> Serializer.chooseSaveFile();
            case "deserialize" -> { System.out.println("\nObjects have been deserialized"); _habitat.deserialize(); }
            case "deserialize dir" -> Serializer.chooseLoadFile();
            case "cfg dir" -> ConfigOperator.chooseSaveFile();
            case "clear" -> {
                try { _doc.remove(0, _doc.getLength()); _off = 1; } 
                catch (BadLocationException e) { e.printStackTrace(); }
            }
            case "help" -> getCommands();
            default -> { System.out.println("\nUnknown command: " + command + "\nType help for more information"); }
        }
    }

    private void getCommands() {
        System.out.println("\nCurrent commands:" + 
                           "\nmanager:" + 
                                "\n\trm - delete all managers" + 
                                "\n\tadd - create N new managers" + 
                                "\n\tcount - get count of managers" +
                           "\nai:" + 
                                "\n\tvelocity - set movement speed for objects" + 
                                "\n\ttime - set time of change movement for developers" + 
                           "\nsim:" + 
                                "\n\tstart - start simulation" + 
                                "\n\tstop - stop simulation" +
                           "\nserialize:" +
                                "\n\t- serialize all objects" +
                                "\n\tdir - set directory for save" +
                           "\ndeserialize" +
                                "\n\t- deserialize all objects" +
                                "\n\tdir - set directory for load" + 
                           "\ncfg dir - set configuration directory" +
                           "\nclear - clear console");
    }

    int read() {
        if (_eol) {
            _cbuf = null;
            _off++;
            _eol = false;
            return -1;
        }
        if (_cbuf == null) {
            _cbuf = _pane.getText().getBytes();
            _len = _doc.getLength();
            return _cbuf[_off++];
        } else if (_off < _len) {
            return _cbuf[_off++];
        } else {
            _eol = true;
            return (int)'\n';
        }
    }

    void write(int b) {
        try {
            _doc.insertString(_doc.getLength(), "" + (char)b, null);
            _off = _doc.getLength() + 1;
            // System.err.print((char)b);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

}

class ConsoleInputStream extends InputStream {

    private ConsoleDialog _console;

    public ConsoleInputStream(ConsoleDialog console) {
        super();
        _console = console;
    }

    @Override
    public int read() {
        return _console.read();
    }
}

class ConsoleOutputStream extends OutputStream {
    
    private ConsoleDialog _console;

    public ConsoleOutputStream(ConsoleDialog console) {
        super();
        _console = console;
    }

    @Override
    public void write(int b) {
        _console.write(b);
    }
}
