package one;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UserDaoTest {

    private UserDao userDao;

    @Before
    public void setUp() {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        userDao = context.getBean("userDao", UserDao.class);
    }
    /**
     * 유저 등록 및 조회 테스트
     *
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Test
    public void addAndGet() throws ClassNotFoundException, SQLException {

        // users table을 비우는 작업 후 검증
        userDao.deleteAll();
        assertThat(userDao.getCount(), is(0));

        User user = new User("rbsks147", "한규빈", "1111111");

        userDao.add(user);

        // user 등록 후 검증
        assertThat(userDao.getCount(), is(1));

        User getUser = userDao.get(user.getId());

//        수정 전
//        System.out.println(getUser.getName());
//        System.out.println(getUser.getPassword());
//        System.out.println(getUser.getId() + " 조회 성공");

//        수정 후
        assertThat(getUser.getName(), is(user.getName()));
        assertThat(getUser.getPassword(), is(user.getPassword()));
    }

    /**
     * 등록한 유저 카운트 수 검증 테스트
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Test
    public void getCount() throws ClassNotFoundException, SQLException {

        List<User> userList = Arrays.asList(
                new User("test1", "test1", "test1"),
                new User("test2", "test2", "test2"),
                new User("test3", "test3", "test3")
        );

        userDao.deleteAll();
        assertThat(userDao.getCount(), is(0));

        int count = 1;
        for (User user : userList) {
            userDao.add(user);
            assertThat(userDao.getCount(), is(count++));
        }
    }

    /**
     * 해당 사용자에 대한 데이터가 없을때 예외 테스트
     *
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Test(expected = SQLException.class)
    public void getUserFailure() throws ClassNotFoundException, SQLException {

        userDao.deleteAll();
        assertThat(userDao.getCount(), is(0));

        userDao.get("exceptionUser");
    }
}
