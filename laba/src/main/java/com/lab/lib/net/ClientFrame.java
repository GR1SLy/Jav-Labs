package com.lab.lib.net;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import com.lab.lib.control.Habitat;
import com.lab.lib.employee.Employee;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class ClientFrame extends JFrame {

    private JTextPane _conPane;
    private Document _doc;
    
    private JComboBox<String> _idBox;
    private JComboBox<String> _nameBox;
    private ArrayList<Integer> _ids;
    private JButton _sendButton;

    private Client _client;

    private Habitat _habitat;

    {
        setTitle("Client");
        setLayout(new GridLayout(4, 1));

        
        JLabel clientLabel = new JLabel("Current clients:", JLabel.CENTER);
        JPanel clientPanel = new JPanel();
        clientPanel.setLayout(new BorderLayout());
        clientPanel.add(clientLabel, BorderLayout.PAGE_END);
        add(clientPanel);
        
        _conPane = new JTextPane();
        _conPane.setEditable(false);
        _conPane.setFocusable(false);
        _conPane.setPreferredSize(new Dimension(150, 50));
        _conPane.setMaximumSize(new Dimension(150, 50));
        JScrollPane pane = new JScrollPane(_conPane);
        JPanel panePanel = new JPanel();
        panePanel.setLayout(new FlowLayout());
        panePanel.add(pane);
        add(panePanel);
        
        _doc = _conPane.getDocument();
        
        JPanel sendPanel = new JPanel();
        sendPanel.setLayout(new FlowLayout());
        
        JLabel sendLabel = new JLabel("Send ");
        sendPanel.add(sendLabel);
        
        String[] names = { "Developers", "Managers" };
        _nameBox = new JComboBox<>(names);
        sendPanel.add(_nameBox);
        
        JLabel toLabel = new JLabel(" to ");
        sendPanel.add(toLabel);
        
        _idBox = new JComboBox<>();
        _idBox.addItem("-");
        sendPanel.add(_idBox);
        add(sendPanel);

        _client = new Client(this);

        _sendButton = new JButton("Send");
        _sendButton.addActionListener(e -> {
            if (_idBox.getSelectedIndex() != 0) {
                int choise = _nameBox.getSelectedIndex();
                int id = Integer.parseInt(_idBox.getSelectedItem().toString());
                _client.idToSend(id, choise);
            }
        });
        JPanel butPanel = new JPanel();
        butPanel.setLayout(new FlowLayout());
        butPanel.add(_sendButton);
        add(butPanel);


        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            _client.close();
        }));

    }

    public ClientFrame(Habitat hbt) throws IOException {
        super();
        _habitat = hbt;
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        System.err.println("Client Frame created");
    }

    public void setConnections(String cons, ArrayList<Integer> ids) {
        try {
            _doc.remove(0, _doc.getLength());
            _doc.insertString(0, cons, null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        if (_ids != null) clearBoxes();
        _ids = ids;
        fillBoxes();
    }

    private void fillBoxes() {
        for (int i = 0; i < _ids.size(); ++i) _idBox.addItem(_ids.get(i).toString());
        _idBox.removeItem(_client.getID().toString());
    }

    private void clearBoxes() {
        for (int i = 0; i < _ids.size(); ++i) _idBox.removeItem(_ids.get(i).toString());
    }

    int findID() {
        return _habitat.findID(new Random());
    }

    public void showFrame() { setVisible(true); }

    LinkedList<Employee> getEmployees(int what) { return _habitat.getEmployees(what); }

    void setEmployees(LinkedList<Employee> employees) { _habitat.setEmployees(employees); }

    Rectangle getGraphBounds() { return _habitat.getGraphBounds(); }
}