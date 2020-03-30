package com.cadi.boardapi.service;

import com.cadi.boardapi.mapper.BoardMapper;
import com.cadi.boardapi.model.BoardReq;
import com.cadi.boardapi.model.DefaultRes;
import com.cadi.boardapi.util.ResponseMessage;
import com.cadi.boardapi.util.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class BoardService {

    private final S3FileUploadService s3FileUploadService;

    private final BoardMapper boardMapper;

    public BoardService(S3FileUploadService s3FileUploadService, BoardMapper boardMapper) {
        this.s3FileUploadService = s3FileUploadService;
        this.boardMapper = boardMapper;
    }

    public DefaultRes postBoard (BoardReq boardReq) {
        try {
            if(boardReq.getImages() != null) {
                List<String> urlArray = new ArrayList<String>();
                for(MultipartFile file : boardReq.getImages()) {
                    urlArray.add(s3FileUploadService.upload(file));
                }
                boardReq.setImagesUrl(urlArray);
            }
            for(String url : boardReq.getImagesUrl()) {
                boardMapper.postBoard(boardReq.getTitle(), boardReq.getContent(), url);
            }

            return new DefaultRes(StatusCode.CREATED, ResponseMessage.SUCCESS_BOARD_CREATED);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new DefaultRes(StatusCode.BAD_REQUEST, ResponseMessage.FAIL_BOARD_CREATED);
        }
    }

}
