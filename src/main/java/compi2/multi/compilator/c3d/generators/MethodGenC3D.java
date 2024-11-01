package compi2.multi.compilator.c3d.generators;

import compi2.multi.compilator.analysis.symbolt.ModuleRowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.access.AtomicValue;
import compi2.multi.compilator.c3d.access.RegisterUse;
import compi2.multi.compilator.c3d.access.StackAccess;
import compi2.multi.compilator.c3d.access.StackPtrUse;
import compi2.multi.compilator.c3d.access.TemporalUse;
import compi2.multi.compilator.c3d.cuartetas.AssignationC3D;
import compi2.multi.compilator.c3d.cuartetas.MethodInvC3D;
import compi2.multi.compilator.c3d.cuartetas.OperationC3D;
import compi2.multi.compilator.c3d.util.AdmiRegisters;
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
    private AdmiRegisters admiRegisters;

    public MethodGenC3D() {
        this.paramsGenC3D = new ParamsGenC3D();
        this.admiRegisters = new AdmiRegisters();
    }

    public void settingParams(List<? extends ExpressionGenerateC3D> args,
            AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, Memory temporals,
            SymbolTable st, ModuleRowST moduleRow) {
        //seteando parametros
        if (!args.isEmpty()) {
            TemporalUse temporalMove = this.moveTemporalStack(
                    internalCuartetas, temporals, st.getLastDir()
            );
            //seteando los parametros
            this.paramsGenC3D.generateParamsC3D(
                    admiMemory,
                    internalCuartetas,
                    temporals,
                    moduleRow.getInternalST(),
                    moduleRow.getParams(),
                    args,
                    temporalMove
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

    public TemporalUse moveTemporalStack(List<Cuarteta> internalCuartetas, Memory temporals, int moves) {
        int tempIntCount = temporals.getIntegerCount();
        temporals.setIntegerCount(tempIntCount + 1);
        RegisterUse axIntRegister = new RegisterUse(Register.AX_INT);
        internalCuartetas.add(
                new OperationC3D(
                        axIntRegister,
                        new StackPtrUse(),
                        new AtomicValue(moves),
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
                        axIntRegister
                )
        );
        return new TemporalUse(PrimitiveType.IntegerPT, tempIntCount, temporals);
    }
    
    public TemporalUse recoverReturnValue(List<Cuarteta> internalCuartetas, Memory temporals, 
            int movesStack, int movesToReturn, PrimitiveType type){
        TemporalUse posStack = this.moveTemporalStack(internalCuartetas, temporals, movesStack);
        RegisterUse register = new RegisterUse(admiRegisters.findRegister(type, 2));
        
        int countIntTemp = temporals.getIntegerCount();
        temporals.setIntegerCount(countIntTemp + 1);
        int countTemp = temporals.getCount(type);
        temporals.increment(type, 1);
        
        TemporalUse temporalPos = new TemporalUse(
                PrimitiveType.IntegerPT, countIntTemp, temporals
        );
        TemporalUse temporalRetVal = new TemporalUse(type, countTemp, temporals);
        
        RegisterUse axIntRegister = new RegisterUse(Register.AX_INT);
        RegisterUse bxIntRegister = new RegisterUse(Register.BX_INT);
        
        //encontrar la posicion de retorno en el stack
        internalCuartetas.add(
                new AssignationC3D(
                        axIntRegister, 
                        posStack
                )
        );
        internalCuartetas.add(
                new OperationC3D(
                        bxIntRegister, 
                        axIntRegister, 
                        new AtomicValue(movesToReturn), 
                        DefiniteOperation.Addition
                )
        );
        internalCuartetas.add(
                new AssignationC3D(
                        temporalPos, 
                        bxIntRegister
                )
        );
        //recuperar el valor 
        internalCuartetas.add(
                new AssignationC3D(
                        axIntRegister, 
                        temporalPos
                )
        );
        internalCuartetas.add(
                new AssignationC3D(
                        register, 
                        new StackAccess(type, axIntRegister)
                )
        );
        //setear y retornar el valor de una temporal
        internalCuartetas.add(
                new AssignationC3D(
                        temporalRetVal, 
                        register
                )
        );
        return temporalRetVal;
    }
    
}
