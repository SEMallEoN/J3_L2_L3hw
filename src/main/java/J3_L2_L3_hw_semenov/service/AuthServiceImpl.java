package J3_L2_L3_hw_semenov.service;

import J3_L2_L3_hw_semenov.inter.AuthService;

import java.util.List;

public class AuthServiceImpl implements AuthService {

    private List<UserEntity> userList;

    public AuthServiceImpl() {
//        this.userList = new LinkedList<>();
//        this.userList.add(new UserEntity("nick1","login1", "passw1"));
//        this.userList.add(new UserEntity("nick2","login2", "passw2"));
//        this.userList.add(new UserEntity("nick3","login3", "passw3"));
    }

    @Override
    public void start() {
        System.out.println("Сервис аутентификации запущен");
    }

    @Override
    public UserEntity getUser(String login, String password) {
        DBServiceImpl dbService = new DBServiceImpl();
        UserEntity user = dbService.findUser(login);
        if ((user.getName().equals(login)) & (user.getPassword().equals(password))) {
            return user;
        } else {
            return null;
        }
    }


    @Override
    public void stop() {

    }
}
