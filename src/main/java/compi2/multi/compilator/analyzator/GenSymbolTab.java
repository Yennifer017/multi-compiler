
package compi2.multi.compilator.analyzator;


import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.semantic.pmodule.ModuleDec;
import compi2.multi.compilator.semantic.DefAst;
import compi2.multi.compilator.semantic.jclases.JClass;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class GenSymbolTab extends Generator{
    public void addPascalData(SymbolTable symbolTable, TypeTable typeTable, List<? extends DefAst> listDef, 
            List<String> semanticErrors){
        if(listDef != null && !listDef.isEmpty()){
            for (DefAst def : listDef) {
                try {
                    RowST rowST = def.generateRowST(symbolTable, typeTable, semanticErrors);
                    if(rowST != null){
                        symbolTable.put(rowST.getName(), rowST);
                    }
                    if(def instanceof ModuleDec modDec){
                        modDec.validate(typeTable, semanticErrors);
                    }
                } catch (NullPointerException e) {
                    System.out.println(e);
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void addJavaClases(SymbolTable symbolTable, TypeTable typeTable,
            List<JClass> classes, List<String> semanticErrors){
        if(classes != null && !classes.isEmpty()){
            for (JClass clase : classes) {
                
            }
        }
    }
    
    public SymbolTable generateInternalTable(SymbolTable symbolTable, TypeTable typeTable,
            List<? extends DefAst> listDef, List<String> semanticErrors){
        SymbolTable internalTable = new SymbolTable();
        internalTable.setFather(symbolTable);
        this.addPascalData(internalTable, typeTable, listDef, semanticErrors);
        return internalTable;
    }
    
    public SymbolTable generateInternalTable(){
        SymbolTable internalTable = new SymbolTable();
        return internalTable;
    }
    
    
}
