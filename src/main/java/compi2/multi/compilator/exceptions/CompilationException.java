
package compi2.multi.compilator.exceptions;

/**
 *
 * @author blue-dragon
 */
public class CompilationException extends Exception {

    /**
     * Creates a new instance of <code>CompilationException</code> without
     * detail message.
     */
    public CompilationException() {
    }

    /**
     * Constructs an instance of <code>CompilationException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CompilationException(String msg) {
        super(msg);
    }
}
