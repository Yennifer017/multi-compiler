
package compi2.multi.compilator.c3d.generators.stmts;

import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.interfaces.StmtGenerateC3D;
import compi2.multi.compilator.c3d.util.C3Dpass;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class StmtsGeneratorC3D {
    
    public void generateInternalCuartetas(
            AdmiMemory admiMemory, 
            List<Cuarteta> internalCuartetas, 
            Memory temporals, 
            C3Dpass pass, 
            List<? extends StmtGenerateC3D> internalStmts
    ){
        if(!internalCuartetas.isEmpty()){
            for (StmtGenerateC3D internalStmt : internalStmts) {
                internalStmt.generateCuartetas(admiMemory, internalCuartetas, temporals, pass);
            }
        }
    }
    
}
