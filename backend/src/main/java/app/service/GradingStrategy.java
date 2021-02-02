package app.service;

import app.model.grade.Grade;
import app.model.grade.StrategyName;

public interface GradingStrategy {
    Grade grade(Grade grade);
    StrategyName getStrategyName();
}
