package org.arpit.java2blog.user;

import com.euro.typer.data.source.entity.Player;

import java.util.List;

/**
 * Created by miras108 on 2016-06-04.
 */
public class UserMatchRS {
    private Player player;
    private List<MatchScore> matchScore;
    private String errorMessage;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<MatchScore> getMatchScore() {
        return matchScore;
    }

    public void setMatchScore(List<MatchScore> matchScore) {
        this.matchScore = matchScore;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
