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
public enum TokenizeState {
    DEFAULT, OPERATOR, NUMBER, KEYWORD, STRING, CONSTANT, COMMENT
}
