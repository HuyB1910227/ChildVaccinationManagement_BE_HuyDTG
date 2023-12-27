package vn.huydtg.immunizationservice.web.rest.errors;

import org.springframework.http.HttpStatus;

import org.springframework.web.ErrorResponseException;

import vn.huydtg.immunizationservice.web.rest.utils.ProblemDetailWithCause;
import vn.huydtg.immunizationservice.web.rest.utils.ProblemDetailWithCause.ProblemDetailWithCauseBuilder;
import java.net.URI;

public class BadRequestAlertException extends ErrorResponseException {

    private final String entityName;

    private final String errorKey;

    public static final URI DEFAULT_TYPE = URI.create("http://google.com" + "/problem-with-message");

    public BadRequestAlertException(String defaultMessage, String entityName, String errorKey) {
        this(DEFAULT_TYPE, defaultMessage, entityName, errorKey);
    }

    public BadRequestAlertException(URI type, String defaultMessage, String entityName, String errorKey) {
        super(
                HttpStatus.BAD_REQUEST,
                ProblemDetailWithCauseBuilder
                        .instance()
                        .withType(type)
                        .withStatus(HttpStatus.BAD_REQUEST.value())
                        .withTitle(defaultMessage)
                        .withProperty("message", "error." + errorKey)
                        .withProperty("params", entityName)
                        .build(),
                null
        );
        this.entityName = entityName;
        this.errorKey = errorKey;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getErrorKey() {
        return errorKey;
    }

    public ProblemDetailWithCause problemDetailWithCause() {
        return (ProblemDetailWithCause) this.getBody();
    }
}
