
package compi2.multi.compilator.c3d.generators;

import compi2.multi.compilator.analysis.symbolt.InfParam;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.estruc.SingleData;
import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.analysis.typet.convert.TConvertidor;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.access.AtomicValue;
import compi2.multi.compilator.c3d.access.MemoryAccess;
import compi2.multi.compilator.c3d.access.RegisterUse;
import compi2.multi.compilator.c3d.access.StackAccess;
import compi2.multi.compilator.c3d.access.TemporalUse;
import compi2.multi.compilator.c3d.cuartetas.AssignationC3D;
import compi2.multi.compilator.c3d.cuartetas.OperationC3D;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.c3d.interfaces.ExpressionGenerateC3D;
import compi2.multi.compilator.c3d.util.Register;
import compi2.multi.compilator.semantic.DefiniteOperation;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class ParamsGenC3D {
    
    private AccessGenC3D accessGenC3D;
    private TConvertidor tConvertidor;
    
    public ParamsGenC3D(){
        this.accessGenC3D = new AccessGenC3D();
        this.tConvertidor = new TConvertidor();
    }
    public void generateParamsC3D(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, 
            Memory temporals, SymbolTable symbolTable, List<InfParam> params, 
            List<? extends ExpressionGenerateC3D> expressions, TemporalUse stackTemp){
        if(!params.isEmpty()){
            for (int i = 0; i < params.size(); i++) {
                InfParam param = params.get(i);
                ExpressionGenerateC3D exp = expressions.get(i);
                SingleData rowST = (SingleData) symbolTable.get(param.getName());
                
                MemoryAccess expAccess = accessGenC3D.getAccess(
                        exp, admiMemory, internalCuartetas, temporals, new C3Dpass()
                );
                
                //recuperando la posicion en el stack del metodo
                internalCuartetas.add(
                        new AssignationC3D(
                                new RegisterUse(Register.BX_INT), 
                                stackTemp
                        )
                );
                internalCuartetas.add(
                        new OperationC3D(
                                new RegisterUse(Register.CX_INT), 
                                new RegisterUse(Register.BX_INT), 
                                new AtomicValue(rowST.getRelativeDir()), 
                                DefiniteOperation.Addition
                        )
                );
                //setear el parametro
                PrimitiveType type = tConvertidor.convertAllPrimitive(param.getType());
                internalCuartetas.add(
                        new AssignationC3D(
                                new StackAccess(type, new RegisterUse(Register.CX_INT)), 
                                expAccess
                        )
                );
            }
        }
    }
}
