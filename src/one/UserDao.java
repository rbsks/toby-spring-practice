package one;

import java.sql.*;

public class UserDao {

    private ConnectionMaker connectionMaker;

    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
//        this.connectionMaker = new DConnectionMaker();
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        // db connection
        Connection connection = connectionMaker.makeConnection();

        // add user
        PreparedStatement ps = connection.prepareStatement(
                "insert into users(id, name, password) values(?, ?, ?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        // connection close
        ps.close();
        connection.close();
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        // db connection
        Connection connection = connectionMaker.makeConnection();

        // get user
        PreparedStatement ps = connection.prepareStatement(
                "select * from users where id = ?");
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        rs.next();
        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));

        // connection close
        rs.close();
        ps.close();
        connection.close();

        return user;
    }

    // 상속을 통한 확장
//    public abstract Connection getConnection() throws ClassNotFoundException, SQLException;
//     관심사의 분리
//    private Connection getConnection() throws ClassNotFoundException, SQLException {
//        Class.forName("com.mysql.cj.jdbc.Driver");
//        Connection connection = DriverManager.getConnection(
//                "jdbc:mysql://localhost:3306/toby?autoReconnect=True", "toby_use", "db1234");
//        return connection;
//    }
}
