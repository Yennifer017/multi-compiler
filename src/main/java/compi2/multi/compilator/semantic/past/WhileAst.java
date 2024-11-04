
package compi2.multi.compilator.semantic.past;

import compi2.multi.compilator.semantic.p.ControlStruct;
import compi2.multi.compilator.semantic.p.Statement;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.generators.stmts.CyclesStmtsGenC3D;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.semantic.util.ReturnCase;
import compi2.multi.compilator.semantic.util.SemanticRestrictions;
import compi2.multi.compilator.semantic.p.Expression;
import compi2.multi.compilator.util.Position;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class WhileAst extends ControlStruct{
    
    private Expression condition;
    
    private CyclesStmtsGenC3D stmtGenC3D;

    public WhileAst(Expression condition, List<Statement> internalStmts, Position initPos) {
        super(initPos);
        this.condition = condition;
        super.internalStmts = internalStmts;
        this.stmtGenC3D = new CyclesStmtsGenC3D();
    }

    @Override
    public ReturnCase validate(SymbolTable symbolTable, TypeTable typeTable, 
            List<String> semanticErrors, SemanticRestrictions restrictions) {
        super.validateCondition(condition, symbolTable, typeTable, semanticErrors);
        return super.validateInternalStmts(symbolTable, typeTable, semanticErrors, 
                new SemanticRestrictions(
                        true, 
                        true, 
                        restrictions.getReturnType(), 
                        restrictions.getReturnType())
        );
        
    }

    @Override
    public void generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, Memory temporals, C3Dpass pass) {
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
