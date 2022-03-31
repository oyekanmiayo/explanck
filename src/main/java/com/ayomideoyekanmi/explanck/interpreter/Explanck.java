package com.ayomideoyekanmi.explanck.interpreter;

import com.ayomideoyekanmi.explanck.models.ExplanckDTO;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * @author: aoyekanmi
 * @date: 11/03/2022
 */
public class Explanck {
    private static final Interpreter interpreter = new Interpreter();

    public static void main(String[] args) {
        String exp = "2 ^ 3";
        exp = "120+12.3*53-9";
        Scanner scanner = new Scanner(exp);
        List<Token> tokens = scanner.scanTokens();
        System.out.println(tokens);

        Parser parser = new Parser(tokens);
        Expr expr = parser.parse();

        System.out.println(new AstPrinter().print(expr));
        System.out.println(interpreter.interpret(expr));
    }

    public ExplanckDTO interpret(ExplanckDTO explanckDTO) {
        String exp = explanckDTO.getExpression();
        Scanner scanner = new Scanner(exp);
        Parser parser = new Parser(scanner.scanTokens());
        Expr expr = parser.parse();
        String value = interpreter.interpret(expr);

        explanckDTO.setValue(value);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        try {
            String val = mapper.writeValueAsString(expr);
            System.out.println(val);
//            explanckDTO.setAst(val);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return explanckDTO;
    }

    static void error(String message) {
        System.err.println("Error: " + message);
    }
}
