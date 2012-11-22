package net.kenytt.csuf.cpsc476.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.kenytt.csuf.cpsc476.db.ITweetDAO;
import net.kenytt.csuf.cpsc476.db.IUserDAO;
import net.kenytt.csuf.cpsc476.db.Tweet;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

@WebServlet("/popular")
public class PopularTweetServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private WebApplicationContext ctx;

    public void init() throws ServletException {
        super.init();
        ctx = WebApplicationContextUtils
                .getRequiredWebApplicationContext(getServletContext());
    }

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        ITweetDAO tweetDao = (ITweetDAO) ctx.getBean("tweetDao");

        out.println(Tag.generate("h1", "Popular Tweets"));

        out.println("<form method=\"POST\">");

        out.println("<table border=\"1\"");
        out.println(Tag.generate(
                "tr",
                Tag.generate("th", "&nbsp;")
                        + Tag.generate("th", "screen_name")
                        + Tag.generate("th", "text")
                        + Tag.generate("th", "retweet_count")
                        + Tag.generate("th", "created_at")));
        for (Tweet t : tweetDao.getPopularTweets()) {
            String id = Integer.valueOf(t.getId()).toString();
            out.println(Tag.generate(
                    "tr",
                    Tag.generate("td",
                            Tag.input("radio", "id", id) + t.toHTML())));
        }
        out.println("</table>");

        out.println("Screen name: " + Tag.input("text", "screen_name"));
        out.println(Tag.input("submit", "submit", "Retweet!"));

        out.println("</form>");
    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        String idString = request.getParameter("id");
        String screenName = request.getParameter("screen_name");

        int id = 0;
        try {
            id = Integer.valueOf(idString);
        } catch (NumberFormatException e) {
            // id == 0
        }

        if (screenName == null || screenName.length() == 0 || id == 0) {
            response.sendRedirect("http://httpcats.herokuapp.com/400");
            return;
        }

        ITweetDAO tweetDao = (ITweetDAO) ctx.getBean("tweetDao");
        IUserDAO userDao = (IUserDAO) ctx.getBean("userDao");

        int userId = userDao.getUserId(screenName);
        tweetDao.retweet(id, userId);

        response.sendRedirect("/tweets/popular");
    }

}
