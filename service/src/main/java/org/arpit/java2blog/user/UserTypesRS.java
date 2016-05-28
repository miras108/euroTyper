package org.arpit.java2blog.user;

import com.euro.typer.data.source.entity.PlayerType;
import org.hibernate.usertype.UserType;

import java.util.List;

/**
 * Created by miras108 on 2016-05-28.
 */
public class UserTypesRS {
    private List<PlayerType> playerTypes;
    private String message;

    public List<PlayerType> getPlayerTypes() {
        return playerTypes;
    }

    public void setPlayerTypes(List<PlayerType> playerTypes) {
        this.playerTypes = playerTypes;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
