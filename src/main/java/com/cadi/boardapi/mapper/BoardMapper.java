package com.cadi.boardapi.mapper;

import com.cadi.boardapi.dto.Board;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BoardMapper {

    @Insert("INSERT INTO board(title, content, board_image) VALUES (#{title},#{content},#{image})")
    void postBoard(@Param("title") String title, @Param("content") String content, @Param("image") String image);

    @Select("SELECT title, content, board_image FROM board")
    List<Board> getBoardList();


}
