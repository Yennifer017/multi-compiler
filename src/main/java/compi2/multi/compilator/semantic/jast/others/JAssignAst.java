
package compi2.multi.compilator.semantic.jast.others;

import compi2.multi.compilator.analysis.jerarquia.NodeJerarTree;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.semantic.j.JExpression;
import compi2.multi.compilator.semantic.j.JStatement;
import compi2.multi.compilator.semantic.jast.inv.JContextRef;
import compi2.multi.compilator.semantic.jast.inv.JInvocation;
import compi2.multi.compilator.semantic.util.Label;
import compi2.multi.compilator.semantic.util.ReturnCase;
import compi2.multi.compilator.semantic.util.SemanticRestrictions;
import compi2.multi.compilator.util.Position;
import java.util.LinkedList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class JAssignAst extends JStatement{
    
    private List<JInvocation> variable;
    private JExpression value;
    
    public JAssignAst(Position initPos, List<JInvocation> variable, JExpression value) {
        super(initPos);
        this.variable = variable;
        this.value = value;
    }
    
    public JAssignAst(Position initPos, List<JInvocation> variable, 
            JExpression value, JContextRef first) {
        super(initPos);
        try {
            variable.get(0).setContext(first);
            this.variable = variable;
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            this.variable = new LinkedList<>();
        }
        this.value = value;
    }

    @Override
    public ReturnCase validate(JSymbolTable globalST, SymbolTable symbolTable, 
            TypeTable typeTable, NodeJerarTree jerar, List<String> semanticErrors, 
            SemanticRestrictions restrictions) {
        Label typeValue = value.validateData(
                globalST, symbolTable, typeTable, jerar, semanticErrors, restrictions
        );
        Label typeVar = validateInvocation(globalST, symbolTable, typeTable, jerar, semanticErrors);
        
        if(!typeVar.getName().equals(typeValue.getName())){
            semanticErrors.add(errorsRep.incorrectTypeError(
                    typeValue.getName(), 
                    typeVar.getName(), 
                    initPos)
            );
        }
        return new ReturnCase(false);
    }
    
    
    private Label validateInvocation(JSymbolTable globalST, SymbolTable symbolTable, 
            TypeTable typeTable, NodeJerarTree jerar, List<String> semanticErrors){
        Label currentType = null;
        for (int i = 0; i < variable.size(); i++) {
            JInvocation invocation = variable.get(i);
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
        }
        return currentType;
    }

    @Override
    public void generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, Memory temporals, C3Dpass pass) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
}
