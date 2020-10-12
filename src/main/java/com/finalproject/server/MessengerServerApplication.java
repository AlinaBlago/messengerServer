package com.finalproject.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class MessengerServerApplication implements WebMvcConfigurer{

    public static void main(String[] args)  {
        SpringApplication.run(MessengerServerApplication.class, args);
      //userService.init();
      //messageService.init();// // // }
}

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.setTaskExecutor(mvcTaskExecutor());
        configurer.setDefaultTimeout(30_000);
    }

    @Bean
    public ThreadPoolTaskExecutor mvcTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setThreadNamePrefix("mvc-task-");
        return taskExecutor;
    }
//    @PreDestroy
//    public void onExit() throws IOException {
//
//    //  userService.save();
//    //  messageService.save();
//    }

}


