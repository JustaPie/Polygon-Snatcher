package edu.fsu.cs.mobile.firebaseautoconnecttest;

/**
 * Class to containing data on capture zones
 */

public class Zone {
    int garnetteamscore;
    int goldteamscore;

    public Zone(){
        garnetteamscore = 0;
        goldteamscore = 0;
    }

    public Zone (int GarnetScore, int GoldScore){
        garnetteamscore = GarnetScore;
        goldteamscore = GoldScore;
    }

    public int getgarnetteamscore(){
        return garnetteamscore;
    }

    public  int getgoldteamscore(){
        return goldteamscore;
    }

    public void setgarnetscore(int score){
        garnetteamscore = score;
    }

    public void setgoldscore(int score){
        goldteamscore = score;
    }
}
