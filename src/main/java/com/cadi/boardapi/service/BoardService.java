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

    private final JwtService jwtService;

    public BoardService(S3FileUploadService s3FileUploadService, BoardMapper boardMapper, JwtService jwtService) {
        this.s3FileUploadService = s3FileUploadService;
        this.boardMapper = boardMapper;
        this.jwtService = jwtService;
    }

    public DefaultRes postBoard (BoardReq boardReq, String token) {
        try {
            JwtService.Token decodedToken = jwtService.decode(token);
            if(decodedToken == null) {
                return new DefaultRes(StatusCode.UNAUTHORIZED, ResponseMessage.FAIL_BOARD_CREATED);
            }

            if(boardReq.getImages() != null) {
                List<String> urlArray = new ArrayList<String>();
                for(MultipartFile file : boardReq.getImages()) {
                    urlArray.add(s3FileUploadService.upload(file));
                }
                boardReq.setImagesUrl(urlArray);
            }
            for(String url : boardReq.getImagesUrl()) {
                boardMapper.postBoard(boardReq.getTitle(), boardReq.getContent(), url, decodedToken.getUser_idx());
            }

            return new DefaultRes(StatusCode.CREATED, ResponseMessage.SUCCESS_BOARD_CREATED);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new DefaultRes(StatusCode.BAD_REQUEST, ResponseMessage.FAIL_BOARD_CREATED);
        }
    }

    public DefaultRes getBoardList () {
        try {
            return new DefaultRes(StatusCode.OK, ResponseMessage.SUCCESS_GET_BOARD_LIST, boardMapper.getBoardList());
        } catch (Exception e) {
            log.error(e.getMessage());
            return new DefaultRes(StatusCode.BAD_REQUEST, ResponseMessage.FAIL_GET_BOARD_LIST);
        }
    }

    public DefaultRes getBoard (int board_idx) {
        try {
            return new DefaultRes(StatusCode.OK, ResponseMessage.SUCCESS_GET_BOARD, boardMapper.getBoard(board_idx));
        } catch (Exception e) {
            log.error(e.getMessage());
            return new DefaultRes(StatusCode.BAD_REQUEST, ResponseMessage.FAIL_GET_BOARD);
        }
    }

}
