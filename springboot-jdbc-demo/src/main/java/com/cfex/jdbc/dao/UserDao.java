package com.cfex.jdbc.dao;

import com.cfex.jdbc.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
@AllArgsConstructor
public class UserDao {
    final JdbcTemplate template;

    public User findById(Integer id) {
        String sql = "select id, name from user where id = ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), id);
    }

    public int insert(User user) {
        String sql = "insert into user(name) values (?)";
        Object[] args = {user.getName()};
        return template.update(sql, args);
    }

    public int insertBatch(List<User> users) {
        String sql = "insert into user(name) values (?)";
        template.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                User user = users.get(i);
                ps.setString(1, user.getName());
            }

            @Override
            public int getBatchSize() {
                return users.size();
            }
        });
        return users.size();
    }
}
