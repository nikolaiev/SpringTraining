package ua.rd.domain.service;

import ua.rd.domain.Tweet;

/**
 * Created by Vladyslav_Nikolaiev on 7/5/2017.
 */
public interface TweetService {
    Iterable<Tweet> getAllTweets();
}
