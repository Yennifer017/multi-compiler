package compi2.multi.compilator.semantic.jast.inv;

import compi2.multi.compilator.analysis.jerarquia.NodeJerarTree;
import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.ClassST;
import compi2.multi.compilator.analysis.symbolt.clases.FieldST;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.symbolt.estruc.SingleData;
import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.analyzator.Analyzator;
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
import compi2.multi.compilator.semantic.util.Label;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter
@Setter
public class JVarUse extends JInvocation {

    private RowST rowST;

    public JVarUse(Label inv, JContextRef context) {
        super(inv, context);
    }

    @Override
    public Label validate(JSymbolTable globalST, SymbolTable symbolTable,
            TypeTable typeTable, NodeJerarTree jerar, List<String> semanticErrors) {
        switch (context) {
            case JContextRef.Local:
                SymbolTable currentST = symbolTable;
                while (currentST != null) {
                    if (currentST.containsKey(this.inv.getName())) {
                        this.rowST = currentST.get(this.inv.getName());
                        if (rowST instanceof SingleData) {
                            SingleData singleData = (SingleData) rowST;
                            return new Label(singleData.getType(), this.inv.getPosition());
                        } else {
                            semanticErrors.add(super.errorsRep.invalidCategoryAccessError(
                                    rowST.getName(),
                                    rowST.getCategory().getName(),
                                    this.inv.getPosition())
                            );
                            return new Label(Analyzator.ERROR_TYPE, this.inv.getPosition());
                        }
                    }
                    currentST = symbolTable.getFather();
                }
            case JContextRef.FromObject:
                currentST = jerar.getClassST().getFieldsST();
                if (currentST.containsKey(this.inv.getName())) {
                    this.rowST = currentST.get(this.inv.getName());
                    if (rowST instanceof FieldST) {
                        FieldST fieldST = (FieldST) rowST;
                        return new Label(fieldST.getType(), this.inv.getPosition());
                    } else {
                        int index = 1;
                        while (symbolTable.containsKey(
                                refFun.getSTName(this.inv.getName(), index))) {
                            RowST anotherRowST = symbolTable.get(
                                    refFun.getSTName(this.inv.getName(), index)
                            );
                            if (anotherRowST instanceof FieldST) {
                                return new Label(anotherRowST.getType(), this.inv.getPosition());
                            }
                            index++;
                        }
                    }
                }
            default: //from father
                NodeJerarTree currentNode = jerar.getFather();
                while (currentNode != null) {
                    currentST = currentNode.getClassST().getFieldsST();
                    if (currentST.containsKey(this.inv.getName())) {
                        this.rowST = currentST.get(this.inv.getName());
                        if (rowST instanceof FieldST) {
                            FieldST fieldST = (FieldST) rowST;
                            return new Label(fieldST.getType(), this.inv.getPosition());
                        } else {
                            return continueFind(currentST, semanticErrors);
                        }
                    }
                    currentNode = currentNode.getFather();
                }
                semanticErrors.add(
                        super.errorsRep.undefiniteVarUseError(this.inv.getName(), this.inv.getPosition())
                );
                return new Label(Analyzator.ERROR_TYPE, this.inv.getPosition());
        }
    }

    private Label continueFind(SymbolTable symbolTable, List<String> semanticErrors) {
        int index = 1;
        while (symbolTable.containsKey(
                refFun.getSTName(this.inv.getName(), index))) {
            RowST anotherRowST = symbolTable.get(refFun.getSTName(this.inv.getName(), index));
            if (anotherRowST instanceof FieldST) {
                return new Label(anotherRowST.getType(), this.inv.getPosition());
            }
            index++;
        }
        semanticErrors.add(
            super.errorsRep.undefiniteVarUseError(this.inv.getName(), this.inv.getPosition())
        );
        return new Label(Analyzator.ERROR_TYPE, this.inv.getPosition());
    }

    @Override
    public Label validate(JSymbolTable globalST, SymbolTable symbolTable,
            TypeTable typeTable, NodeJerarTree jerar, List<String> semanticErrors, Label previus) {
        if (globalST.containsKey(previus.getName())) {
            ClassST classST = globalST.get(previus.getName());
            SymbolTable fieldsClassST = classST.getFieldsST();
            if (fieldsClassST.containsKey(this.inv.getName())) {
                rowST = fieldsClassST.get(this.inv.getName());
                return new Label(rowST.getType(), this.inv.getPosition());
            } else {
                semanticErrors.add(errorsRep.invalidInvocationError(
                        previus.getName(), this.inv.getName(), this.inv.getPosition())
                );
            }
        } else {
            semanticErrors.add(errorsRep.invalidInvocationError(
                    previus.getName(), this.inv.getName(), this.inv.getPosition())
            );
        }
        return new Label(Analyzator.ERROR_TYPE, this.inv.getPosition());
    }

