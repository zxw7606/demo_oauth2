package com.example.oauth2_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Map;


//@EnableWebMvc
@SpringBootApplication
@Controller
public class Oauth2ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Oauth2ServerApplication.class, args);
    }


//    @RequestMapping("/oauth/confirm_access")
//    public String confirmAccess(Model model){
//
//        Map<String, Object> map = model.asMap();
//        for (Map.Entry<String,Object> entry:map.entrySet()){
//            System.out.println(String.format("{} : {}",entry.getKey(),entry.getValue()));
//        }
//
//        return "check";
//    }

}
