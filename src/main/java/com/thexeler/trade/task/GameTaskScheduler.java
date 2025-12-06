package com.thexeler.trade.task;

import com.thexeler.trade.TradeApplication;
import com.thexeler.trade.repository.UserRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class GameTaskScheduler {
    @Autowired
    @Qualifier("applicationTaskExecutor")
    private TaskExecutor taskExecutor;

    @Autowired
    private UserRepository userRepository;

    @Getter
    private static boolean isNextDayCalculating = false;

    @Scheduled(cron = "0 0 4 * * ?")
    public void nextDayCalculate() {
        TradeApplication.logger.info("Next day calculating...");

        isNextDayCalculating = true;

        userRepository.findAll().forEach(user -> {
            taskExecutor.execute(() -> {
                user.setBalance(user.getBalance() + 5);
            });
        });

        isNextDayCalculating = false;

        TradeApplication.logger.info("Next day calculated.");
    }
}
