package J3_L2_hw_semenov;

import J3_L2_hw_semenov.handler.ClientHandler;
import J3_L2_hw_semenov.inter.DBService;
import J3_L2_hw_semenov.service.ClientService;
import J3_L2_hw_semenov.service.DBServiceImpl;
import J3_L2_hw_semenov.service.ServerImpl;

import java.io.IOException;

public class ServerApp {
    public static void main(String[] args) throws IOException {
        new ServerImpl();
    }
}
