package one;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * JdbcTemplate을 사용하여 database에 접근하는 UserDaoJdbc
 */
public class UserDaoJdbc implements UserDao {

    /**
     * JdbcContext -> JdbcTemplate으로 변경
     * 여태까지 템플릿/콜백 패턴으로 리팩토링 한 부분은 JdbcTemplate 내부를 이해하기 위한 목적이었음
     * JdbcTemplate도 database에 커넥션하는 부분과 자원을 반납하는 부분과 같이 반복되는 부분을 템플릿/콜백 패턴을ㅓㅇ적용하여 제공하고 있음
     */
    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

    public UserDaoJdbc(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(User user) {
        this.jdbcTemplate.update("insert into users(id, name, password) values(?, ?, ?)",
                user.getId(), user.getName(), user.getPassword());
    }

    public User get(String id) {
        User getUser = this.jdbcTemplate.queryForObject("select * from users where id =?",
                new Object[]{id},
                (rs, rowNum) -> {
                    User user = new User();
                    user.setId(rs.getString("id"));
                    user.setName(rs.getString("name"));
                    user.setPassword(rs.getString("password"));
                    return user;
                });
        return getUser;
    }

    public void deleteAll() {
        this.jdbcTemplate.update("delete from users");
    }

    public int getCount() {
        Integer query = this.jdbcTemplate.query(
                con -> con.prepareStatement("select count(id) from users"),
                rs -> {
                    rs.next();
                    return rs.getInt(1);
                });
        return query;
    }
}
