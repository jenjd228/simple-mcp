package com.ai.simple_mcp.repository;

import com.ai.simple_mcp.domain.Decision;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class DecisionRepository {

    private final List<Decision> decisions = new ArrayList<>();

    public void save(Decision decision) {
        decisions.add(decision);
    }

    public List<Decision> findAll() {
        return List.copyOf(decisions);
    }
}
