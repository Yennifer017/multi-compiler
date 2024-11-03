
package compi2.multi.compilator.semantic.cexp;

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
import compi2.multi.compilator.semantic.cast.inv.objs.CInvocation;
import compi2.multi.compilator.semantic.jast.inv.InvocationsUtil;
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
    
    private List<CInvocation> invocations;
    
    private InvocationsC3DGen invC3DGen;
    private InvocationsUtil invsUtil;
    
    private PrimitiveType primType;
    
    public CObjectUseExp(List<CInvocation> invocations) {
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
            CInvocation first = invocations.get(0);
            this.pos = first.getInv().getPosition();
            Label type = invsUtil.validateInvocation(
                    imports, 
                    clasesST, 
                    symbolTable, 
                    pascalST, 
                    typeTable, 
                    semanticErrors, 
                    invocations, 
                    pos, 
                    true,
                    false
            );
                    
            this.primType = super.tConvert.convertAllPrimitive(type.getName());
            return new Label(type.getName(), type.getPosition());
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
                admiMemory, internalCuartetas, temporals, pass, invocations, primType, -1
        );
    }
    
}
