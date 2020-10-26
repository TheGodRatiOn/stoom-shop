package com.stoom.demo.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GameUserRequest {
    @JsonProperty
    private String reviewGameID;
    @JsonProperty
    private String reviewUserID;
}
