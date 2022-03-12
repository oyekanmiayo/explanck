package com.ayomideoyekanmi.explanck.models;

import lombok.Data;

/**
 * @author: aoyekanmi
 * @date: 11/03/2022
 */
@Data
public class ExplanckDTO {
    String expression;
    String value;

    String ast;
}
