package com.stoom.demo.responses;

import com.stoom.demo.entities.Game;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GameResponse {
    private String gameResID;
    private String gameResTitle;
    private int gameResPrice;
    private String gameResURL;

    public GameResponse(Game game){
        this.setGameResID(game.getGameID());
        this.setGameResPrice(game.getGamePrice());
        this.setGameResTitle(game.getGameTitle());
        this.setGameResURL(game.getGameURL());
    }
}
