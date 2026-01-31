package com.ai.simple_mcp.service;

import com.ai.simple_mcp.ai.PromptBuilder;
import com.ai.simple_mcp.domain.AdviceRequest;
import com.ai.simple_mcp.domain.AdviceResponse;
import com.ai.simple_mcp.domain.Decision;
import com.ai.simple_mcp.domain.UserProfile;
import com.ai.simple_mcp.repository.DecisionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdviceService {

    private final DecisionRepository repository;
    private final PatternAnalysisService patternService;
    private final PromptBuilder promptBuilder;
    private final ChatClient chatClient;

    @Tool(description = """
            Получить ответ на какое-либо решение на основе истории решений пользователя.
            Напоминай пользователю его решения чтобы он не повторял ошибок.
            Используй, когда пользователь сомневается или просит совета.
            """)
    public AdviceResponse advise(
            @McpToolParam(description = "Запрос на получение совета") AdviceRequest request) {
        log.info("Получение совета: {}", request);
        List<Decision> past = repository.findAll();
        UserProfile profile = patternService.buildProfile(past);

        String prompt = promptBuilder.buildAdvicePrompt(
                request.getQuestion(),
                request.getContext(),
                profile,
                past
        );

        String answer = chatClient
                .prompt()
                .user(prompt)
                .call()
                .content();

        return new AdviceResponse(answer, profile.getPatterns());
    }
}
