
package compi2.multi.compilator.c3d.generators;

import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.access.AtomicValue;
import compi2.multi.compilator.c3d.access.MemoryAccess;
import compi2.multi.compilator.c3d.access.RegisterUse;
import compi2.multi.compilator.c3d.cuartetas.AssignationC3D;
import compi2.multi.compilator.c3d.util.AdmiRegisters;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.c3d.util.ExpressionGenerateC3D;
import compi2.multi.compilator.c3d.util.Register;
import compi2.multi.compilator.c3d.util.RetParamsC3D;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class AccessGenC3D {
    private AdmiRegisters admiRegisters;
    
    public AccessGenC3D(){
        this.admiRegisters = new AdmiRegisters();
    }
    
    public MemoryAccess getAccess(ExpressionGenerateC3D expression, AdmiMemory admiMemory,
            List<Cuarteta> internalCuartetas, Memory temporals, C3Dpass pass) {
        RetParamsC3D retParamC3D = expression.generateCuartetas(admiMemory, internalCuartetas, temporals, pass);
        if (retParamC3D.getTemporalUse() != null) {

            Register register = admiRegisters.findRegister(
                    retParamC3D.getTemporalUse().getType(), 1
            );
            internalCuartetas.add(
                    new AssignationC3D(
                            new RegisterUse(register),
                            retParamC3D.getTemporalUse()
                    )
            );
            return new RegisterUse(register);
        } else {
            return new AtomicValue(retParamC3D.getAtomicValue());
        }
    }
    
    public MemoryAccess getRegisterAccess(List<Cuarteta> internalCuartetas, RetParamsC3D retParamC3D, 
            int registerNumber){
        if (retParamC3D.getTemporalUse() != null) {

            Register register = admiRegisters.findRegister(
                    retParamC3D.getTemporalUse().getType(), registerNumber
            );
            internalCuartetas.add(
                    new AssignationC3D(
                            new RegisterUse(register),
                            retParamC3D.getTemporalUse()
                    )
            );
            return new RegisterUse(register);
        } else {
            return new AtomicValue(retParamC3D.getAtomicValue());
        }
    }
    
}
