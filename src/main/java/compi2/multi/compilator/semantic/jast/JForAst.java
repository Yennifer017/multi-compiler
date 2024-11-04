
package compi2.multi.compilator.semantic.jast;

import compi2.multi.compilator.analysis.jerarquia.NodeJerarTree;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.cuartetas.GotoC3D;
import compi2.multi.compilator.c3d.cuartetas.LabelC3D;
import compi2.multi.compilator.c3d.generators.stmts.CyclesStmtsGenC3D;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.semantic.j.JControlStmt;
import compi2.multi.compilator.semantic.j.JExpression;
import compi2.multi.compilator.semantic.j.JStatement;
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
public class JForAst extends JControlStmt{

    private JStatement uniqueStmt;
    private JExpression condition;
    private JStatement everyStmt;
    
    private CyclesStmtsGenC3D stmtGenC3D;
    
    public JForAst(Position initPos, JStatement uniqueStmt, JExpression condition, 
            JStatement everyStmt, List<JStatement> internalStmts) {
        super(initPos);
        super.internalStmts = internalStmts;
        this.uniqueStmt = uniqueStmt;
        this.condition = condition;
        this.everyStmt = everyStmt;
        stmtGenC3D = new CyclesStmtsGenC3D();
    }

    @Override
    public ReturnCase validate(JSymbolTable globalST, SymbolTable symbolTable, 
            TypeTable typeTable, NodeJerarTree jerar, List<String> semanticErrors, 
            SemanticRestrictions restrictions) {
        if(uniqueStmt != null){
            uniqueStmt.validate(globalST, symbolTable, typeTable, jerar, semanticErrors, restrictions);
        }
        if(everyStmt != null){
            uniqueStmt.validate(globalST, symbolTable, typeTable, jerar, semanticErrors, restrictions);
        }
        super.validateCondition(globalST, symbolTable, typeTable, jerar, semanticErrors, 
                restrictions, condition);
        return super.validateInternalStmts(globalST, symbolTable, typeTable, jerar, semanticErrors, 
                new SemanticRestrictions(
                        true, 
                        true, 
                        restrictions.getReturnType(),
                        restrictions.getReturnType()
                ));
    }

    @Override
    public void generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, 
            Memory temporals, C3Dpass pass) {
        stmtGenC3D.generateForCuartetas(
                admiMemory, 
                internalCuartetas, 
                temporals, 
                pass, 
                internalStmts, 
                uniqueStmt, 
                condition, 
                everyStmt
        );
        
    }
}