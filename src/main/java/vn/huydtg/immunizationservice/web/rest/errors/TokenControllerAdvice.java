package vn.huydtg.immunizationservice.web.rest.errors;



import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import vn.huydtg.immunizationservice.web.rest.vm.ErrorDetails;

import java.util.Date;

@RestControllerAdvice
public class TokenControllerAdvice {
    @ExceptionHandler(value = TokenRefreshException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorDetails handleTokenRefreshException(TokenRefreshException tokenRefreshException, WebRequest webRequest) {
        return new ErrorDetails(new Date(), tokenRefreshException.getMessage(), webRequest.getDescription(false));
    }
}
