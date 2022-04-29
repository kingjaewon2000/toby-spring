package com.example.demo.users;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class UserDaoJdbc implements UserDao {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private final SqlService sqlService;

    public UserDaoJdbc(DataSource dataSource, SqlService sqlService) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.sqlService = sqlService;
    }

    private RowMapper<User> userMapper = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));

        return user;
    };

    public void createDatabase() throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;

        try {
            c = dataSource.getConnection();

            ps = c.prepareStatement("create table users(\n" +
                    "    id varchar(10) primary key,\n" +
                    "    name varchar(20) not null,\n" +
                    "    password varchar(10) not null\n" +
                    ");");

            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }

            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                }
            }
        }

    }

    public void add(final User user) {
        jdbcTemplate.update(sqlService.getKey("userAdd"),
                user.getId(),
                user.getName(),
                user.getPassword());
    }

    public User get(String id) {
        return jdbcTemplate.queryForObject(sqlService.getKey("userGet"), userMapper, id);
    }

    public List<User> getAll() {
        return jdbcTemplate.query(sqlService.getKey("userGetAll"), userMapper);
    }

    public void deleteAll() {
        jdbcTemplate.update(sqlService.getKey("userDeleteAll"));
    }

    public int getCount() {
       return jdbcTemplate.query(sqlService.getKey("userGetCount"), rs -> {
            rs.next();
            return rs.getInt(1);
        });
    }

}