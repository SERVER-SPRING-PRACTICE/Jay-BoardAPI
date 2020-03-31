package com.cadi.boardapi.mapper;

import com.cadi.boardapi.dto.Board;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BoardMapper {

    @Insert("INSERT INTO board(title, content, board_image, user_idx) VALUES (#{title}, #{content}, #{image}, #{user_idx})")
    void postBoard(@Param("title") String title, @Param("content") String content, @Param("image") String image, @Param("user_idx") int user_idx);

    @Select("SELECT title, content, board_image FROM board")
    List<Board> getBoardList();

    @Select("SELECT title, content, board_image FROM board WHERE board_idx = #{board_idx}")
    Board getBoard(@Param("board_idx") int board_idx);

}
