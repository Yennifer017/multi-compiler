
package compi2.multi.compilator.c3d.access;

import compi2.multi.compilator.assembly.AssemblyComps;
import compi2.multi.compilator.assembly.components.Literal;
import compi2.multi.compilator.assembly.interfaces.IImmediateValue;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class AtomicValue<T> extends MemoryAccess implements IImmediateValue{
    private T value;

    public AtomicValue(T value) {
        this.value = value;
    }

    @Override
    public void generateCcode(StringBuilder builder) {
        if(value instanceof String){
            builder.append("std::string(\"");
            builder.append(value);
            builder.append("\")");
        } else if (value instanceof Character){
            builder.append("'");
            builder.append(value);
            builder.append("'");
        } else {
            builder.append(value);
        }
    }

    @Override
    public void prepareForAssembly(AssemblyComps ac) {
        if((value instanceof String) || (value instanceof Character)){
            ac.getLiteralPool().getOrCreate(value.toString());
        }
    }

    @Override
    public Literal getLiteral(AssemblyComps ac) {
        return ac.getLiteralPool().get(value.toString());
    }
    
}
