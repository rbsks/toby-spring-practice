package one;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UserDaoTest {

    @Test
    public void addAndGet() throws ClassNotFoundException, SQLException {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao userDao = context.getBean("userDao", UserDao.class);

        User user = new User();
        user.setId("rbsks147");
        user.setName("한규빈");
        user.setPassword("11111");

        userDao.add(user);

        User getUser = userDao.get(user.getId());

//        수정 전
//        System.out.println(getUser.getName());
//        System.out.println(getUser.getPassword());
//        System.out.println(getUser.getId() + " 조회 성공");

//        수정 후
        assertThat(getUser.getName(), is(user.getName()));
        assertThat(getUser.getPassword(), is(user.getPassword()));
    }
}
