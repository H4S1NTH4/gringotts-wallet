package com.example.finance_tracker.Service;

import com.example.finance_tracker.Entity.Goal;
import com.example.finance_tracker.Repository.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class GoalService {
    @Autowired
    private GoalRepository goalRepository;

    public Goal createGoal(Goal goal) {
        //goal.setCollectedAmount(BigDecimal.ZERO);
        //goal.setStatus(GoalStatus.NOT_STARTED);
        return goalRepository.save(goal);
    }

    public List<Goal> getAllGoals() {
        return goalRepository.findAll();
    }


    public List<Goal> getUserGoals(String userId) {
        return goalRepository.findGoalsByUserId(userId);
    }

    public Optional<Goal> getGoalById(String goalId) {
        return goalRepository.findById(goalId);
    }


    public void deleteGoal(String goalId) {
        goalRepository.deleteById(goalId);
    }

    public Goal addMoneyToGoal(Goal goalDetails) {
        Goal goal = getGoalById(goalDetails.getGoalId()).orElseThrow(() -> new RuntimeException("Goal not found"));
        //BigDecimal collectedAmount = goal.getCollectedAmount();
        //BigDecimal targetAmount = goal.getTargetAmount();
        BigDecimal newCollectedAmount = goalDetails.getCollectedAmount().add(goal.getCollectedAmount());


        goal.setCollectedAmount(newCollectedAmount);
        if (goal.getTargetAmount().compareTo(newCollectedAmount) <= 0) {
            goal.setGoalStatus(Goal.GoalStatus.COMPLETED);
        } else {
            goal.setGoalStatus(Goal.GoalStatus.ONGOING);
        }
        goalRepository.save(goal);
        return goal;
    }

}
