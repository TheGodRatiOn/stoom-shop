package com.stoom.demo.requests;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class GameRequest {
    @JsonProperty
    private String gameReqTitle;
    @JsonProperty
    private int gameReqPrice;
    @JsonProperty
    private String gameReqURL;
}
