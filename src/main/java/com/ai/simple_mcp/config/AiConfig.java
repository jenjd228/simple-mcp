package com.ai.simple_mcp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.openai.api.ResponseFormat;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AiConfig {

    private final ChatClient.Builder builder;

//    @Bean
//    public ToolCallbackProvider toolCallbackProvider(AdviceService adviceService) {
//        ToolCallbackProvider provider = ToolCallbackProvider.from(adviceService);
//        provider.register(adviceService); // автоматически найдёт методы с @Tool
//        return provider;
//    }

    @Bean
    public ChatClient chatClient(ToolCallbackProvider toolCallbackProvider) {
        return builder
                .defaultAdvisors(simpleLoggerAdvisor())
                .defaultToolCallbacks(toolCallbackProvider)
                .defaultOptions(OpenAiChatOptions.builder()
                                                 .model(OpenAiApi.ChatModel.GPT_4_1_MINI)
                                                 .temperature(0.0)
                                                 .responseFormat(ResponseFormat.builder()
                                                                               .type(ResponseFormat.Type.TEXT)
                                                                               .build())
                                                 .build())
                .build();
    }

    @Bean
    public SimpleLoggerAdvisor simpleLoggerAdvisor() {
        return new SimpleLoggerAdvisor();
    }

}
