package com.stoom.demo.responses;

import com.stoom.demo.entities.GameUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GameUserResponse {
    private String guResGameID;
    private String guResUserID;

    public GameUserResponse(GameUser gameUser){
        this.setGuResGameID(gameUser.getGuGameID());
        this.setGuResUserID(gameUser.getGuUserID());
    }
}
