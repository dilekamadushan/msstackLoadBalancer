package com.grydtech.msstack.loadbalancer;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/manager")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
    
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("2222222222222222222222In Manager Controller");
      
        
        CuratorMembershipProtocol curatorMembershipProtocol = new CuratorMembershipProtocol();
        curatorMembershipProtocol.setConnectionString("localhost:2181");
        curatorMembershipProtocol.start();
        List<Member> memberList = curatorMembershipProtocol.getMembers("order");
        for(Member member: memberList){
            System.out.println(member.toString());
        }
    
        Greeting greeting = restTemplate
                .getForObject("http://localhost:8081/greeting", Greeting.class);
        
        //return new Greeting(counter.incrementAndGet(),String.format(template, name));
        return greeting;
    }
}
