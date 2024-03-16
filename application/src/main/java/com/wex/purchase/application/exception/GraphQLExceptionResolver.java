package com.wex.purchase.application.exception;

import com.wex.purchase.core.exception.CoreException;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

import java.text.MessageFormat;
import java.util.stream.Collectors;

@Component
public class GraphQLExceptionResolver extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if(ex instanceof ConstraintViolationException) {
            var ex1 = (ConstraintViolationException) ex;
            var messages = ex1.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());
            return GraphqlErrorBuilder.newError()
                    .errorType(ErrorType.BAD_REQUEST)
                    .message(StringUtils.join(messages, ";"))
                    .build();
        }

        if(ex instanceof BindException && ex.getMessage().contains("typeMismatch")) {
            var bindException = (BindException) ex;
            var message = MessageFormat.format("The value {0} is not valid for {1}",
                    bindException.getFieldError().getRejectedValue(), bindException.getFieldError().getField());
            return GraphqlErrorBuilder.newError()
                    .errorType(ErrorType.BAD_REQUEST)
                    .message(message)
                    .build();
        }

        if(ex instanceof CoreException) {
            return GraphqlErrorBuilder.newError()
                    .errorType(ErrorType.BAD_REQUEST)
                    .message(((CoreException) ex).getErrorMessage().getMessage())
                    .build();
        }

        return GraphqlErrorBuilder.newError()
                .errorType(ErrorType.INTERNAL_ERROR)
                .message(ex.getMessage())
                .build();
    }
}