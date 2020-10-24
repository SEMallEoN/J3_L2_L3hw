package J3_L2_hw_semenov.inter;

public interface AuthService {

    void start();
    String getNick(String login, String password);
    void stop();

}
