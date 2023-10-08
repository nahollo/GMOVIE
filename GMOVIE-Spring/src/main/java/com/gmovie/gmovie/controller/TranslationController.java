package com.gmovie.gmovie.controller;

import com.gmovie.gmovie.service.TranslationService;
import com.method.SpeechToText1;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

@Controller
public class TranslationController {
    private final TranslationService translationService;

    // @Autowired
    public TranslationController(TranslationService translationService) {
        this.translationService = translationService;
    }

    @GetMapping("/translateEngToKor")
    public String translateEngToKor(@RequestParam String text) {
        return translationService.engToKor(text);
    }

    @GetMapping("/translateKorToEng")
    public String translateKorToEng(@RequestParam String text) {
        return translationService.korToEng(text);
    }

    @PostMapping("/translate")
    public String translate(@RequestBody String text, @RequestParam String sourceLang,
            @RequestParam String targetLang) {
        // 필요한 로직을 구현하여 번역 요청 처리
        // translationService.translate(text, sourceLang, targetLang);
        return "번역 결과";
    }

    @GetMapping("/temp")
    public String temp(Model model) throws IOException {
        SpeechToText1 method = new SpeechToText1();
        String originalText = "";
        String toEngText = translationService.korToEng(originalText);
        String toKorText = translationService.engToKor(toEngText);

        // 모델에 데이터 추가
        model.addAttribute("originalText", originalText);
        model.addAttribute("toEngText", toEngText);
        model.addAttribute("toKorText", toKorText);

        return "translationResult";
    }

}