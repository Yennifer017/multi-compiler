
package compi2.multi.compilator.semantic.jast.others;

import compi2.multi.compilator.analysis.jerarquia.NodeJerarTree;
import compi2.multi.compilator.analysis.symbolt.Category;
import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.symbolt.estruc.JArrayST;
import compi2.multi.compilator.analysis.symbolt.estruc.SingleData;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.semantic.j.JExpression;
import compi2.multi.compilator.semantic.j.JStatement;
import compi2.multi.compilator.semantic.jclases.components.JArrayType;
import compi2.multi.compilator.semantic.jclases.components.JType;
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

    public JDeclaration(Position initPos, String name, JType type) {
        super(initPos);
        this.name = name;
        this.type = type;
    }
    
    public JDeclaration(Position initPos, String name, JType type, JExpression value){
        super(initPos);
        this.name = name;
        this.type = type;
        this.value = value;
    }

    @Override
    public ReturnCase validate(JSymbolTable globalST, SymbolTable symbolTable, 
            TypeTable typeTable, NodeJerarTree jerar, List<String> semanticErrors, 
            SemanticRestrictions restrictions) {
        if(canInsert(symbolTable, semanticErrors)){
            if(type instanceof JArrayType){
                rowST = new JArrayST(
                        name, 
                        type.getName().getName(), 
                        type.getArrayDimensions(), 
                        symbolTable.getLastDir()
                );
                symbolTable.incrementLastDir(1);
                symbolTable.put(name, rowST);
            } else {
                rowST = new SingleData(
                        name, 
                        Category.Variable, 
                        type.getName().getName(), 
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
    public void generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, Memory temporals, C3Dpass pass) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
