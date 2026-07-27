
package compi2.multi.compilator.c3d.access;

import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.assembly.AssemblyComps;
import compi2.multi.compilator.assembly.interfaces.VarAssignable;
import compi2.multi.compilator.assembly.utils.AssemblyPrinter;
import compi2.multi.compilator.c3d.util.Register;

/**
 *
 * @author blue-dragon
 */
public class RegisterUse extends MemoryAccess implements VarAssignable{
    
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
        return AssemblyPrinter.getPrintIntCode(register.getNasmRegister());
    }

    @Override
    public PrimitiveType getTypeAsign() {
        return this.register.getType();
    }

    @Override
    public String getAssemblyRepresentationForAssign() {
        return this.register.getNasmRegister();
    }

}
