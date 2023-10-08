package com.gmovie.gmovie.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebRTCController<WebRTCOffer, WebRTCAnswer> {

    @MessageMapping("/offer")
    @SendTo("/topic/offer")
    public WebRTCOffer processOffer(WebRTCOffer offer) {
        // Offer 처리 로직
        return offer;
    }

    @MessageMapping("/answer")
    @SendTo("/topic/answer")
    public WebRTCAnswer processAnswer(WebRTCAnswer answer) {
        // Answer 처리 로직
        return answer;
    }
}
