package compi2.multi.compilator.semantic.cast.dec;

import compi2.multi.compilator.analysis.symbolt.AdditionalInfoST;
import compi2.multi.compilator.analysis.symbolt.Category;
import compi2.multi.compilator.analysis.symbolt.InfParam;
import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.ConstructorST;
import compi2.multi.compilator.analysis.symbolt.clases.DirInstanceST;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.symbolt.estruc.SingleData;
import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.analyzator.FunctionRefAnalyzator;
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
import compi2.multi.compilator.c3d.generators.ParamsGenC3D;
import compi2.multi.compilator.c3d.util.Register;
import compi2.multi.compilator.exceptions.NoDataFoundEx;
import compi2.multi.compilator.semantic.DefiniteOperation;
import compi2.multi.compilator.semantic.c.CDef;
import compi2.multi.compilator.semantic.c.CExp;
import compi2.multi.compilator.semantic.c.CImports;
import compi2.multi.compilator.semantic.util.Label;
import java.util.LinkedList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter
@Setter
public class CObjectDec extends CDef {

    private Label objectName;
    private List<CExp> args;

    private FunctionRefAnalyzator refFun;
    private ParamsGenC3D paramsGenC3D;

    private ConstructorST constructorST;
    private SymbolTable st;
    //private ObjectST objectST;
    private SingleData singleDataST;

    public CObjectDec(Label name) {
        super.name = name;
        args = new LinkedList<>();
        this.refFun = new FunctionRefAnalyzator();
        this.paramsGenC3D = new ParamsGenC3D();
    }

    public CObjectDec(Label name, List<CExp> args) {
        super.name = name;
        this.args = args;
        this.refFun = new FunctionRefAnalyzator();
        this.paramsGenC3D = new ParamsGenC3D();
    }

    @Override
    public RowST generateRowST(CImports imports, JSymbolTable clasesST,
            SymbolTable symbolTable, SymbolTable pascalST, TypeTable typeTable,
            List<String> semanticErrors) {
        if (symbolTable.containsKey(this.name.getName())) {
            semanticErrors.add(super.errorsRep.repeatedDeclarationError(
                    name.getName(), name.getPosition())
            );
            return null;
        } else {
            List<InfParam> argsStringList = refFun.validateArgs(
                    args, imports, clasesST, symbolTable, pascalST, typeTable, semanticErrors
            );
            if (clasesST.containsKey(objectName.getName())) {
                SymbolTable objST = clasesST.get(objectName.getName()).getMethodsST();
                constructorST = existReference(objST, argsStringList, semanticErrors);
                st = symbolTable;
                if (constructorST != null) {
                    /*this.objectST = new ObjectST(this.name.getName(), objectName.getName());
                    return this.objectST;*/
                    int relativeDir = symbolTable.getLastDir();
                    symbolTable.incrementLastDir(relativeDir + 1);
                    this.singleDataST = new SingleData(
                            this.name.getName(),
                            Category.JObject, objectName.getName(),
                            relativeDir
                    );
                    return this.singleDataST;
                }
            } else {
                semanticErrors.add(
                        errorsRep.undefiniteClassError(
                                objectName.getName(), objectName.getPosition()
                        )
                );
            }
        }
        return null;
    }

    private ConstructorST existReference(SymbolTable symbolTable, List<InfParam> argsStringList,
            List<String> semanticErrors) {
        if (symbolTable.containsKey(objectName.getName())) {
            RowST rowST = symbolTable.get(objectName.getName());
            //exactly match
            if (rowST instanceof ConstructorST) {
                ConstructorST constructorST = (ConstructorST) rowST;
                if (refFun.hasTheSameArgs(constructorST.getParams(), argsStringList)) {
                    return constructorST;
                } else {
                    try {
                        return verificateOthersConstructs(symbolTable, argsStringList);
                    } catch (NoDataFoundEx e) {
                    }
                }
            } else {
                try {
                    return verificateOthersConstructs(symbolTable, argsStringList);
                } catch (NoDataFoundEx e) {
                }
            }
            //converted match
            if (rowST instanceof ConstructorST) {
                ConstructorST constructorST = (ConstructorST) rowST;
                if (refFun.hasTheSameConvertArgs(constructorST.getParams(), argsStringList)) {
                    return constructorST;
                } else {
                    try {
                        return verificateTransConstructs(symbolTable, argsStringList);
                    } catch (NoDataFoundEx e) {
                    }
                }
            } else {
                try {
                    return verificateTransConstructs(symbolTable, argsStringList);
                } catch (NoDataFoundEx e) {
                }
            }
        }
        semanticErrors.add(
                super.errorsRep.undefiniteConstructorError(name.getPosition())
        );
        return null;
    }

