
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
    
}
