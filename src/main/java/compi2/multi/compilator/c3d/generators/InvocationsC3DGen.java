
package compi2.multi.compilator.c3d.generators;

import compi2.multi.compilator.analysis.typet.PrimitiveType;
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
import compi2.multi.compilator.c3d.interfaces.ExpressionGenerateC3D;
import compi2.multi.compilator.c3d.interfaces.InvocationGenerateC3D;
import compi2.multi.compilator.c3d.util.Register;
import compi2.multi.compilator.c3d.util.RetJInvC3D;
import compi2.multi.compilator.c3d.util.RetParamsC3D;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class InvocationsC3DGen {

    private AdmiRegisters admiRegisters;
    private AccessGenC3D accessGenC3D;

    public InvocationsC3DGen() {
        admiRegisters = new AdmiRegisters();
        accessGenC3D = new AccessGenC3D();
    }

    public RetParamsC3D generateCuartetasExp(
            AdmiMemory admiMemory,
            List<Cuarteta> internalCuartetas, 
            Memory temporals, 
            C3Dpass pass,
            List<? extends InvocationGenerateC3D> invocations, 
            PrimitiveType primType, 
            int instanceRef
    ) {
        RetJInvC3D invRet = this.generateC3DInvocations(
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
            case RetJInvC3D.HEAP_ACCESS ->
                new HeapAccess(primType, new RegisterUse(Register.AX_INT));
            case RetJInvC3D.STACK_ACCESS ->
                new StackAccess(primType, new RegisterUse(Register.AX_INT));
            default ->
                new RegisterUse(Register.AX_INT);
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

    public RetJInvC3D generateC3DInvocations(
            AdmiMemory admiMemory, List<Cuarteta> internalCuartetas,
            Memory temporals, List<? extends InvocationGenerateC3D> invocations, int instanceRef) {
        RetJInvC3D currentRetInv = null;
        for (int i = 0; i < invocations.size(); i++) {
            InvocationGenerateC3D invocation = invocations.get(i);
            if (i == 0) {
                currentRetInv = invocation.generateCuartetas(
                        admiMemory, internalCuartetas, temporals, instanceRef
                );
            } else {
                currentRetInv = invocation.generateCuartetas(
                        admiMemory, internalCuartetas, temporals,
                        currentRetInv
                );
            }
        }
        return currentRetInv;
    }

    public void generateCuartetasAssign(
            AdmiMemory admiMemory, 
            List<Cuarteta> internalCuartetas,
            Memory temporals, 
            C3Dpass pass,
            List<? extends InvocationGenerateC3D> variable, 
            PrimitiveType primType, 
            ExpressionGenerateC3D value,
            int instanceRef
    ) {
        RetJInvC3D invRet = this.generateC3DInvocations(
                admiMemory, internalCuartetas, temporals, variable, instanceRef
        );
        MemoryAccess expAccess = accessGenC3D.getAccess(
                value, admiMemory, internalCuartetas, temporals, pass
        );

        RegisterUse bxIntRegister = new RegisterUse(Register.BX_INT);
        internalCuartetas.add(
                new AssignationC3D(
                        bxIntRegister,
                        invRet.getTemporalUse()
                )
        );
        MemoryAccess assignAccess;
        assignAccess = switch (invRet.getTypeAccess()) {
            case RetJInvC3D.HEAP_ACCESS ->
                new HeapAccess(primType, bxIntRegister);
            case RetJInvC3D.STACK_ACCESS ->
                new StackAccess(primType, bxIntRegister);
            default ->
                bxIntRegister;
        };
        internalCuartetas.add(
                new AssignationC3D(
                        assignAccess,
                        expAccess
                )
        );
    }

}
