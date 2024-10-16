
package compi2.multi.compilator.semantic.j;

import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.analyzator.StmtsAnalizator;
import compi2.multi.compilator.semantic.util.ReturnCase;
import compi2.multi.compilator.semantic.util.SemanticRestrictions;
import compi2.multi.compilator.util.Position;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public abstract class JControlStmt extends JStatement{
    protected StmtsAnalizator stmtsAnalizator;
    protected List<JStatement> internalStmts;

    public JControlStmt(Position initPos) {
        super(initPos);
    }
    
    protected ReturnCase validateInternalStmts(SymbolTable symbolTable, TypeTable typeTable, 
            List<String> semanticErrors, SemanticRestrictions restrictions){
        /*SymbolTable currentST = new SymbolTable();
        currentST.setFather(symbolTable);
        return stmtsAnalizator.validateInternalStmts(currentST, typeTable, semanticErrors, 
                restrictions, internalStmts);*/
        return null;
    }
    
    
}
