package com.roydon.app.controller;

import com.roydon.openai.util.OpenAiUtils;
import com.theokanning.openai.completion.CompletionChoice;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/openai")
public class OpenAiChatApi {

    @PostMapping("/chat/question")
    public String openAiChat(String question) {
        List<CompletionChoice> questionAnswer = OpenAiUtils.getQuestionAnswer(question);
        StringBuilder sb = new StringBuilder();
        for (CompletionChoice completionChoice : questionAnswer) {
            sb.append(completionChoice.getText());
            System.out.println(completionChoice.getText());
        }
        return sb.toString();
    }
}
