
package compi2.multi.compilator.c3d.util;

import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public interface InvocationGenerateC3D {
    /**
     * Genera cuartetas
     * @param admiMemory
     * @param internalCuartetas
     * @param temporals
     * @param instanceStackRef
     * @return un objeto que contiene la posicion de memoria del objeto
     * ya sea en el heap o en el stack
     */
    public RetJInvC3D generateCuartetas(AdmiMemory admiMemory, 
            List<Cuarteta> internalCuartetas, 
            Memory temporals, int instanceStackRef);
    
    public abstract RetJInvC3D generateCuartetas(AdmiMemory admiMemory,
            List<Cuarteta> internalCuartetas,
            Memory temporals, RetJInvC3D previus);
    
    public abstract boolean hasReturnType();
    public abstract boolean isStatement();
    public abstract boolean canAssign();
}
