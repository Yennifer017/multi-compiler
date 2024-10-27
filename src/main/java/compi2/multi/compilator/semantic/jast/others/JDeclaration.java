
package compi2.multi.compilator.semantic.jast.others;

import compi2.multi.compilator.analysis.jerarquia.NodeJerarTree;
import compi2.multi.compilator.analysis.symbolt.Category;
import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.symbolt.estruc.JArrayST;
import compi2.multi.compilator.analysis.symbolt.estruc.SingleData;
import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.analyzator.ExpGenC3D;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.access.AtomicValue;
import compi2.multi.compilator.c3d.access.MemoryAccess;
import compi2.multi.compilator.c3d.access.RegisterUse;
import compi2.multi.compilator.c3d.access.StackAccess;
import compi2.multi.compilator.c3d.access.StackPtrUse;
import compi2.multi.compilator.c3d.cuartetas.AssignationC3D;
import compi2.multi.compilator.c3d.cuartetas.OperationC3D;
import compi2.multi.compilator.c3d.util.AdmiRegisters;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.c3d.util.Register;
import compi2.multi.compilator.c3d.util.RetParamsC3D;
import compi2.multi.compilator.semantic.DefiniteOperation;
import compi2.multi.compilator.semantic.j.JExpression;
import compi2.multi.compilator.semantic.j.JStatement;
import compi2.multi.compilator.semantic.jclases.components.JArrayType;
import compi2.multi.compilator.semantic.jclases.components.JType;
import compi2.multi.compilator.semantic.util.Label;
import compi2.multi.compilator.semantic.util.ReturnCase;
import compi2.multi.compilator.semantic.util.SemanticRestrictions;
import compi2.multi.compilator.util.Position;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class JDeclaration extends JStatement{
    
    private String name;
    private JType type;
    private JExpression value;
    
    private RowST rowST;
    private PrimitiveType primType;
    
    private AdmiRegisters admiRegisters;
    private ExpGenC3D expGenC3D;

    public JDeclaration(Position initPos, String name, JType type) {
        super(initPos);
        this.name = name;
        this.type = type;
        this.admiRegisters = new AdmiRegisters();
        this.expGenC3D = new ExpGenC3D();
    }
    
    public JDeclaration(Position initPos, String name, JType type, JExpression value){
        super(initPos);
        this.name = name;
        this.type = type;
        this.value = value;
        this.admiRegisters = new AdmiRegisters();
        this.expGenC3D = new ExpGenC3D();
    }

    @Override
    public ReturnCase validate(JSymbolTable globalST, SymbolTable symbolTable, 
            TypeTable typeTable, NodeJerarTree jerar, List<String> semanticErrors, 
            SemanticRestrictions restrictions) {
        if(value != null){
            Label typeExp = value.validateData(
                    globalST, symbolTable, typeTable, jerar, semanticErrors, restrictions
            );
            //TODO: validate type
        } 
        if(canInsert(symbolTable, semanticErrors)){
            if(type instanceof JArrayType){
                String stringType = type.getName().getName();
                this.primType = super.tConvert.convertAllPrimitive(stringType);
                rowST = new JArrayST(
                        name, 
                        stringType, 
                        type.getArrayDimensions(), 
                        symbolTable.getLastDir()
                );
                symbolTable.incrementLastDir(1);
                symbolTable.put(name, rowST);
            } else {
                String stringType = type.getName().getName();
                this.primType = super.tConvert.convertAllPrimitive(stringType);
                rowST = new SingleData(
                        name, 
                        Category.Variable, 
                        stringType, 
                        symbolTable.getLastDir()
                );
                symbolTable.incrementLastDir(1);
                symbolTable.put(name, rowST);
            }
        }
        return new ReturnCase(false);
    }
    
    
    private boolean canInsert(SymbolTable symbolTable, List<String> semanticErrors){
        SymbolTable currentST = symbolTable;
        while (currentST != null) {            
            if(currentST.containsKey(name)){
                semanticErrors.add(errorsRep.repeatedDeclarationError(name, initPos));
                return false;
            }
            currentST = currentST.getFather();
        }
        return true;
    }

    @Override
    public void generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, 
            Memory temporals, C3Dpass pass) {
        if(rowST instanceof SingleData){
            SingleData singleData = (SingleData) rowST;
            generateSingleCuartetas(admiMemory, internalCuartetas, temporals, pass, singleData);
        } else {
            //declarar array
            throw new RuntimeException("se quiere declarar un array");
        }
        
    }
    
    private void generateSingleCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, 
            Memory temporals, C3Dpass pass, SingleData singleData){
        int tempCount = temporals.getIntegerCount();
        temporals.setIntegerCount(tempCount + 1);
        if(value != null){
            MemoryAccess access = expGenC3D.getAccess(
                    value, admiMemory, internalCuartetas, temporals, new C3Dpass()
            );
            internalCuartetas.add(
                    new OperationC3D(
                            new RegisterUse(Register.BX_INT), 
                            new StackPtrUse(), 
                            new AtomicValue(singleData.getRelativeDir()), 
                            DefiniteOperation.Addition
                    )
            );
            internalCuartetas.add(
                    new AssignationC3D(
                            new StackAccess(primType, new RegisterUse(Register.BX_INT)),
                            access
                    )
            );
        } else {
            internalCuartetas.add(
                    new OperationC3D(
                            new RegisterUse(Register.BX_INT), 
                            new StackPtrUse(), 
                            new AtomicValue(singleData.getRelativeDir()), 
                            DefiniteOperation.Addition
                    )
            );
            internalCuartetas.add(
                    new AssignationC3D(
                            new StackAccess(primType, new RegisterUse(Register.BX_INT)),
                            new AtomicValue(primType.getDefaultVal())
                    )
            );
        }
    }
    
}
