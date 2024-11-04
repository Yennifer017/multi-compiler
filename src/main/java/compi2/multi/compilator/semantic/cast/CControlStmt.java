
package compi2.multi.compilator.semantic.cast;

import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.c3d.generators.ExpGenC3D;
import compi2.multi.compilator.analyzator.StmtsAnalizator;
import compi2.multi.compilator.semantic.c.CExp;
import compi2.multi.compilator.semantic.c.CImports;
import compi2.multi.compilator.semantic.c.CStatement;
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
public abstract class CControlStmt extends CStatement{
    
    protected List<CStatement> internalStmts;
    
    protected StmtsAnalizator stmtsAnalizator;
    protected ExpGenC3D expGenC3D;
    
    public CControlStmt(Position initPos, List<CStatement> internalStmt) {
        super(initPos);
        this.internalStmts = internalStmt;
        this.stmtsAnalizator = new StmtsAnalizator();
        this.expGenC3D = new ExpGenC3D();
    }
    
    protected void validateCondition(CImports imports, JSymbolTable clasesST, 
            SymbolTable symbolTable, SymbolTable pascalST, TypeTable typeTable, 
            List<String> semanticErrors, SemanticRestrictions restrictions, CExp condition){
        Label typeCond = condition.validateComplexData(
                imports, clasesST, symbolTable, pascalST, typeTable, semanticErrors
        );
        if(!tConvert.isNumericIntegerType(typeCond.getName())){
            semanticErrors.add(errorsRep.incorrectTypeError(
                    typeCond.getName(), 
                    PrimitiveType.BooleanPT.getName(), 
                    typeCond.getPosition())
            );
        }
    }
    
    protected ReturnCase validateInternal(CImports imports, JSymbolTable clasesST, 
            SymbolTable symbolTable, SymbolTable pascalST, TypeTable typeTable, 
            List<String> semanticErrors, SemanticRestrictions restrictions){
        return stmtsAnalizator.validateInternalStmts(
                imports, 
                clasesST, 
                symbolTable, 
                pascalST, 
                typeTable, 
                semanticErrors, 
                restrictions, 
                internalStmts
        );
    }
    
}
