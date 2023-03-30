package com.validator.service;

import org.junit.jupiter.api.DisplayName;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.validator.service.Validator.checkExpression;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDataProvider {
    @DataProvider(name = "equation")
    public Object[][] createEquations() {
        return new Object[][]{
                {"2*x+5=17", true},
                {"x*2+1=0", true},
                {"x/2-1=0", true}
        };
    }

    @Test(dataProvider = "equation")
    @DisplayName("Equations: {}, expected result: {}")
    void checkEquationsDataProvider(String equation, boolean expectedResult) {
        assertEquals(expectedResult, checkExpression(equation));
    }

    @DataProvider(name = "equations")
    public Iterator<Object[]> createEquationsFromFile() throws IOException {
        List<Object[]> data = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("src/main/resources/equations.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                data.add(new Object[]{parts[0], Boolean.parseBoolean(parts[1])});
            }
        }
        return data.iterator();
    }

    @Test(dataProvider = "equations")
    @DisplayName("Equations: {}, expected result: {}")
    void checkEquationsFile(String equation, boolean expectedResult) {
        assertEquals(expectedResult, checkExpression(equation));
    }
}