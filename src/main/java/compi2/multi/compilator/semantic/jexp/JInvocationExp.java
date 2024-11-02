
package compi2.multi.compilator.semantic.jexp;

import compi2.multi.compilator.analysis.jerarquia.NodeJerarTree;
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
import compi2.multi.compilator.semantic.j.JExpression;
import compi2.multi.compilator.semantic.jast.inv.InvocationsUtil;
import compi2.multi.compilator.semantic.jast.inv.JContextRef;
import compi2.multi.compilator.semantic.jast.inv.JInvocation;
import compi2.multi.compilator.semantic.util.Label;
import compi2.multi.compilator.semantic.util.SemanticRestrictions;
import java.util.LinkedList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class JInvocationExp extends JExpression{
    private List<JInvocation> invocations;
    
    private InvocationsUtil invsUtil;
    private PrimitiveType primType;
    private int instanceRef;
    
    private InvocationsC3DGen invC3DGen;
    
    public JInvocationExp(List<JInvocation> invocations){
        super(null);
        this.invocations = invocations;
        this.invsUtil = new InvocationsUtil();
        this.invC3DGen = new InvocationsC3DGen();
    }
    
    public JInvocationExp(List<JInvocation> invocations, JContextRef firstContext){
        super(null);
        this.invsUtil =  new InvocationsUtil();
        this.invC3DGen = new InvocationsC3DGen();
        try {
            invocations.get(0).setContext(firstContext);
            this.invocations = invocations;
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            this.invocations = new LinkedList<>();
        }
    }

    @Override
    public Label validateData(JSymbolTable globalST, SymbolTable symbolTable, TypeTable typeTable, NodeJerarTree jerar, List<String> semanticErrors, SemanticRestrictions restrictions) {
        try {
            this.pos = invocations.get(0).getInv().getPosition();
        } catch (IndexOutOfBoundsException e) {
            //add error
        }
        Label type = invsUtil.validateInvocation(
                globalST, 
                symbolTable, 
                typeTable, 
                jerar, 
                semanticErrors, 
                invocations, 
                pos, 
                true,
                false
        );
        this.primType = super.tConvert.convertAllPrimitive(type.getName());
        this.instanceRef = super.refAnalyzator.findInstanceRef(symbolTable);
        return new Label(type.getName(), type.getPosition());
    }

    @Override
    public Label validateSimpleData(List<String> semanticErrors) {
        try {
            this.pos = invocations.get(0).getInv().getPosition();
            semanticErrors.add(errorsRep.ilegalUseError("Lista de invocaciones", pos));
        } catch (IndexOutOfBoundsException e) {
            //add error
        }
        return new Label(Analyzator.ERROR_TYPE, pos);
    }

    @Override
    public RetParamsC3D generateCuartetas(AdmiMemory admiMemory, 
            List<Cuarteta> internalCuartetas, Memory temporals, C3Dpass pass) {
        return this.invC3DGen.generateCuartetasExp(
                admiMemory, internalCuartetas, temporals, pass, invocations, primType, instanceRef
        );
    }

}
