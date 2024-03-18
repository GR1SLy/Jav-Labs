package lib.control;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
import javax.swing.SwingConstants;


public class MenuPanel extends JPanel {
    private JTextField _getDevTime, _getManTime, _getDevLifeTime, _getManLifeTime;
    private JLabel _welcomeLabel, _devLabel, _manLabel;
    private JButton _startButton;
    private JComboBox<Integer> _devChance, _manPercent;
    private JPanel _centerPanel, _welcomePanel, _devLabelPanel, _manLabelPanel, 
    _devTimePanel, _manTimePanel, _devLifeTimePanel, _manLifeTimePanel, _devChancePanel, _manPercentPanel;
    int _generateDevTime, _generateManTime, _devLifeTime, _manLifeTime, _generateDevChance, _generateManPercent;

    {
        _generateDevTime = 2;
        _generateDevChance = 50;
        _devLifeTime = 10;
        _generateManTime = 4;
        _generateManPercent = 50;
        _manLifeTime = 10;

        _getDevTime = new JTextField("2", 10);
        _getDevTime.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String s = _getDevTime.getText();
                try {
                    _generateDevTime = Integer.parseInt(s);
                    System.out.println(_generateDevTime);
                } catch (NumberFormatException ec) {
                    System.out.println("INCORRECT Dev Time: " + s);
                    _getDevTime.setText("" + _generateDevTime);
                    JOptionPane.showMessageDialog(null, "Invalid developer's generate time", "ERROR", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        _getDevTime.setHorizontalAlignment(JTextField.CENTER);

        _getManTime = new JTextField("4", 10);
        _getManTime.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String s = _getManTime.getText();
                try {
                    _generateManTime = Integer.parseInt(s);
                    System.out.println(_generateManTime);
                } catch (NumberFormatException ec) {
                    System.out.println("INCORRECT Man Time " + s);
                    _getManTime.setText("" + _generateManTime);
                    JOptionPane.showMessageDialog(null, "Invalid manager's time", "ERROR", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        _getManTime.setHorizontalAlignment(JTextField.CENTER);

        _getDevLifeTime = new JTextField("10", 10);
        _getDevLifeTime.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String s = _getDevLifeTime.getText();
                try {
                    _devLifeTime = Integer.parseInt(s);
                    System.out.println(_devLifeTime);
                } catch (NumberFormatException ec) {
                    System.out.println("INCORRECT Dev LifeTime: " + s);
                    _getDevTime.setText("" + _devLifeTime);
                    JOptionPane.showMessageDialog(null, "Invalid developer's life time", "ERROR", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        _getDevLifeTime.setHorizontalAlignment(JTextField.CENTER);

        _getManLifeTime = new JTextField("10", 10);
        _getManLifeTime.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String s = _getManLifeTime.getText();
                try {
                    _manLifeTime = Integer.parseInt(s);
                    System.out.println(_manLifeTime);
                } catch (NumberFormatException ec) {
                    System.out.println("INCORRECT Man LifeTime: " + s);
                    _getDevTime.setText("" + _manLifeTime);
                    JOptionPane.showMessageDialog(null, "Invalid manager's life time", "ERROR", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        _getManLifeTime.setHorizontalAlignment(JTextField.CENTER);

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
        _devChance.setPreferredSize(new Dimension(130, 30));
        ((JLabel)_devChance.getRenderer()).setHorizontalAlignment(JLabel.CENTER);

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
        _manPercent.setPreferredSize(new Dimension(130, 30));
        ((JLabel)_manPercent.getRenderer()).setHorizontalAlignment(JLabel.CENTER);

        _welcomeLabel = new JLabel("Welcome");
        _welcomeLabel.setFont(new Font("Impact", Font.BOLD, 16));

        _welcomePanel = new JPanel();
        _welcomePanel.setLayout(new FlowLayout());
        _welcomePanel.add(_welcomeLabel);

        _centerPanel = new JPanel();
        _centerPanel.setLayout(new GridLayout(4, 2));

        _devLabel = new JLabel("Developer:", SwingConstants.CENTER);
        _devLabel.setFont(new Font("Lucida Console", Font.ITALIC, 14));

        _devLabelPanel = new JPanel();
        _devLabelPanel.setLayout(new BorderLayout());
        _devLabelPanel.add(_devLabel, BorderLayout.PAGE_END);

        _manLabel = new JLabel("Manager:", SwingConstants.CENTER);
        _manLabel.setFont(new Font("Lucida Console", Font.ITALIC, 14));

        _manLabelPanel = new JPanel();
        _manLabelPanel.setLayout(new BorderLayout());
        _manLabelPanel.add(_manLabel, BorderLayout.PAGE_END);

        _devTimePanel = new JPanel();
        _devTimePanel.setLayout(new FlowLayout());
        _devTimePanel.add(_getDevTime);
        _devTimePanel.setBorder(BorderFactory.createTitledBorder("Time:"));

        _devLifeTimePanel = new JPanel();
        _devLifeTimePanel.setLayout(new FlowLayout());
        _devLifeTimePanel.add(_getDevLifeTime);
        _devLifeTimePanel.setBorder(BorderFactory.createTitledBorder("Life Time:"));

        _manLifeTimePanel = new JPanel();
        _manLifeTimePanel.setLayout(new FlowLayout());
        _manLifeTimePanel.add(_getManLifeTime);
        _manLifeTimePanel.setBorder(BorderFactory.createTitledBorder("Life Time:"));

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
        _centerPanel.add(_devLifeTimePanel);
        _centerPanel.add(_manLifeTimePanel);
        _centerPanel.add(_devChancePanel);
        _centerPanel.add(_manPercentPanel);


        setLayout(new BorderLayout());
        add(_welcomePanel, BorderLayout.PAGE_START);
        add(_startButton, BorderLayout.PAGE_END);
        add(_centerPanel, BorderLayout.CENTER);
    }

    JButton getStartButton() { return _startButton; }
}
