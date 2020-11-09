package com.stoom.demo.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @JsonProperty
    private String userReqName;
    @JsonProperty
    private String userPassword;
    @JsonProperty
    private String userReqRole;
}
