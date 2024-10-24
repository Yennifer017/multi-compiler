
package compi2.multi.compilator.semantic.jast;

import compi2.multi.compilator.analysis.jerarquia.NodeJerarTree;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.semantic.j.JControlStmt;
import compi2.multi.compilator.semantic.j.JExpression;
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
public class JSwitchAst extends JControlStmt{
    
    private List<JCase> cases;
    private JExpression expression;
    
    public JSwitchAst(Position initPos, JExpression expression, List<JCase> cases) {
        super(initPos);
        this.expression = expression;
        this.cases = cases;
    }

    @Override
    public ReturnCase validate(JSymbolTable globalST, SymbolTable symbolTable, 
            TypeTable typeTable, NodeJerarTree jerar, List<String> semanticErrors, 
            SemanticRestrictions restrictions) {
        Label typeCase = expression.validateData(
                globalST, symbolTable, typeTable, jerar, semanticErrors, restrictions
        );
        
        ReturnCase returnCase = new ReturnCase(true);
        if(cases != null && !cases.isEmpty()){
            boolean unreachable = false;
            for (int i = 0; i < cases.size(); i++) {
                JCase simpleCase = cases.get(i);
                ReturnCase currentCase = simpleCase.validate(
                        globalST, symbolTable, typeTable, jerar, semanticErrors, restrictions, typeCase.getName()
                );
                if(!currentCase.isAllScenaries()){
                    returnCase.setAllScenaries(false);
                }
                
                if(unreachable){
                    semanticErrors.add(errorsRep.unrachableCodeError(simpleCase.getInitPos()));
                }
                if(!unreachable && simpleCase.isDefault() && i != (cases.size() - 1) ){
                    unreachable = true;
                }
            }
        }
        
        return returnCase;
    }

    @Override
    public void generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, Memory temporals, C3Dpass pass) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
