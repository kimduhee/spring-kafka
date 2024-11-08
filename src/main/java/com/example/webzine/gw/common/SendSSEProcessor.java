package com.example.webzine.gw.common;

import com.example.webzine.gw.dto.MemberDTO;
import com.example.webzine.gw.dto.SSENotificationData;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class SendSSEProcessor {
    private final Map<String, Sinks.Many<ServerSentEvent<Object>>> sinks = new HashMap<>();

    /**
     * Send notification to user
     */
    public void personalSend(SSENotificationData notificationData, String userId) {
        if(sinks.containsKey(userId)){      //알림을 받을 사용자가 현재 SSE로 연결한 경우 알림 발송
            sinks.get(userId).tryEmitNext(ServerSentEvent.builder()
                    .event("message")
                    .data(notificationData)
                    .id(notificationData.getNotificationId())
                    .comment(notificationData.getMessage())
                    .build());
        }
    }

    /**
     * Send notification to users
     */
    public void groupSend(SSENotificationData notificationData, List<MemberDTO> users) {
        users.forEach(user -> {     //그룹에 속한 사용자들에게 알림 발송
            personalSend(notificationData, user.getId());
        });
    }

    public Mono<Boolean> successMessageSend(String userId) {
        return Mono.just(userId)
                .flatMap(id -> {
                    if (sinks.containsKey(id)) {      //알림을 받을 사용자가 현재 SSE로 연결한 경우 알림 발송
                        sinks.get(id).tryEmitNext(ServerSentEvent.builder()
                                .event("config")
                                .data("Connected Successfully")
                                .comment("Connected Successfully")
                                .build());
                        return Mono.just(true);
                    }
                    return Mono.error(new Exception());
                });
    }

    /**
     * Connect to SSE
     */
    public Flux<ServerSentEvent<Object>> connect(String userId) {


        if(sinks.containsKey(userId)){  //이미 SSE 연결이 되어있는 경우
            return sinks.get(userId).asFlux();
        }

        //SSE 연결이 되어있지 않은 경우
        sinks.put(userId, Sinks.many().multicast().onBackpressureBuffer());
        return sinks.get(userId).asFlux().doOnCancel(()->{
            System.out.println("### SSE Notification Cancelled by client: " + userId);
            this.finish(userId);
        });
    }

    /**
     * Disconnect from SSE
     */
    public void finish(String userId) {
        sinks.get(userId).tryEmitComplete();
        sinks.remove(userId);
    }
}
