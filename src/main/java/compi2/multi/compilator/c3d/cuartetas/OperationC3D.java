
package compi2.multi.compilator.c3d.cuartetas;

import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.assembly.AssemblyComps;
import compi2.multi.compilator.assembly.interfaces.VarAssignable;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.access.MemoryAccess;
import compi2.multi.compilator.semantic.DefiniteOperation;
import jdk.jshell.spi.ExecutionControl;

/**
 *
 * @author blue-dragon
 */
public class OperationC3D extends Cuarteta{
    private MemoryAccess variable;
    private MemoryAccess first;
    private MemoryAccess second;
    private DefiniteOperation operation;

    public OperationC3D(MemoryAccess variable, MemoryAccess first, 
            MemoryAccess second, DefiniteOperation operation) {
        this.variable = variable;
        this.first = first;
        this.second = second;
        this.operation = operation;
    }

    @Override
    public void generateCcode(StringBuilder builder) {
        variable.generateCcode(builder);
        builder.append(" = ");
        if(operation == DefiniteOperation.Power){
            builder.append("static_cast<float>(pow(");
            first.generateCcode(builder);
            builder.append(",");
            second.generateCcode(builder);
            builder.append("));\n");
        } else {
            first.generateCcode(builder);
            builder.append(operation.getSign());
            second.generateCcode(builder);
            builder.append(";\n");
        }
    }

    @Override
    public void generateAssemblyNasmCode(StringBuilder builder, AssemblyComps ac) {
        VarAssignable varAssign = (VarAssignable) variable; 
        switch (varAssign.getTypeAsign()) {
            case PrimitiveType.IntegerPT, PrimitiveType.BooleanPT:
                builder.append("mov ")
                        .append(varAssign.getAssemblyRepresentationForAssign())
                        .append(", ")
                        .append(1)
                        .append("\nadd ")
                        .append(varAssign.getAssemblyRepresentationForAssign())
                        .append(", ")
                        .append(1)
                        .append("\n")
                        ;
                break;
            default:
                throw new RuntimeException("not supported yet");
        }
    }
    
}
