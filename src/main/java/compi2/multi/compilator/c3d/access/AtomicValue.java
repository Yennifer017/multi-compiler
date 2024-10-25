
package compi2.multi.compilator.c3d.access;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class AtomicValue<T> extends MemoryAccess{
    private T value;

    public AtomicValue(T value) {
        this.value = value;
    }

    @Override
    public void generateCcode(StringBuilder builder) {
        if(value instanceof String){
            builder.append("\"");
            builder.append(value);
            builder.append("\"");
        } else if (value instanceof Character){
            builder.append("'");
            builder.append(value);
            builder.append("'");
        } else {
            builder.append(value);
        }
    }
    
}
