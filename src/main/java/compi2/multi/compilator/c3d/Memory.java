
package compi2.multi.compilator.c3d;

import compi2.multi.compilator.analysis.typet.PrimitiveType;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class Memory implements CodeTransformable{
    private int stringCount;
    private int integerCount;
    private int floatCount;
    private int charCount;
    private int booleanCount;
    
    private String name;
    
    public Memory(String name){
        this.name = name;
    }

    @Override
    public StringBuilder generateCcode(StringBuilder builder) {
        definite(builder, PrimitiveType.IntegerPT, integerCount);
        definite(builder, PrimitiveType.StringPT, stringCount);
        definite(builder, PrimitiveType.RealPT, floatCount);
        definite(builder, PrimitiveType.CharPT, charCount);
        definite(builder, PrimitiveType.BooleanPT, booleanCount);
        return builder;
    }
    
    private StringBuilder definite(StringBuilder builder, PrimitiveType type, int count){
        builder.append(type.getCName()).append(" ");
        builder.append(this.name).append(type.getName());
        builder.append("[");
        builder.append(count);
        builder.append("];\n");
        return builder;
    }
    
    public String getMemoryName(PrimitiveType type){
        return this.name + type.getName();
    }
    
}
