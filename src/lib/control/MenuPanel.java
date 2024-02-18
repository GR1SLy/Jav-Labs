package lib.control;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class MenuPanel extends JPanel {
    private JTextField _getDevTime, _getManTime;
    private JLabel _welcomeLabel, _devLabel, _manLabel;
    private JButton _startButton;
    private JComboBox<Integer> _devChance, _manPercent;
    private JPanel _centerPanel, _welcomePanel, _devLabelPanel, _manLabelPanel, 
    _devTimePanel, _manTimePanel, _devChancePanel, _manPercentPanel;
    int _generateDevTime, _generateManTime, _generateDevChance, _generateManPercent;

    {
        _generateDevTime = 2;
        _generateDevChance = 50;
        _generateManTime = 4;
        _generateManPercent = 50;

        _getDevTime = new JTextField("2", 6);
        _getDevTime.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String s = _getDevTime.getText();
                try {
                    _generateDevTime = Integer.parseInt(s);
                    System.out.println(_generateDevTime);
                } catch (NumberFormatException ec) {
                    System.out.println("INCORRECT Dev Time: " + s);
                    _getDevTime.setText("" + _generateDevTime);
                    JOptionPane.showMessageDialog(null, "Invalid developer's generate time", "ERROR", 2);
                }
            }
        });

        _getManTime = new JTextField("4", 6);
        _getManTime.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String s = _getManTime.getText();
                try {
                    _generateManTime = Integer.parseInt(s);
                    System.out.println(_generateManTime);
                } catch (NumberFormatException ec) {
                    System.out.println("INCORRECT Man Time " + s);
                    _getManTime.setText("" + _generateManTime);
                    JOptionPane.showMessageDialog(null, "Invalid manager's time", "ERROR", 2);
                }
            }
        });

        _startButton = new JButton("Start");
        _startButton.setFocusable(false);

        _devChance = new JComboBox<Integer>();
        for (int i = 0; i <= 100; i += 10) {
            _devChance.addItem(i);
        }
        _devChance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _generateDevChance = (int)_devChance.getSelectedItem();
                System.out.println(_generateDevChance);
            }
        });
        _devChance.setSelectedItem(50);

        _manPercent = new JComboBox<Integer>();
        for (int i = 0; i <= 100; i += 10) {
            _manPercent.addItem(i);
        }
        _manPercent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _generateManPercent = (int)_manPercent.getSelectedItem();
                System.out.println(_generateManPercent);
            }
        });
        _manPercent.setSelectedItem(50);

        _welcomeLabel = new JLabel("Welcome");
        _welcomeLabel.setFont(new Font("Impact", Font.BOLD, 16));

        _welcomePanel = new JPanel();
        _welcomePanel.setLayout(new FlowLayout());
        _welcomePanel.add(_welcomeLabel);

        _centerPanel = new JPanel();
        _centerPanel.setLayout(new GridLayout(3, 2));

        _devLabel = new JLabel("Developer:");
        _devLabel.setFont(new Font("Lucida Console", Font.ITALIC, 14));

        _devLabelPanel = new JPanel();
        _devLabelPanel.setLayout(new FlowLayout());
        _devLabelPanel.add(_devLabel);

        _manLabel = new JLabel("Manager:");
        _manLabel.setFont(new Font("Lucida Console", Font.ITALIC, 14));

        _manLabelPanel = new JPanel();
        _manLabelPanel.setLayout(new FlowLayout());
        _manLabelPanel.add(_manLabel);

        _devTimePanel = new JPanel();
        _devTimePanel.setLayout(new FlowLayout());
        _devTimePanel.add(_getDevTime);
        _devTimePanel.setBorder(BorderFactory.createTitledBorder("Time:"));

        _manTimePanel = new JPanel();
        _manTimePanel.setLayout(new FlowLayout());
        _manTimePanel.add(_getManTime);
        _manTimePanel.setBorder(BorderFactory.createTitledBorder("Time:"));

        _devChancePanel = new JPanel();
        _devChancePanel.setLayout(new FlowLayout());
        _devChancePanel.add(_devChance);
        _devChancePanel.setBorder(BorderFactory.createTitledBorder("Chance:"));

        _manPercentPanel = new JPanel();
        _manPercentPanel.setLayout(new FlowLayout());
        _manPercentPanel.add(_manPercent);
        _manPercentPanel.setBorder(BorderFactory.createTitledBorder("Percent:"));

        _centerPanel.add(_devLabelPanel);
        _centerPanel.add(_manLabelPanel);
        _centerPanel.add(_devTimePanel);
        _centerPanel.add(_manTimePanel);
        _centerPanel.add(_devChancePanel);
        _centerPanel.add(_manPercentPanel);


        setLayout(new BorderLayout());
        add(_welcomePanel, BorderLayout.PAGE_START);
        add(_startButton, BorderLayout.PAGE_END);
        add(_centerPanel, BorderLayout.CENTER);
    }

    JButton getStartButton() { return _startButton; }
}
