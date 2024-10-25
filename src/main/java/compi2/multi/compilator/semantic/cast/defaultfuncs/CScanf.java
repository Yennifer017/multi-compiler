
package compi2.multi.compilator.semantic.cast.defaultfuncs;

import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.symbolt.estruc.SingleData;
import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.access.RegisterUse;
import compi2.multi.compilator.c3d.access.StackAccess;
import compi2.multi.compilator.c3d.cuartetas.AssignationC3D;
import compi2.multi.compilator.c3d.cuartetas.funcs.ScanC3D;
import compi2.multi.compilator.c3d.util.AdmiRegisters;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.c3d.util.Register;
import compi2.multi.compilator.exceptions.ConvPrimitiveException;
import compi2.multi.compilator.semantic.c.CImports;
import compi2.multi.compilator.semantic.c.CStatement;
import compi2.multi.compilator.semantic.util.Label;
import compi2.multi.compilator.semantic.util.ReturnCase;
import compi2.multi.compilator.semantic.util.SemanticRestrictions;
import compi2.multi.compilator.util.Position;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class CScanf extends CStatement{
    
    private Label mascara;
    private Label variable;
    
    private PrimitiveType type;
    private SingleData singleData;
    private AdmiRegisters admiRegisters;
    
    public CScanf(Position initPos, Label mascara, Label variable) {
        super(initPos);
        this.mascara = mascara;
        this.variable = variable;
        this.admiRegisters = new AdmiRegisters();
    }

    @Override
    public ReturnCase validate(CImports imports, JSymbolTable clasesST, 
            SymbolTable symbolTable, SymbolTable pascalST, TypeTable typeTable, 
            List<String> semanticErrors, SemanticRestrictions restrictions) {
        type = inferType(mascara, semanticErrors);
        if(symbolTable.containsKey(this.variable.getName())){
            RowST rowST = symbolTable.get(this.variable.getName());
            if(rowST instanceof SingleData){
                singleData = (SingleData) rowST;
                if(!singleData.getType().equals(type.getName())
                        && tConvert.canUpgradeType(singleData.getType(), type.getName())){
                    semanticErrors.add(errorsRep.incorrectTypeError(
                            type.getName(), singleData.getType(), initPos)
                    );
                } else {
                    try {
                        type = tConvert.convertPrimitive(singleData.getType());
                    } catch (ConvPrimitiveException e) {
                        semanticErrors.add(errorsRep.incorrectTypeError(
                                singleData.getType(), variable.getPosition())
                        );
                    }
                }
            } else {
                semanticErrors.add(errorsRep.undefiniteVarUseError(
                    this.variable.getName(), this.variable.getPosition())
                );
            }
        } else {
            semanticErrors.add(errorsRep.undefiniteVarUseError(
                    this.variable.getName(), this.variable.getPosition())
            );
        }
        return new ReturnCase(false);
    }

    @Override
    public void generateCuartetas(AdmiMemory admiMemory, 
            List<Cuarteta> internalCuartetas, Memory temporals, C3Dpass pass) {
        Register register = admiRegisters.findRegister(type, 1);
        internalCuartetas.add(
                new ScanC3D(new RegisterUse(register))
        );
        internalCuartetas.add(
                new AssignationC3D(
                        new StackAccess(type, singleData.getRelativeDir()), 
                        new RegisterUse(register)
                )
        );
    }
    
    private PrimitiveType inferType(Label mascara, List<String> semanticErrors){
        switch (mascara.getName()) {
            case "%d":
                return PrimitiveType.IntegerPT;
            case "%c":
                return PrimitiveType.CharPT;
            case "%f":
                return PrimitiveType.RealPT;
            case "%s":
                return PrimitiveType.StringPT;
            default:
                semanticErrors.add(errorsRep.mascaraError(mascara.getPosition()));
                return PrimitiveType.IntegerPT;
        }
    }

    
}
