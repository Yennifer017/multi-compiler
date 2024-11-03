
package compi2.multi.compilator.semantic.jast.inv;

import compi2.multi.compilator.analysis.jerarquia.NodeJerarTree;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.semantic.c.CImports;
import compi2.multi.compilator.semantic.cast.inv.objs.CInvocation;
import compi2.multi.compilator.semantic.util.Label;
import compi2.multi.compilator.util.ErrorsRep;
import compi2.multi.compilator.util.Position;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class InvocationsUtil {
    
    private ErrorsRep errorsRep;
    
    public InvocationsUtil(){
        this.errorsRep = new ErrorsRep();
    }
    
    public Label validateInvocation(JSymbolTable globalST, SymbolTable symbolTable, 
            TypeTable typeTable, NodeJerarTree jerar, List<String> semanticErrors,
            List<JInvocation> invocations, Position initPos, boolean needExpression){
        Label currentType = null;
        for (int i = 0; i < invocations.size(); i++) {
            JInvocation invocation = invocations.get(i);
            if(i == 0){
                currentType = invocation.validate(
                        globalST, symbolTable, typeTable, jerar, semanticErrors
                );
            } else {
                currentType = invocation.validate(
                        globalST, symbolTable, typeTable, 
                        jerar, semanticErrors, currentType
                );
            }
            
            if(i == (invocations.size() - 1) ){
                if(needExpression && !invocation.hasReturnType()){
                    semanticErrors.add(
                        errorsRep.notExpressionError(initPos)
                    );
                } else if(!needExpression && !invocation.isStatement()){ //need statement
                    semanticErrors.add(
                        errorsRep.notStatementError(initPos)
                    );
                }
            }

        }
        return currentType;
    }
    
    
    public Label validateInvocation(CImports imports, JSymbolTable clasesST, 
            SymbolTable symbolTable, SymbolTable pascalST,
            TypeTable typeTable, List<String> semanticErrors, 
            List<CInvocation> invocations, Position initPos, boolean needExpression){
        Label currentType = null;
        for (int i = 0; i < invocations.size(); i++) {
            CInvocation invocation = invocations.get(i);
            if(i == 0){
                currentType = invocation.validate(
                        imports, clasesST, symbolTable, pascalST, typeTable, semanticErrors
                );
            } else {
                currentType = invocation.validate(
                        imports, clasesST, symbolTable, pascalST, 
                        typeTable, semanticErrors, currentType
                );
            }
            
            if(i == (invocations.size() - 1) ){
                if(needExpression && !invocation.hasReturnType()){
                    semanticErrors.add(
                        errorsRep.notExpressionError(initPos)
                    );
                } else if(!needExpression && !invocation.isStatement()){ //need statement
                    semanticErrors.add(
                        errorsRep.notStatementError(initPos)
                    );
                }
            }

        }
        return currentType;
    }
    
}
