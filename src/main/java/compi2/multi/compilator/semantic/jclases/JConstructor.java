
package compi2.multi.compilator.semantic.jclases;

import compi2.multi.compilator.semantic.jclases.components.JArg;
import compi2.multi.compilator.analysis.symbolt.AccessMod;
import compi2.multi.compilator.analysis.symbolt.AdditionalInfoST;
import compi2.multi.compilator.analysis.symbolt.InfParam;
import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.ClassST;
import compi2.multi.compilator.analysis.symbolt.clases.ConstructorST;
import compi2.multi.compilator.analysis.symbolt.clases.DirInstanceST;
import compi2.multi.compilator.analysis.symbolt.clases.FieldST;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.analyzator.Analyzator;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.access.AtomicValue;
import compi2.multi.compilator.c3d.access.HeapPtrUse;
import compi2.multi.compilator.c3d.access.RegisterUse;
import compi2.multi.compilator.c3d.access.StackAccess;
import compi2.multi.compilator.c3d.access.StackPtrUse;
import compi2.multi.compilator.c3d.access.TemporalUse;
import compi2.multi.compilator.c3d.cuartetas.AssignationC3D;
import compi2.multi.compilator.c3d.cuartetas.FunctionC3D;
import compi2.multi.compilator.c3d.cuartetas.OperationC3D;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.c3d.util.Register;
import compi2.multi.compilator.semantic.DefiniteOperation;
import compi2.multi.compilator.semantic.j.JStatement;
import compi2.multi.compilator.semantic.util.Label;
import compi2.multi.compilator.semantic.util.SemanticRestrictions;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class JConstructor extends JFunction{
    
    private ConstructorST constructorST;
    
    public JConstructor(Label name, AccessMod access, List<JArg> args, List<JStatement> internalStmts) {
        super(access, args, internalStmts);
        super.name = name;
    }
    
    @Override
    public RowST generateRowST(SymbolTable symbolTable, TypeTable typeTable, 
            List<String> semanticErrors) {
        if(super.nameClass.equals(this.name.getName())){
            List<InfParam> params = super.generateArgsList();
            String nameForST = getNameFunctionForST(symbolTable, params);
            if(nameForST != null){
                this.constructorST = new ConstructorST(
                        nameForST, 
                        super.generateInternalST(true), 
                        params, access
                );
                String finalName = super.getFinalName(constructorST.getName());
                constructorST.setCompleateName(finalName);
                return this.constructorST;
            } else {
                semanticErrors.add(
                        super.errorsRep.redeclareFunctionError(
                                name.getName(), params, name.getPosition()
                        )
                );
            }
        } else {
            semanticErrors.add(super.errorsRep.noConstructorError(
                    super.nameClass, name.getName(), name.getPosition())
            );
        }
        return null;
    }
    
    @Override
    public void validateInternal(JSymbolTable globalST, TypeTable typeTable, 
            List<String> semanticErrors) {
        if(!jerar.getFather().isObject()){
            //validar el uso de constructores
        }
        super.validateArgs(
                globalST, constructorST.getInternalST(), typeTable, semanticErrors
        );
        SemanticRestrictions restrictions = new SemanticRestrictions(
                false, false, Analyzator.VOID_METHOD
        );
        super.validateInternalStmts(
                globalST, 
                constructorST.getInternalST(), 
                typeTable, 
                semanticErrors, 
                restrictions
        );
    }
    
    @Override
    protected String getNameFunctionForST(SymbolTable symbolTable, List<InfParam> argsStringList){
        if(symbolTable.containsKey(name.getName())){            
            RowST rowST = symbolTable.get(name.getName());
            if(rowST instanceof ConstructorST){
                ConstructorST functionST = (ConstructorST) rowST;
                if(refFun.hasTheSameArgs(functionST.getParams(), argsStringList)){
                    return null;
                } else {
                    return verificateOthersConstructs(symbolTable, argsStringList);
                }
            } else {
                return verificateOthersConstructs(symbolTable, argsStringList);
            }
        } else {
            return this.name.getName();
        }
    }
    
    private String verificateOthersConstructs(SymbolTable symbolTable, List<InfParam> argsStringList){
        int index = 1;
        while (symbolTable.containsKey(
                refFun.getSTName(this.name.getName(), index))) {
            RowST anotherRowST = symbolTable.get(refFun.getSTName(this.name.getName(), index));
            if (anotherRowST instanceof ConstructorST) {
                ConstructorST f1 = (ConstructorST) anotherRowST;
                if (refFun.hasTheSameArgs(f1.getParams(), argsStringList)) {
                    return null;
                }
            }
            index++;
        }
        return refFun.getSTName(this.name.getName(), index);
    }

    @Override
    public void generateCuartetas(AdmiMemory admiMemory, ClassST classST) {
        List<Cuarteta> internalCuartetas = new LinkedList<>();
        Memory temporals = new Memory("internal");
        
        RegisterUse axIntRegister = new RegisterUse(Register.AX_INT);
        RegisterUse bxIntRegister = new RegisterUse(Register.BX_INT);
        
        DirInstanceST dirInstanceST = (DirInstanceST) 
                constructorST.getInternalST().get(AdditionalInfoST.DIR_INSTANCE_ROW.getNameRow());
        int instanceDir = temporals.getIntegerCount();
        int stackDir = instanceDir + 1;
        temporals.setIntegerCount(instanceDir + 2);
        
        TemporalUse instanceRefTemp = new TemporalUse(
                PrimitiveType.IntegerPT, instanceDir, temporals
        );
        internalCuartetas.add( 
                new AssignationC3D(
                        instanceRefTemp, 
                        new HeapPtrUse())
        );
        internalCuartetas.add( // h = h + nfields
                new OperationC3D(
                        new HeapPtrUse(), 
                        new HeapPtrUse(), 
                        new AtomicValue<>(classST.getFieldsST().size()),
                        DefiniteOperation.Addition
                )
        );
        internalCuartetas.add(
                new OperationC3D(
                        axIntRegister, 
                        new StackPtrUse(), 
                        new AtomicValue(dirInstanceST.getDirMemory()), 
                        DefiniteOperation.Addition
                )
        );
        internalCuartetas.add(
                new AssignationC3D(
                        new TemporalUse(PrimitiveType.IntegerPT, stackDir, temporals), 
                        axIntRegister
                )
        );
        
        internalCuartetas.add(
                new AssignationC3D(
                        axIntRegister, 
                        new TemporalUse(PrimitiveType.IntegerPT, stackDir, temporals)
                )
        );
        internalCuartetas.add(
                new AssignationC3D(
                        bxIntRegister, 
                        instanceRefTemp
                )
        );
        internalCuartetas.add(
                new AssignationC3D(
                        new StackAccess(
                                PrimitiveType.IntegerPT, 
                                axIntRegister
                        ),
                        bxIntRegister
                )
        );
        this.generateCuartetasFields(
                admiMemory, internalCuartetas, temporals, classST, instanceRefTemp
        );
        super.generateInternalCuartetas(
                admiMemory, internalCuartetas, temporals, new C3Dpass()
        );
        admiMemory.getCuartetas().add(
                new FunctionC3D(
                    constructorST.getCompleateName(), 
                    temporals, 
                    internalCuartetas)
        );
        admiMemory.getDefinitions().add(constructorST.getCompleateName());
    }
    
    private void generateCuartetasFields(AdmiMemory admiMemory, 
            List<Cuarteta> internalCuartetas, Memory temporals, 
            ClassST classST, TemporalUse instanceRefTemp){
        for (String orderedField : classST.getFieldsOrdered()) {
            FieldST fieldST = (FieldST) classST.getFieldsST().get(orderedField);
            fieldST.getJfield().generateCuartetas(
                    admiMemory, internalCuartetas, temporals, instanceRefTemp
            );
        }
    }
    
}
