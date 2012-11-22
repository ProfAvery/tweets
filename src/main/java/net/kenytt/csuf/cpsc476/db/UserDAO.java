package net.kenytt.csuf.cpsc476.db;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;


import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation=Propagation.SUPPORTS)
public class UserDAO extends JdbcDaoSupport implements IUserDAO {

    public int getUserId(String screenName) {
        try {
            JdbcTemplate jdbcTemplate = getJdbcTemplate();

            int userId = jdbcTemplate.queryForInt(
                    "SELECT id FROM users WHERE screen_name = ?", screenName);

            return userId;
        } catch (EmptyResultDataAccessException e) {
            DataSource dataSource = getDataSource();

            SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(dataSource)
                    .withTableName("users")
                    .usingColumns("screen_name", "password")
                    .usingGeneratedKeyColumns("id");

            Map<String, Object> values = new HashMap<String, Object>();
            values.put("screen_name", screenName);
            values.put("password", new StringBuilder(screenName).reverse()
                    .toString());

            Number userId = jdbcInsert.executeAndReturnKey(values);
            return userId.intValue();
        }
    }
}
