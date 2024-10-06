
package compi2.multi.compilator.semantic.jast;

import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.semantic.util.ReturnCase;
import compi2.multi.compilator.semantic.util.SemanticRestrictions;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public interface JSimpleStmt {
    public ReturnCase validateSS(
            SymbolTable symbolTable, 
            TypeTable typeTable, 
            List<String> semanticErrors, 
            SemanticRestrictions restrictions
    );
}
