package com.sampaio.cursoestudo.resources.Exceptions;

import com.sampaio.cursoestudo.exception.AuthorizationException;
import com.sampaio.cursoestudo.exception.DataIntegrityException;
import com.sampaio.cursoestudo.exception.ObjectNotFoundExcpetion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundExcpetion.class)
    public ResponseEntity<ErroPadrao> objectNotFound(ObjectNotFoundExcpetion e, HttpServletRequest request){

        ErroPadrao erroPadrao = new ErroPadrao(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroPadrao);
    }

    @ExceptionHandler(DataIntegrityException.class)
    public ResponseEntity<ErroPadrao> dataIntegrity(DataIntegrityException e, HttpServletRequest request){

        ErroPadrao erroPadrao = new ErroPadrao(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroPadrao);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroPadrao> validacao(MethodArgumentNotValidException e, HttpServletRequest request){

        ValidacaoError erroPadrao = new ValidacaoError(HttpStatus.BAD_REQUEST.value(), "Erro de validação", System.currentTimeMillis());

        for (FieldError fieldError : e.getBindingResult().getFieldErrors()){
            erroPadrao.setError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroPadrao);
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<ErroPadrao> authorizationException(AuthorizationException e, HttpServletRequest request){

        ErroPadrao erroPadrao = new ErroPadrao(HttpStatus.FORBIDDEN.value(), e.getMessage(), System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(erroPadrao);
    }

}
