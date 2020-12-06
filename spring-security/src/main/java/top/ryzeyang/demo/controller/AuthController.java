package top.ryzeyang.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import top.ryzeyang.demo.common.api.CommonResult;
import top.ryzeyang.demo.model.dto.UserDTO;
import top.ryzeyang.demo.utils.CommonResultUtil;
import top.ryzeyang.demo.utils.JwtTokenUtil;

import java.util.Collection;

/**
 * @author Ken
 * @date 2020/11/5 上午 09:40
 */
@RestController
@ResponseBody
@RequestMapping("/mock")
public class AuthController {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Qualifier("users")
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    AuthenticationManager authenticationManager;

    @GetMapping("/userinfo")
    public CommonResult<Collection<? extends GrantedAuthority>> getUserInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return CommonResultUtil.success(authorities);
    }

    /**
     * 模拟登陆
     */
    @PostMapping("/login")
    public CommonResult<String> login(@RequestBody UserDTO userDTO){
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        UsernamePasswordAuthenticationToken token
                = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authenticate = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String t = jwtTokenUtil.generateToken(userDetails);
        return CommonResultUtil.success(t);
    }

}
