
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
public class CForAst extends CControlStmt{
    private CStatement first;
    private CStatement last;
    private CExp condition;
    public CForAst(Position initPos, List<CStatement> internalStmt, 
            CStatement first, CStatement last, CExp condition) {
        super(initPos, internalStmt);
        this.first = first;
        this.last = last;
        this.condition = condition;
    }

    @Override
    public ReturnCase validate(CImports imports, JSymbolTable clasesST, 
            SymbolTable symbolTable, SymbolTable pascalST, TypeTable typeTable, 
            List<String> semanticErrors, SemanticRestrictions restrictions) {
        if(first != null){
            first.validate(
                    imports, clasesST, symbolTable, pascalST, typeTable, semanticErrors, restrictions
            );
        }
        if(last != null){
            last.validate(
                    imports, clasesST, symbolTable, pascalST, typeTable, semanticErrors, restrictions
            );
        }
        super.validateCondition(
                imports, clasesST, symbolTable, pascalST, typeTable, semanticErrors, restrictions, condition
        );
        return super.validateInternal(
                imports, clasesST, symbolTable, pascalST, typeTable, semanticErrors,
                new SemanticRestrictions(
                        true, 
                        true, 
                        restrictions.getReturnType(),
                        restrictions.getReturnType()
                )
        );
    }

    @Override
    public void generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, Memory temporals, C3Dpass pass) {
        if(first != null){
            first.generateCuartetas(admiMemory, internalCuartetas, temporals, pass);
        }
        int etInicio = admiMemory.getCountLabels();
        int etTrue =  etInicio + 1;
        int etFalse = etInicio + 2;
        admiMemory.setCountLabels(admiMemory.getCountLabels() + 3);
        
        internalCuartetas.add(
                new LabelC3D(etInicio)
        );
        
        //validate condition
        C3Dpass passInternal = new C3Dpass(etFalse, etInicio);
        super.expGenC3D.generateConditionCuartetas(
                admiMemory, internalCuartetas, temporals, pass, passInternal, condition, etTrue
        );
        
        internalCuartetas.add(
                new GotoC3D(etFalse)
        );
        internalCuartetas.add(
                new LabelC3D(etTrue)
        );
        super.generateInternalCuartetas(admiMemory, internalCuartetas, temporals, passInternal);
        if(last != null){
            last.generateCuartetas(admiMemory, internalCuartetas, temporals, pass);
        }
        internalCuartetas.add(
                new GotoC3D(etInicio)
        );
        internalCuartetas.add(
                new LabelC3D(etFalse)
        );
    }
    
}
