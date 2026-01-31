package com.ai.simple_mcp.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Запрос на получение совета")
public class AdviceRequest {
    @Schema(description = "Вопрос для получения совета", example = "Как улучшить производительность приложения?")
    private String question;
    
    @Schema(description = "Дополнительный контекст для вопроса", example = "Приложение написано на Java с использованием Spring Boot")
    private String context;
}
