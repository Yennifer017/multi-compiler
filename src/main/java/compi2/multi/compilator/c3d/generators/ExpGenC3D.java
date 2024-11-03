package compi2.multi.compilator.c3d.generators;

import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.access.AtomicStringConvC3D;
import compi2.multi.compilator.c3d.access.AtomicValue;
import compi2.multi.compilator.c3d.access.MemoryAccess;
import compi2.multi.compilator.c3d.access.RegisterUse;
import compi2.multi.compilator.c3d.access.TemporalUse;
import compi2.multi.compilator.c3d.cuartetas.AssignationC3D;
import compi2.multi.compilator.c3d.cuartetas.GotoC3D;
import compi2.multi.compilator.c3d.cuartetas.IfC3D;
import compi2.multi.compilator.c3d.cuartetas.LabelC3D;
import compi2.multi.compilator.c3d.cuartetas.OperationC3D;
import compi2.multi.compilator.c3d.cuartetas.UnaryOpC3D;
import compi2.multi.compilator.c3d.util.AdmiRegisters;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.c3d.interfaces.ExpressionGenerateC3D;
import compi2.multi.compilator.c3d.util.Register;
import compi2.multi.compilator.c3d.util.RetParamsC3D;
import compi2.multi.compilator.semantic.DefiniteOperation;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class ExpGenC3D {

    private AdmiRegisters admiRegisters;
    private AccessGenC3D accessGenC3D;

    public ExpGenC3D() {
        this.admiRegisters = new AdmiRegisters();
        this.accessGenC3D = new AccessGenC3D();
    }

    public RetParamsC3D generateAndCuartetas(AdmiMemory admiMemory,
            List<Cuarteta> internalCuartetas, Memory temporals, C3Dpass pass,
            ExpressionGenerateC3D leftExp, ExpressionGenerateC3D rightExp,
            PrimitiveType type, DefiniteOperation operation) {
        int count = temporals.getBooleanCount();
        temporals.setBooleanCount(temporals.getBooleanCount() + 1);

        int firstL = admiMemory.getCountLabels();
        admiMemory.setCountLabels(admiMemory.getCountLabels() + 4);

        this.generateConditionCuartetas(
                admiMemory, internalCuartetas, temporals, pass,
                pass, leftExp, firstL
        );
        internalCuartetas.add(
                new GotoC3D(firstL + 1)
        );
        internalCuartetas.add(
                new LabelC3D(firstL)
        );
        this.generateConditionCuartetas(
                admiMemory, internalCuartetas, temporals, pass,
                pass, rightExp, firstL + 2
        );
        internalCuartetas.add(
                new GotoC3D(firstL + 1)
        );
        internalCuartetas.add(
                new LabelC3D(firstL + 1)
        );
        //t = 0
        internalCuartetas.add(
                new AssignationC3D(
                        new TemporalUse(PrimitiveType.BooleanPT, count, temporals),
                        new AtomicValue(0)
                )
        );
        internalCuartetas.add(
                new GotoC3D(firstL + 3)
        );
        internalCuartetas.add(
                new LabelC3D(firstL + 2)
        );
        //t = 1
        internalCuartetas.add(
                new AssignationC3D(
                        new TemporalUse(PrimitiveType.BooleanPT, count, temporals),
                        new AtomicValue(1)
                )
        );
        internalCuartetas.add(
                new GotoC3D(firstL + 3)
        );
        internalCuartetas.add(
                new LabelC3D(firstL + 3)
        );
        return new RetParamsC3D(
                new TemporalUse(PrimitiveType.BooleanPT, count, temporals)
        );
    }

    public RetParamsC3D generateOrCuartetas(AdmiMemory admiMemory,
            List<Cuarteta> internalCuartetas, Memory temporals, C3Dpass pass,
            ExpressionGenerateC3D leftExp, ExpressionGenerateC3D rightExp,
            PrimitiveType type, DefiniteOperation operation) {
        int count = temporals.getBooleanCount();
        temporals.setBooleanCount(temporals.getBooleanCount() + 1);

        int firstL = admiMemory.getCountLabels();
        admiMemory.setCountLabels(admiMemory.getCountLabels() + 4);

        this.generateConditionCuartetas(
                admiMemory, internalCuartetas, temporals, pass,
                pass, leftExp, firstL
        );
        internalCuartetas.add(
                new GotoC3D(firstL + 1)
        );
        internalCuartetas.add(
                new LabelC3D(firstL)
        );
        //t = 1
        internalCuartetas.add(
                new AssignationC3D(
                        new TemporalUse(PrimitiveType.BooleanPT, count, temporals),
                        new AtomicValue(1)
                )
        );
        internalCuartetas.add(
                new GotoC3D(firstL + 2)
        );
        internalCuartetas.add(
                new LabelC3D(firstL + 1)
        );
        this.generateConditionCuartetas(
                admiMemory, internalCuartetas, temporals, pass,
                pass, rightExp, firstL
        );
        internalCuartetas.add(
                new GotoC3D(firstL + 3)
        );
        internalCuartetas.add(
                new LabelC3D(firstL + 3)
        );
        //t = 0
        internalCuartetas.add(
                new AssignationC3D(
                        new TemporalUse(PrimitiveType.BooleanPT, count, temporals),
                        new AtomicValue(0)
                )
        );
        internalCuartetas.add(
                new GotoC3D(firstL + 2)
        );
        internalCuartetas.add(
                new LabelC3D(firstL + 2)
        );
        return new RetParamsC3D(
                new TemporalUse(PrimitiveType.BooleanPT, count, temporals)
        );
    }

    public void generateConditionCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas,
            Memory temporals, C3Dpass pass, C3Dpass passInternal, ExpressionGenerateC3D condition,
            int trueLabel) {
        RetParamsC3D retCondition = condition.generateCuartetas(
                admiMemory, internalCuartetas, temporals, passInternal
        );

        if (retCondition.getTemporalUse() != null) {
            internalCuartetas.add(
                    new AssignationC3D(
                            new RegisterUse(Register.AX_INT),
                            retCondition.getTemporalUse()
                    )
            );
            temporals.setIntegerCount(temporals.getIntegerCount() + 1);
            internalCuartetas.add(
                    new IfC3D(
                            new RegisterUse(Register.AX_INT),
                            new AtomicValue(1),
                            DefiniteOperation.GraterEq,
                            new GotoC3D(trueLabel)
                    )
            );
        } else {
            internalCuartetas.add(
                    new IfC3D(
                            new AtomicValue(retCondition.getAtomicValue()),
                            new AtomicValue(1),
                            DefiniteOperation.GraterEq,
                            new GotoC3D(trueLabel)
                    )
            );
        }
    }

    public RetParamsC3D generateAritCuartetas(AdmiMemory admiMemory,
            List<Cuarteta> internalCuartetas, Memory temporals, C3Dpass pass,
            ExpressionGenerateC3D leftExp, ExpressionGenerateC3D rightExp,
            PrimitiveType type, DefiniteOperation operation
    ) {

        RetParamsC3D ret1 = leftExp.generateCuartetas(admiMemory, internalCuartetas, temporals, pass);
        RetParamsC3D ret2 = rightExp.generateCuartetas(admiMemory, internalCuartetas, temporals, pass);
        MemoryAccess firstAccess = accessGenC3D.getRegisterAccess(internalCuartetas, ret1, 1);
        MemoryAccess secondAccess = accessGenC3D.getRegisterAccess(internalCuartetas, ret2, 2);
        
        if (firstAccess instanceof AtomicValue atomicVal && atomicVal.getValue() instanceof String) {
            secondAccess = new AtomicStringConvC3D(secondAccess);
        }

        if (secondAccess instanceof AtomicValue atomicVal && atomicVal.getValue() instanceof String) {
            firstAccess = new AtomicStringConvC3D(firstAccess);
        }

        int count = temporals.getCount(type);
        temporals.increment(type, 1);
        Register register = admiRegisters.findRegister(type, 3);

        internalCuartetas.add(
                new OperationC3D(
                        new RegisterUse(register),
                        firstAccess,
                        secondAccess,
                        operation
                )
        );
        internalCuartetas.add(
                new AssignationC3D(
                        new TemporalUse(type, count, temporals),
                        new RegisterUse(register)
                )
        );
        return new RetParamsC3D(
                new TemporalUse(type, count, temporals)
        );
    }

    public RetParamsC3D generateAritUnaryCuartetas(AdmiMemory admiMemory,
            List<Cuarteta> internalCuartetas, Memory temporals, C3Dpass pass,
            ExpressionGenerateC3D exp,
            PrimitiveType type, DefiniteOperation operation
    ) {
        RetParamsC3D retParamC3D = exp.generateCuartetas(admiMemory, internalCuartetas, temporals, pass);
        Register register = admiRegisters.findRegister(type, 1);
        int count = temporals.getCount(type);
        temporals.increment(type, 1);
        if (retParamC3D.getTemporalUse() != null) {
            internalCuartetas.add(
                    new UnaryOpC3D(
                            new RegisterUse(register),
                            operation,
                            retParamC3D.getTemporalUse()
                    )
            );
        } else {
            internalCuartetas.add(
                    new UnaryOpC3D(
                            new RegisterUse(register),
                            operation,
                            new AtomicValue(retParamC3D.getAtomicValue())
                    )
            );
        }
        internalCuartetas.add(
                new AssignationC3D(
                        new TemporalUse(type, count, temporals),
                        new RegisterUse(register)
                )
        );
        return new RetParamsC3D(
                new TemporalUse(type, count, temporals)
        );
    }

    public RetParamsC3D generateNotCuartetas(AdmiMemory admiMemory,
            List<Cuarteta> internalCuartetas, Memory temporals, C3Dpass pass,
            ExpressionGenerateC3D exp,
            PrimitiveType type, DefiniteOperation operation) {
        MemoryAccess access = accessGenC3D.getAccess(exp, admiMemory, internalCuartetas, temporals, pass);
        int fistLabel = admiMemory.getCountLabels();
        admiMemory.setCountLabels(admiMemory.getCountLabels() + 3);
        int count = temporals.getBooleanCount();
        temporals.setBooleanCount(temporals.getBooleanCount() + 1);
        internalCuartetas.add(
                new IfC3D(
                        access,
                        new AtomicValue(1),
                        DefiniteOperation.GraterEq,
                        new GotoC3D(fistLabel)
                )
        );
        internalCuartetas.add(
                new GotoC3D(fistLabel + 1)
        );
        internalCuartetas.add(
                new LabelC3D(fistLabel)
        );
        //t = 0
        internalCuartetas.add(
                new AssignationC3D(
                        new TemporalUse(PrimitiveType.BooleanPT, count, temporals),
                        new AtomicValue(0)
                )
        );
        internalCuartetas.add(
                new GotoC3D(fistLabel + 2)
        );
        internalCuartetas.add(
                new LabelC3D(fistLabel + 1)
        );
        //t = 1
        internalCuartetas.add(
                new AssignationC3D(
                        new TemporalUse(PrimitiveType.BooleanPT, count, temporals),
                        new AtomicValue(1)
                )
        );
        internalCuartetas.add(
                new GotoC3D(fistLabel + 2)
        );
        internalCuartetas.add(
                new LabelC3D(fistLabel + 2)
        );
        return new RetParamsC3D(
                new TemporalUse(PrimitiveType.BooleanPT, count, temporals)
        );
    }

}
