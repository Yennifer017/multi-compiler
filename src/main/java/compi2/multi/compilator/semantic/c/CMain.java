
package compi2.multi.compilator.semantic.c;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class CMain {
    private CImports imports;
    private List<CStatement> stmts;
    
    public CMain(CImports imports, Object consts, Object vars, List<CStatement> stmts){
        this.imports = imports;
        this.stmts = stmts;
    }
}
