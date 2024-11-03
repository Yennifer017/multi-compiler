package compi2.multi.compilator.c3d.generators;

import compi2.multi.compilator.analysis.symbolt.clases.FieldST;
import compi2.multi.compilator.analysis.symbolt.estruc.SingleData;
import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.access.AtomicValue;
import compi2.multi.compilator.c3d.access.HeapAccess;
import compi2.multi.compilator.c3d.access.RegisterUse;
import compi2.multi.compilator.c3d.access.StackAccess;
import compi2.multi.compilator.c3d.access.StackPtrUse;
import compi2.multi.compilator.c3d.access.TemporalUse;
import compi2.multi.compilator.c3d.cuartetas.AssignationC3D;
import compi2.multi.compilator.c3d.cuartetas.OperationC3D;
import compi2.multi.compilator.c3d.util.Register;
import compi2.multi.compilator.c3d.util.RetJInvC3D;
import compi2.multi.compilator.semantic.DefiniteOperation;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class InvC3DGen {

    public RetJInvC3D generateCuartAccessVar(AdmiMemory admiMemory,
            List<Cuarteta> internalCuartetas, Memory temporals, int instanceStackRef,
            SingleData singleData) {
        RegisterUse axIntRegister = new RegisterUse(Register.AX_INT);

        int currentIntTemp = temporals.getIntegerCount();
        temporals.setIntegerCount(currentIntTemp + 1);
        internalCuartetas.add( //AX = ptr + n
                new OperationC3D(
                        axIntRegister,
                        new StackPtrUse(),
                        new AtomicValue<>(singleData.getRelativeDir()),
                        DefiniteOperation.Addition
                )
        );
        internalCuartetas.add( //t[0] = AX_INT
                new AssignationC3D(
                        new TemporalUse(
                                PrimitiveType.IntegerPT,
                                currentIntTemp,
                                temporals
                        ),
                        axIntRegister
                )
        );
        return new RetJInvC3D(
                new TemporalUse(
                        PrimitiveType.IntegerPT,
                        currentIntTemp,
                        temporals
                ),
                RetJInvC3D.STACK_ACCESS
        );
    }
    
    
    public RetJInvC3D generateCuartAccessVar(AdmiMemory admiMemory,
            List<Cuarteta> internalCuartetas, Memory temporals, RetJInvC3D previus,
            FieldST fieldST) {
        int countTemp = temporals.getIntegerCount();
        temporals.setIntegerCount(countTemp + 2);
        
        RegisterUse axIntRegister =  new RegisterUse(Register.AX_INT);
        RegisterUse bxIntRegister =  new RegisterUse(Register.BX_INT);
        
        internalCuartetas.add(
                new AssignationC3D(
                        axIntRegister, 
                        previus.getTemporalUse()
                )
        );
        switch (previus.getTypeAccess()) {
            case RetJInvC3D.HEAP_ACCESS -> internalCuartetas.add(
                        new AssignationC3D(
                                bxIntRegister,
                                new HeapAccess(
                                        PrimitiveType.IntegerPT,
                                        axIntRegister
                                )
                        )
                );
            case RetJInvC3D.STACK_ACCESS -> internalCuartetas.add(
                        new AssignationC3D(
                                bxIntRegister,
                                new StackAccess(
                                        PrimitiveType.IntegerPT,
                                        axIntRegister
                                )
                        )
                );
            default -> internalCuartetas.add(
                        new AssignationC3D(bxIntRegister, axIntRegister)
            );
        }
        internalCuartetas.add(
                new AssignationC3D(
                        new TemporalUse(PrimitiveType.IntegerPT, countTemp, temporals), 
                        bxIntRegister
                )
        );
        internalCuartetas.add(
                new AssignationC3D(
                        axIntRegister, 
                        new TemporalUse(PrimitiveType.IntegerPT, countTemp, temporals)
                )
        );
        internalCuartetas.add(
                new OperationC3D(
                        bxIntRegister, 
                        axIntRegister, 
                        new AtomicValue(fieldST.getRelativeDir()), 
                        DefiniteOperation.Addition
                )
        );
        internalCuartetas.add(
                new AssignationC3D(
                        new TemporalUse(PrimitiveType.IntegerPT, countTemp + 1, temporals), 
                        bxIntRegister
                )
        );
        return new RetJInvC3D(
                new TemporalUse(PrimitiveType.IntegerPT, countTemp + 1, temporals), 
                RetJInvC3D.HEAP_ACCESS
        );
    }
    
}
