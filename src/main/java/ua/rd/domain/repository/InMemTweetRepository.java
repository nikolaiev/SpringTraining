package ua.rd.domain.repository;

import ua.rd.domain.Tweet;

import java.util.Arrays;
import java.util.List;

public class InMemTweetRepository implements TweetRepository{
    private List<Tweet> tweets;

    public void init(){
        tweets= Arrays.asList(
                new Tweet("Andrii","First Tweet"),
                new Tweet("Andrii","First Tweet")
        );
    }


    @Override
    @Benchmark
    public Iterable<Tweet> getAllTweets() {
     return tweets;
    }
}
