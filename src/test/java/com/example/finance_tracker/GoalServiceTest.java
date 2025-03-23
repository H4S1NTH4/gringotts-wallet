//package com.example.finance_tracker;
//
//import com.example.finance_tracker.Entity.Goal;
//import com.example.finance_tracker.Repository.GoalRepository;
//import com.example.finance_tracker.Service.GoalService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.Optional;
//
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//public class GoalServiceTest {
//
//    @Mock
//    private GoalRepository goalRepository;
//
//    @InjectMocks
//    private GoalService goalService;
//
//    private Goal goal;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        goal = new Goal();
//        goal.setGoalId("1");
//        goal.setUserId("user1");
//        goal.setTargetAmount(new BigDecimal("1000"));
//        goal.setCollectedAmount(new BigDecimal("0"));
//        goal.setGoalStatus(Goal.GoalStatus.NOT_STARTED);
//    }
//
//    @Test
//    public void testCreateGoal() {
//        when(goalRepository.save(goal)).thenReturn(goal);
//
//        Goal createdGoal = goalService.createGoal(goal);
//
//        assertNotNull(createdGoal);
//        assertEquals(goal.getGoalId(), createdGoal.getGoalId());
//        verify(goalRepository, times(1)).save(goal);
//    }
//
//    @Test
//    public void testGetAllGoals() {
//        when(goalRepository.findAll()).thenReturn(List.of(goal));
//
//        List<Goal> goals = goalService.getAllGoals();
//
//        assertNotNull(goals);
//        assertEquals(1, goals.size());
//        assertEquals(goal.getGoalId(), goals.get(0).getGoalId());
//        verify(goalRepository, times(1)).findAll();
//    }
//
//    @Test
//    public void testGetUserGoals() {
//        when(goalRepository.findGoalsByUserId("user1")).thenReturn(List.of(goal));
//
//        List<Goal> userGoals = goalService.getUserGoals("user1");
//
//        assertNotNull(userGoals);
//        assertEquals(1, userGoals.size());
//        assertEquals(goal.getGoalId(), userGoals.get(0).getGoalId());
//        verify(goalRepository, times(1)).findGoalsByUserId("user1");
//    }
//
//    @Test
//    public void testGetGoalById() {
//        when(goalRepository.findById("1")).thenReturn(Optional.of(goal));
//
//        Optional<Goal> foundGoal = goalService.getGoalById("1");
//
//        assertTrue(foundGoal.isPresent());
//        assertEquals(goal.getGoalId(), foundGoal.get().getGoalId());
//        verify(goalRepository, times(1)).findById("1");
//    }
//
//    @Test
//    public void testDeleteGoal() {
//        doNothing().when(goalRepository).deleteById("1");
//
//        goalService.deleteGoal("1");
//
//        verify(goalRepository, times(1)).deleteById("1");
//    }
//
//    @Test
//    public void testAddMoneyToGoal() {
//        Goal goalDetails = new Goal();
//        goalDetails.setGoalId("1");
//        goalDetails.setCollectedAmount(new BigDecimal("500"));
//
//        when(goalRepository.findById("1")).thenReturn(Optional.of(goal));
//        when(goalRepository.save(goal)).thenReturn(goal);
//
//        Goal updatedGoal = goalService.addMoneyToGoal(goalDetails);
//
//        assertNotNull(updatedGoal);
//        assertEquals(new BigDecimal("500"), updatedGoal.getCollectedAmount());
//        assertEquals(Goal.GoalStatus.ONGOING, updatedGoal.getGoalStatus());
//        verify(goalRepository, times(1)).findById("1");
//        verify(goalRepository, times(1)).save(goal);
//    }
//
//    @Test
//    public void testAddMoneyToGoal_CompletingGoal() {
//        Goal goalDetails = new Goal();
//        goalDetails.setGoalId("1");
//        goalDetails.setCollectedAmount(new BigDecimal("1000"));
//
//        goal.setCollectedAmount(new BigDecimal("500"));
//        goal.setTargetAmount(new BigDecimal("1000"));
//
//        when(goalRepository.findById("1")).thenReturn(Optional.of(goal));
//        when(goalRepository.save(goal)).thenReturn(goal);
//
//        Goal updatedGoal = goalService.addMoneyToGoal(goalDetails);
//
//        assertNotNull(updatedGoal);
//        assertEquals(new BigDecimal("1500"), updatedGoal.getCollectedAmount());
//        assertEquals(Goal.GoalStatus.COMPLETED, updatedGoal.getGoalStatus());
//        verify(goalRepository, times(1)).findById("1");
//        verify(goalRepository, times(1)).save(goal);
//    }
//
//    @Test
//    public void testAddMoneyToGoal_GoalNotFound() {
//        Goal goalDetails = new Goal();
//        goalDetails.setGoalId("1");
//
//        when(goalRepository.findById("1")).thenReturn(Optional.empty());
//
//        RuntimeException exception = assertThrows(RuntimeException.class, () -> goalService.addMoneyToGoal(goalDetails));
//        assertEquals("Goal not found", exception.getMessage());
//    }
//}
