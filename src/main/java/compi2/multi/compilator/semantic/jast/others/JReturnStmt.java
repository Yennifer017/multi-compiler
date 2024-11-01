package compi2.multi.compilator.semantic.jast.others;

import compi2.multi.compilator.analysis.jerarquia.NodeJerarTree;
import compi2.multi.compilator.analysis.symbolt.AdditionalInfoST;
import compi2.multi.compilator.analysis.symbolt.ReturnRow;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.access.AtomicValue;
import compi2.multi.compilator.c3d.access.MemoryAccess;
import compi2.multi.compilator.c3d.access.RegisterUse;
import compi2.multi.compilator.c3d.access.StackAccess;
import compi2.multi.compilator.c3d.access.StackPtrUse;
import compi2.multi.compilator.c3d.cuartetas.AssignationC3D;
import compi2.multi.compilator.c3d.cuartetas.GotoC3D;
import compi2.multi.compilator.c3d.cuartetas.OperationC3D;
import compi2.multi.compilator.c3d.generators.AccessGenC3D;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.c3d.util.Register;
import compi2.multi.compilator.semantic.DefiniteOperation;
import compi2.multi.compilator.semantic.j.JExpression;
import compi2.multi.compilator.semantic.j.JStatement;
import compi2.multi.compilator.semantic.util.Label;
import compi2.multi.compilator.semantic.util.ReturnCase;
import compi2.multi.compilator.semantic.util.SemanticRestrictions;
import compi2.multi.compilator.util.Position;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class JReturnStmt extends JStatement {

    private JExpression passExp;
    
    private ReturnRow retRowSt;
    private PrimitiveType type;
    
    private AccessGenC3D accessGenC3D;

    public JReturnStmt(Position initPos, JExpression passExp) {
        super(initPos);
        this.passExp = passExp;
        this.accessGenC3D = new AccessGenC3D();
    }

    @Override
    public ReturnCase validate(JSymbolTable globalST, SymbolTable symbolTable,
            TypeTable typeTable, NodeJerarTree jerar, List<String> semanticErrors,
            SemanticRestrictions restrictions) {
        Label type = passExp.validateData(globalST, symbolTable, typeTable,
                jerar, semanticErrors, restrictions);
        if (!type.getName().equals(restrictions.getReturnType())
                && !tConvert.canUpgradeType(restrictions.getReturnType(), type.getName())) {
            semanticErrors.add(errorsRep.incorrectTypeError(
                    type.getName(),
                    restrictions.getReturnType(),
                    type.getPosition())
            );
        } else {
            SymbolTable currentST = symbolTable;
            while (currentST != null) {           
                if(currentST.getFather() == null){
                    break;
                }
                currentST = currentST.getFather();
            }
            try {
                this.type = super.tConvert.convertAllPrimitive(restrictions.getReturnType());
                retRowSt = (ReturnRow) currentST.get(AdditionalInfoST.DIR_RETORNO_ROW.getNameRow());
            } catch (ClassCastException | NullPointerException e) {
                System.out.println(e);
            }
        }
        return new ReturnCase(true);
    }

    @Override
    public void generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, 
            Memory temporals, C3Dpass pass) {
        MemoryAccess expAccess = accessGenC3D.getAccess(
                passExp, admiMemory, internalCuartetas, temporals, pass
        );
        //seteando el retorno
        internalCuartetas.add(
                new OperationC3D(
                        new RegisterUse(Register.BX_INT), 
                        new StackPtrUse(), 
                        new AtomicValue(retRowSt.getRelativeDir()), 
                        DefiniteOperation.Addition
                )
        );
        internalCuartetas.add(
                new AssignationC3D(
                        new StackAccess(
                                type, 
                                new RegisterUse(Register.BX_INT)
                        ), 
                        expAccess
                )
        );
        //finalizando el metodo
        internalCuartetas.add(
                new GotoC3D(pass.getEndMethod())
        );
    }

}
