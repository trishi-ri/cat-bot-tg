package ru.v1as.tg.cat.service.clock;

import static ru.v1as.tg.cat.tg.MdcTgContext.fromCurrentMdc;
import static ru.v1as.tg.cat.utils.LogUtils.logExceptions;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class SimpleThreadSleepClock implements BotClock {

    private final ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(4);

    @Override
    public void schedule(Runnable task, long amount, TimeUnit minutes) {
        executor.schedule(fromCurrentMdc().wrap(logExceptions(task)), amount, minutes);
    }
}
