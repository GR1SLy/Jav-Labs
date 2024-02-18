package lib.control;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModalWindow extends JFrame {
    private JPanel _areaPanel, _controlPanel, _labelPanel, _infoPanel;
    private JTextArea _labelArea, _infoArea;
    private JButton _okButton, _cancelButton;
    private SimTimer _timer;
    {
        setTitle("INFORMATION");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        _areaPanel = new JPanel();
        _areaPanel.setLayout(new BorderLayout());
        add(_areaPanel, BorderLayout.CENTER);
        
        _labelPanel = new JPanel();
        _labelPanel.setLayout(new FlowLayout());
        _areaPanel.add(_labelPanel, BorderLayout.PAGE_START);

        _labelArea = new JTextArea();
        _labelArea.setEditable(false);
        _labelArea.setText("SIMULATION STATISTICS");
        _labelArea.setForeground(Color.RED);
        _labelArea.setFont(new Font("Impact", Font.BOLD, 16));
        _labelPanel.add(_labelArea);
        
        _infoPanel = new JPanel();
        _infoPanel.setLayout(new FlowLayout());
        _areaPanel.add(_infoPanel, BorderLayout.CENTER);

        _infoArea = new JTextArea();
        _infoArea.setEditable(false);
        _infoArea.setFont(new Font("Lucida Console", Font.ITALIC, 14));
        _infoPanel.add(_infoArea);

        _controlPanel = new JPanel();
        _controlPanel.setLayout(new FlowLayout());
        add(_controlPanel, BorderLayout.PAGE_END);

        _okButton = new JButton("OK");
        _okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                _timer.stop();
                dispose();
            }
        });

        _cancelButton = new JButton("Cancel");
        _cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                _timer.resume();
                dispose();
            }
        });

        _controlPanel.add(_okButton);
        _controlPanel.add(_cancelButton);
    }

    public ModalWindow(final SimTimer timer) {
        _timer = timer;
    }
    
    public void showWindow(long totalTime, int devCount, int manCount, int empCount) {
        _infoArea.setText("Time: " + totalTime / 1000 + " seconds"
         + "\nDevelopers count: " + devCount
         + "\nManagers count: " + manCount
         + "\nTotal employees count: " + empCount);
        pack();
        setVisible(true);
    }
}
