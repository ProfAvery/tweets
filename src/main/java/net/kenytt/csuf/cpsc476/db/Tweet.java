package net.kenytt.csuf.cpsc476.db;

import java.util.Date;

import net.kenytt.csuf.cpsc476.web.Tag;

public class Tweet {

    private int Id;
    private int userId;
    private String screenName;
    private String text;
    private int retweetCount;
    private Date createdAt;

    public String toHTML() {
        return Tag.generate("td", screenName) + Tag.generate("td", text)
                + Tag.generate("td", Integer.toString(retweetCount))
                + Tag.generate("td", createdAt.toString());
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(int retweetCount) {
        this.retweetCount = retweetCount;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

}
