package lib.net;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;

import lib.employee.Employee;

public class Server extends Thread{
    public static final int PORT = 9423;
    // private static ArrayList<ClientSocket> $sockets = new ArrayList<>();
    private static HashMap<Integer, ClientSocket> $sockets = new HashMap<>();
    private ServerSocket _serverSocket;
    private static int _id = 0;
    
    public Server() throws IOException {
        super();
        _serverSocket = new ServerSocket(PORT);
        System.out.println("Server \"" + _serverSocket + "\" started");
    }

    public static int getID() { return _id; }

    private void shedule() throws IOException {
        try {
            while(true) {
                Socket socket = _serverSocket.accept();
                try {
                    // $sockets.addLast(new ClientSocket(socket, _id++));
                    $sockets.put(_id, new ClientSocket(socket, _id++));
                    ClientSocket.updateConnections();
                    System.out.println("Client \"" + socket + "\" connected");
                } catch (IOException e) { socket.close(); }
            }
        } finally { _serverSocket.close(); }
    }

    public void run() {
        try {
            shedule();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientSocket extends Thread {
        private BufferedReader _in;
        private BufferedWriter _out;
        private ObjectOutputStream _oout;
        private ObjectInputStream _oin;
        private Integer _clientID;
        private Socket _socket;

        private boolean _isRunning = false;
        
        public ClientSocket(Socket socket, int id) throws IOException {
            _socket = socket;
            _in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            _out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            _oin = new ObjectInputStream(_socket.getInputStream());
            _oout = new ObjectOutputStream(_socket.getOutputStream());
            _clientID = id;
            send("ur id");
            send(_clientID.toString());
            start();
        }

        @Override
        public void start() {
            _isRunning = true;
            super.start();
        }

        public void run() {
            try {
                String line;
                while(_isRunning) {
                    line = _in.readLine();
                    checkCommand(line);
                }
            } catch (IOException e) {}
        }

        private void checkCommand(String command) {
            switch (command) {
                case "stop" -> {
                    System.out.println("Connection " + _socket + " closed");
                    Server.$sockets.remove(this._clientID);
                    send("stop");
                    updateConnections();
                    close();
                    _isRunning = false;
                }
                case "get cons" -> {
                    send("cur cons");
                    send(getConnections());
                }
                case "send to" -> {
                    try {
                        int id = Integer.parseInt(_in.readLine());
                        int what = Integer.parseInt(_in.readLine());
                        objectsExchange(id, what);
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        private void objectsExchange(int id, int what) throws ClassNotFoundException, IOException {
            System.out.println("Starting exchanging");
            ClientSocket secondSocket = Server.$sockets.get(id);
            System.out.println("Cur socket: " + _socket + "\n2nd socket: " + secondSocket);
            System.out.println("Trying to get emp1 list");
            LinkedList<Employee> emp1 = requsetEmployees(what);
            System.out.println("\nEmp from 1st socket:\n" + emp1);
            System.out.println("Trying to get emp2 list");
            int what2;
            if (what == 0) what2 = 1; else what2 = 0;
            LinkedList<Employee> emp2 = secondSocket.requsetEmployees(what2);
            System.out.println("Emp from 2nd socket:\n" + emp2);
            sendEmployees(emp2);
            secondSocket.sendEmployees(emp1);
        }

        @SuppressWarnings("unchecked")
        private LinkedList<Employee> requsetEmployees(Integer what) throws ClassNotFoundException, IOException {
            send("gimme emp");
            send(what.toString());
            System.out.println("Trying to read list");
            LinkedList<Employee> list = (LinkedList<Employee>)_oin.readObject();
            System.out.println("List read");
            return list;
        }
        
        private void sendEmployees(LinkedList<Employee> list) throws IOException {
            send("catch emp");
            _oout.writeObject(list);
        }

        private void send(String msg) {
            try {
                _out.write(msg + "\n");
                _out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private static void updateConnections() {
            String cons = getConnections();
            for (ClientSocket socket : Server.$sockets.values()) {
                socket.send("cur cons"); 
                socket.send(cons);
            }
            System.out.println($sockets);
        }

        private static String getConnections() {
            String res = "";
            for (ClientSocket socket : $sockets.values()) {
                res += socket + "Z";
            }
            return res;
        }

        private void close() {
            try {
                _socket.close();
                _in.close();
                _out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return "" + _clientID + " " + _socket.getPort();
        }
    }
}