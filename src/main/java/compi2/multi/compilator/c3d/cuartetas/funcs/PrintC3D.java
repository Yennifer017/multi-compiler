
package compi2.multi.compilator.c3d.cuartetas.funcs;

import compi2.multi.compilator.assembly.AssemblyComps;
import compi2.multi.compilator.assembly.AssemblyConf;
import compi2.multi.compilator.assembly.components.Literal;
import compi2.multi.compilator.assembly.interfaces.AssemblyPreparable;
import compi2.multi.compilator.assembly.interfaces.IImmediateValue;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.access.MemoryAccess;

/**
 *
 * @author blue-dragon
 */
public class PrintC3D extends Cuarteta implements AssemblyPreparable{
    
    private MemoryAccess access;
    private boolean withLn;
    
    public PrintC3D(MemoryAccess access, boolean withLn){
        this.access = access;
        this.withLn = withLn;
    }

    @Override
    public void generateCcode(StringBuilder builder) {
        builder.append("std::cout<<");
        access.generateCcode(builder);
        if(withLn){
            builder.append("<< std::endl;\n");
        } else {
            builder.append(";\n");
        }
        
    }

    @Override
    public void prepareForAssembly(AssemblyComps ac) {
        if(access instanceof IImmediateValue iImmediateValue){
            iImmediateValue.prepareForAssembly(ac);
        }
    }

    @Override
    public void generateAssemblyNasmCode(StringBuilder builder, AssemblyComps ac) {
        if(access instanceof IImmediateValue iImmediateValue){
            Literal literal = iImmediateValue.getLiteral(ac);
            builder.append("mov rsi, ")
                    .append(literal.getLabel())
                    .append("\nmov rdx, ")
                    .append(literal.getLenghtLabel())
                    .append("\ncall ")
                    .append(AssemblyConf.PRINT_STRING_FUNCT_NAME)
                    .append("\n\n");
        } else {
            throw new UnsupportedOperationException("Aun no soportado");
        }
    }
    
}
