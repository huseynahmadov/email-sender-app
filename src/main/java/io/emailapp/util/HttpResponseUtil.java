package io.emailapp.util;

import io.emailapp.model.dto.HttpResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public enum HttpResponseUtil {

    HTTP_RESPONSE_UTIL;

    public <T> HttpResponse buildHttpResponse(T data, String message, String mapKey, HttpStatus httpStatus) {
        return HttpResponse.builder()
                .timestamp(LocalDateTime.now().toString())
                .data(Map.of(mapKey, data))
                .message(message)
                .status(httpStatus)
                .statusCode(httpStatus.value())
                .build();
    }

}
