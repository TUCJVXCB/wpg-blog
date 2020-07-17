package com.wpg.wpgblog.controller;

import com.wpg.wpgblog.common.ApiResponse;
import com.wpg.wpgblog.common.BaseException;
import com.wpg.wpgblog.common.Status;
import com.wpg.wpgblog.dao.UserDao;
import com.wpg.wpgblog.entity.User;
import com.wpg.wpgblog.exception.MyException;
import com.wpg.wpgblog.form.LoginForm;
import com.wpg.wpgblog.form.RegisterForm;
import com.wpg.wpgblog.util.JwtUtil;
import com.wpg.wpgblog.vo.TokenVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    @ApiOperation("登陆")
    public ApiResponse login(@RequestBody @Valid LoginForm loginForm) {
        String email = loginForm.getEmail();
        String password = loginForm.getPassword();
        User user = userDao.findByEmail(email);
        if (user.getPassword().equals(password)) {
            String JwtToken = jwtUtil.createJWT(email, user.getUsername());
            return ApiResponse.ofSuccess(new TokenVO(JwtToken, user.getEmail(), user.getAvatar(), user.getUsername()));
        }else {
            return ApiResponse.ofStatus(Status.USERNAME_PASSWORD_ERROR);
        }
    }

    @GetMapping("/info")
    @ApiOperation("获得用户信息")
    public ApiResponse getByToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String email = jwtUtil.parseJWT(token).getId();
        return ApiResponse.ofSuccess(userDao.findByEmail(email));
    }

    @PostMapping("/register")
    @ApiOperation("注册")
    public ApiResponse register(@RequestBody RegisterForm registerForm) {
        User user = new User();
        BeanUtils.copyProperties(registerForm, user);
        int result = userDao.addUser(user);

        if (result > 0) {
            String token = jwtUtil.createJWT(registerForm.getEmail(), registerForm.getNickname());
            return ApiResponse.ofSuccess(new TokenVO(token, registerForm.getEmail(), "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif", registerForm.getNickname()));
        }else {
            return ApiResponse.ofStatus(Status.EMAIL_DUPLICATE);
        }
    }
    @PostMapping("/logout")
    @ApiOperation("退出登陆")
    public ApiResponse logout(HttpServletRequest request) {
//        try {
//            // 设置JWT过期
//            jwtUtil.invalidateJWT(request);
//        } catch (SecurityException e) {
//            throw new MyException(Status.UNAUTHORIZED);
//        }
//        return ApiResponse.ofStatus(Status.LOGOUT);

        jwtUtil.invalidateJWT(request);
        return ApiResponse.ofSuccess();
    }

}
