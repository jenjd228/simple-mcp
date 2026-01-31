package com.ai.simple_mcp.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
@Schema(description = "Профиль пользователя с ценностями, проблемами и паттернами поведения")
public class UserProfile {
    @Schema(description = "Ценности пользователя", example = "[\"свобода\", \"деньги\", \"рост\"]")
    private Set<String> values = new HashSet<>();        // свобода, деньги, рост
    
    @Schema(description = "Проблемные точки пользователя", example = "[\"выгорание\", \"неопределённость\"]")
    private Set<String> painPoints = new HashSet<>();    // выгорание, неопределённость
    
    @Schema(description = "Паттерны поведения и их количество", example = "{\"затягивает решения\": 3, \"импульсивность\": 2}")
    private Map<String, Integer> patterns = new HashMap<>(); // "затягивает решения" -> 3
}
