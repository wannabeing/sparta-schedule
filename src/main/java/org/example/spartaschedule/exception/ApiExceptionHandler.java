package org.example.spartaschedule.exception;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice // @ControllerAdvice + @ResponseBody
public class ApiExceptionHandler {
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
}
