package J3_L2_hw_semenov.service;

import J3_L2_hw_semenov.handler.ClientHandler;
import J3_L2_hw_semenov.inter.AuthService;
import J3_L2_hw_semenov.inter.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class ServerImpl implements Server {

    private List<ClientHandler> clients;
    private AuthServiceImpl authService;

    public ServerImpl() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT); // Создаем сокет на сервере
            authService = new AuthServiceImpl(); // Создаем список авторизованных клиентов
            authService.start(); // Сообщение о запуске службы авторизации клиентов
            clients = new LinkedList<>(); // Создаем список клиентов
            // Цикл подключения клиентов
            while (true) { // Подключение клиентов
                System.out.println("Ожидаем подключения клиентов");
                Socket socket = serverSocket.accept(); // Ожидание подключения клиента
                System.out.println("Клиент подключился");
                new ClientHandler(this, socket); // Создаем для каждого клиент свой обработчик
            }
        } catch (IOException e) {
            System.out.println("Проблема на сервере");
        } finally {
            if (authService != null) {
                authService.stop(); // Сообщение об остановке сервера аутентификации
            }
        }
    }

    @Override
    public boolean isNickBusy(String Nick) {
        return false;
    }

    @Override
    public void broadcastClientList() {

    }

    @Override
    public void broadcastMsg(String msg) {

    }

    @Override
    public void subcribe(ClientHandler client) {

    }

    @Override
    public void unsubcribe(ClientHandler client) {

    }

    @Override
    public void sndMsgToClient(ClientHandler from, String to, String msg) {

    }

    @Override
    public AuthServiceImpl getAuthService() {
        return authService;
    }
}

