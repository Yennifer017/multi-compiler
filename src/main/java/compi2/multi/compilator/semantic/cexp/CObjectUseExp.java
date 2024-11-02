
package compi2.multi.compilator.semantic.cexp;

import compi2.multi.compilator.analysis.symbolt.Category;
import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.analyzator.Analyzator;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.generators.InvocationsC3DGen;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.c3d.util.RetParamsC3D;
import compi2.multi.compilator.semantic.c.CExp;
import compi2.multi.compilator.semantic.c.CImports;
import compi2.multi.compilator.semantic.jast.inv.InvocationsUtil;
import compi2.multi.compilator.semantic.jast.inv.JInvocation;
import compi2.multi.compilator.semantic.util.Label;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class CObjectUseExp extends CExp{
    
    private List<JInvocation> invocations;
    
    private InvocationsC3DGen invC3DGen;
    private InvocationsUtil invsUtil;
    
    private PrimitiveType primType;
    private int instanceRef;
    
    
    public CObjectUseExp(List<JInvocation> invocations) {
        super(null);
        this.invocations = invocations;
        this.invsUtil = new InvocationsUtil();
        this.invC3DGen = new InvocationsC3DGen();
    }

    @Override
    public Label validateComplexData(CImports imports, JSymbolTable clasesST, 
            SymbolTable symbolTable, SymbolTable pascalST, TypeTable typeTable, 
            List<String> semanticErrors) {
        try {
            JInvocation first = invocations.get(0);
            this.pos = first.getInv().getPosition();
            if(symbolTable.containsKey(first.getInv().getName())){
                RowST rowST = symbolTable.get(first.getInv().getName());
                if(rowST.getCategory().equals(Category.JObject)){
                    Label type = invsUtil.validateInvocation(
                            clasesST, 
                            symbolTable, 
                            typeTable, 
                            null, 
                            semanticErrors, 
                            invocations, 
                            pos, 
                            true, 
                            true
                    );
                    this.primType = super.tConvert.convertAllPrimitive(type.getName());
                    this.instanceRef = super.refAnalyzator.findInstanceRef(symbolTable);
                    return new Label(type.getName(), type.getPosition());
                } else {
                    semanticErrors.add(
                        super.errorsRep.noObjectVarError(
                                first.getInv().getName(), 
                                first.getInv().getPosition()
                        )
                    );
                }
            } else {
                semanticErrors.add(
                        super.errorsRep.undefiniteVarUseError(
                                first.getInv().getName(), 
                                first.getInv().getPosition()
                        )
                );
            }
        } catch (IndexOutOfBoundsException e) {
            //add error
        }
        return new Label(Analyzator.ERROR_TYPE, pos);
    }

    @Override
    public Label validateSimpleData(SymbolTable symbolTable, List<String> semanticErrors) {
        try {
            this.pos = invocations.get(0).getInv().getPosition();
            semanticErrors.add(errorsRep.ilegalUseError("Lista de invocaciones", pos));
        } catch (IndexOutOfBoundsException e) {
            //add error
        }
        return new Label(Analyzator.ERROR_TYPE, pos);
    }

    @Override
    public RetParamsC3D generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, 
            Memory temporals, C3Dpass pass) {
        return this.invC3DGen.generateCuartetasExp(
                admiMemory, internalCuartetas, temporals, pass, invocations, primType, instanceRef
        );
    }
    
}
