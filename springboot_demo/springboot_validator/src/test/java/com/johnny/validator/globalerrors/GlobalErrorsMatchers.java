package com.johnny.validator.globalerrors;

import lombok.NoArgsConstructor;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.ModelResultMatchers;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@NoArgsConstructor
class GlobalErrorsMatchers extends ModelResultMatchers {

    static GlobalErrorsMatchers globalErrors() {
        return new GlobalErrorsMatchers();
    }

    ResultMatcher hasGlobalError(String attribute, String expectedMessage) {
        return result -> {
            BindingResult bindingResult = getBindingResult(result.getModelAndView(), attribute);
            bindingResult.getGlobalErrors()
                    .stream()
                    .filter(oe -> attribute.equals(oe.getObjectName()))
                    .forEach(oe -> assertEquals("Expected default message", expectedMessage, oe.getDefaultMessage()));
        };
    }

    private BindingResult getBindingResult(ModelAndView mav, String name) {
        BindingResult result = (BindingResult) mav.getModel().get(BindingResult.MODEL_KEY_PREFIX + name);
        assertTrue("No BindingResult for attribute: " + name, result != null);
        assertTrue("No global errors for attribute [" + name + "]", result.getGlobalErrorCount() > 0);
        return result;
    }
}