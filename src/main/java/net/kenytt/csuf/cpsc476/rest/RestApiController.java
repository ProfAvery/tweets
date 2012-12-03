package net.kenytt.csuf.cpsc476.rest;

import java.util.List;

import net.kenytt.csuf.cpsc476.db.ITweetDAO;
import net.kenytt.csuf.cpsc476.db.IUserDAO;
import net.kenytt.csuf.cpsc476.db.Tweet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class RestApiController {

    ITweetDAO tweetDao;
    IUserDAO userDao;

    @Autowired
    public RestApiController(ITweetDAO tweetDao, IUserDAO userDao) {
        this.tweetDao = tweetDao;
        this.userDao = userDao;
    }

    @RequestMapping("/recent")
    public @ResponseBody
    List<Tweet> getRecentTweets(Model model) {
        return tweetDao.getRecentTweets();
    }

    /*
     * POST /tweets/api/new HTTP/1.1
     * Host: localhost:8080
     * Content-Type: application/json
     * Content-Length: 65
     * 
     * {
     *     "screenName": "TheOnion",
     *     "text": "Recession-Proof Jobs Include Any In Which You Witness Your Boss Kill Someone http://onion.com/SbBacj"
     * }
    */
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    void createTweet(@RequestBody Tweet t,
            @RequestParam(defaultValue = "false") boolean async) {
        
        int userId = t.getUserId();
        if (userId == 0) {
            userId = userDao.getUserId(t.getScreenName());
            t.setUserId(userId);
        }
        
        if (async) {
            // TODO: Message Queuing
        } else {
            tweetDao.createTweet(t);
        }
        
    }

}
