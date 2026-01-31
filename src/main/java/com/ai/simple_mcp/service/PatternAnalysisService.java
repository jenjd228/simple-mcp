package com.ai.simple_mcp.service;

import com.ai.simple_mcp.ai.PromptBuilder;
import com.ai.simple_mcp.domain.Decision;
import com.ai.simple_mcp.domain.UserProfile;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatternAnalysisService {

    private final ChatClient chatClient;
    private final PromptBuilder promptBuilder;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public UserProfile buildProfile(List<Decision> decisions) {
        log.info("buildProfile {}", decisions);
        if (decisions.isEmpty()) {
            return new UserProfile();
        }

        String prompt = promptBuilder.buildPatternAnalysisPrompt(decisions);

        String json = chatClient
                .prompt()
                .system("Ты внимательный аналитик поведения.")
                .user(prompt)
                .call()
                .content();

        return parse(json);
    }

    private UserProfile parse(String json) {
        UserProfile profile = new UserProfile();

        try {
            JsonNode root = objectMapper.readTree(json);

            root.path("values")
                .forEach(v -> profile.getValues().add(v.asText()));

            root.path("patterns")
                .fields()
                .forEachRemaining(e ->
                                          profile.getPatterns().put(
                                                  e.getKey(),
                                                  e.getValue().asInt()
                                          )
                );

        } catch (Exception e) {
            // fail-safe: не ломаем весь MCP
            profile.getPatterns().put("analysis_failed", 1);
        }

        return profile;
    }
}
