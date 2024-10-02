/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package compi2.multi.compilator.exceptions;

/**
 *
 * @author blue-dragon
 */
public class SemanticException extends Exception{

    /**
     * Creates a new instance of <code>SemanticException</code> without detail
     * message.
     */
    public SemanticException() {
    }

    /**
     * Constructs an instance of <code>SemanticException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public SemanticException(String msg) {
        super(msg);
    }
}
