package J3_L2_L3_hw_semenov.inter;

import J3_L2_L3_hw_semenov.service.UserEntity;

public interface DBService {

    UserEntity findUser(String login);
    boolean add(UserEntity user);
    void updateUserName(UserEntity user, String newName);
}
