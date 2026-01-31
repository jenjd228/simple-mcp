package com.ai.simple_mcp.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Запрос на создание решения")
public class CreateDecisionRequest {
    @Schema(description = "Вопрос для принятия решения", example = "Менять ли работу?")
    private String question;
    
    @Schema(description = "Выбранный вариант", example = "Уйти")
    private String option;
    
    @Schema(description = "Обоснование выбора", example = "Предлагают лучшие условия и зарплату")
    private String reasoning;
    
    @Schema(description = "Эмоциональное состояние", example = "Взволнованно, но оптимистично")
    private String emotionalState;

    @Schema(description = "Сожалеет ли", example = "Да")
    private boolean regret;
}
