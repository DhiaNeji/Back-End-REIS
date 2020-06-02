package Reis.PFE.demo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import Reis.PFE.Entities.Customer;
import Reis.PFE.dao.CustomerRepository;
import me.xdrop.fuzzywuzzy.FuzzySearch;

@SpringBootApplication(scanBasePackages = {"Reis.PFE.config","Reis.PFE.demo","Reis.PFE.dao","Reis.PFE.Entities","Reis.PFE.Service","Reis.PFE.Controller"})
@EntityScan("Reis.PFE.Entities")
@EnableJpaRepositories("Reis.PFE.dao")
@EnableAsync
@EnableCaching
public class ProjectApplication implements CommandLineRunner{
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private RepositoryRestConfiguration repositoryRestConfiguration;
	@Bean("threadPoolTaskExecutor")
    public TaskExecutor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(1000);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setThreadNamePrefix("AsyncExe-");
        return executor;
    }
	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
	this.repositoryRestConfiguration.exposeIdsFor(Customer.class);
	}
}
