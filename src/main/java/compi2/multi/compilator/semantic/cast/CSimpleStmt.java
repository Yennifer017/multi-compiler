
package compi2.multi.compilator.semantic.cast;

import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.semantic.c.CImports;
import compi2.multi.compilator.semantic.c.CStatement;
import compi2.multi.compilator.semantic.util.ReturnCase;
import compi2.multi.compilator.semantic.util.SemanticRestrictions;
import compi2.multi.compilator.util.Position;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class CSimpleStmt extends CStatement{
    
    private boolean isBreak;
    
    public CSimpleStmt(Position initPos, boolean isBreak) {
        super(initPos);
        this.isBreak = isBreak;
    }

    @Override
    public ReturnCase validate(CImports imports, JSymbolTable clasesST, 
            SymbolTable symbolTable, SymbolTable pascalST, TypeTable typeTable, 
            List<String> semanticErrors, SemanticRestrictions restrictions) {
        if (isBreak && !restrictions.allowBreak()) {
            semanticErrors.add(super.errorsRep.ilegalStmt("BREAK", initPos));
        } else if (!isBreak && !restrictions.allowBreak()) {
            semanticErrors.add(super.errorsRep.ilegalStmt("CONTINUE", initPos));
        }
        return new ReturnCase(false);
    }
    
}
