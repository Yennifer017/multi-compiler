
package compi2.multi.compilator.semantic.cexp;

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
public class CVarArrayUse extends CExp{
    private String name;
    private List<CExp> arrayAccess;
    
    public CVarArrayUse(Position pos, String name, List<CExp> arrayAccess) {
        super(pos);
        this.name = name;
        this.arrayAccess = arrayAccess;
    }
    
}
