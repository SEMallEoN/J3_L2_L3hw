package J3_L2_L3_hw_semenov.inter;

import J3_L2_L3_hw_semenov.service.UserEntity;

public interface AuthService {

    void start();
    UserEntity getUser(String name, String password);
    void stop();

}
