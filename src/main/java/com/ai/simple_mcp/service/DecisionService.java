package com.ai.simple_mcp.service;

import com.ai.simple_mcp.domain.CreateDecisionRequest;
import com.ai.simple_mcp.domain.Decision;
import com.ai.simple_mcp.repository.DecisionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DecisionService {

    private final DecisionRepository repository;

    @Tool(description = """
        Сохранить принятое пользователем решение.
        Если не хватает информации не придумывай и спроси спроси пользователя.
        """)
    public Decision create(CreateDecisionRequest req) {
        log.info("create: {}", req);
        Decision decision = new Decision();
        decision.setSituation(req.getQuestion());
        decision.setOption(req.getOption());
        decision.setReasoning(req.getReasoning());
        decision.setEmotionalState(req.getEmotionalState());
        decision.setRegret(req.isRegret());

        repository.save(decision);
        return decision;
    }
}
