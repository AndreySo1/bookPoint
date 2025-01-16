package by.tms.bookpoint.utils;

import by.tms.bookpoint.dto.ErrorResponseMap;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Component
public class ErrorsUtils {

    public ErrorResponseMap errorsResponse (BindingResult bindingResult){
        ErrorResponseMap errorResponseMap = new ErrorResponseMap();
        List<String> errors = new ArrayList<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.add(fieldError.getDefaultMessage());
            errorResponseMap.getErrors().put(fieldError.getField(), errors);
        }
        return errorResponseMap;
    }
}
