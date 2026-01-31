package com.ai.simple_mcp.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisor;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisorChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
public class MyLogAdvisor implements CallAdvisor, StreamAdvisor {

    private void before(ChatClientRequest chatClientRequest) {
        log.info("prompt：\n{}", chatClientRequest.prompt().getContents());
    }

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {
        this.before(chatClientRequest);
        ChatClientResponse chatClientResponse = callAdvisorChain.nextCall(chatClientRequest);

        if (chatClientResponse.chatResponse() != null) {
            String responseText = chatClientResponse.chatResponse().getResult().getOutput().getText();
            log.info("AI：\n{}", responseText);
        } else {
            log.error("AI：\n{}", chatClientResponse);
        }

        return chatClientResponse;
    }

    @Override
    public Flux<ChatClientResponse> adviseStream(ChatClientRequest chatClientRequest, StreamAdvisorChain streamAdvisorChain) {
        this.before(chatClientRequest);

        Flux<ChatClientResponse> chatClientResponseFlux = streamAdvisorChain.nextStream(chatClientRequest);

        Mono<List<ChatClientResponse>> collectedFlux = chatClientResponseFlux.collectList();
        collectedFlux
                .doOnNext(responses -> {
                    StringBuilder resContent = new StringBuilder();
                    responses.forEach(resContent::append);
                    log.info("AI：\n{}", resContent);
                })
                .subscribe();
        return chatClientResponseFlux;
    }

    @Override
    public String getName() {
        return "MyLogAdvisor";
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}
