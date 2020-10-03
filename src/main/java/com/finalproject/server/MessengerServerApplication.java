package com.finalproject.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PreDestroy;
import java.io.IOException;

@SpringBootApplication
public class MessengerServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessengerServerApplication.class, args);
      //userService.init();
      //messageService.init();// // // }
}
//    @PreDestroy
//    public void onExit() throws IOException {
//
//    //  userService.save();
//    //  messageService.save();
//    }

}


