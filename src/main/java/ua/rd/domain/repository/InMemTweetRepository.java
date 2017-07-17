package ua.rd.domain.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ua.rd.domain.Tweet;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;


@Service("tweetRepository")
public class InMemTweetRepository implements TweetRepository{
    private List<Tweet> tweets;

    @PostConstruct
    public void init(){
        tweets= Arrays.asList(
                new Tweet("Andrii","First Tweet"),
                new Tweet("Andrii","First Tweet")
        );
    }

    public InMemTweetRepository(List<Tweet> tweets) {
        this.tweets = tweets;
    }

    public InMemTweetRepository() {

    }


    @Override
    @Benchmark
    public Iterable<Tweet> getAllTweets() {
     return tweets;
    }
}
