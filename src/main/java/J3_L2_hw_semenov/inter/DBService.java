package J3_L2_hw_semenov.inter;

import J3_L2_hw_semenov.service.UserEntity;

import java.util.ArrayList;

public interface DBService {

    ArrayList<UserEntity> findAll();
    UserEntity findUser(String login);
    boolean add(UserEntity user);
    boolean updateUserName(String login, String newNick);
}
