package one;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        UserDao userDao = new DUserDao();

        User user = new User();
        user.setId("rbsks147");
        user.setName("한규빈");
        user.setPassword("11111");

        userDao.add(user);

        System.out.println(user.getId() + " 등록 성공");

        User getUser = userDao.get(user.getId());
        System.out.println(getUser.getName());
        System.out.println(getUser.getPassword());
        System.out.println(getUser.getId() + " 조회 성공");
    }
}
