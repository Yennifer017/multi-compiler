package compi2.multi.compilator.semantic.pexp;

import compi2.multi.compilator.analysis.symbolt.Category;
import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.estruc.SingleData;
import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.analyzator.Analyzator;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.access.RegisterUse;
import compi2.multi.compilator.c3d.access.StackAccess;
import compi2.multi.compilator.c3d.access.TemporalUse;
import compi2.multi.compilator.c3d.cuartetas.AssignationC3D;
import compi2.multi.compilator.c3d.util.AdmiRegisters;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.c3d.util.Register;
import compi2.multi.compilator.c3d.util.RetParamsC3D;
import compi2.multi.compilator.exceptions.ConvPrimitiveException;
import compi2.multi.compilator.semantic.Expression;
import compi2.multi.compilator.semantic.util.Label;
import compi2.multi.compilator.util.Position;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class PVarUse extends Expression {

    private String accessId;

    private PrimitiveType type;
    private SingleData singleData;
    private AdmiRegisters admiRegisters;

    public PVarUse(String accessId, Position pos) {
        this.accessId = accessId;
        super.pos = pos;
        this.admiRegisters = new AdmiRegisters();
    }

    @Override
    public Label validateSimpleData(SymbolTable symbolTable, List<String> semanticErrors) {
        try {
            if (refAnalyzator.existReference(symbolTable, semanticErrors,
                    new Label(accessId, this.pos))) {
                RowST row = refAnalyzator.getFromST(symbolTable, accessId);

                String typeInST = row.getType();
                if (row instanceof SingleData) {
                    singleData = (SingleData) row;
                    try {
                        type = tConvert.convertPrimitive(typeInST);
                    } catch (ConvPrimitiveException e) {
                        //add error
                    }
                } else {
                    semanticErrors.add(super.errorsRep.invalidCategoryAccessError(
                            row.getName(),
                            row.getCategory().getName(),
                            pos)
                    );
                    typeInST = Analyzator.ERROR_TYPE;
                }

                if (row.getCategory() == Category.Constant) {
                    return new Label(row.getType(), pos);
                }
            }
        } catch (NullPointerException e) {
        }
        semanticErrors.add(errorsRep.ilegalUseError(accessId, pos));
        return new Label(Analyzator.ERROR_TYPE, pos);

    }

    @Override
    public Label validateComplexData(SymbolTable symbolTable, TypeTable typeTable,
            List<String> semanticErrors) {
        if (refAnalyzator.existReference(symbolTable, semanticErrors,
                new Label(accessId, this.pos))) {

            RowST row = refAnalyzator.getFromST(symbolTable, accessId);
            String typeInST = row.getType();

            if (row instanceof SingleData) {
                singleData = (SingleData) row;
                try {
                    type = tConvert.convertPrimitive(typeInST);
                } catch (ConvPrimitiveException e) {
                    //add error
                }
            } else {
                semanticErrors.add(super.errorsRep.invalidCategoryAccessError(
                        row.getName(),
                        row.getCategory().getName(),
                        pos)
                );
                typeInST = Analyzator.ERROR_TYPE;
            }

            if (typeInST == null) { //no tiene un tipo especifico
                semanticErrors.add(super.errorsRep.invalidCategoryAccessError(
                        row.getName(),
                        row.getCategory().getName(),
                        pos)
                );
                typeInST = Analyzator.ERROR_TYPE;
            }
            return new Label(typeInST, pos);
        } else if (accessId != null) {
            semanticErrors.add(errorsRep.undefiniteVarUseError(accessId, pos));
        }
        return new Label(Analyzator.ERROR_TYPE, pos);
    }

    @Override
    public RetParamsC3D generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, Memory temporals, C3Dpass pass) {
        int count = temporals.getCount(type);
        temporals.increment(type, 1);
        Register register = admiRegisters.findRegister(type, 1);
        internalCuartetas.add(
                new AssignationC3D(
                        new RegisterUse(register),
                        new StackAccess(type, singleData.getRelativeDir())
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
