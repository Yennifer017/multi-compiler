
package compi2.multi.compilator.c3d.generators;

import compi2.multi.compilator.analysis.symbolt.ModuleRowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.ConstructorST;
import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.access.AtomicValue;
import compi2.multi.compilator.c3d.access.RegisterUse;
import compi2.multi.compilator.c3d.access.StackPtrUse;
import compi2.multi.compilator.c3d.access.TemporalUse;
import compi2.multi.compilator.c3d.cuartetas.AssignationC3D;
import compi2.multi.compilator.c3d.cuartetas.MethodInvC3D;
import compi2.multi.compilator.c3d.cuartetas.OperationC3D;
import compi2.multi.compilator.c3d.util.ExpressionGenerateC3D;
import compi2.multi.compilator.c3d.util.Register;
import compi2.multi.compilator.semantic.DefiniteOperation;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class MethodGenC3D {
    private ParamsGenC3D paramsGenC3D;

    public MethodGenC3D() {
        this.paramsGenC3D = new ParamsGenC3D();
    }
    
    public void settingParams(List<? extends ExpressionGenerateC3D> args,
            AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, Memory temporals,
            SymbolTable st, ModuleRowST moduleRow) {
        //seteando parametros
        if (!args.isEmpty()) {
            int tempIntCount = temporals.getIntegerCount();
            temporals.setIntegerCount(tempIntCount + 1);
            //moviendose temporalmente en el stack
            internalCuartetas.add(
                    new OperationC3D(
                            new RegisterUse(Register.AX_INT),
                            new StackPtrUse(),
                            new AtomicValue(st.getLastDir()),
                            DefiniteOperation.Addition
                    )
            );
            internalCuartetas.add(
                    new AssignationC3D(
                            new TemporalUse(
                                    PrimitiveType.IntegerPT,
                                    tempIntCount,
                                    temporals
                            ),
                            new RegisterUse(Register.AX_INT)
                    )
            );
            //seteando los parametros
            this.paramsGenC3D.generateParamsC3D(
                    admiMemory,
                    internalCuartetas,
                    temporals,
                    moduleRow.getInternalST(),
                    moduleRow.getParams(),
                    args,
                    new TemporalUse(PrimitiveType.IntegerPT, tempIntCount, temporals)
            );
        }
    }
    
    public void invocateMethod(List<Cuarteta> internalCuartetas,
            SymbolTable st, String compleateMethodName) {
        //moviendose en el stack
        internalCuartetas.add(
                new OperationC3D(
                        new StackPtrUse(),
                        new StackPtrUse(),
                        new AtomicValue(st.getLastDir()),
                        DefiniteOperation.Addition
                )
        );
        //invocando el metodo
        internalCuartetas.add(
                new MethodInvC3D(compleateMethodName)
        );
        //regresar al stack
        internalCuartetas.add(
                new OperationC3D(
                        new StackPtrUse(),
                        new StackPtrUse(),
                        new AtomicValue(st.getLastDir()),
                        DefiniteOperation.Substraction
                )
        );
    }
    
}
