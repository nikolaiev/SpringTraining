package ua.rd.domain.repository;

import ua.rd.domain.Tweet;



/*Lazy loading*/
public interface TweetRepository {

    Iterable<Tweet> getAllTweets();

}
