package com.ai.simple_mcp.ai;

import com.ai.simple_mcp.domain.Decision;
import com.ai.simple_mcp.domain.UserProfile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PromptBuilder {

    public String buildAdvicePrompt(
            String question,
            String context,
            UserProfile profile,
            List<Decision> pastDecisions
    ) {

        return """
        Ты — персональный помощник по принятию решений.

        О пользователе:
        Ценности: %s
        Повторяющиеся ошибки: %s

        Прошлые решения:
        %s

        Текущая дилемма:
        Вопрос: %s
        Контекст: %s

        Дай совет, укажи риски и напомни паттерны.
        Говори честно, без мотивационных клише.
        """
                .formatted(
                        profile.getValues(),
                        profile.getPatterns(),
                        summarizeAdvicePrompt(pastDecisions),
                        question,
                        context
                );
    }

    private String summarizeAdvicePrompt(List<Decision> decisions) {
        return decisions.stream()
                        .limit(10)
                        .map(d -> "- " + d.getSituation() + " (regret=" + d.isRegret() + ")")
                        .reduce("", (a, b) -> a + "\n" + b);
    }

    public String buildPatternAnalysisPrompt(List<Decision> decisions) {

        return """
        Ты анализируешь историю решений одного человека.

        Твоя задача:
        1. Найти повторяющиеся паттерны поведения
        2. Определить ценности пользователя
        3. Найти ситуации, о которых он чаще всего жалеет

        История решений:
        %s

        Верни результат СТРОГО в JSON формате:

        {
          "values": ["..."],
          "patterns": {
            "pattern_name": 1-5
          }
        }

        Где:
        - values — устойчивые ценности
        - patterns — повторяющиеся паттерны поведения
        - вес (1–5) — насколько часто / сильно паттерн проявляется

        Не добавляй ничего вне {}.
        """
                .formatted(summarizePatternAnalysisPrompt(decisions));
    }

    private String summarizePatternAnalysisPrompt(List<Decision> decisions) {
        return decisions.stream()
                        .map(d -> """
                Вопрос: %s
                Выбор: %s
                Эмоции: %s
                Итог: %s
                Сожаление: %s
                """
                                .formatted(
                                        d.getSituation(),
                                        d.getOption(),
                                        d.getEmotionalState(),
                                        d.getOutcome(),
                                        d.isRegret()
                                )
                        )
                        .reduce("", (a, b) -> a + "\n" + b);
    }
}
