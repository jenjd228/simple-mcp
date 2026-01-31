package com.ai.simple_mcp.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Запрос на отражение решения")
public class ReflectDecisionRequest {
    @Schema(description = "Результат решения", example = "Смена работы оказалась удачной")
    private String outcome;
    
    @Schema(description = "Наличие сожаления о решении", example = "false")
    private boolean regret;
}
