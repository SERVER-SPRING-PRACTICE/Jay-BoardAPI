package com.cadi.boardapi.service;

import com.cadi.boardapi.mapper.UserMapper;
import com.cadi.boardapi.model.DefaultRes;
import com.cadi.boardapi.model.SignUpReq;
import com.cadi.boardapi.util.PasswordUtil;
import com.cadi.boardapi.util.ResponseMessage;
import com.cadi.boardapi.util.StatusCode;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public DefaultRes signUp(final SignUpReq signUpReq) {
        // 비밀번호 확인
        if(!signUpReq.getPassword().equals(signUpReq.getConfirmPw())) {
            return new DefaultRes(StatusCode.BAD_REQUEST, ResponseMessage.FAIL_USER);
        }
        // 비밀번호 암호화
        PasswordUtil passwordUtil = new PasswordUtil();
        signUpReq.setPassword(passwordUtil.encryptSHA256(signUpReq.getPassword()));
        userMapper.signUp(signUpReq.getId(), signUpReq.getPassword(), signUpReq.getName(), signUpReq.getEmail(), signUpReq.getPhone());
        return new DefaultRes(StatusCode.CREATED, ResponseMessage.CREATED_USER);
    }

}
