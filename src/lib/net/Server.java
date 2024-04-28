package lib.net;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread{
    public static final int PORT = 9423;
    private static ArrayList<ClientSocket> $sockets = new ArrayList<>();
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
                    $sockets.addLast(new ClientSocket(socket, _id++));
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
        private Integer _clientID;
        private Socket _socket;

        private boolean _isRunning = false;
        
        public ClientSocket(Socket socket, int id) throws IOException {
            _socket = socket;
            _in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            _out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
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
                    Server.$sockets.remove(this);
                    send("stop");
                    updateConnections();
                    close();
                    _isRunning = false;
                }
                case "get cons" -> {
                    send("cur cons");
                    send(getConnections());
                }
            }
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
            for (ClientSocket socket : Server.$sockets) {
                socket.send("cur cons"); 
                socket.send(cons);
            }
            System.out.println($sockets);
        }

        private static String getConnections() {
            String res = "";
            for (ClientSocket socket : $sockets) {
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