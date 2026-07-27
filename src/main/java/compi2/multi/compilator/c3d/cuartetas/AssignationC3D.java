
package compi2.multi.compilator.c3d.cuartetas;

import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.assembly.AssemblyComps;
import compi2.multi.compilator.assembly.interfaces.VarAssignable;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.access.MemoryAccess;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class AssignationC3D extends Cuarteta{
    private MemoryAccess variable;
    private MemoryAccess first;

    public AssignationC3D(MemoryAccess variable, MemoryAccess first) {
        this.variable = variable;
        this.first = first;
    }

    @Override
    public void generateCcode(StringBuilder builder) {
        variable.generateCcode(builder);
        builder.append(" = ");
        first.generateCcode(builder);
        builder.append(";\n");
    }

    @Override
    public void generateAssemblyNasmCode(StringBuilder builder, AssemblyComps ac) {
        VarAssignable varAssign = (VarAssignable) variable; 
        switch (varAssign.getTypeAsign()) {
            case PrimitiveType.IntegerPT, PrimitiveType.BooleanPT:
                builder.append("mov ")
                        .append(varAssign.getAssemblyRepresentation())
                        .append(", ")
                        .append(first.getAssemblyRepresentation())
                        .append("\n")
                        ;
                break;
            default:
                throw new RuntimeException("not supported yet");
        }
    }
    
}
