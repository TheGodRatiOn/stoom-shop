package com.stoom.demo.responses;

import com.stoom.demo.entities.Message;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MessageResponse {
    private String messageResID;
    private String messageResSenderUser;
    private String messageResReceiverUser;
    private String messageResText;
    private String messageResDate;

    public MessageResponse(Message message){
        this.setMessageResID(message.getMessageID());
        this.setMessageResSenderUser(message.getMessageSenderUser().getUserName());
        this.setMessageResReceiverUser(message.getMessageReceiverUser());
        this.setMessageResDate(message.getMessageDate().toString());
        this.setMessageResText(message.getMessageText());
    }
}
