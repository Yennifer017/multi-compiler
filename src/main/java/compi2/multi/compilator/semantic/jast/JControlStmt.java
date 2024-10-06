
package compi2.multi.compilator.semantic.jast;

import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.semantic.ControlStruct;
import compi2.multi.compilator.semantic.util.ReturnCase;
import compi2.multi.compilator.semantic.util.SemanticRestrictions;
import compi2.multi.compilator.util.Position;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public abstract class JControlStmt extends ControlStruct{

    public JControlStmt(Position initPos) {
        super(initPos);
    }
    
    @Override
    protected ReturnCase validateInternalStmts(SymbolTable symbolTable, TypeTable typeTable, 
            List<String> semanticErrors, SemanticRestrictions restrictions){
        SymbolTable currentST = new SymbolTable();
        currentST.setFather(symbolTable);
        return stmtsAnalizator.validateInternalStmts(currentST, typeTable, semanticErrors, 
                restrictions, internalStmts);
    }
    
    
}
