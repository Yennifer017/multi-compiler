/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package compi2.multi.compilator.exceptions;

/**
 *
 * @author blue-dragon
 */
public class FileExtensionException extends Exception{

    /**
     * Creates a new instance of <code>FileExtensionException</code> without
     * detail message.
     */
    public FileExtensionException() {
    }

    /**
     * Constructs an instance of <code>FileExtensionException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public FileExtensionException(String msg) {
        super(msg);
    }
}
