package com.wpg.wpgblog.exception;

import com.wpg.wpgblog.common.BaseException;
import com.wpg.wpgblog.common.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MyException extends BaseException {
    public MyException(Status status) {
        super(status);
    }

    public MyException(Status status, Object data) {
        super(status, data);
    }

    public MyException(Integer code, String message) {
        super(code, message);
    }

    public MyException(Integer code, String message, Object data) {
        super(code, message, data);
    }
}
