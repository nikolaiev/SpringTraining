package ua.rd.domain.service;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import ua.rd.domain.Tweet;
import ua.rd.domain.repository.Benchmark;
import ua.rd.domain.repository.InMemTweetRepository;
import ua.rd.domain.repository.TweetRepository;


@Service("tweetService")
@Data
public /*abstract*/ class SimpleTweetService implements TweetService{
    private final TweetRepository tweetRepository;
    private ApplicationContext context;

    @Autowired
    public SimpleTweetService(TweetRepository tweetRepository){
        this.tweetRepository=tweetRepository;
    }

    @Override
    @Benchmark
    public Iterable<Tweet> getAllTweets() {
        return tweetRepository.getAllTweets();
    }

    @Override
    public Tweet newTweet(String user, String message){
        Tweet tweet = createNewTweet();
        tweet.setUser(user);
        tweet.setMsg(message);
        return tweet;
    }

    //@Lookup
    //public abstract Tweet createNewTweet();

    public Tweet createNewTweet(){return new Tweet("1","1");}
}
