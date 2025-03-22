package com.example.finance_tracker.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Document(collection = "goals")
public class Goal {
        @Id
        private String goalId;

        private String userId;
        private BigDecimal targetAmount;
        private BigDecimal collectedAmount;
        private LocalDate startDate;
        private LocalDate deadLine;
        private String description;
        private GoalStatus goalStatus;

    public enum GoalStatus {
        COMPLETED, ONGOING, NOT_STARTED
    }
    public Goal(String userId, BigDecimal amount, BigDecimal collectedAmount, LocalDate startDate, LocalDate deadLine, String description,GoalStatus goalStatus) {
        this.userId = userId;
        this.targetAmount = amount;
        this.collectedAmount = collectedAmount;
        this.startDate = startDate;
        this.deadLine = deadLine;
        this.description = description;
        this.goalStatus =goalStatus;
    }
    public Goal(){}

    public String getGoalId() {
        return goalId;
    }

    public void setGoalId(String goalId) {
        this.goalId = goalId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(BigDecimal targetAmount) {
        this.targetAmount = targetAmount;
    }

    public BigDecimal getCollectedAmount() {
        return collectedAmount;
    }

    public void setCollectedAmount(BigDecimal collectedAmount) {
        this.collectedAmount = collectedAmount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(LocalDate deadLine) {
        this.deadLine = deadLine;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GoalStatus getGoalStatus() {
        return goalStatus;
    }

    public void setGoalStatus(GoalStatus goalStatus) {
        this.goalStatus = goalStatus;
    }
}
