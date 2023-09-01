package io.emailapp.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;
import static lombok.AccessLevel.PROTECTED;

@Data
@SuperBuilder
@JsonInclude(NON_DEFAULT)
@FieldDefaults(level = PROTECTED)
public class HttpResponse {

    String timestamp;
    int statusCode;
    HttpStatus status;
    String message;
    String developerMessage;
    String path;
    String requestMethod;
    Map<?, ?> data;

}
