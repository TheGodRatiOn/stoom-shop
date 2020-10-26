package com.stoom.demo.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameResponse {
    private String gameResID;
    private String gameResTitle;
    private String gameResReviewFK;
    private int gameResPrice;
    private String gameResURL;
}
