package compi2.multi.compilator.c3d.generators;

import compi2.multi.compilator.analysis.symbolt.AdditionalInfoST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.ConstructorST;
import compi2.multi.compilator.analysis.symbolt.clases.DirInstanceST;
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
import compi2.multi.compilator.c3d.util.ExpressionGenerateC3D;
import compi2.multi.compilator.c3d.util.Register;
import compi2.multi.compilator.c3d.util.RetParamsC3D;
import compi2.multi.compilator.semantic.DefiniteOperation;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class ConstructorC3DGen {

    private ParamsGenC3D paramsGenC3D;

    public ConstructorC3DGen() {
        this.paramsGenC3D = new ParamsGenC3D();
    }

    public RetParamsC3D generateCuartetas(List<? extends ExpressionGenerateC3D> args,
            AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, Memory temporals,
            SymbolTable st, ConstructorST constructorST) {
        this.settingParams(args, admiMemory, internalCuartetas, temporals, st, constructorST);
        this.invocateConstructor(internalCuartetas, st, constructorST);
        return this.recoverReference(internalCuartetas, temporals, st, constructorST);
    }

    private void settingParams(List<? extends ExpressionGenerateC3D> args,
            AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, Memory temporals,
            SymbolTable st, ConstructorST constructorST) {
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
                    constructorST.getInternalST(),
                    constructorST.getParams(),
                    args,
                    new TemporalUse(PrimitiveType.IntegerPT, tempIntCount, temporals)
            );
        }
    }

    private void invocateConstructor(List<Cuarteta> internalCuartetas,
            SymbolTable st, ConstructorST constructorST) {
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
                new MethodInvC3D(constructorST.getCompleateName())
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

    private RetParamsC3D recoverReference(List<Cuarteta> internalCuartetas, Memory temporals,
            SymbolTable st, ConstructorST constructorST) {
        int tempCount = temporals.getIntegerCount();
        temporals.setIntegerCount(tempCount + 3);
        DirInstanceST dirInstanceST = (DirInstanceST) constructorST.getInternalST().get(AdditionalInfoST.DIR_INSTANCE_ROW.getNameRow());

        //moviendonos temporalmente al stack del constructor
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
                                tempCount,
                                temporals
                        ),
                        new RegisterUse(Register.AX_INT)
                )
        );
        //obteniendo la posicion de la referencia
        internalCuartetas.add(
                new AssignationC3D(
                        new RegisterUse(Register.AX_INT),
                        new TemporalUse(
                                PrimitiveType.IntegerPT,
                                tempCount,
                                temporals
                        )
                )
        );
        internalCuartetas.add(
                new OperationC3D(
                        new RegisterUse(Register.BX_INT),
                        new RegisterUse(Register.AX_INT),
                        new AtomicValue(dirInstanceST.getDirMemory()),
                        DefiniteOperation.Addition
                )
        );
        internalCuartetas.add(
                new AssignationC3D(
                        new TemporalUse(
                                PrimitiveType.IntegerPT,
                                tempCount + 1,
                                temporals
                        ),
                        new RegisterUse(Register.BX_INT)
                )
        );
        //obteniendo la referencia
        internalCuartetas.add(
                new AssignationC3D(
                        new RegisterUse(Register.AX_INT),
                        new TemporalUse(
                                PrimitiveType.IntegerPT,
                                tempCount + 1,
                                temporals
                        )
                )
        );
        internalCuartetas.add(
                new AssignationC3D(
                        new RegisterUse(Register.BX_INT),
                        new StackAccess(
                                PrimitiveType.IntegerPT,
                                new RegisterUse(Register.AX_INT)
                        )
                )
        );
        internalCuartetas.add(
                new AssignationC3D(
                        new TemporalUse(
                                PrimitiveType.IntegerPT,
                                tempCount + 2,
                                temporals
                        ),
                        new RegisterUse(Register.BX_INT)
                )
        );
        return new RetParamsC3D(
                new TemporalUse(
                        PrimitiveType.IntegerPT,
                        tempCount + 2,
                        temporals
                ));
    }
}
