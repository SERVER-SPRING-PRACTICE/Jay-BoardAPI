package com.cadi.boardapi.controller;

import com.cadi.boardapi.model.BoardReq;
import com.cadi.boardapi.model.DefaultRes;
import com.cadi.boardapi.service.BoardService;
import com.cadi.boardapi.util.ResponseMessage;
import com.cadi.boardapi.util.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class BoardController {

    private static final DefaultRes FAIL_DEFAULT_RES = new DefaultRes(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping("board")
    public ResponseEntity postBoard(BoardReq boardReq, @RequestPart(value = "images", required = false) final MultipartFile[] images) {

        try {
            if(images != null) {
                List<MultipartFile> files = new ArrayList<>();
                for(MultipartFile file : images) {
                    files.add(file);
                }
                boardReq.setImages(files);
            }
            return new ResponseEntity(boardService.postBoard(boardReq), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
