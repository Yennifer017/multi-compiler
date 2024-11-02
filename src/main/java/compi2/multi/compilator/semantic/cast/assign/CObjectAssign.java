
package compi2.multi.compilator.semantic.cast.assign;

import compi2.multi.compilator.analysis.symbolt.Category;
import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.analyzator.RefAnalyzator;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.generators.InvocationsC3DGen;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.semantic.c.CExp;
import compi2.multi.compilator.semantic.c.CImports;
import compi2.multi.compilator.semantic.jast.inv.InvocationsUtil;
import compi2.multi.compilator.semantic.jast.inv.JInvocation;
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
public class CObjectAssign extends CAssign{
    
    private List<JInvocation> jinvocations;
    
    private InvocationsUtil invsUtil;
    private InvocationsC3DGen invC3DGen;
    private RefAnalyzator refAnalyzator;
    
    private int instanceRef;
    private PrimitiveType primType;
    
    public CObjectAssign(Position initPos, CExp expression, List<JInvocation> jInvocations) {
        super(initPos, expression);
        this.invsUtil = new InvocationsUtil();
        this.invC3DGen = new InvocationsC3DGen();
        this.refAnalyzator = new RefAnalyzator();
    }

    @Override
    public ReturnCase validate(CImports imports, JSymbolTable clasesST, SymbolTable symbolTable, 
            SymbolTable pascalST, TypeTable typeTable, List<String> semanticErrors, 
            SemanticRestrictions restrictions) {
        Label typeValue = expression.validateComplexData(
                imports, clasesST, symbolTable, pascalST, typeTable, semanticErrors
        );
        try {
            JInvocation first = jinvocations.get(0);
            this.initPos = first.getInv().getPosition();
            if(symbolTable.containsKey(first.getInv().getName())){
                RowST rowST = symbolTable.get(first.getInv().getName());
                if(rowST.getCategory().equals(Category.JObject)){
                    Label typeVar = invsUtil.validateInvocation(
                            clasesST, 
                            symbolTable, 
                            typeTable, 
                            null, 
                            semanticErrors, 
                            jinvocations, 
                            initPos, 
                            true, 
                            true
                    );
                    this.instanceRef = refAnalyzator.findInstanceRef(symbolTable);
                    primType = super.tConvert.convertAllPrimitive(typeVar.getName());
                    //verificar que sean del mismo tipo
                    if(!typeVar.getName().equals(typeValue.getName())){
                        semanticErrors.add(errorsRep.incorrectTypeError(
                                typeValue.getName(), 
                                typeVar.getName(), 
                                initPos)
                        );
                    }
                } else {
                    semanticErrors.add(
                        super.errorsRep.noObjectVarError(
                                first.getInv().getName(), 
                                first.getInv().getPosition()
                        )
                    );
                }
            } else {
                semanticErrors.add(
                        super.errorsRep.undefiniteVarUseError(
                                first.getInv().getName(), 
                                first.getInv().getPosition()
                        )
                );
            }
        } catch (IndexOutOfBoundsException e) {
            //add error
        }
        return new ReturnCase(false);
    }

    @Override
    public void generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, Memory temporals, C3Dpass pass) {
        this.invC3DGen.generateCuartetasAssign(
                admiMemory, 
                internalCuartetas, 
                temporals, pass, 
                jinvocations, 
                primType, 
                expression, 
                instanceRef
        );
    }
    
}
