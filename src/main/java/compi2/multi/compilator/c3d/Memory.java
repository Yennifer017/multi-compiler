
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
    
    private int bytes;
    
    private String name;
    
    public Memory(String name){
        this.name = name;
    }
    
    public void incrementMemory(int generalCount){
        stringCount = generalCount;
        integerCount = generalCount;
        floatCount = generalCount;
        charCount = generalCount;
        booleanCount = generalCount;
    }

    @Override
    public void generateCcode(StringBuilder builder) {
        definite(builder, PrimitiveType.IntegerPT, integerCount);
        definite(builder, PrimitiveType.StringPT, stringCount);
        definite(builder, PrimitiveType.RealPT, floatCount);
        definite(builder, PrimitiveType.CharPT, charCount);
        definite(builder, PrimitiveType.BooleanPT, booleanCount);
    }
    
    private void definite(StringBuilder builder, PrimitiveType type, int count){
        if(count > 0){
            builder.append(type.getCName()).append(" ");
            builder.append(this.name).append(type.getName());
            builder.append("[");
            builder.append(count);
            builder.append("];\n");
        }
    }
    
    public String getMemoryName(PrimitiveType type){
        return this.name + type.getName();
    }
    
    public int getCount(PrimitiveType type){
        switch (type) {
            case PrimitiveType.IntegerPT:
                return this.integerCount;
            case PrimitiveType.BooleanPT:
                return this.booleanCount;
            case PrimitiveType.RealPT:
                return this.floatCount;
            case PrimitiveType.StringPT:
                return this.stringCount;
            default:
                return this.charCount;
        }
    }
    
    public void increment(PrimitiveType type, int increment){
        switch (type) {
            case PrimitiveType.IntegerPT -> this.integerCount += increment;
            case PrimitiveType.BooleanPT -> this.booleanCount += increment;
            case PrimitiveType.RealPT -> this.floatCount += increment;
            case PrimitiveType.StringPT -> this.stringCount += increment;
            default -> this.charCount += increment;
        }
    }

    /*@Override
    public void generateAssemblyCode(StringBuilder builder) {
        builder.append(this.name)
                .append(" resb ")
                .append(this.bytes)
                .append("\n");
    }*/
    
}
