
package compi2.multi.compilator.semantic.jast.others;

import compi2.multi.compilator.analysis.jerarquia.NodeJerarTree;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.c3d.generators.ExpGenC3D;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.generators.AccessGenC3D;
import compi2.multi.compilator.c3d.generators.InvocationsC3DGen;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.semantic.j.JExpression;
import compi2.multi.compilator.semantic.j.JStatement;
import compi2.multi.compilator.semantic.jast.inv.InvocationsUtil;
import compi2.multi.compilator.semantic.jast.inv.JContextRef;
import compi2.multi.compilator.semantic.jast.inv.JInvocation;
import compi2.multi.compilator.semantic.util.Label;
import compi2.multi.compilator.semantic.util.ReturnCase;
import compi2.multi.compilator.semantic.util.SemanticRestrictions;
import compi2.multi.compilator.util.Position;
import java.util.LinkedList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class JAssignAst extends JStatement{
    
    private List<JInvocation> variable;
    private JExpression value;
    
    private PrimitiveType primType;
    private int instanceRef;
    
    private ExpGenC3D expGenC3D;
    private AccessGenC3D accessGenC3D;
    private InvocationsUtil invsUtil;
    private InvocationsC3DGen invC3DGen;
    
    public JAssignAst(Position initPos, List<JInvocation> variable, JExpression value) {
        super(initPos);
        this.variable = variable;
        this.value = value;
        this.expGenC3D = new ExpGenC3D();
        this.accessGenC3D = new AccessGenC3D();
        this.invsUtil = new InvocationsUtil();
        this.invC3DGen = new InvocationsC3DGen();
    }
    
    public JAssignAst(Position initPos, List<JInvocation> variable, 
            JExpression value, JContextRef first) {
        super(initPos);
        this.expGenC3D = new ExpGenC3D();
        this.accessGenC3D = new AccessGenC3D();
        this.invsUtil = new InvocationsUtil();
        this.invC3DGen = new InvocationsC3DGen();
        try {
            variable.get(0).setContext(first);
            this.variable = variable;
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            this.variable = new LinkedList<>();
        }
        this.value = value;
    }

    @Override
    public ReturnCase validate(JSymbolTable globalST, SymbolTable symbolTable, 
            TypeTable typeTable, NodeJerarTree jerar, List<String> semanticErrors, 
            SemanticRestrictions restrictions) {
        Label typeValue = value.validateData(
                globalST, symbolTable, typeTable, jerar, semanticErrors, restrictions
        );
        Label typeVar = invsUtil.validateInvocation(
                globalST, 
                symbolTable, 
                typeTable, 
                jerar, 
                semanticErrors, 
                variable, 
                initPos, 
                true,
                true
        );
        this.instanceRef = super.refAnalyzator.findInstanceRef(symbolTable);
        primType = super.tConvert.convertAllPrimitive(typeVar.getName());
        
        if(!typeVar.getName().equals(typeValue.getName())
                && !tConvert.canUpgradeType(typeVar.getName(),
                                    typeValue.getName()) ){
            semanticErrors.add(errorsRep.incorrectTypeError(
                    typeValue.getName(), 
                    typeVar.getName(), 
                    initPos)
            );
        }
        return new ReturnCase(false);
    }
    

    @Override
    public void generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, 
            Memory temporals, C3Dpass pass) {
        this.invC3DGen.generateCuartetasAssign(
                admiMemory, internalCuartetas, temporals, pass, variable, primType, value, instanceRef
        );
    }
    
}
