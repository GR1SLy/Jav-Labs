package com.lab.lib.net;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import java.util.ArrayList;
import java.util.LinkedList;

import com.lab.lib.employee.Developer;
import com.lab.lib.employee.Employee;
import com.lab.lib.employee.Manager;

public class Client {

    private Socket _socket;
    private int _id;

    private WriteMsg _write;

    private ClientFrame _clientFrame;
    
    public Client(ClientFrame clientFrame) throws IOException {
        super();
        _socket = new Socket("localhost", Server.PORT);
        ReadMsg _read = new ReadMsg(_socket);
        _read.start();
        _write = new WriteMsg(_socket);
        _write.start();
        _clientFrame = clientFrame;
        System.err.println("Client created");
    }

    public void close() {
        try {
            _write.sendString("stop");
            _socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void idToSend(Integer id, Integer what) {
        try {
            _write.sendString("send to");
            _write.sendString(id.toString());
            _write.sendString(what.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Integer getID() { return _id; }

    private class ReadMsg extends Thread {
        private BufferedReader _in;
        private boolean _isRunning = false;

        public ReadMsg(Socket socket) throws IOException {
            super();
            System.err.println("Reader created");
        }

        public void start() {
            _isRunning = true;
            super.start();
        }

        public synchronized void run() {
            try {
                _in = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
            } catch (IOException e) {}
            while(_isRunning) {
                String msg;
                try {
                    msg = _in.readLine();
                    checkCommand(msg);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        private void checkCommand(String command) throws ClassNotFoundException, IOException {
            switch (command) {
                case "stop" -> { Client.this.close(); _isRunning = false; }
                case "cur cons" -> { 
                    String msg = _in.readLine();
                    catchConnections(msg); 
                }
                case "ur id" -> { 
                    String msg = _in.readLine();
                    _id = Integer.parseInt(msg); 
                    System.err.println("ID::" + _id); 
                }
                case "gimme emp" -> { 
                    int what = Integer.parseInt(_in.readLine());
                    boolean first;
                    String line = _in.readLine();
                    if (line.equals("1")) first = true; else first = false;
                    LinkedList<Employee> emp = _clientFrame.getEmployees(what);
                    System.err.println("Requested list: " + emp);
                    String semp = listToString(emp);
                    _write.sendString(semp);
                    if (!first) _write.sendString(semp);
                }
                case "catch emp" -> {
                    System.err.println("Reading emp...");
                    String semp = _in.readLine();
                    LinkedList<Employee> employees = stringToList(semp);
                    System.err.println("Read emp: " + employees);
                    _clientFrame.setEmployees(employees);
                }
                default -> System.err.println(command);
            }
        }

        private void catchConnections(String cons) {
            if (cons.length() == 0) return;
            StringBuilder sb = new StringBuilder();
            ArrayList<Integer> ids = new ArrayList<>();
            int i = 0;
            while (i < cons.length()) {
                sb.append("ID::");
                String id = "";
                while (cons.charAt(i) != ' ') { 
                    sb.append(cons.charAt(i));
                    id += cons.charAt(i++);
                }
                ids.add(Integer.parseInt(id));
                i++;
                sb.append(" PORT::");
                while (cons.charAt(i) != 'Z') sb.append(cons.charAt(i++));
                i++;
                if (i < cons.length()) sb.append('\n');
            }
            sb.delete(i - 1, i);
            String res = sb.toString();
            _clientFrame.setConnections(res, ids);
        }

        private String listToString(LinkedList<Employee> emp) {
            StringBuilder sb = new StringBuilder();
            for (Employee employee : emp) {
                sb.append(employee.getBirthTime() + ":" + employee.getX() + ":" + employee.getY() + ":");
                if (employee instanceof Developer) sb.append("D");
                else sb.append("M");
            }
            return sb.toString();
        }

        private LinkedList<Employee> stringToList(String emp) {
            LinkedList<Employee> list = new LinkedList<>();
            int i = 0;
            String sbt, sx, sy;
            int bt, x, y;
            Rectangle rect = _clientFrame.getGraphBounds();
            while (i < emp.length()) {
                sbt = sx = sy = "";
                while (emp.charAt(i) != ':') sbt += emp.charAt(i++);
                bt = Integer.parseInt(sbt);
                i++;
                while (emp.charAt(i) != ':') sx += emp.charAt(i++);
                x = Integer.parseInt(sx);
                i++;
                while (emp.charAt(i) != ':') sy += emp.charAt(i++);
                y = Integer.parseInt(sy);
                i++;
                if (emp.charAt(i) == 'D') list.addLast(new Developer(rect.width, rect.height, bt, _clientFrame.findID(), x, y));
                else list.addLast(new Manager(rect.width, rect.height, bt, _clientFrame.findID(), x, y));
                i++;
            }
            return list;
        }
    }

    private class WriteMsg extends Thread {
        private BufferedWriter _out;
        private BufferedReader _reader;
        private boolean _isRunning = false;

        public WriteMsg(Socket socket) throws IOException {
            super();
            _reader = new BufferedReader(new InputStreamReader(System.in));
            System.err.println("Writer created");
        }

        public void start() {
            _isRunning = true;
            super.start();
        }

        public synchronized void run() {
            try {
                _out = new BufferedWriter(new OutputStreamWriter(_socket.getOutputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            while(_isRunning) {
                try {
                    String line = _reader.readLine();
                    if (line.equals("stop")) {
                        System.out.println("Closing...");
                        sendString("stop");
                        _isRunning = false;
                    }
                    sendString(line);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void sendString(String msg) throws IOException {
            _out.write(msg + '\n');
            _out.flush();
        }
    }
}
