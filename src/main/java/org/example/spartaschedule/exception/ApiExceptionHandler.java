package org.example.spartaschedule.exception;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice // @ControllerAdvice + @ResponseBody
public class ApiExceptionHandler {
    /**
     * [예외] 유효하지 않은 URL 경로로 접근하려는 경우 호출되는 메서드
     * @param exception 유효하지 않은 URL 입력 시 발생한 예외 객체
     * @return 에러 메시지와 에러 상태코드 반환
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFoundUrlException(NoHandlerFoundException exception) {
        Map<String, String> error = new HashMap<>(); // 에러<필드, 메시지> 변수
        error.put("error", "URL 경로를 다시 확인해주세요!: " + exception.getRequestURL());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * [예외] 요청 데이터 유효성 검사 실패시 호출되는 메서드
     * @param exception 유효성 검사 실패로 발생한 예외 객체
     * @return 유효성 검사 실패한 필드와 메시지, 에러 상태 코드 반환
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>(); // 에러<필드, 메시지> 변수

        exception.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * [예외] 요청한 데이터가 DB에 없을 경우 호출되는 메서드
     * @param exception 데이터가 없을때 발생한 예외 객체
     * @return 에러 메시지와 에러 상태코드 반환
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> handleResponseStatusException(ResponseStatusException exception) {
        Map<String, String> error = new HashMap<>(); // 에러<메시지, 코드> 변수
        error.put("error", exception.getReason());
        return new ResponseEntity<>(error, exception.getStatusCode());
    }

    /**
     * [예외] 사용자가 입력한 필드 값이 존재하지 않을 경우 호출되는 메서드
     * @param exception 필드 값이 존재하지 않을 때 발생한 예외 객체
     * @return 에러 메시지와 에러 상태코드 반환
     */
    @ExceptionHandler(UnrecognizedPropertyException.class)
    public ResponseEntity<Map<String, String>> handleUnrecognizedProperty(UnrecognizedPropertyException exception) {
        Map<String, String> error = new HashMap<>(); // 에러<메시지, 코드> 변수
        error.put("error", String.format("허용되지 않은 필드 '%s'가 포함되어 있습니다.", exception.getPropertyName()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * [예외] 파라미터 타입을 전달 받았을 경우 호출되는 메서드
     * @param exception 파라미터 타입을 전달 받았을 경우에 발생한 예외 객체
     * @return 에러 메시지와 에러코드 반환
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        Map<String, String> error = new HashMap<>(); // 에러<메시지, 코드> 변수
        error.put("error", String.format("파라미터 '%s'의 타입이 올바르지 않습니다", exception.getName()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * [예외] 사용자 요청 JSON 을 제대로 읽을 수 없을 때 호출되는 메서드
     * @param exception 요청 JSON 을 읽을 수 없을 때 호출되는 예외 객체
     * @return 에러 메시지와 에러코드 반환
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleInvalidJson(HttpMessageNotReadableException exception) {
        Map<String, String> error = new HashMap<>(); // 에러<메시지, 코드> 변수
        error.put("error", "RequestBody JSON 형식이 올바르지 않습니다. 형식을 다시 확인해주세요.");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
