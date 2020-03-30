package com.cadi.boardapi.model;

import com.cadi.boardapi.util.ResponseMessage;
import com.cadi.boardapi.util.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DefaultRes<T> {

    private int status;
    private String message;
    private T data;

    public DefaultRes(final int statusCode, final String responseMessage) {
        this.status = statusCode;
        this.message = responseMessage;
        this.data = null;
    }

    public static<T> DefaultRes<T> res (final int statusCode, final String responseMessage) {
        return res(statusCode, responseMessage, null);
    }

    public static<T> DefaultRes<T> res(final int statusCode, final String responseMessage, final T t) {
        return DefaultRes.<T>builder()
                .data(t)
                .status(statusCode)
                .message(responseMessage)
                .build();
    }

    public static final DefaultRes FAIL_DEFAULT_RES = new DefaultRes(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);

}