    private ConstructorST verificateOthersConstructs(SymbolTable symbolTable, List<InfParam> argsStringList)
            throws NoDataFoundEx {
        int index = 1;
        while (symbolTable.containsKey(
                refFun.getSTName(this.objectName.getName(), index))) {
            RowST anotherRowST = symbolTable.get(refFun.getSTName(this.objectName.getName(), index));
            if (anotherRowST instanceof ConstructorST) {
                ConstructorST f1 = (ConstructorST) anotherRowST;
                if (refFun.hasTheSameArgs(f1.getParams(), argsStringList)) {
                    return f1;
                }
            }
            index++;
        }
        throw new NoDataFoundEx();
    }

    private ConstructorST verificateTransConstructs(SymbolTable symbolTable, List<InfParam> argsStringList)
            throws NoDataFoundEx {
        int index = 1;
        while (symbolTable.containsKey(
                refFun.getSTName(this.name.getName(), index))) {
            RowST anotherRowST = symbolTable.get(refFun.getSTName(this.name.getName(), index));
            if (anotherRowST instanceof ConstructorST) {
                ConstructorST f1 = (ConstructorST) anotherRowST;
                if (refFun.hasTheSameConvertArgs(f1.getParams(), argsStringList)) {
                    return f1;
                }
            }
            index++;
        }
        throw new NoDataFoundEx();
    }

    @Override
    public void generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas,
            Memory temporals) {
        //moviendose temporalmente en el stack
        internalCuartetas.add(
                new OperationC3D(
                        new RegisterUse(Register.AX_INT),
                        new StackPtrUse(),
                        new AtomicValue(st.getLastDir()),
                        DefiniteOperation.Addition
                )
        );
        int tempIntCount = temporals.getIntegerCount();
        temporals.setIntegerCount(tempIntCount + 1);
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
        //moviendose en el stack
        internalCuartetas.add(
                new OperationC3D(
                        new StackPtrUse(),
                        new StackPtrUse(),
                        new AtomicValue(st.getLastDir()),
                        DefiniteOperation.Addition
                )
        );
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
        //moverse en el stack y recuperar la referencia
        internalCuartetas.add(
                new OperationC3D(
                        new RegisterUse(Register.AX_INT),
                        new StackPtrUse(),
                        new AtomicValue(st.getLastDir()),
                        DefiniteOperation.Addition
                )
        );
        int otherTemporal = temporals.getIntegerCount();
        temporals.setIntegerCount(otherTemporal + 1);
        internalCuartetas.add(
                new AssignationC3D(
                        new TemporalUse(
                                PrimitiveType.IntegerPT,
                                otherTemporal,
                                temporals
                        ),
                        new RegisterUse(Register.AX_INT)
                )
        );

        //recuperar la referencia
        DirInstanceST dirInstanceST = (DirInstanceST) constructorST.getInternalST().get(AdditionalInfoST.DIR_INSTANCE_ROW.getNameRow());

        //recuperando la posicion en el stack del metodo
        internalCuartetas.add(
                new AssignationC3D(
                        new RegisterUse(Register.BX_INT),
                        new RegisterUse(Register.AX_INT)
                )
        );
        internalCuartetas.add(
                new OperationC3D(
                        new RegisterUse(Register.CX_INT),
                        new RegisterUse(Register.BX_INT),
                        new AtomicValue(dirInstanceST.getDirMemory()),
                        DefiniteOperation.Addition
                )
        );
        //setear la referencia devuelta
        internalCuartetas.add(
                new AssignationC3D(
                        new StackAccess(
                                PrimitiveType.IntegerPT,
                                new RegisterUse(Register.CX_INT)
                        ),
                        new RegisterUse(Register.AX_INT)
                )
        );
    }

}
