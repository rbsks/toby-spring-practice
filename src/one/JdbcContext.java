package one;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcContext {

    private ConnectionMaker connectionMaker;

    public void setConnection(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    /**
     * template/callback 패턴
     * connection을 가져오고 statement를 실행하고 예외를 처리하고 자원을 반납하는 고정된 작업 흐름을 재사용하고
     * StatementStrategy을 바꿔가면서 사용하는 패턴
     * 전략 패턴과 비슷하지만 전략 패턴은 여러 개의 메소드를 가진 인터페이스를 사용할 수 있다면 템플릿/콜백 패턴은 보통 단일 메소드를 가진
     * 인터페이스를 사용한다.
     *
     * @param statementStrategy 특정 로직을 담은 메소드를 실행시키는 것을 목적으로 전달되는 오브젝트(functional object)
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void workWithStatementStrategy(StatementStrategy statementStrategy) throws ClassNotFoundException, SQLException {
        try(Connection connection = connectionMaker.makeConnection();
            PreparedStatement ps = statementStrategy.makePreparedStatement(connection)) {

            ps.executeUpdate();

        } catch (SQLException e) {
            throw e;
        }
    }

}
