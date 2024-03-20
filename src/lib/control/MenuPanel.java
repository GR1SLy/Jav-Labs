package lib.control;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import lib.employee.Developer;
import lib.employee.Manager;


public class MenuPanel extends JPanel {
    private JTextField _getDevTime, _getManTime, _getDevLifeTime, _getManLifeTime;
    private JLabel _welcomeLabel, _devLabel, _manLabel;
    private JComboBox<Integer> _devChance, _manPercent;
    private JButton _submitButton;
    private Habitat _habitat;
    private JPanel _centerPanel, _welcomePanel, _devLabelPanel, _manLabelPanel, 
                   _devTimePanel, _manTimePanel, _devLifeTimePanel, _manLifeTimePanel,
                   _devChancePanel, _manPercentPanel;

    {
        //<---------Submit Button--------->
        _submitButton = new JButton("Submit");
        _submitButton.setFocusable(false);
        _submitButton.addActionListener(e -> {
            try {
                Developer.setGenerateTime(Integer.parseInt(_getDevTime.getText()));
                Developer.setGenerateChance((int)_devChance.getSelectedItem());
                Developer.setLifeTime(Integer.parseInt(_getDevLifeTime.getText()));
                Manager.setGenerateTime(Integer.parseInt(_getManTime.getText()));
                Manager.setGeneratePercent((int)_manPercent.getSelectedItem());
                Manager.setLifeTime(Integer.parseInt(_getManLifeTime.getText()));
                _habitat.switchCard();
            } catch (Exception ex) {
                System.out.println("ERROR: Incorrect data");
                JOptionPane.showMessageDialog(MenuPanel.this, "Invalid time data", "ERROR", JOptionPane.ERROR_MESSAGE);
                _getDevTime.setText("2");
                _getDevLifeTime.setText("10");
                _getManTime.setText("4");
                _getManLifeTime.setText("10");
            }
        });

        //<---------Text Areas--------->
        _getDevTime = new JTextField("2", 10);
        _getDevTime.setHorizontalAlignment(JTextField.CENTER);

        _devTimePanel = new JPanel();
        _devTimePanel.setLayout(new FlowLayout());
        _devTimePanel.add(_getDevTime);
        _devTimePanel.setBorder(BorderFactory.createTitledBorder("Time:"));


        _getManTime = new JTextField("4", 10);
        _getManTime.setHorizontalAlignment(JTextField.CENTER);

        _manTimePanel = new JPanel();
        _manTimePanel.setLayout(new FlowLayout());
        _manTimePanel.add(_getManTime);
        _manTimePanel.setBorder(BorderFactory.createTitledBorder("Time:"));


        _getDevLifeTime = new JTextField("10", 10);
        _getDevLifeTime.setHorizontalAlignment(JTextField.CENTER);

        _devLifeTimePanel = new JPanel();
        _devLifeTimePanel.setLayout(new FlowLayout());
        _devLifeTimePanel.add(_getDevLifeTime);
        _devLifeTimePanel.setBorder(BorderFactory.createTitledBorder("Life Time:"));


        _getManLifeTime = new JTextField("10", 10);
        _getManLifeTime.setHorizontalAlignment(JTextField.CENTER);

        _manLifeTimePanel = new JPanel();
        _manLifeTimePanel.setLayout(new FlowLayout());
        _manLifeTimePanel.add(_getManLifeTime);
        _manLifeTimePanel.setBorder(BorderFactory.createTitledBorder("Life Time:"));

        //<---------ComboBoxes--------->
        _devChance = new JComboBox<Integer>();
        for (int i = 0; i <= 100; i += 10) {
            _devChance.addItem(i);
        }
        _devChance.setSelectedItem(50);
        _devChance.setPreferredSize(new Dimension(130, 30));
        ((JLabel)_devChance.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        _devChancePanel = new JPanel();
        _devChancePanel.setLayout(new FlowLayout());
        _devChancePanel.add(_devChance);
        _devChancePanel.setBorder(BorderFactory.createTitledBorder("Chance:"));


        _manPercent = new JComboBox<Integer>();
        for (int i = 0; i <= 100; i += 10) {
            _manPercent.addItem(i);
        }
        _manPercent.setSelectedItem(50);
        _manPercent.setPreferredSize(new Dimension(130, 30));
        ((JLabel)_manPercent.getRenderer()).setHorizontalAlignment(JLabel.CENTER);

        _manPercentPanel = new JPanel();
        _manPercentPanel.setLayout(new FlowLayout());
        _manPercentPanel.add(_manPercent);
        _manPercentPanel.setBorder(BorderFactory.createTitledBorder("Percent:"));

        //<---------Welcome Label--------->
        _welcomeLabel = new JLabel("Welcome");
        _welcomeLabel.setFont(new Font("Impact", Font.BOLD, 16));

        _welcomePanel = new JPanel();
        _welcomePanel.setLayout(new FlowLayout());
        _welcomePanel.add(_welcomeLabel);

        //<------------------>
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

        //<---------Adds--------->
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
        add(_centerPanel, BorderLayout.CENTER);
        add(_submitButton, BorderLayout.PAGE_END);
    }

    public MenuPanel(Habitat habitat) {
        super();
        _habitat = habitat;
    }
}
