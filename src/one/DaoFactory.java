package one;

public class DaoFactory {

    public UserDao userDao() {
        UserDao userDao = new UserDao(getConnectionMaker());
        return userDao;
    }

    private ConnectionMaker getConnectionMaker() {
        ConnectionMaker connectionMaker = new DConnectionMaker();
        return connectionMaker;
    }
}
