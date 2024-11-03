
package compi2.multi.compilator.analyzator;


import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.ClassST;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.semantic.pmodule.ModuleDec;
import compi2.multi.compilator.semantic.DefAst;
import compi2.multi.compilator.semantic.c.CDef;
import compi2.multi.compilator.semantic.c.CImports;
import compi2.multi.compilator.semantic.c.CStatement;
import compi2.multi.compilator.semantic.jclases.JClass;
import compi2.multi.compilator.semantic.util.SemanticRestrictions;
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
    
    public void addCData(CImports imports, JSymbolTable clasesST, 
            SymbolTable symbolTable, SymbolTable pascalST, TypeTable typeTable, 
            List<? extends CDef> definitions, List<String> semanticErrors){
        if(definitions != null && !definitions.isEmpty()){
            for (CDef definition : definitions) {
                try {
                    RowST rowST = definition.generateRowST(
                            imports, clasesST, symbolTable, pascalST, typeTable, semanticErrors
                    );
                    if(rowST != null){
                        symbolTable.put(rowST.getName(), rowST);
                    }
                } catch (NullPointerException e){
                    System.out.println(e);
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void validateMainStmts(List<CStatement> stmts, 
            CImports imports, JSymbolTable clasesST, 
            SymbolTable symbolTable, SymbolTable pascalST, TypeTable typeTable, 
            List<? extends CDef> definitions, List<String> semanticErrors){
        if(stmts != null && !stmts.isEmpty()){
            try {
                for (CStatement stmt : stmts) {
                    stmt.validate(
                            imports, 
                            clasesST, 
                            symbolTable, 
                            pascalST, 
                            typeTable, 
                            semanticErrors, 
                            new SemanticRestrictions(
                                    false,
                                    false, 
                                    Analyzator.VOID_METHOD
                            )
                    );
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
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
    
    
    public void addJavaClases(JSymbolTable symbolTable, TypeTable typeTable,
            List<JClass> classes, List<String> semanticErrors){
        if(classes != null && !classes.isEmpty()){
            for (JClass clase : classes) {
                try {
                    RowST rowST = clase.generateRowST(symbolTable, null, typeTable, semanticErrors);
                    if(rowST != null){
                        symbolTable.put(rowST.getName(), (ClassST) rowST);
                    }
                } catch (NullPointerException e) {
                    System.out.println(e);
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void addJavaInternalClasses(JSymbolTable symbolTable, TypeTable typeTable,
            List<JClass> classes, List<String> semanticErrors){
        if(classes != null && !classes.isEmpty()){
            for (JClass clase : classes) {
                try {
                    clase.completeFieldsAndMethods(symbolTable, typeTable, semanticErrors);
                } catch (NullPointerException e) {
                    System.out.println(e);
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void internalJavaSemanticValitations(JSymbolTable symbolTable, TypeTable typeTable,
            List<JClass> classes, List<String> semanticErrors){
        if(classes != null && !classes.isEmpty()){
            for (JClass clase : classes) {
                try {
                    clase.validateInternal(symbolTable, typeTable, semanticErrors);
                } catch (NullPointerException e) {
                    System.out.println(e);
                    e.printStackTrace();
                }
            }
        }
    }
    
}
