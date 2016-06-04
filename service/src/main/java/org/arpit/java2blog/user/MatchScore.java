package org.arpit.java2blog.user;

import com.euro.typer.data.source.entity.Match;

/**
 * Created by miras108 on 2016-06-04.
 */
public class MatchScore {
    private Match match;
    String type;

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
