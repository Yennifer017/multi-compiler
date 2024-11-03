
package compi2.multi.compilator.semantic.j;

import compi2.multi.compilator.analysis.jerarquia.NodeJerarTree;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.analysis.typet.convert.TConvertidor;
import compi2.multi.compilator.analyzator.RefAnalyzator;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.interfaces.StmtGenerateC3D;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.semantic.util.ReturnCase;
import compi2.multi.compilator.semantic.util.SemanticRestrictions;
import compi2.multi.compilator.util.ErrorsRep;
import compi2.multi.compilator.util.Position;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public abstract class JStatement implements StmtGenerateC3D{
    protected Position initPos;
    
    public abstract ReturnCase validate(JSymbolTable globalST, SymbolTable symbolTable, TypeTable typeTable, 
            NodeJerarTree jerar, List<String> semanticErrors, SemanticRestrictions restrictions);
    
    @Override
    public abstract void generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, 
            Memory temporals, C3Dpass pass);
    
    protected ErrorsRep errorsRep;
    protected TConvertidor tConvert;
    protected RefAnalyzator refAnalyzator;
    
    public JStatement(Position initPos){
        this.initPos = initPos;
        this.errorsRep = new ErrorsRep();
        tConvert = new TConvertidor();
        refAnalyzator = new RefAnalyzator();
    }
}
