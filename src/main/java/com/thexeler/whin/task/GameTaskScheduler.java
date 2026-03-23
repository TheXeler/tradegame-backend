package com.thexeler.whin.task;

import com.thexeler.whin.WarhammerApplication;
import com.thexeler.whin.repository.UserRepository;
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
    private static boolean isNextWeekCalculating = false;

    @Scheduled(cron = "0 0 6 * * 1")
    public void nextWeekCalculate() {
        WarhammerApplication.logger.info("Next week calculating...");

        isNextWeekCalculating = true;

        userRepository.findAll().forEach(user -> {
            taskExecutor.execute(() -> {
                user.setTax(user.getTax() + user.getBountyTax());
                user.setBountyTax(0);
                user.setAlloy(user.getAlloy() + user.getBountyAlloy());
                user.setBountyAlloy(0);

                userRepository.save(user);
            });
        });

        userRepository.flush();

        isNextWeekCalculating = false;

        WarhammerApplication.logger.info("Next week calculated.");
    }
}
