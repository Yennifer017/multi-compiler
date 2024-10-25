
package compi2.multi.compilator.semantic.cast;

import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.semantic.c.CExp;
import compi2.multi.compilator.semantic.c.CImports;
import compi2.multi.compilator.semantic.c.CStatement;
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
public class CForAst extends CControlStmt{
    private CStatement first;
    private CStatement last;
    private CExp condition;
    public CForAst(Position initPos, List<CStatement> internalStmt, 
            CStatement first, CStatement last, CExp condition) {
        super(initPos, internalStmt);
        this.first = first;
        this.last = last;
        this.condition = condition;
    }

    @Override
    public ReturnCase validate(CImports imports, JSymbolTable clasesST, 
            SymbolTable symbolTable, SymbolTable pascalST, TypeTable typeTable, 
            List<String> semanticErrors, SemanticRestrictions restrictions) {
        if(first != null){
            first.validate(
                    imports, clasesST, symbolTable, pascalST, typeTable, semanticErrors, restrictions
            );
        }
        if(last != null){
            last.validate(
                    imports, clasesST, symbolTable, pascalST, typeTable, semanticErrors, restrictions
            );
        }
        super.validateCondition(
                imports, clasesST, symbolTable, pascalST, typeTable, semanticErrors, restrictions, condition
        );
        return super.validateInternal(
                imports, clasesST, symbolTable, pascalST, typeTable, semanticErrors,
                new SemanticRestrictions(
                        true, 
                        true, 
                        restrictions.getReturnType(),
                        restrictions.getReturnType()
                )
        );
    }
    
}
