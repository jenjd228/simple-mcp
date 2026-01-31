package com.ai.simple_mcp.ai;

import com.ai.simple_mcp.service.AdviceService;
import com.ai.simple_mcp.service.DecisionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class DecisionAdvisor {

    private final ChatClient chatClient;
    private final @Lazy AdviceService adviceService;
    private final @Lazy DecisionService decisionService;

    public String processUserMessage(String userMessage) {
        log.info("processUserMessage {}", userMessage);
        return chatClient.prompt()
                         .system("""
                                         Ты — персональный помощник по принятию решений.
                                         Если тебе не хватает данных — задай вопрос.
                                         Если нужно сохранить решение — используй инструменты.
                                         Как первостепенный источник используй инструменты.
                                         Если пользователь принял решение то сохрани его.""")
                         .user(userMessage)
                         .toolCallbacks(ToolCallbacks.from(this, adviceService, decisionService))
                         .call()
                         .content();
    }

    @Tool(description = "Получить текущее время")
    public LocalDateTime now(){
        log.info("now()");
        return LocalDateTime.now();
    }
}