    @Override
    public RetJInvC3D generateCuartetas(AdmiMemory admiMemory,
            List<Cuarteta> internalCuartetas, Memory temporals, int instanceStackRef) {
        if (rowST instanceof SingleData) {
            SingleData singleData = (SingleData) rowST;
            int currentIntTemp = temporals.getIntegerCount();
            temporals.setIntegerCount(currentIntTemp + 1);
            internalCuartetas.add( //AX = ptr + n
                    new OperationC3D(
                            new RegisterUse(Register.AX_INT),
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
                            new RegisterUse(Register.AX_INT)
                    )
            );
            return new RetJInvC3D(
                    new TemporalUse(
                            PrimitiveType.IntegerPT,
                            currentIntTemp,
                            temporals
                    ),
                    false
            );
        } else if (rowST instanceof FieldST) {
            //TODO: validar si es heredado
            FieldST fieldST = (FieldST) rowST;
            int temporalCount = temporals.getIntegerCount();
            temporals.setIntegerCount(temporalCount + 1);
            internalCuartetas.add(
                    new OperationC3D(
                            new RegisterUse(Register.AX_INT), 
                            new StackPtrUse(), 
                            new AtomicValue(instanceStackRef), 
                            DefiniteOperation.Addition
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
                    new OperationC3D(
                            new RegisterUse(Register.AX_INT),
                            new RegisterUse(Register.BX_INT),
                            new AtomicValue(fieldST.getRelativeDir()),
                            DefiniteOperation.Addition
                    )
            );
            internalCuartetas.add(
                    new AssignationC3D(
                            new TemporalUse(
                                    PrimitiveType.IntegerPT,
                                    temporalCount,
                                    temporals
                            ),
                            new RegisterUse(Register.AX_INT)
                    )
            );
            return new RetJInvC3D(
                    new TemporalUse(
                            PrimitiveType.IntegerPT,
                            temporalCount,
                            temporals
                    ),
                    true
            );
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public RetJInvC3D generateCuartetas(AdmiMemory admiMemory,
            List<Cuarteta> internalCuartetas, Memory temporals, RetJInvC3D previus) {
        FieldST fieldST = (FieldST) rowST;
        int countTemp = temporals.getIntegerCount();
        temporals.setIntegerCount(countTemp + 2);
        
        internalCuartetas.add(
                new AssignationC3D(
                        new RegisterUse(Register.AX_INT), 
                        previus.getTemporalUse()
                )
        );
        if(previus.isHeapAccess()){
            internalCuartetas.add(
                    new AssignationC3D(
                            new RegisterUse(Register.BX_INT), 
                            new HeapAccess(
                                    PrimitiveType.IntegerPT, 
                                    new RegisterUse(Register.AX_INT)
                            )
                    )
            );
        } else {
            internalCuartetas.add(
                    new AssignationC3D(
                            new RegisterUse(Register.BX_INT), 
                            new StackAccess(
                                    PrimitiveType.IntegerPT, 
                                    new RegisterUse(Register.AX_INT)
                            )
                    )
            );
        }
        internalCuartetas.add(
                new AssignationC3D(
                        new TemporalUse(PrimitiveType.IntegerPT, countTemp, temporals), 
                        new RegisterUse(Register.BX_INT)
                )
        );
        internalCuartetas.add(
                new AssignationC3D(
                        new RegisterUse(Register.AX_INT), 
                        new TemporalUse(PrimitiveType.IntegerPT, countTemp, temporals)
                )
        );
        internalCuartetas.add(
                new OperationC3D(
                        new RegisterUse(Register.BX_INT), 
                        new RegisterUse(Register.AX_INT), 
                        new AtomicValue(fieldST.getRelativeDir()), 
                        DefiniteOperation.Addition
                )
        );
        internalCuartetas.add(
                new AssignationC3D(
                        new TemporalUse(PrimitiveType.IntegerPT, countTemp + 1, temporals), 
                        new RegisterUse(Register.BX_INT)
                )
        );
        return new RetJInvC3D(
                new TemporalUse(PrimitiveType.IntegerPT, countTemp + 1, temporals), 
                true
        );
    }
    
    @Override
    public boolean isStatement() {
        return false;
    }

    @Override
    public boolean hasReturnType() {
        return true;
    }

}
