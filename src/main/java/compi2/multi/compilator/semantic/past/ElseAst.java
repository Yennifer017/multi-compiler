
package compi2.multi.compilator.semantic.past;

import compi2.multi.compilator.semantic.p.ControlStruct;
import compi2.multi.compilator.semantic.p.Statement;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.cuartetas.GotoC3D;
import compi2.multi.compilator.c3d.generators.stmts.StmtsGeneratorC3D;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.semantic.util.ReturnCase;
import compi2.multi.compilator.semantic.util.SemanticRestrictions;
import compi2.multi.compilator.util.Position;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class ElseAst extends ControlStruct{
    
    private StmtsGeneratorC3D stmtGenC3D;
    
    public ElseAst(List<Statement> stmts){
        super(null);
        super.internalStmts = stmts;
        stmtGenC3D = new StmtsGeneratorC3D();
    }

    @Override
    public ReturnCase validate(SymbolTable symbolTable, TypeTable typeTable, 
            List<String> semanticErrors, SemanticRestrictions restrictions) {
        return super.validateInternalStmts(symbolTable, typeTable, semanticErrors, restrictions);
    }
    
    public void setInitPos(Position initPos){
        super.initPos = initPos;
    }

    @Override
    public void generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, Memory temporals, C3Dpass pass) {
        stmtGenC3D.generateInternalCuartetas(admiMemory, internalCuartetas, temporals, pass, internalStmts);
        internalCuartetas.add(
                new GotoC3D(pass.getEndIfLabel())
        );
    }

}
