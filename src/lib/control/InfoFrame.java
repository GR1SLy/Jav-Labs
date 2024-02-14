package lib.control;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.*;

class InfoFrame extends JFrame {
    private JPanel _centerPanel, _text1Panel, _text2Panel, _text3Panel, _text4Panel, _text5Panel, _text6Panel;
    private JButton _closeButton;
    private JLabel _text1, _text2, _text3, _text4, _text5, _text6;
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        _closeButton = new JButton("OK");
        _closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        add(_closeButton, BorderLayout.PAGE_END);

        _centerPanel = new JPanel();
        _centerPanel.setLayout(new GridLayout(6, 1));
        add(_centerPanel, BorderLayout.CENTER);

        _text1Panel = new JPanel();
        _text1Panel.setLayout(new FlowLayout());
        _text1 = new JLabel("SIMULATION CANCELLED");
        _text1.setFont(new Font("Impact", Font.BOLD, 16));
        _text1.setForeground(Color.RED);
        _text1Panel.add(_text1);
        _centerPanel.add(_text1Panel);

        _text2Panel = new JPanel();
        _text2Panel.setLayout(new FlowLayout());
        _text2 = new JLabel();
        _text2Panel.add(_text2);
        _centerPanel.add(_text2Panel);
        
        _text3Panel = new JPanel();
        _text3Panel.setLayout(new FlowLayout());
        _text3 = new JLabel("Current statistics:");
        _text3.setFont(new Font("Lucida Console", Font.ITALIC, 14));
        _text3.setForeground(Color.MAGENTA);
        _text3Panel.add(_text3);
        _centerPanel.add(_text3Panel);

        _text4Panel = new JPanel();
        _text4Panel.setLayout(new FlowLayout());
        _text4 = new JLabel();
        _text4Panel.add(_text4);
        _centerPanel.add(_text4Panel);
        
        _text5Panel = new JPanel();
        _text5Panel.setLayout(new FlowLayout());
        _text5 = new JLabel();
        _text5Panel.add(_text5);
        _centerPanel.add(_text5Panel);
        
        _text6Panel = new JPanel();
        _text6Panel.setLayout(new FlowLayout());
        _text6 = new JLabel();
        _text6Panel.add(_text6);
        _centerPanel.add(_text6Panel);
    }

    InfoFrame(int x, int y) {
        setBounds(x / 2 - 150, y / 2 - 150, 300, 300);
    }

    void createFrame(long finishTime, int devCount, int manCount, int empCount) {
        setTitle("INFORMATION");
        _text2.setText("Simulation time: " + finishTime + " seconds");
        _text4.setText("Developers created: " + devCount);
        _text5.setText("Managers created: " + manCount);
        _text6.setText("Total employees created: " + empCount);
        setVisible(true);
    }
}
