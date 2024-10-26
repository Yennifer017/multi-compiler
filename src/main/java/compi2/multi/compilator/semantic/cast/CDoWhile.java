
package compi2.multi.compilator.semantic.cast;

import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.cuartetas.GotoC3D;
import compi2.multi.compilator.c3d.cuartetas.LabelC3D;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.semantic.c.CExp;
import compi2.multi.compilator.semantic.c.CImports;
import compi2.multi.compilator.semantic.c.CStatement;
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
public class CDoWhile extends CControlStmt{
    
    private CExp condition;
    
    public CDoWhile(Position initPos, List<CStatement> internalStmt, CExp condition) {
        super(initPos, internalStmt);
        this.condition = condition;
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
                new SemanticRestrictions(
                        true, 
                        true, 
                        restrictions.getReturnType(), 
                        restrictions.getReturnType())
        );
    }

    @Override
    public void generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, Memory temporals, C3Dpass pass) {
        int firstLabel = admiMemory.getCountLabels();
        int finalLabel = firstLabel + 1;
        admiMemory.setCountLabels(admiMemory.getCountLabels() + 2);
        
        internalCuartetas.add(
                new LabelC3D(firstLabel)
        );
        
        C3Dpass passInternal = new C3Dpass(finalLabel, firstLabel);
        super.generateInternalCuartetas(admiMemory, internalCuartetas, temporals, passInternal);
        super.expGenC3D.generateConditionCuartetas(
                admiMemory, internalCuartetas, temporals, pass, passInternal, condition, firstLabel
        );
        
        internalCuartetas.add(
                new GotoC3D(finalLabel)
        );
        internalCuartetas.add(
                new LabelC3D(finalLabel)
        );
    }
    
}
