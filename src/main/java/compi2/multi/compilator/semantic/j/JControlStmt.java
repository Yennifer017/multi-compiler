
package compi2.multi.compilator.semantic.j;

import compi2.multi.compilator.analysis.jerarquia.NodeJerarTree;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.analyzator.StmtsAnalizator;
import compi2.multi.compilator.semantic.util.Label;
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
    
    protected ReturnCase validateInternalStmts(JSymbolTable globalST, SymbolTable symbolTable, TypeTable typeTable, 
            NodeJerarTree jerar, List<String> semanticErrors, SemanticRestrictions restrictions){
        SymbolTable currentST = new SymbolTable();
        currentST.setFather(symbolTable);
        return stmtsAnalizator.validateInternalStmts(globalST, symbolTable, 
                typeTable, jerar, semanticErrors, restrictions, internalStmts);
    }
    
    protected void validateCondition(JSymbolTable globalST, SymbolTable symbolTable, TypeTable typeTable, 
            NodeJerarTree jerar, List<String> semanticErrors, SemanticRestrictions restrictions, 
            JExpression condition){
        Label typeCond = condition.validateData(globalST, symbolTable, 
                typeTable, jerar, semanticErrors, restrictions);
        if(!tConvert.isNumericIntegerType(typeCond.getName())){
            semanticErrors.add(errorsRep.incorrectTypeError(
                    typeCond.getName(), 
                    PrimitiveType.BooleanPT.getName(), 
                    typeCond.getPosition())
            );
        }
    }
    
    
}
