package br.com.oath2.loja.javacore.domain.exceptions;

public class IncorrectPasswordException extends RuntimeException{
    public IncorrectPasswordException(String message) {
        super(message);
    }
}
