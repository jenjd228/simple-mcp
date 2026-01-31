package com.ai.simple_mcp.api;

import com.ai.simple_mcp.domain.*;
import com.ai.simple_mcp.service.AdviceService;
import com.ai.simple_mcp.service.DecisionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/mcp")
@RequiredArgsConstructor
@Tag(name = "MCP Controller", description = "API для получения советов на основе вопросов и контекста")
public class McpController {

    private final AdviceService adviceService;
    private final DecisionService decisionService;

    @PostMapping("/advice")
    @Operation(summary = "Получить совет", description = "Возвращает совет на основе заданного вопроса и контекста")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Совет успешно получен",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = AdviceResponse.class))),
        @ApiResponse(responseCode = "400", description = "Некорректный запрос",
                content = @Content)
    })
    public AdviceResponse advice(
            @Parameter(description = "Запрос на получение совета", required = true,
                    schema = @Schema(implementation = AdviceRequest.class))
            @RequestBody AdviceRequest request) {
        return adviceService.advise(request);
    }

    @PostMapping("/decision")
    @Operation(summary = "Создать решение", description = "Создает новое решение с вопросом, вариантами и выбранным ответом")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Решение успешно создано",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Decision.class))),
        @ApiResponse(responseCode = "400", description = "Некорректный запрос",
                content = @Content)
    })
    public Decision createDecision(
            @Parameter(description = "Запрос на создание решения", required = true,
                    schema = @Schema(implementation = CreateDecisionRequest.class))
            @RequestBody CreateDecisionRequest request
    ) {
        return decisionService.create(request);
    }
}
