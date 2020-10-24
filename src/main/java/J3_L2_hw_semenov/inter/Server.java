package J3_L2_hw_semenov.inter;

import J3_L2_hw_semenov.handler.ClientHandler;
import J3_L2_hw_semenov.service.AuthServiceImpl;

public interface Server {
    int PORT = 8189;

    boolean isNickBusy(String Nick);

    void broadcastClientList();

    void broadcastMsg(String msg);

    void subcribe(ClientHandler client);

    void unsubcribe(ClientHandler client);

    void sndMsgToClient(ClientHandler from, String to, String msg);

    AuthServiceImpl getAuthService();
}
