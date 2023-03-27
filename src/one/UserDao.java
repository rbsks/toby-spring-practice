package one;

import com.mysql.cj.jdbc.PreparedStatementWrapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ObjectUtils;

import java.sql.*;
import java.util.Objects;

public class UserDao {

    private ConnectionMaker connectionMaker;

    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
//        this.connectionMaker = new DConnectionMaker();
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        // db connection
        Connection connection = connectionMaker.makeConnection();
        PreparedStatement ps = connection.prepareStatement(
                "insert into users(id, name, password) values(?, ?, ?)");
        try (connection;ps) {

            // add user

            ps.setString(1, user.getId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw e;
        }
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        // db connection
        Connection connection = connectionMaker.makeConnection();
        PreparedStatement ps = connection.prepareStatement("select * from users where id = ?");

        try {
            ps.setString(1, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ResultSet rs = ps.executeQuery();

        try (connection;ps;rs) {
            User user = null;
            if (rs.next()) {
                user = new User();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
            }

            if (ObjectUtils.isEmpty(user)) throw new SQLException("user not found");

            return user;
        } catch (SQLException e) {
            throw e;
        }
    }

    public void deleteAll() throws ClassNotFoundException, SQLException {
        Connection connection = connectionMaker.makeConnection();
        PreparedStatement ps = connection.prepareStatement("delete from users");
        try(connection;ps) {
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    public int getCount() throws ClassNotFoundException, SQLException {
        Connection connection = connectionMaker.makeConnection();
        PreparedStatement ps = connection.prepareStatement("select count(id) from users");
        ResultSet rs = ps.executeQuery();
        try (connection;ps;rs) {
            rs.next();
            int count = rs.getInt(1);

            return count;
        } catch (SQLException e) {
            throw e;
        }
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
