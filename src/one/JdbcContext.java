package one;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcContext {

    private ConnectionMaker connectionMaker;

    public void setConnection(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void workWithStatementStrategy(StatementStrategy statementStrategy) throws ClassNotFoundException, SQLException {
        Connection connection = connectionMaker.makeConnection();
        PreparedStatement ps = statementStrategy.makePreparedStatement(connection);

        try(connection;ps) {

            ps.executeUpdate();

        } catch (SQLException e) {
            throw e;
        }
    }

}
