package app.service.impl;

import app.model.grade.StrategyName;
import app.service.GradingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class GradingStrategyFactory {
    private Map<StrategyName, GradingStrategy> strategies;

    @Autowired
    public GradingStrategyFactory(Set<GradingStrategy> strategySet) {
        createStrategy(strategySet);
    }

    public GradingStrategy findStrategy(StrategyName strategyName) {
        return strategies.get(strategyName);
    }

    private void createStrategy(Set<GradingStrategy> strategySet) {
        strategies = new HashMap<>();
        strategySet.forEach(strategy -> strategies.put(strategy.getStrategyName(), strategy));
    }
}
