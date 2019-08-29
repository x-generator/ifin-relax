package com.ifinrelax.util;

import org.springframework.context.MessageSource;

import javax.naming.OperationNotSupportedException;
import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.springframework.context.i18n.LocaleContextHolder.getLocale;

/**
 * @author Timur Berezhnoi
 */
public class ConstraintViolationHolder {

    /**
     * The instance should not be created.
     *
     * @throws OperationNotSupportedException during creation an instance in the class.
     */
    private ConstraintViolationHolder() throws OperationNotSupportedException {
        throw new OperationNotSupportedException("Do not instanciate the ConstraintViolationHolder class, this is util class");
    }

    /**
     * Get messages from message source
     *
     * @param messageSource - spring message source.
     * @param constraintViolations - violations
     * @return lis of messages.
     */
    public static <T> List<String> getMessages(MessageSource messageSource, Set<ConstraintViolation<T>> constraintViolations) {
        Iterator<ConstraintViolation<T>> constraintViolationIterator = constraintViolations.iterator();
        List<String> messages = new ArrayList<>();
        while(constraintViolationIterator.hasNext()) {
            messages.add(messageSource.getMessage(constraintViolationIterator.next().getMessage(), null, getLocale()));
        }
        return messages;
    }
}
