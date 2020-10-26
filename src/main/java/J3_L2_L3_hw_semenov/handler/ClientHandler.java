package J3_L2_L3_hw_semenov.handler;

import J3_L2_L3_hw_semenov.inter.DBService;
import J3_L2_L3_hw_semenov.inter.Server;
import J3_L2_L3_hw_semenov.service.DBServiceImpl;
import J3_L2_L3_hw_semenov.service.UserEntity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private Server server;
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private UserEntity user;

    public String getNick(){
        return user.getName();
    }

    public ClientHandler(Server server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            this.dis = new DataInputStream(socket.getInputStream());
            this.dos = new DataOutputStream(socket.getOutputStream());
            this.user = user;
            new Thread(() -> {
                try {
                    long a = System.currentTimeMillis();
                    while ((a - System.currentTimeMillis()) > -120000) {
                        if (authentication()) {
                            readMessage();
                        } else {
                            System.out.println("The client is not logged in within 120 seconds");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    closeConnection();
                }
            }).start();
        } catch (IOException e) {
            throw new RuntimeException("Problems creating a client handler");
        }
    }

    private boolean authentication() throws IOException {
        while (true) {
            String str = dis.readUTF();
            if (str.startsWith("/auth")) {
                String[] dataArray = str.split("\\s");
                user = server.getAuthService().getUser(dataArray[1], dataArray[2]);
                if (user != null) {
                    if (!server.isNickBusy(user.getName())) {
                        sendMsg("/authOk " + user.getName());
                        this.user = user;
                        server.broadcastMsg(this.user + " Join to chat");
                        server.subcribe(this);
                        return true;
                    } else {
                        server.broadcastMsg("You are logged in");
                        return false;
                    }
                } else {
                    server.broadcastMsg("Incorrect password or login");
                    return false;
                }
            }
        }
    }

    public void sendMsg(String msg) {
        try {
            dos.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readMessage() throws IOException {
        while (true) {
            String clientStr = dis.readUTF();
            if (clientStr.startsWith("/")) {
                if (clientStr.equals("/exit")) {
                    return;
                }
                if (clientStr.startsWith("/w")) {
                    String[] strArray = clientStr.split("\\s");
                    String nickName = strArray[1];
                    String msg = clientStr.substring(4 + nickName.length());
                    server.sndMsgToClient(this, nickName, msg);
                }
                if (clientStr.startsWith("/changeNickTo")){
                    String[] strArray = clientStr.split("\\s");
                    String oldNick = user.getName();
                    DBService dbService = new DBServiceImpl();
                    dbService.updateUserName(user, strArray[1]);
                    server.broadcastMsg(oldNick + " Change Nick To " + user.getName());
                }
                continue;
            }
            sendMsg(user.getName() + ": " + clientStr);
        }
    }


    private void closeConnection() {
        server.unsubcribe(this);
        server.broadcastMsg(user.getName() + ": out from chat");

        try {
            dis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
