
package compi2.multi.compilator.c3d.access;

import compi2.multi.compilator.assembly.AssemblyComps;
import compi2.multi.compilator.c3d.util.Register;

/**
 *
 * @author blue-dragon
 */
public class RegisterUse extends MemoryAccess{
    
    private Register register;

    public RegisterUse(Register register) {
        this.register = register;
    }

    @Override
    public void generateCcode(StringBuilder builder) {
        builder.append(register.getName());
    }
    
    public boolean isStringRegister(){
        return switch (register) {
            case Register.AX_STRING, BX_STRING, CX_STRING -> true;
            default -> false;
        };
    }

    @Override
    public String getNasmPrintableCode(AssemblyComps ac) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
