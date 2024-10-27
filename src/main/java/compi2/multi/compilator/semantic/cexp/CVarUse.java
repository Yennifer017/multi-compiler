package compi2.multi.compilator.semantic.cexp;

import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.symbolt.estruc.SingleData;
import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.analyzator.Analyzator;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.access.AtomicValue;
import compi2.multi.compilator.c3d.access.RegisterUse;
import compi2.multi.compilator.c3d.access.StackAccess;
import compi2.multi.compilator.c3d.access.StackPtrUse;
import compi2.multi.compilator.c3d.access.TemporalUse;
import compi2.multi.compilator.c3d.cuartetas.AssignationC3D;
import compi2.multi.compilator.c3d.cuartetas.OperationC3D;
import compi2.multi.compilator.c3d.util.AdmiRegisters;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.c3d.util.Register;
import compi2.multi.compilator.c3d.util.RetParamsC3D;
import compi2.multi.compilator.exceptions.ConvPrimitiveException;
import compi2.multi.compilator.semantic.DefiniteOperation;
import compi2.multi.compilator.semantic.c.CExp;
import compi2.multi.compilator.semantic.c.CImports;
import compi2.multi.compilator.semantic.util.Label;
import compi2.multi.compilator.util.Position;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class CVarUse extends CExp {

    private String name;

    private PrimitiveType type;
    private SingleData singleData;
    private AdmiRegisters admiRegisters;

    public CVarUse(Position pos, String name) {
        super(pos);
        this.name = name;
        this.admiRegisters = new AdmiRegisters();
    }

    @Override
    public Label validateComplexData(CImports imports, JSymbolTable clasesST,
            SymbolTable symbolTable, SymbolTable pascalST, TypeTable typeTable,
            List<String> semanticErrors) {
        if (refAnalyzator.existReference(symbolTable, semanticErrors,
                new Label(name, this.pos))) {
            RowST rowST = refAnalyzator.getFromST(symbolTable, name);
            String typeInST = rowST.getType();
            if (rowST instanceof SingleData) {
                singleData = (SingleData) rowST;
                try {
                    type = tConvert.convertPrimitive(typeInST);
                } catch (ConvPrimitiveException e) {
                    //add error
                }
            } else {
                semanticErrors.add(super.errorsRep.invalidCategoryAccessError(
                        rowST.getName(),
                        rowST.getCategory().getName(),
                        pos)
                );
                typeInST = Analyzator.ERROR_TYPE;
            }
            return new Label(typeInST, pos);
        } else {
            semanticErrors.add(errorsRep.undefiniteVarUseError(name, pos));
        }
        return new Label(Analyzator.ERROR_TYPE, pos);
    }

    @Override
    public Label validateSimpleData(SymbolTable symbolTable, List<String> semanticErrors) {
        semanticErrors.add(errorsRep.ilegalUseError(name, pos));
        return new Label(Analyzator.ERROR_TYPE, pos);
    }

    @Override
    public RetParamsC3D generateCuartetas(AdmiMemory admiMemory,
            List<Cuarteta> internalCuartetas, Memory temporals, C3Dpass pass) {
        int count = temporals.getCount(type);
        temporals.increment(type, 1);
        Register register = admiRegisters.findRegister(type, 2);

        internalCuartetas.add(
                new OperationC3D(
                        new RegisterUse(Register.AX_INT),
                        new StackPtrUse(),
                        new AtomicValue<>(singleData.getRelativeDir()),
                        DefiniteOperation.Addition
                )
        );
        
        internalCuartetas.add(
                new AssignationC3D(
                        new RegisterUse(register),
                        new StackAccess(type, new RegisterUse(Register.AX_INT))
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

}
