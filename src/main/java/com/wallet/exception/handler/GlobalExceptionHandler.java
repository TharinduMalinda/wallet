package com.wallet.exception.handler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.wallet.exception.*;
import com.wallet.dto.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;

/**
 * This class act as central point of catching all exceptions.
 *
 * @author Malinda
 *
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    static final String LOG_CONTSTANT = "Resolved by GlobalExceptionHandler : ";
    static final String LOG_ERROR = "error";

    final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(HttpMessageNotReadableException.class )// need to modify. this exception for initial balance bigdecimal check
    public ResponseEntity<ResponseDTO> test1(InvalidFormatException ex){
        HashMap<String ,String > errorMap = new HashMap<>();
        ex.getPath().forEach(error->
            errorMap.put(error.getFieldName(),"Invalid input")
        );
        log.info(LOG_CONTSTANT,ex.getMessage());
        return new ResponseEntity<>(new ResponseDTO(LOG_ERROR,errorMap),  HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler({MethodArgumentNotValidException.class}  )
    public ResponseEntity<ResponseDTO> test1(MethodArgumentNotValidException ex){
        HashMap<String ,String > errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error->
            errorMap.put(error.getField(),error.getDefaultMessage())
        );
        log.info(LOG_CONTSTANT,ex.getMessage());
        return new ResponseEntity<>(new ResponseDTO(LOG_ERROR,errorMap),  HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ConstraintViolationException.class}  )
    public ResponseEntity<ResponseDTO> test1(ConstraintViolationException ex){
        HashMap<String ,String > errorMap = new HashMap<>();

        ex.getConstraintViolations().forEach(error->
            errorMap.put(error.getPropertyPath().toString(),error.getMessage())
        );
        log.info(LOG_CONTSTANT,ex.getMessage());
        return new ResponseEntity<>(new ResponseDTO(LOG_ERROR,errorMap),  HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({DataIntegrityViolationException.class}  )
    public ResponseEntity<ResponseDTO> test1(DataIntegrityViolationException ex){
        if(ex.getRootCause()==null)
            return new ResponseEntity<>(new ResponseDTO(LOG_ERROR,"Unknown error"),  HttpStatus.BAD_REQUEST);

        String message = null;
            if(ex.getRootCause().toString().contains("TRANSACTION(TXN_ID)")){
                message = "Duplicate transaction id";
            }

        log.info(LOG_CONTSTANT,ex.getMessage());
        return new ResponseEntity<>(new ResponseDTO(LOG_ERROR,message),  HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JWTTokenExpireException.class  )
    public ResponseEntity<ResponseDTO> test(JWTTokenExpireException ex){
        log.info(LOG_CONTSTANT,ex.getMessage());
        return new ResponseEntity<>(new ResponseDTO(LOG_ERROR, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class  )
    public ResponseEntity<ResponseDTO> test(IllegalArgumentException ex){
        log.info(LOG_CONTSTANT,ex.getMessage());
        return new ResponseEntity<>(new ResponseDTO(LOG_ERROR, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class  )
    public ResponseEntity<ResponseDTO> test(BadCredentialsException ex){
        log.info(LOG_CONTSTANT,ex.getMessage());
        return new ResponseEntity<>(new ResponseDTO(LOG_ERROR, "Incorrect credentials"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class  )
    public ResponseEntity<ResponseDTO> test(ResourceNotFoundException ex){
        log.info(LOG_CONTSTANT,ex.getMessage());
        return new ResponseEntity<>(new ResponseDTO(LOG_ERROR, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(StaticFieldUpdateException.class  )
    public ResponseEntity<ResponseDTO> test(StaticFieldUpdateException ex){
        log.info(LOG_CONTSTANT,ex.getMessage());
        return new ResponseEntity<>(new ResponseDTO(LOG_ERROR, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InsufficientBalanceException.class  )
    public ResponseEntity<ResponseDTO> test(InsufficientBalanceException ex){
        log.info(LOG_CONTSTANT,ex.getMessage());
        return new ResponseEntity<>(new ResponseDTO(LOG_ERROR, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceAlreadyExistException.class  )
    public ResponseEntity<ResponseDTO> test(ResourceAlreadyExistException ex){
        log.info(LOG_CONTSTANT,ex.getMessage());
        return new ResponseEntity<>(new ResponseDTO(LOG_ERROR, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO> test(Exception ex){
        log.error("catch by by GlobelExceptionHandler : ",ex);
        return new ResponseEntity<>(new ResponseDTO(LOG_ERROR, "Unidentified exception"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
