package com.stoom.demo.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserRequest {
    @JsonProperty
    private String userReqName;
    @JsonProperty
    private String userReqRole;
}
