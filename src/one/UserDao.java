package one;

import org.springframework.util.ObjectUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    private ConnectionMaker connectionMaker;
    private JdbcContext jdbcContext;

    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
        this.jdbcContext = new JdbcContext();
        this.jdbcContext.setConnection(connectionMaker);
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        this.jdbcContext.workWithStatementStrategy(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(
                            "insert into users(id, name, password) values(?, ?, ?)");
                    ps.setString(1, user.getId());
                    ps.setString(2, user.getName());
                    ps.setString(3, user.getPassword());
                    return ps;
                }
        );
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
        this.jdbcContext.workWithStatementStrategy(
                connection -> connection.prepareStatement("delete from users")
        );
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
}
