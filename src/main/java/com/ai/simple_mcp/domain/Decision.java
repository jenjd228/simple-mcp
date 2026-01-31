package com.ai.simple_mcp.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.Instant;

@Data
@Schema(description = "Решение с вопросом, вариантами и результатом")
public class Decision {
    @Schema(description = "Временная метка создания решения", example = "2024-01-15T10:30:00Z")
    private Instant timestamp = Instant.now();
    
    @Schema(description = "Вопрос для принятия решения", example = "Менять ли работу?")
    private String situation;
    
    @Schema(description = "Выбранный вариант", example = "Уйти")
    private String option;
    
    @Schema(description = "Обоснование выбора", example = "Предлагают лучшие условия и зарплату")
    private String reasoning;
    
    @Schema(description = "Эмоциональное состояние", example = "Взволнованно, но оптимистично")
    private String emotionalState;
    
    @Schema(description = "Результат решения", example = "Смена работы оказалась удачной")
    private String outcome;
    
    @Schema(description = "Наличие сожаления о решении", example = "false")
    private boolean regret;
}
