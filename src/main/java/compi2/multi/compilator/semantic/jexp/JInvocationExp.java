
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
import compi2.multi.compilator.c3d.access.HeapAccess;
import compi2.multi.compilator.c3d.access.MemoryAccess;
import compi2.multi.compilator.c3d.access.RegisterUse;
import compi2.multi.compilator.c3d.access.StackAccess;
import compi2.multi.compilator.c3d.access.TemporalUse;
import compi2.multi.compilator.c3d.cuartetas.AssignationC3D;
import compi2.multi.compilator.c3d.util.AdmiRegisters;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.c3d.util.Register;
import compi2.multi.compilator.c3d.util.RetJInvC3D;
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
    
    private AdmiRegisters admiRegisters;
    
    public JInvocationExp(List<JInvocation> invocations){
        super(null);
        this.invocations = invocations;
        this.invsUtil = new InvocationsUtil();
        this.admiRegisters = new AdmiRegisters();
    }
    
    public JInvocationExp(List<JInvocation> invocations, JContextRef firstContext){
        super(null);
        this.invsUtil =  new InvocationsUtil();
        this.admiRegisters = new AdmiRegisters();
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
                true
        );
        this.primType = super.tConvert.convertAllPrimitive(type.getName());
        this.instanceRef = super.refAnalyzator.findInstanceRef(symbolTable);
        return new Label(type.getName(), type.getPosition());
    }

    @Override
    public Label validateSimpleData(List<String> semanticErrors) {
        semanticErrors.add(errorsRep.ilegalUseError("Lista de invocaciones", pos));
        return new Label(Analyzator.ERROR_TYPE, pos);
    }

    @Override
    public RetParamsC3D generateCuartetas(AdmiMemory admiMemory, 
            List<Cuarteta> internalCuartetas, Memory temporals, C3Dpass pass) {
        RetJInvC3D invRet = invsUtil.generateC3DInvocations(
                admiMemory, internalCuartetas, temporals, invocations, instanceRef
        );
        Register register = admiRegisters.findRegister(primType, 2);
        int temporalCount = temporals.getCount(primType);
        temporals.increment(primType, 1);
        
        internalCuartetas.add(
                new AssignationC3D(
                        new RegisterUse(Register.AX_INT), 
                        invRet.getTemporalUse()
                )
        );
        MemoryAccess fromInvMAccess;
        fromInvMAccess = switch (invRet.getTypeAccess()) {
            case RetJInvC3D.HEAP_ACCESS -> new HeapAccess(primType, new RegisterUse(Register.AX_INT));
            case RetJInvC3D.STACK_ACCESS -> new StackAccess(primType, new RegisterUse(Register.AX_INT));
            default -> new RegisterUse(Register.AX_INT);
        };
        
        internalCuartetas.add(
                new AssignationC3D(
                        new RegisterUse(register), 
                        fromInvMAccess
                )
        );
        internalCuartetas.add(
                new AssignationC3D(
                        new TemporalUse(primType, temporalCount, temporals), 
                        new RegisterUse(register)
                )
        );
        return new RetParamsC3D(
                new TemporalUse(primType, temporalCount, temporals)
        );
    }

}
