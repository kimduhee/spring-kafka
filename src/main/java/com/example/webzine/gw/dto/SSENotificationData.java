package com.example.webzine.gw.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
//@Setter
public class SSENotificationData {
    @JsonProperty("notificationId")
    private String notificationId;

    @JsonProperty("message")
    private String message;
}
