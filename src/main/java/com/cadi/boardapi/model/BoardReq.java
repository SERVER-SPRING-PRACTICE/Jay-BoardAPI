package com.cadi.boardapi.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class BoardReq {

    private String title;
    private String content;

    private List<MultipartFile> images;
    private List<String> imagesUrl;

}
