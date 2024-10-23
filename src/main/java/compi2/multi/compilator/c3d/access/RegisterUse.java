
package compi2.multi.compilator.c3d.access;

/**
 *
 * @author blue-dragon
 */
public class RegisterUse extends MemoryAccess{
    public static final String AX_INT = "AX_INT";
    public static final String BX_INT = "BX_INT";
    public static final String CX_INT = "CX_INT";
    
    private String register;

    public RegisterUse(String register) {
        this.register = register;
    }
}
