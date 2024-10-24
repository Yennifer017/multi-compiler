
package compi2.multi.compilator.semantic.cast.dec;

import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.semantic.DefAst;
import compi2.multi.compilator.semantic.c.CDef;
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
public class CVarDec extends CDef{
    
    private PrimitiveType type;
    private CExp exp;
    
    public CVarDec(Label name, PrimitiveType type, CExp exp){
        super.name = name;
        this.type = type;
        this.exp = exp;
    }
    
    public CVarDec(Label name, PrimitiveType type){
        super.name = name;
        this.type = type;
    }

    
}
