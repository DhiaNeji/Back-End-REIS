package Reis.PFE.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

public class ThreadPoolConfiguration implements SchedulingConfigurer{
@Bean(name="asynchThreadPoolTaskExecutor")
	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		// TODO Auto-generated method stub
		
	}
public ThreadPoolTaskExecutor threadPoolTaskExecutor()
{
	ThreadPoolTaskExecutor threadPoolTaskExecutor=new ThreadPoolTaskExecutor();
	threadPoolTaskExecutor.setCorePoolSize(10);
	threadPoolTaskExecutor.setMaxPoolSize(20);
	threadPoolTaskExecutor.setQueueCapacity(10);
	threadPoolTaskExecutor.setThreadNamePrefix("asynchThreadPoolTaskExecutor");
	threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
	return threadPoolTaskExecutor;
}
}
