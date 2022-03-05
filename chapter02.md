# 2장 테스트

- 스프링은 IoC/DI를 이용해 객체지향 프로그래밍 언어의 근본과 가치를 개발자가 손쉽게 적용하고 사용할 수 있게 도와주는 기술이다.
- 또한 스프링이 강조하고 있는 가치를 두고 있는 것은 테스트다.
- 테스트는 스프링을 학습하는 데 있어 가장 효과적인 방법의 하나다.

## UserDaoTest(수동 테스트)
```java
public class UserDaoTest {

   public static void main(String[] args) throws SQLException {
        ApplicationContext context = 
        new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao dao = context.getBean("userDao", UserDao.class);
        
        User user = new User();
        user.setId("whiteship");
        user.setName("백기선");
        user.setPassword("married");

        dao.add(user);

        System.out.println(user.getId() + " 등록 성공");

        User user2 = dao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());

        System.out.println(user2.getId() + " 조회 성공");
   }
}
```

- 자바에서 가장 손쉽게 실행 가능한 main() 메소드를 이용한다.
- 테스트할 대상인 UserDao의 오브젝트를 가져와 메소드를 호출한다.
- 테스트에 사용할 입력 값(User 오브젝트)을 직접 코드에서 만들어 넣어준다.
- 테스트의 결과를 콘솔에 출력해준다.
- 각 단계의 작업이 에러 없이 끝나면 콘솔에 성공 메세지로 출력해준다.

## UserDaoTest의 문제점
1. 수동 확인 작업의 번거로움
2. 실행 작업의 번거로움

## UserDaoTest 개선
```java
public class UserDaoTest {

   public static void main(String[] args) throws SQLException {
        ApplicationContext context = 
        new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao dao = context.getBean("userDao", UserDao.class);

        User user = new User();
        user.setId("whiteship");
        user.setName("백기선");
        user.setPassword("married");

        dao.add(user);
        User user2 = dao.get(user.getId());

        if (!user.getName().equals(user2.getName())) {
            System.out.println("테스트 실패 (name)");
        } else if (!user.getPassword().equals(user2.getPassword())) {
            System.out.println("테스트 실패 (password)");
        } else {
            System.out.println("조회 테스트 성공");
        }
   }
}
```

## UserDaoTest(JUnit5)
```java
public class UserDaoTest {

   @Test
   void addAndGet() throws SQLException {
       ApplicationContext context = 
       new AnnotationConfigApplicationContext(DaoFactory.class);
       UserDao dao = (UserDao) context.getBean("userDao");

       User user = new User();
       user.setId("gyumee");
       user.setName("박상철");
       user.setPassword("springno1");

       dao.add(user);
       User user2 = dao.get(user.getId());

       // org.assertj.core.api.Assertions
       assertThat(user.getPassword()).isEqualTo(user2.getPassword());
       assertThat(user.getName()).isEqualTo(user2.getName());

   }
}
```

## 테스트 결과의 일관성
```java
public void deleteAll() throws SQLException {
    Connection c = dataSource.getConnection();

    PreparedStatement ps = c.prepareStatement("delete from users");
    ps.executeUpdate();

    ps.close();
    c.close();
}
```

```java
public int getCount() throws SQLException {
    Connection c = dataSource.getConnection();
    PreparedStatement ps = c.prepareStatement("select count(*) from users");
    ResultSet rs = ps.executeQuery();
    rs.next();
    int count = rs.getInt(1);
    rs.close();
    ps.close();
    c.close();

    return count;
}
```

```java
public class UserDaoTest {

    @Test
    void addAndGet() throws SQLException {
        ApplicationContext context = 
        new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao dao = (UserDao) context.getBean("userDao");

        dao.deleteAll();
        assertThat(dao.getCount()).isEqualTo(0);

        User user = new User();
        user.setId("gyumee");
        user.setName("박상철");
        user.setPassword("springno1");

        dao.add(user);
        assertThat(dao.getCount()).isEqualTo(1);
        User user2 = dao.get(user.getId());

        // org.assertj.core.api.Assertions
        assertThat(user.getPassword()).isEqualTo(user2.getPassword());
        assertThat(user.getName()).isEqualTo(user2.getName());
    }
}
```

