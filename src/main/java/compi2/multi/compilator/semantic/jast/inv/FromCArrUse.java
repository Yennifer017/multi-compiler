
package compi2.multi.compilator.semantic.jast.inv;

import compi2.multi.compilator.semantic.c.CExp;
import compi2.multi.compilator.util.Position;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class FromCArrUse extends JVarUse{
    
    private List<CExp> arrayAccess;
    
    public FromCArrUse(Position position, String name, List<CExp> arrayAccess) {
        super(position, name, JContextRef.Local);
        this.arrayAccess = arrayAccess;
    }
    
    @Override
    public boolean isStatement() {
        return false;
    }
    
}
