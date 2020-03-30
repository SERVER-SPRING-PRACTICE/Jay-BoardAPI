package com.cadi.boardapi.service;

import com.cadi.boardapi.dto.User;
import com.cadi.boardapi.mapper.UserMapper;
import com.cadi.boardapi.model.DefaultRes;
import com.cadi.boardapi.model.SignInReq;
import com.cadi.boardapi.model.SignUpReq;
import com.cadi.boardapi.util.PasswordUtil;
import com.cadi.boardapi.util.ResponseMessage;
import com.cadi.boardapi.util.StatusCode;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserMapper userMapper;

    private final JwtService jwtService;

    public UserService(UserMapper userMapper, JwtService jwtService) {
        this.userMapper = userMapper;
        this.jwtService = jwtService;
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

    public DefaultRes singIn(final SignInReq signInReq) {
        PasswordUtil passwordUtil = new PasswordUtil();
        signInReq.setPassword(passwordUtil.encryptSHA256(signInReq.getPassword()));
        final User user = userMapper.signIn(signInReq.getId(), signInReq.getPassword());

        if(user == null) {
            return new DefaultRes(StatusCode.UNAUTHORIZED, ResponseMessage.LOGIN_FAIL);
        }
        // JWT 생성
        final JwtService.TokenRes tokenDto = new JwtService.TokenRes(jwtService.createJwtToken(user.getUser_idx()));
        return DefaultRes.res(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, tokenDto);
    }
}
