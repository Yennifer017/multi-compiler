
package compi2.multi.compilator.analysis.typet;

import java.util.HashMap;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public final class TypeTable extends HashMap<String, Type>{
    
    private TypeTable father;
    
    public TypeTable(){
        insertPrimData();
    }
    
    public TypeTable(Boolean insertPrimitive){
        if(insertPrimitive){
            insertPrimData();
        }
    }
    
    private void insertPrimData(){
        for (PrimitiveType primType : PrimitiveType.values()) {
            this.put(primType.getName(), primType.getType());
        }
    }
    
}
