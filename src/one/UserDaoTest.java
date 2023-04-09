package one;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoFactory.class)
public class UserDaoTest {

    @Autowired
    private UserDaoJdbc userDaoJdbc;

    /**
     * 유저 등록 및 조회 테스트
     *
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Test
    public void addAndGet() {

        // users table을 비우는 작업 후 검증
        userDaoJdbc.deleteAll();
        assertThat(userDaoJdbc.getCount(), is(0));

        User user = new User("rbsks147", "한규빈", "1111111");

        userDaoJdbc.add(user);

        // user 등록 후 검증
        assertThat(userDaoJdbc.getCount(), is(1));

        User getUser = userDaoJdbc.get(user.getId());

        assertThat(getUser.getName(), is(user.getName()));
        assertThat(getUser.getPassword(), is(user.getPassword()));
    }

    /**
     * 등록한 유저 카운트 수 검증 테스트
     *
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Test
    public void getCount() {

        List<User> userList = Arrays.asList(
                new User("test1", "test1", "test1"),
                new User("test2", "test2", "test2"),
                new User("test3", "test3", "test3")
        );

        userDaoJdbc.deleteAll();
        assertThat(userDaoJdbc.getCount(), is(0));

        int count = 1;
        for (User user : userList) {
            userDaoJdbc.add(user);
            assertThat(userDaoJdbc.getCount(), is(count++));
        }
    }

    /**
     * 해당 사용자에 대한 데이터가 없을때 예외 테스트
     *
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Test(expected = EmptyResultDataAccessException.class)
    public void getUserFailure() {

        userDaoJdbc.deleteAll();
        assertThat(userDaoJdbc.getCount(), is(0));

        userDaoJdbc.get("exceptionUser");
    }

    /**
     * users table에 같은 id insert시 예외 테스트
     *
     */
    @Test(expected = DataAccessException.class)
    public void duplicateKey() {
        userDaoJdbc.deleteAll();

        User user = new User("rbsks147", "한규빈", "test1234");
        userDaoJdbc.add(user);
        userDaoJdbc.add(user);
    }

}
