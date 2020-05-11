package com.marcinadd.repairshop.aop;

import com.marcinadd.repairshop.item.buyable.part.NotEnoughPartsInStockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NotEnoughPartsInStockException.class)
    public ResponseEntity handleNotEnoughPartsInStockException(NotEnoughPartsInStockException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
    }
}
