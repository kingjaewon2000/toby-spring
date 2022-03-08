package com.example.demo.users;

import com.example.demo.DaoFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class) // Junit4 @RunWith
@ContextConfiguration(classes = DaoFactory.class)
class UserDaoTest {

    @Autowired
    private UserDao dao;
    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    void beforeEach() {
        this.user1 = new User("gyumee", "박성철", "springno1");
        this.user2 = new User("leegw700", "이길원", "sprinmgno2");
        this.user3 = new User("bumjin", "박범진", "springno3");
    }

//    @Test
//    void main() throws SQLException {
//        User user = new User();
//        user.setId("whiteship");
//        user.setName("백기선");
//        user.setPassword("married");
//
//        dao.add(user);
//
////        System.out.println(user.getId() + " 등록 성공");
//
//        User user2 = dao.get(user.getId());
////        System.out.println(user2.getName());
////        System.out.println(user2.getPassword());
////        System.out.println(user2.getId() + " 조회 성공");
//
//        if (!user.getName().equals(user2.getName())) {
//            System.out.println("테스트 실패 (name)");
//        }
//        else if (!user.getPassword().equals(user2.getPassword())) {
//            System.out.println("테스트 실패 (password)");
//        }
//        else {
//            System.out.println("조회 테스트 성공");
//        }
//    }

//    @Test
//    void addAndGet() throws SQLException {
//        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
//        UserDao dao = (UserDao) context.getBean("userDao");
//
//        dao.deleteAll();
//        assertThat(dao.getCount()).isEqualTo(0);
//
//        User user = new User();
//        user.setId("gyumee");
//        user.setName("박상철");
//        user.setPassword("springno1");
//
//        dao.add(user);
//        assertThat(dao.getCount()).isEqualTo(1);
//
//        User user2 = dao.get(user.getId());
//
//        // org.assertj.core.api.Assertions
//        assertThat(user.getPassword()).isEqualTo(user2.getPassword());
//        assertThat(user.getName()).isEqualTo(user2.getName());
//
//    }

    @Test
    void addAndGet() throws SQLException {
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

    @Test
    void getUserFailure() throws SQLException {
        dao.deleteAll();
        assertThat(dao.getCount()).isEqualTo(0);

        Assertions.assertThatThrownBy(() -> dao.get("unknown_id"))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    void count() throws SQLException {
        dao.deleteAll();
        assertThat(dao.getCount()).isEqualTo(0);

        dao.add(user1);
        assertThat(dao.getCount()).isEqualTo(1);

        dao.add(user2);
        assertThat(dao.getCount()).isEqualTo(2);

        dao.add(user3);
        assertThat(dao.getCount()).isEqualTo(3);
    }
}