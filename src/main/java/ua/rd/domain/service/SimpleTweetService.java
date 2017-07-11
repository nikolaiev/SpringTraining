package ua.rd.domain.service;

import lombok.Data;
import ua.rd.domain.Tweet;
import ua.rd.domain.repository.Benchmark;
import ua.rd.domain.repository.InMemTweetRepository;
import ua.rd.domain.repository.TweetRepository;

@Data
public class SimpleTweetService implements TweetService{
    private final TweetRepository tweetRepository;

    public SimpleTweetService(TweetRepository tweetRepository){
        this.tweetRepository=tweetRepository;
    }

    @Override
    public Iterable<Tweet> getAllTweets() {
        return tweetRepository.getAllTweets();
    }
}
