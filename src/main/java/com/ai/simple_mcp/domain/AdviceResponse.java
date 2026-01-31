package com.ai.simple_mcp.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Ответ с советом и паттернами")
public class AdviceResponse {
    @Schema(description = "Полученный совет", example = "Рекомендую использовать кэширование и оптимизировать запросы к базе данных")
    private String advice;
    
    @Schema(description = "Найденные паттерны и их количество", example = "{\"performance\": 3, \"optimization\": 2}")
    private Map<String, Integer> patterns;
}
