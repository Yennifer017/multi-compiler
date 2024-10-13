
package compi2.multi.compilator.semantic.cast.others;

import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.semantic.c.CExp;
import compi2.multi.compilator.semantic.util.Label;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class CArrayDec extends CVarDec{
    private List<CExp> dims;
    public CArrayDec(Label name, PrimitiveType type, CExp exp, List<CExp> dims) {
        super(name, type, exp);
        this.dims = dims;
    }
    
    public CArrayDec(Label name, PrimitiveType type, List<CExp> dims) {
        super(name, type);
        this.dims = dims;
    }
    
}
