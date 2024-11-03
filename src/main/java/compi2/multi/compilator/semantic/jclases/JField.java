
package compi2.multi.compilator.semantic.jclases;

import compi2.multi.compilator.semantic.jclases.components.JType;
import compi2.multi.compilator.semantic.jclases.components.Typable;
import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.FieldST;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.analysis.typet.convert.TConvertidor;
import compi2.multi.compilator.analyzator.Analyzator;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.access.AtomicValue;
import compi2.multi.compilator.c3d.access.HeapAccess;
import compi2.multi.compilator.c3d.access.MemoryAccess;
import compi2.multi.compilator.c3d.access.RegisterUse;
import compi2.multi.compilator.c3d.access.TemporalUse;
import compi2.multi.compilator.c3d.cuartetas.AssignationC3D;
import compi2.multi.compilator.c3d.cuartetas.OperationC3D;
import compi2.multi.compilator.c3d.generators.AccessGenC3D;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.c3d.util.Register;
import compi2.multi.compilator.exceptions.ConvPrimitiveException;
import compi2.multi.compilator.semantic.DefiniteOperation;
import compi2.multi.compilator.semantic.j.JExpression;
import compi2.multi.compilator.semantic.util.Label;
import compi2.multi.compilator.semantic.util.SemanticRestrictions;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class JField extends JDef implements Typable{
    
    private JExpression expAssign;
    private JType type;
    
    private SymbolTable symbolTable;
    private FieldST fieldST;
    
    private AccessGenC3D accessGenC3D;
    private TConvertidor tConvertidor;
    
    public JField(){
        super(null);
        this.accessGenC3D = new AccessGenC3D();
        this.tConvertidor =  new TConvertidor();
    }

    @Override
    public RowST generateRowST(SymbolTable symbolTable, TypeTable typeTable, 
            List<String> semanticErrors) {
        String nameForST = getNameFunctionForST(symbolTable);
        if(nameForST != null){
            int dir = symbolTable.getLastDir();
            symbolTable.incrementLastDir(1);
            this.fieldST = new FieldST(
                    name.getName(), 
                    type.getName().getName(), 
                    access, 
                    dir, 
                    this
            );
            return fieldST;
        } else {
            semanticErrors.add(
                super.errorsRep.repeatedDeclarationError(
                        name.getName(), name.getPosition()
                )
            );
        }
        return null;
    }

    @Override
    public void defineType(JType type) {
        this.type = type;
    }
    
    protected String getNameFunctionForST(SymbolTable symbolTable){
        if(symbolTable.containsKey(name.getName())){            
            return null;
        } else {
            return this.name.getName();
        }
    }

    @Override
    public void validateInternal(JSymbolTable globalST, TypeTable typeTable, List<String> semanticErrors) {
        this.type.validateSemantic(globalST, typeTable, semanticErrors, false);
        if(expAssign != null){
            Label typeAssign = expAssign.validateData(globalST, symbolTable, 
                    typeTable, 
                    jerar, 
                    semanticErrors, 
                    new SemanticRestrictions(false, false, "")
            );
            if(!typeAssign.getName().equals(this.type.getCompleateName())){
                semanticErrors.add(errorsRep.incorrectTypeError(
                        typeAssign.getName(), 
                        this.type.getCompleateName(), 
                        typeAssign.getPosition())
                );
            }
        }
    }
    
    public void generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, 
            Memory temporals, TemporalUse instanceRefTemp){
        int countTemp = temporals.getIntegerCount();
        temporals.setIntegerCount(countTemp + 1);
        RegisterUse axIntRegister = new RegisterUse(Register.AX_INT);
        RegisterUse bxIntRegister = new RegisterUse(Register.BX_INT);
        if(expAssign != null){
            PrimitiveType primType = this.tConvertidor.convertAllPrimitive(
                    this.type.getName().getName()
            );
            
            internalCuartetas.add(
                    new AssignationC3D(
                            axIntRegister, 
                            instanceRefTemp
                    )
            );
            internalCuartetas.add(
                    new OperationC3D(
                            bxIntRegister, 
                            axIntRegister, 
                            new AtomicValue(this.fieldST.getRelativeDir()), 
                            DefiniteOperation.Addition
                    )
            );
            internalCuartetas.add(
                    new AssignationC3D(
                            new TemporalUse(PrimitiveType.IntegerPT, countTemp, temporals), 
                            bxIntRegister
                    )
            );
            
            //recuperando lo necesario para la asignacion
            internalCuartetas.add(
                    new AssignationC3D(
                            bxIntRegister, 
                            new TemporalUse(PrimitiveType.IntegerPT, countTemp, temporals)
                    )
            );
            MemoryAccess accessExp = accessGenC3D.getAccess(
                    expAssign, admiMemory, internalCuartetas, temporals, new C3Dpass()
            );
            internalCuartetas.add(
                    new AssignationC3D(
                            new HeapAccess(primType, bxIntRegister), 
                            accessExp
                    )
            );
        } else { //asignar un valor por defecto
            Object defaultVal;
            PrimitiveType primType;
            try {
                primType = this.tConvertidor.convertPrimitive(
                        this.type.getName().getName()
                );
                defaultVal = primType.getDefaultVal();
            } catch (ConvPrimitiveException e) {
                defaultVal = Analyzator.NULL_REFERENCE;
                primType = PrimitiveType.IntegerPT;
            }
            
            internalCuartetas.add(
                    new AssignationC3D(
                            axIntRegister, 
                            instanceRefTemp
                    )
            );
            internalCuartetas.add(
                    new OperationC3D(
                            bxIntRegister, 
                            axIntRegister, 
                            new AtomicValue(this.fieldST.getRelativeDir()), 
                            DefiniteOperation.Addition
                    )
            );
            internalCuartetas.add(
                    new AssignationC3D(
                            new TemporalUse(PrimitiveType.IntegerPT, countTemp, temporals), 
                            bxIntRegister
                    )
            );
            //recuperando lo necesario para la asignacion
            internalCuartetas.add(
                    new AssignationC3D(
                            axIntRegister, 
                            new TemporalUse(PrimitiveType.IntegerPT, countTemp, temporals)
                    )
            );
            internalCuartetas.add(
                    new AssignationC3D(
                            new HeapAccess(primType, axIntRegister), 
                            new AtomicValue(defaultVal)
                    )
            );
        }
    }
    
}
