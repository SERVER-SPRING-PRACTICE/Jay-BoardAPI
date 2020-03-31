package com.cadi.boardapi.mapper;

import com.cadi.boardapi.dto.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    // 회원가입
    @Insert("INSERT INTO user(id, password, name, email, phone) VALUES (#{id},#{pw},#{name},#{email},#{phone})")
    void signUp(@Param("id") final String id, @Param("pw") final String pw, @Param("name") final String name, @Param("email") final String email, @Param("phone") final String phone);

    // 로그인
    @Select("SELECT user_idx FROM user WHERE id=#{id} AND password=#{pw}")
    User signIn(@Param("id") final String id, @Param("pw") final String pw);
}
