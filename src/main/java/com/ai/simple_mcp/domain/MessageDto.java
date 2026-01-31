package com.ai.simple_mcp.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сообщение для обмена с AI")
public class MessageDto {
    @Schema(description = "Текст сообщения", example = "Помоги мне принять решение о смене работы")
    private String text;
}
