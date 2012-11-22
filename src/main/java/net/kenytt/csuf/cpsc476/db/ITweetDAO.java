package net.kenytt.csuf.cpsc476.db;

import java.util.List;


public interface ITweetDAO {
    List<Tweet> getRecentTweets();
    List<Tweet> getPopularTweets();
    Tweet getTweet(int id);
    void createTweet(Tweet t);
    void retweet(int id, int userId);
}
