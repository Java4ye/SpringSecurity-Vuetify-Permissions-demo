package top.ryzeyang.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ken
 * @date 2020/11/9 下午 03:01
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/hello/anonymous")
    public String hello2() {
        return "anonymous";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/hello/admin")
    public String helloAdmin() {
        return "hello Admin";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/hello/user")
    public String helloUser() {
        return "hello user";
    }

    @PreAuthorize("hasAnyAuthority('system:user:query')")
    @GetMapping("/hello/user2")
    public String helloUser2() {
        return "hello user2";
    }

}
