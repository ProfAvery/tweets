package net.kenytt.csuf.cpsc476.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.kenytt.csuf.cpsc476.db.ITweetDAO;
import net.kenytt.csuf.cpsc476.db.Tweet;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

@WebServlet("/recent")
public class RecentTweetServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        WebApplicationContext ctx = WebApplicationContextUtils
                .getRequiredWebApplicationContext(getServletContext());
        ITweetDAO tweetDao = (ITweetDAO) ctx.getBean("tweetDao");

        PrintWriter out = response.getWriter();

        out.println(Tag.generate("h1", "Recent Tweets"));
        out.println("<table border=\"1\"");
        out.println(Tag.generate(
                "tr",
                Tag.generate("th", "screen_name") + Tag.generate("th", "text")
                        + Tag.generate("th", "retweet_count")
                        + Tag.generate("th", "created_at")));
        for (Tweet t : tweetDao.getRecentTweets()) {
            out.println(Tag.generate("tr", t.toHTML()));
        }
        out.println("</table>");
    }
}
