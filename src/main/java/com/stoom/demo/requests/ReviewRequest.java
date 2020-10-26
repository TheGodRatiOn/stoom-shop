package com.stoom.demo.requests;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ReviewRequest {
    @JsonProperty
    private String reviewText;
    @JsonProperty
    private float reviewAssessment;
    @JsonProperty
    private String reviewGameID;
    @JsonProperty
    private String reviewUserID;
}
