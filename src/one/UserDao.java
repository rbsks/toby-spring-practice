package one;

/**
 * 기술에 독립적인 UserDao interface
 * Jpa, Hibernate 등 다른 데이터 엑세스 기술의 API마다 구현체를 만들어 독립적으로 사용할 수 있도록 분리
 */
public interface UserDao {

    void add(User user);
    User get(String id);
    void deleteAll();
    int getCount();
}
