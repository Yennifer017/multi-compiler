
package compi2.multi.compilator.exceptions;

/**
 *
 * @author blue-dragon
 */
public class NoDataFoundEx extends Exception {

    /**
     * Creates a new instance of <code>NoDataFoundEx</code> without detail
     * message.
     */
    public NoDataFoundEx() {
    }

    /**
     * Constructs an instance of <code>NoDataFoundEx</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public NoDataFoundEx(String msg) {
        super(msg);
    }
}
