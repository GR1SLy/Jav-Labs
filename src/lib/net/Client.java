package lib.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

import lib.employee.Employee;

public class Client {

    private Socket _socket;
    private int _id;

    private WriteMsg _write;

    private ClientFrame _clientFrame;
    
    public Client(ClientFrame clientFrame) throws IOException {
        super();
        System.err.println("Trying to create client");
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
            _write.send("stop");
            _socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void idToSend(Integer id, Integer what) {
        try {
            _write.send("send to");
            _write.send(id.toString());
            _write.send(what.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Integer getID() { return _id; }

    private class ReadMsg extends Thread {
        private BufferedReader _in;
        private ObjectInputStream _oin;

        public ReadMsg(Socket socket) throws IOException {
            super();
            System.err.println("Trying to create reader");
            InputStream is = socket.getInputStream();
            // _oin = new ObjectInputStream(is);
            _in = new BufferedReader(new InputStreamReader(is));
            System.err.println("Reader created");
        }

        @SuppressWarnings("unchecked")
        public synchronized void run() {
            /* try {
                _oin = new ObjectInputStream(_socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            } */
            while(true) {
                try {
                    String msg = _in.readLine();
                    if (msg.equals("stop")) {
                        Client.this.close();
                        break;
                    } else if (msg.equals("cur cons")) { msg = _in.readLine(); catchConnections(msg); }
                    else if (msg.equals("ur id")) { msg = _in.readLine(); _id = Integer.parseInt(msg); System.err.println("ID::" + _id); }
                    else if (msg.equals("gimme emp")) { int what = Integer.parseInt(_in.readLine()); _write.sendObject(_clientFrame.getEmployees(what)); }
                    else if (msg.equals("catch emp")) {
                        System.err.println("Trying to create obj reader");
                        ObjectInputStream oin = new ObjectInputStream(_socket.getInputStream());
                        System.err.println("Trying to read list");
                        LinkedList<Employee> employees = (LinkedList<Employee>)oin.readObject();
                        System.err.println("\nReaded obj:\n" + employees);
                        _clientFrame.setEmployees(employees);
                    }
                    else System.out.println(msg);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
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
                ids.addLast(Integer.parseInt(id));
                i++;
                sb.append(" PORT::");
                while (cons.charAt(i) != 'Z') sb.append(cons.charAt(i++));
                i++;
                if (i < cons.length()) sb.append('\n');
            }
            sb.delete(i - 1, i);
            String res = sb.toString();
            // System.out.println(res);
            _clientFrame.setConnections(res, ids);
        }
    }

    private class WriteMsg extends Thread {
        private BufferedWriter _out;
        private BufferedReader _reader;
        private ObjectOutputStream _oout;

        public WriteMsg(Socket socket) throws IOException {
            super();
            System.err.println("Trying to create writer");
            _out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            _reader = new BufferedReader(new InputStreamReader(System.in));
            _oout = new ObjectOutputStream(_socket.getOutputStream());
            System.err.println("Writer created");
        }

        public synchronized void run() {
            while(true) {
                try {
                    String line = _reader.readLine();
                    if (line.equals("stop")) {
                        System.out.println("Closing...");
                        send("stop");
                        break;
                    }
                    send(line);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        void sendObject(LinkedList<Employee> obj) {
            try {
                _oout.writeObject(obj);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void send(String msg) throws IOException {
            _out.write(msg + "\n");
            _out.flush();
        }
    }
}
