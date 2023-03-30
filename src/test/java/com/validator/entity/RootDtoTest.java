package com.validator.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class RootDtoTest {

    @DisplayName("Test RootDto constructor")
    @ParameterizedTest(name = "Case {index}: input={0}, expectedId={1}, expectedValue={2}")
    @CsvSource({
            ", , ",
            "1, 'no Roots', 'no Roots'",
            "2, 'Root', 'Root'"
    })
    void testRootDtoConstructor(Long inputId, String inputValue, String expectedValue) {
        Root root = Root.builder().rootId(inputId).root(inputValue).build();
        RootDto rootDto = new RootDto(root);
        assertNotNull(rootDto.getRootId());
        assertEquals(expectedValue, rootDto.getValue());
    }

    @DisplayName("Test RootDto constructor")
    @ParameterizedTest(name = "Case {index}: inputId={0}, inputValue={1}, expectedValue={2}")
    @CsvSource({
            "1, 'no Roots', 'no Roots'",
            "2, 'Root', 'Root'"
    })
    void testRootDtoConstructor(String inputId, String inputValue, String expectedValue) {
        Long id = inputId != null && !inputId.isEmpty() ? Long.parseLong(inputId) : null;
        RootDto rootDto = new RootDto(Root.builder().rootId(id).root(inputValue).build());
        assertEquals(id, rootDto.getRootId());
        assertEquals(expectedValue, rootDto.getValue());
    }
}

