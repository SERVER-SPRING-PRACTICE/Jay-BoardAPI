package com.cadi.boardapi.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    // 회원가입
    @Insert("INSERT INTO user(id, password, name, email, phone) VALUES (#{id},#{pw},#{name},#{email},#{phone})")
    void signUp(@Param("id") final String id, @Param("pw") final String pw, @Param("name") final String name, @Param("email") final String email, @Param("phone") final String phone);
}
