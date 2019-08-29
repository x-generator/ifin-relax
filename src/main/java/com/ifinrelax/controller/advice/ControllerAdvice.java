package com.ifinrelax.controller.advice;

import com.ifinrelax.dto.message.ResponseMessageDTO;
import com.ifinrelax.exception.EntityNotFoundException;
import com.ifinrelax.exception.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.springframework.context.i18n.LocaleContextHolder.getLocale;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * Handler for validations.
 *
 * @author Timur Berezhnoi
 */
@RestControllerAdvice
public class ControllerAdvice {

    private final MessageSource messageSource;

    @Autowired
    public ControllerAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public List<ResponseMessageDTO> processValidationError(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        return processFieldErrors(fieldErrors);
    }

    @ExceptionHandler(value = {UserAlreadyExistException.class, UsernameNotFoundException.class, EntityNotFoundException.class})
    @ResponseStatus(BAD_REQUEST)
    public ResponseMessageDTO exceptionProcessor(RuntimeException exception) {
        return new ResponseMessageDTO(exception.getMessage());
    }

    private List<ResponseMessageDTO> processFieldErrors(List<FieldError> fieldErrors) {
        List<ResponseMessageDTO> result = new ArrayList<>();

        if(fieldErrors != null) {
            for(FieldError fieldError: fieldErrors) {
                Locale currentLocale = getLocale();
                String localizedMessage = messageSource.getMessage(fieldError.getDefaultMessage(), null, currentLocale);
                result.add(new ResponseMessageDTO(localizedMessage));
            }
        }
        return result;
    }
}