package com.cadi.boardapi.controller;

import com.cadi.boardapi.model.DefaultRes;
import com.cadi.boardapi.model.SignInReq;
import com.cadi.boardapi.model.SignUpReq;
import com.cadi.boardapi.service.UserService;
import com.cadi.boardapi.util.ResponseMessage;
import com.cadi.boardapi.util.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {

    private static final DefaultRes FAIL_DEFAULT_RES = new DefaultRes(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 회원가입 (url : /signup)
     * @param signUpReq 객체
     * @return ResponseEntity
     */
    @PostMapping("signup")
    public ResponseEntity signUp (@RequestBody SignUpReq signUpReq) {
        try {
            return new ResponseEntity(userService.signUp(signUpReq), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("signin")
    public ResponseEntity signIn (@RequestBody SignInReq signInReq) {
        try {
            return new ResponseEntity(userService.singIn(signInReq), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
