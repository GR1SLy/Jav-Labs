package lib.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

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
    }

    public void close() {
        try {
            _write.send("stop");
            _socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Integer getID() { return _id; }

    private class ReadMsg extends Thread {
        private BufferedReader _in;

        public ReadMsg(Socket socket) throws IOException {
            super();
            _in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }

        public synchronized void run() {
            while(true) {
                try {
                    String msg = _in.readLine();
                    if (msg.equals("stop")) {
                        Client.this.close();
                        break;
                    } else if (msg.equals("cur cons")) { msg = _in.readLine(); catchConnections(msg); }
                    else if (msg.equals("ur id")) { msg = _in.readLine(); _id = Integer.parseInt(msg); System.err.println("ID:::" + _id); }
                    else System.out.println(msg);
                } catch (IOException e) {
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

        public WriteMsg(Socket socket) throws IOException {
            super();
            _out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            _reader = new BufferedReader(new InputStreamReader(System.in));
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

        private void send(String msg) throws IOException {
            _out.write(msg + "\n");
            _out.flush();
        }
    }
}
