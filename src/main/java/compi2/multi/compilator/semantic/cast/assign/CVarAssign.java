
package compi2.multi.compilator.semantic.cast.assign;

import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.symbolt.estruc.SingleData;
import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.c3d.generators.ExpGenC3D;
import compi2.multi.compilator.analyzator.RefAnalyzator;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.access.MemoryAccess;
import compi2.multi.compilator.c3d.access.StackAccess;
import compi2.multi.compilator.c3d.cuartetas.AssignationC3D;
import compi2.multi.compilator.c3d.generators.AccessGenC3D;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.exceptions.ConvPrimitiveException;
import compi2.multi.compilator.semantic.c.CExp;
import compi2.multi.compilator.semantic.c.CImports;
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
public class CVarAssign extends CAssign{
    
    private String nameVar;
    
    private RefAnalyzator refAnalyzator;
    private ExpGenC3D expGenC3D;
    private AccessGenC3D accessGenC3D;
    
    private SingleData singleData;
    private PrimitiveType type;
    
    public CVarAssign(Position initPos, String nameVar, CExp expression) {
        super(initPos, expression);
        this.nameVar = nameVar;
        this.refAnalyzator = new RefAnalyzator();
        this.expGenC3D = new ExpGenC3D();
        this.accessGenC3D = new AccessGenC3D();
    }

    @Override
    public ReturnCase validate(CImports imports, JSymbolTable clasesST, SymbolTable symbolTable, SymbolTable pascalST, TypeTable typeTable, List<String> semanticErrors, SemanticRestrictions restrictions) {
        Label typeExp = expression.validateComplexData(
                imports, clasesST, symbolTable, pascalST, typeTable, semanticErrors
        );
        if(refAnalyzator.existReference(symbolTable, semanticErrors, 
                        new Label(nameVar, this.initPos))){
            RowST rowST = refAnalyzator.getFromST(symbolTable, nameVar);
            if(rowST instanceof SingleData){
                singleData = (SingleData) rowST;
                if(!singleData.getType().equals(typeExp.getName())
                        && !super.tConvert.canUpgradeType(singleData.getType(), typeExp.getName())){
                    //add error
                }
                try {
                    type = tConvert.convertPrimitive(rowST.getType());
                } catch (ConvPrimitiveException e) {
                    //add error
                }
            } else {
                semanticErrors.add(super.errorsRep.invalidCategoryAccessError(
                        rowST.getName(), 
                        rowST.getCategory().getName(), 
                        initPos)
                );
            }
        } else {
            semanticErrors.add(errorsRep.undefiniteVarUseError(nameVar, initPos));
        }
        return new ReturnCase(false);
    }

    @Override
    public void generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, Memory temporals, C3Dpass pass) {
        MemoryAccess memoryAccess = accessGenC3D.getAccess(
                expression, admiMemory, internalCuartetas, temporals, pass
        );
        internalCuartetas.add(
                new AssignationC3D(
                        new StackAccess(type, singleData.getRelativeDir()),
                        memoryAccess
                )
        );
    }
    
    
}
