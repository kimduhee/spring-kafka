package com.example.webzine.gw.controller;

import com.example.webzine.gw.service.CustomProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/kafka")
@RequiredArgsConstructor
@Slf4j
public class KafkaController {

    private final CustomProducer customProducer;

    @GetMapping("/producer1")
    public String producer1(String msg) {
        if(log.isDebugEnabled()) {
            log.debug("message : {}", msg);
        }

        try {
            customProducer.send1(msg);
        } catch(Exception e) {

        }
        return "success";
    }

    @GetMapping("/producer2")
    public String producer2(String msg) {
        if(log.isDebugEnabled()) {
            log.debug("message : {}", msg);
        }

        try {
            customProducer.send2(msg);
        } catch(Exception e) {

        }

        return "success";
    }
}
