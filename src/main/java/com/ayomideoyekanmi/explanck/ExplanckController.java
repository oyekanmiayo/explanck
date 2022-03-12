package com.ayomideoyekanmi.explanck;

import com.ayomideoyekanmi.explanck.interpreter.Explanck;
import com.ayomideoyekanmi.explanck.models.ExplanckDTO;
import org.springframework.web.bind.annotation.*;

/**
 * @author: aoyekanmi
 * @date: 11/03/2022
 */
@RestController
public class ExplanckController {
    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @CrossOrigin()
    @PostMapping("/interpret")
    public ExplanckDTO interpret(@RequestBody ExplanckDTO explanckDTO) {
        return new Explanck().interpret(explanckDTO);
    }
}
