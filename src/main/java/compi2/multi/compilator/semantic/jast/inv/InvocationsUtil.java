
package compi2.multi.compilator.semantic.jast.inv;

import compi2.multi.compilator.analysis.jerarquia.NodeJerarTree;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.util.RetJInvC3D;
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
    
    public RetJInvC3D generateC3DInvocations(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, 
            Memory temporals, List<JInvocation> invocations, int instanceRef){
        RetJInvC3D currentRetInv = null;
        for (int i = 0; i < invocations.size(); i++) {
            JInvocation invocation = invocations.get(i);
            if(i == 0){
                currentRetInv = invocation.generateCuartetas(
                        admiMemory, internalCuartetas, temporals, instanceRef
                );
            } else {
                currentRetInv = invocation.generateCuartetas(
                        admiMemory, internalCuartetas, temporals, 
                        currentRetInv.getTemporalUse()
                );
            }
        }
        return currentRetInv;
    }
    
    public Label validateInvocation(JSymbolTable globalST, SymbolTable symbolTable, 
            TypeTable typeTable, NodeJerarTree jerar, List<String> semanticErrors,
            List<JInvocation> invocations, Position initPos, boolean canBeStmt){
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
            
            if((i == invocations.size() - 1) 
                    && (canBeStmt != invocation.isStatement())){
                semanticErrors.add(
                        errorsRep.notAssignationAcurrateError(initPos)
                );
            }
        }
        return currentType;
    }
    
}
