package io.falcon.assignment.exceptions;

import io.falcon.assignment.utils.ApiResponseCodes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.falcon.assignment.utils.Constants.MESSAGE;
import static io.falcon.assignment.utils.Constants.URI;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<Object> handleBadRequest(BadRequestException ex, WebRequest request) {
        log.error("Received bad request error :: {}", ex.getMessage());
        return responseEntity(ex.getErrorCode(), request, null);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleInternalServerError(Exception ex, WebRequest request) {
        log.error("handleInternalServerError :: {}", ex.getMessage());
        if (ex instanceof ConstraintViolationException) {
            return responseEntity(ApiResponseCodes.FORM_CONSTRAINT_ERROR, request, null);
        }
        if (ex instanceof ValidationException) {
            if (ex.getCause() instanceof BadRequestException) {
                BadRequestException exception = (BadRequestException) ex.getCause();
                return responseEntity(exception.getErrorCode(), request, null);
            }
            return responseEntity(ApiResponseCodes.FORM_CONSTRAINT_ERROR, request, null);
        }

        return responseEntity(ApiResponseCodes.INTERNAL_SERVER_ERROR, request, Stream.of(ex.getMessage()).collect(Collectors.toMap(k -> MESSAGE, v -> v)));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> fieldMappings = ex.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.toMap(k -> MESSAGE, fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage(), (field1, field2) -> field1));
        return responseEntity(ApiResponseCodes.FORM_CONSTRAINT_ERROR, request, fieldMappings);
    }

    private ResponseEntity<Object> responseEntity(ApiResponseCodes apiResult, WebRequest request, Map<String, String> errorMessages) {
        Map<String, String> messages = errorMessages == null ? new HashMap<>() : errorMessages;
        messages.put(URI, ((ServletWebRequest) request).getRequest().getRequestURI());
        messages.computeIfAbsent(MESSAGE, key -> apiResult.getDescription());

        return ResponseEntity
                .status(HttpStatus.valueOf(apiResult.getHttpCode()))
                .body(apiResult.asResponse(messages));
    }
}
