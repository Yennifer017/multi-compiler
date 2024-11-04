
package compi2.multi.compilator.semantic.cast;

import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.generators.stmts.CyclesStmtsGenC3D;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.semantic.c.CExp;
import compi2.multi.compilator.semantic.c.CImports;
import compi2.multi.compilator.semantic.c.CStatement;
import compi2.multi.compilator.semantic.util.ReturnCase;
import compi2.multi.compilator.semantic.util.SemanticRestrictions;
import compi2.multi.compilator.util.Position;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class CWhileAst extends CControlStmt{
    private CExp condition;
    
    private CyclesStmtsGenC3D stmtGenC3D;
    
    public CWhileAst(Position initPos, List<CStatement> internalStmt, CExp condition) {
        super(initPos, internalStmt);
        this.condition = condition;
        this.stmtGenC3D = new CyclesStmtsGenC3D();
    }

    @Override
    public ReturnCase validate(CImports imports, JSymbolTable clasesST, 
            SymbolTable symbolTable, SymbolTable pascalST, TypeTable typeTable, 
            List<String> semanticErrors, SemanticRestrictions restrictions) {
        super.validateCondition(
                imports, clasesST, symbolTable, pascalST, 
                typeTable, semanticErrors, restrictions, condition
        );
        return super.validateInternal(
                imports, clasesST, symbolTable, pascalST, typeTable, semanticErrors, 
                new SemanticRestrictions(true, true, restrictions.getReturnType())
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