## addAndGet() 테스트 보완
```java
public class UserDaoTest {

    @Test
    void addAndGet() throws SQLException {
        ApplicationContext context = 
        new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao dao = (UserDao) context.getBean("userDao");

        User user1 = new User("gyumee", "박성철", "springno1");
        User user2 = new User("leegw700", "이길원", "sprinmgno2");

        dao.deleteAll();
        assertThat(dao.getCount()).isEqualTo(0);

        dao.add(user1);
        dao.add(user2);
        assertThat(dao.getCount()).isEqualTo(2);

        User userget1 = dao.get(user1.getId());
        assertThat(user1.getPassword()).isEqualTo(userget1.getPassword());
        assertThat(user1.getName()).isEqualTo(userget1.getName());

        User userget2 = dao.get(user2.getId());
        assertThat(user2.getPassword()).isEqualTo(userget2.getPassword());
        assertThat(user2.getName()).isEqualTo(userget2.getName());
    }
}
```

```java
public class UserDaoTest {

    @Test
    void getUserFailure() throws SQLException {
        ApplicationContext context = 
        new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao dao = (UserDao) context.getBean("userDao");

        dao.deleteAll();
        assertThat(dao.getCount()).isEqualTo(0);

        Assertions.assertThatThrownBy(() -> dao.get("unknown_id"))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }
}
```

```java
public User get(String id) throws SQLException {
    Connection c = dataSource.getConnection();

    PreparedStatement ps = c.prepareStatement("select * from users where id = ?");
    ps.setString(1, id);

    ResultSet rs = ps.executeQuery();

    User user = null;
    if (rs.next()) {
        user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));
    }

    rs.close();
    ps.close();
    c.close();

    if (user == null) throw new EmptyResultDataAccessException(1);

    return user;
}
```

## 테스트 주도 개발
테스트를 먼저 만들고 테스트를 통과하기 위한 것을 짜는 것 즉, 만드는 과정에서 우선 테스트를 작성하고 그걸 통과하는 코드를 만들고를 반복하면서 제대로 동작하는지에 대한 피드백을 적극적으로 받는 것이다.

## 테스트 코드 개선
```java
ApplicationContext context = 
new AnnotationConfigApplicationContext(DaoFactory.class);
UserDao dao = (UserDao) context.getBean("userDao");
```

```java 
public class UserDaoTest {

    private UserDao dao;

    @BeforeEach
    void beforeEach() {
        ApplicationContext context = 
        new AnnotationConfigApplicationContext(DaoFactory.class);
        this.dao = (UserDao) context.getBean("userDao");
    }

    @Test
    void addAndGet() throws SQLException {
        // ApplicationContext context = 
        // new AnnotationConfigApplicationContext(DaoFactory.class);
        // UserDao dao = (UserDao) context.getBean("userDao");

        User user1 = new User("gyumee", "박성철", "springno1");
        User user2 = new User("leegw700", "이길원", "sprinmgno2");

        dao.deleteAll();
        assertThat(dao.getCount()).isEqualTo(0);

        dao.add(user1);
        dao.add(user2);
        assertThat(dao.getCount()).isEqualTo(2);

        User userget1 = dao.get(user1.getId());
        assertThat(user1.getPassword()).isEqualTo(userget1.getPassword());
        assertThat(user1.getName()).isEqualTo(userget1.getName());

        User userget2 = dao.get(user2.getId());
        assertThat(user2.getPassword()).isEqualTo(userget2.getPassword());
        assertThat(user2.getName()).isEqualTo(userget2.getName());
    }
}
```

## JUnit5 동작 흐름
1. 테스트 클래스에서 @Test가 붙은 void형이며 파라미터가 없는 테스트 메소드를 모두 찾는다.
2. 테스트 클래스의 오브젝트를 하나 만든다.
3. @BeforeEach가 붙은 메소드가 있으면 실행한다.
4. @Test가 붙은 메소드를 하나 호출하고 테스트 결과를 저장해둔다.
5. @After가 붙은 메소드가 있으면 실행한다.
6. 나머지 테스트 메소드에 대해 2 ~ 5번을 반복한다.
7. 모든 테스트의 결과를 종합해서 돌려준다.

## 스프링 테스트 적용
```java
@ExtendWith(SpringExtension.class) // Junit4 @RunWith
@ContextConfiguration(classes = TestConfig.class)
class UserDaoTest {
    @Autowired
    ApplicationContext context;

    @Autowired
    UserDao dao;

    @BeforeEach
    void beforeEach() {
    }
}
```