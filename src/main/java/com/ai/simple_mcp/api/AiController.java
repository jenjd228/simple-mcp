package com.ai.simple_mcp.api;

import com.ai.simple_mcp.ai.DecisionAdvisor;
import com.ai.simple_mcp.domain.MessageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
@Tag(name = "AI Controller", description = "API для обработки сообщений с помощью искусственного интеллекта")
public class AiController {

    private final DecisionAdvisor aiService;

    @PostMapping("/tools")
    @Operation(summary = "Обработать сообщение", description = "Обрабатывает текстовое сообщение с помощью AI и возвращает ответ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Сообщение успешно обработано",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = MessageDto.class))),
        @ApiResponse(responseCode = "400", description = "Некорректный запрос",
                content = @Content)
    })
    public MessageDto processUserMessage(
            @Parameter(description = "Сообщение для обработки", required = true,
                    schema = @Schema(implementation = MessageDto.class))
            @RequestBody MessageDto message) {
        return new MessageDto(aiService.processUserMessage(message.getText()));
    }
}
