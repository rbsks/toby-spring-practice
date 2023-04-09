package one;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

// 애플리케이션 컨텍스트 또는 빈 팩토리가 사용할 설정정보라는 표시
@Configuration
public class DaoFactory {

    // 오브젝트 생성을 담당하는 IoC용 메소드라는 표시
    @Bean
    public UserDaoJdbc userDaoJdbc() {
        UserDaoJdbc userDaoJdbc = new UserDaoJdbc(dataSource(), jdbcTemplate());
        return userDaoJdbc;
    }

    @Bean
    public ConnectionMaker getConnectionMaker() {
        ConnectionMaker connectionMaker = new DConnectionMaker();
        return connectionMaker;
    }

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

        dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        dataSource.setUrl("jdbc:mysql://localhost:3306/toby?autoReconnect=True");
        dataSource.setUsername("toby_use");
        dataSource.setPassword("db1234");

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}
