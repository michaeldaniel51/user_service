package com.dannycodes.user.service;

import com.dannycodes.user.VO.Department;
import com.dannycodes.user.VO.ResponseTemplateVO;
import com.dannycodes.user.entity.User;
import com.dannycodes.user.repository.UserRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final String USER_SERVICE = "USER-SERVICE";

    int count=1;

    public User saveUser(User user) {
        log.info("inside saveUser UserService");
        return userRepository.save(user);
    }


    public ResponseTemplateVO serviceFallback(Exception e){
        log.info("service is down: pleasehold while our brilliant developers are working overtime to bring the service back on " + new Date());
        return new ResponseTemplateVO();
    }




   //@Retry(name = USER_SERVICE, fallbackMethod = "serviceFallback" )
    //@RateLimiter(name = USER_SERVICE,fallbackMethod = "serviceFallback")
   @CircuitBreaker(name = USER_SERVICE,fallbackMethod = "serviceFallback")
    public ResponseTemplateVO getUserWithDepartment(Long userId) {

        log.info("inside getUserWithDepartment of UserService");
      // System.out.println("Retry method called " + count++ + " times at " + new Date());
        ResponseTemplateVO vo = new ResponseTemplateVO();
        User user = userRepository.findByUserId(userId);

        Department department = restTemplate.getForObject("http://DEPARTMENT-SERVICE/departments/" + user.getDepartmentId(), Department.class);

        vo.setUser(user);
        vo.setDepartment(department);
        return vo;

    }

}