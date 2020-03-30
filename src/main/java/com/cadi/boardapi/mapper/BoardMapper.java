package com.cadi.boardapi.mapper;

import com.cadi.boardapi.model.BoardReq;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BoardMapper {

    @Insert("INSERT INTO board(title, content, board_image1) VALUES (#{title},#{content},#{image})")
    void postBoard(@Param("title") String title, @Param("content") String content, @Param("image") String image);

}
