package com.example.ssh2;

import com.example.ssh2.domain.Book;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

public class BookValidationTests {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    @DisplayName("whenAllFieldsCorrectThenValidationSucceedTest")
    public void whenAllFieldsCorrectThenValidationSucceed() {
        var book = new Book("1234567891011", "Title", "Author", 9.90);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("whenIsbnDefinedButIncorrectThenValidationFailsTest")
    public void whenIsbnDefinedButIncorrectThenValidationFails() {
        var book = new Book("1234567891011Z", "Title", "Author", 9.90);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("The ISBN format must be valid");

    }

}
