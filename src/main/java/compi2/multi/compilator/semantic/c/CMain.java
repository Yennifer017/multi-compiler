
package compi2.multi.compilator.semantic.c;

import compi2.multi.compilator.semantic.cast.dec.CConstDec;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class CMain {
    private List<CConstDec> consts;
    private List<CDef> vars;
    
    private CImports imports;
    private List<CStatement> stmts;
    
    public CMain(CImports imports, List<CConstDec> consts, List<CDef> vars, List<CStatement> stmts){
        this.imports = imports;
        this.stmts = stmts;
        this.consts = consts;
        this.vars = vars;
    }
}
