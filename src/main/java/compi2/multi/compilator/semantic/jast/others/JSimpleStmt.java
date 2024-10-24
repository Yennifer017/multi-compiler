package compi2.multi.compilator.semantic.jast.others;

import compi2.multi.compilator.analysis.jerarquia.NodeJerarTree;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.cuartetas.GotoC3D;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.semantic.j.JStatement;
import compi2.multi.compilator.semantic.util.ReturnCase;
import compi2.multi.compilator.semantic.util.SemanticRestrictions;
import compi2.multi.compilator.util.Position;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class JSimpleStmt extends JStatement {

    private boolean isBreak;

    public JSimpleStmt(Position initPos, boolean isBreak) {
        super(initPos);
        this.isBreak = isBreak;
    }

    public boolean isBreak() {
        return this.isBreak;
    }

    @Override
    public ReturnCase validate(JSymbolTable globalST, SymbolTable symbolTable,
            TypeTable typeTable, NodeJerarTree jerar, List<String> semanticErrors,
            SemanticRestrictions restrictions) {
        if (isBreak && !restrictions.allowBreak()) {
            semanticErrors.add(super.errorsRep.ilegalStmt("BREAK", initPos));
        } else if (!isBreak && !restrictions.allowBreak()) {
            semanticErrors.add(super.errorsRep.ilegalStmt("CONTINUE", initPos));
        }
        return new ReturnCase(false);
    }

    @Override
    public void generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, 
            Memory temporals, C3Dpass pass) {
        if(isBreak){
            internalCuartetas.add(
                    new GotoC3D(pass.getEndLabel())
            );
        } else {
            internalCuartetas.add(
                    new GotoC3D(pass.getStartBucleLabel())
            );
        }
    }

}
