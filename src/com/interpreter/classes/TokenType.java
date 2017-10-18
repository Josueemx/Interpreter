/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interpreter.classes;

/**
 *
 * @author Morales
 */
public enum TokenType {
    NUMBER, STRING, NEWLINE, OPERATOR, EOF, UNKNOWN, 
    ADD, SUBTRACT, MULTIPLY, DIVIDE, MODULUS, EXPONENTIATION, LEFT_PAREN, RIGHT_PAREN, ASSIGNMENT,
    EQUAL, NOTEQUAL, LESS, GREATER, LESSEQUAL, GREATEREQUAL, OR, AND, NOT,
    PRINT, PRINTLN, WAIT, KEYWORD, END, START, WHILE, IF
}
