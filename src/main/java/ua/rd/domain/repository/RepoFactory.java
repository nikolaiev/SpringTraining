package ua.rd.domain.repository;

public class RepoFactory {

    public TweetRepository createTweetRepository(String name){
        System.out.println(name);
        return new InMemTweetRepository();
    }
    /*public static  TweetRepository createTweetRepository(){
        return new InMemTweetRepository();
    }*/
}
