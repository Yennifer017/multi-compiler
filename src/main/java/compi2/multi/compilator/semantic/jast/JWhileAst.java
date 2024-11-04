
package compi2.multi.compilator.semantic.jast;

import compi2.multi.compilator.analysis.jerarquia.NodeJerarTree;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.generators.stmts.CyclesStmtsGenC3D;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.semantic.j.JControlStmt;
import compi2.multi.compilator.semantic.j.JExpression;
import compi2.multi.compilator.semantic.j.JStatement;
import compi2.multi.compilator.semantic.util.ReturnCase;
import compi2.multi.compilator.semantic.util.SemanticRestrictions;
import compi2.multi.compilator.util.Position;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class JWhileAst extends JControlStmt{

    private JExpression condition;
    
    private CyclesStmtsGenC3D stmtGenC3D;

    public JWhileAst(Position initPos, JExpression condition, List<JStatement> internalStmts) {
        super(initPos);
        this.condition = condition;
        super.internalStmts = internalStmts;
        this.stmtGenC3D = new CyclesStmtsGenC3D();
    }

    @Override
    public ReturnCase validate(JSymbolTable globalST, SymbolTable symbolTable, 
            TypeTable typeTable, NodeJerarTree jerar, List<String> semanticErrors, 
            SemanticRestrictions restrictions) {
        super.validateCondition(globalST, symbolTable, typeTable, jerar, 
                semanticErrors, restrictions, condition);
        validateInternalStmts(globalST, symbolTable, typeTable, 
                jerar, semanticErrors, restrictions);
        return new ReturnCase(false);
    }

    @Override
    public void generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, 
            Memory temporals, C3Dpass pass) {
        stmtGenC3D.generateWhileCuartetas(
                admiMemory, 
                internalCuartetas, 
                temporals, 
                pass, 
                internalStmts, 
                condition
        );
    }
    
}
