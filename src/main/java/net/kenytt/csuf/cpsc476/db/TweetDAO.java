package net.kenytt.csuf.cpsc476.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation=Propagation.SUPPORTS)
public class TweetDAO extends JdbcDaoSupport implements ITweetDAO {

    private static class TweetMapper implements RowMapper<Tweet> {

        public Tweet mapRow(ResultSet rs, int rowNum) throws SQLException {
            Tweet t = new Tweet();

            t.setId(rs.getInt("id"));
            t.setUserId(rs.getInt("user_id"));
            t.setScreenName(rs.getString("screen_name"));
            t.setText(rs.getString("text"));
            t.setRetweetCount(rs.getInt("retweet_count"));
            t.setCreatedAt(rs.getTimestamp("created_at"));

            return t;
        }

    }

    private List<Tweet> getTweetsOrderedBy(String column) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        return jdbcTemplate.query(
                "SELECT id, user_id, screen_name, text, retweet_count, created_at "
                        + "FROM tweets, users "
                        + "WHERE tweets.user_id = users.id " + "ORDER BY "
                        + column + " DESC", new TweetMapper());
    }

    public List<Tweet> getRecentTweets() {
        return getTweetsOrderedBy("created_at");
    }

    public List<Tweet> getPopularTweets() {
        return getTweetsOrderedBy("retweet_count");
    }

    public Tweet getTweet(int id) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        return jdbcTemplate.queryForObject(
                "SELECT screen_name, text, retweet_count "
                        + "FROM tweets, users " + "WHERE id = ? "
                        + "AND tweets.user_id = users.id", new Object[] { id },
                new RowMapper<Tweet>() {
                    public Tweet mapRow(ResultSet rs, int rowNum)
                            throws SQLException {
                        Tweet t = new Tweet();

                        t.setScreenName(rs.getString("screen_name"));
                        t.setText(rs.getString("text"));
                        t.setRetweetCount(rs.getInt("retweet_count"));

                        return t;
                    }
                });

    }

    public void createTweet(Tweet t) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        jdbcTemplate.update("INSERT INTO tweets(user_id, text, retweet_count) "
                + "VALUES(?, ?, ?)", t.getUserId(), t.getText(),
                t.getRetweetCount());
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public void retweet(int id, int userId) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();

        Tweet original = getTweet(id);
        jdbcTemplate.update("UPDATE tweets SET retweet_count = ? WHERE id = ?",
                original.getRetweetCount() + 1, id);

        Tweet retweet = new Tweet();

        retweet.setUserId(userId);

        String retweetText = "RT @" + original.getScreenName() + ": "
                + original.getText();
        if (retweetText.length() > 140) {
            retweetText = retweetText.substring(0, 140);
        }

        retweet.setText(retweetText);
        retweet.setRetweetCount(0);

        createTweet(retweet);
    }
}
