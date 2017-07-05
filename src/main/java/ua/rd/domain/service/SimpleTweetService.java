package ua.rd.domain.service;

import lombok.Data;
import ua.rd.domain.Tweet;
import ua.rd.domain.repository.TweetRepository;

@Data
public class SimpleTweetService implements TweetService{
    private final TweetRepository tweetRepository;

    @Override
    public Iterable<Tweet> getAllTweets() {
        return tweetRepository.getAllTweets();
    }
}
