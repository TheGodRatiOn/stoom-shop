package com.stoom.demo.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MessageRequest {
    @JsonProperty
    private String messageSenderUserID;
    @JsonProperty
    private String messageText;
    @JsonProperty
    private String messageRecieverUserID;
}
