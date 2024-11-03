
package compi2.multi.compilator.c3d.interfaces;

import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.util.C3Dpass;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public interface StmtGenerateC3D {
    
    public abstract void generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, 
            Memory temporals, C3Dpass pass);
    
}
