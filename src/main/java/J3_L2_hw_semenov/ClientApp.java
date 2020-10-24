package J3_L2_hw_semenov;

import J3_L2_hw_semenov.handler.ClientHandler;
import J3_L2_hw_semenov.inter.DBService;
import J3_L2_hw_semenov.service.ClientService;

import java.io.IOException;

public class ClientApp {
    public static void main(String[] args) throws IOException {
        new ClientService();
    }
}
