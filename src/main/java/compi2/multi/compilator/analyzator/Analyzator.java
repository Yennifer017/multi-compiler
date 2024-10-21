
package compi2.multi.compilator.analyzator;

import compi2.multi.compilator.analysis.Lexer;
import compi2.multi.compilator.analysis.Parser;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.semantic.pmodule.FunctionDec;
import compi2.multi.compilator.semantic.pmodule.ProcedureDec;
import compi2.multi.compilator.semantic.Statement;
import compi2.multi.compilator.semantic.DefAst;
import compi2.multi.compilator.semantic.c.CMain;
import compi2.multi.compilator.semantic.jclases.JClass;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class Analyzator {
    
    public static final String ERROR_TYPE = "--error--";
    public static final String FUNCTION_SEPARATOR = "#";
    public static final String VOID_METHOD = "__no return__";
    
    private List<String> semanticErrors;   
    private GenTypeTab genTypeTab;
    private GenSymbolTab genSymbolTab;
    
    private SymbolTable pascalSymbolTable;
    private JSymbolTable javaSymbolTable;
    private SymbolTable cSymbolTable;
    private TypeTable typeTable;
    
    private List<FunctionDec> functionsGlobal; 
    private List<ProcedureDec> proceduresGlobal;
    private List<Statement> statementsGlobal;
    
    
    public Analyzator(){
        semanticErrors = new ArrayList<>();
        genTypeTab = new GenTypeTab();
        genSymbolTab = new GenSymbolTab();
        typeTable = new TypeTable(true);
    }
    
    /**
     * Comprueba el codigo generado por el usuario
     * @param pathToSaveImg
     * @param text
     * @return 
     */
    public String comprobate(String pathToSaveImg, String text){
        StringBuilder builder =  new StringBuilder();
        Lexer lexer = new Lexer(new StringReader(text));
        Parser parser = new Parser(lexer, this);
        semanticErrors.clear();
        try {
            parser.parse();
            builder.append(getErrors("ERRORES LEXICOS", lexer.getErrors()));
            builder.append(getErrors("ERRORES SINTACTICOS", parser.getSyntaxErrors()));
            builder.append(getErrors("ERRORES SEMANTICOS", semanticErrors));
            
            if(lexer.getErrors().isEmpty() 
                    && parser.getSyntaxErrors().isEmpty() 
                    && semanticErrors.isEmpty()){
                
                
            } else {
                resetGlobalDec();
            }
        } catch (Exception e) {
            builder.append("Error inesperado:\n");
            builder.append(e);
            e.printStackTrace();
        }
        return builder.toString();
    }
    
    /**
     * Comprueba el codigo generado por el usuario
     * @param text
     * @return 
     */
    public String comprobate(String text){
        return comprobate("", text);
    }
    
    private String getErrors(String title, List<String> errorsList){
        StringBuilder builder = new StringBuilder(title);
        builder.append("\n");
        builder.append("---------------------------------------");
        builder.append("\n");
        if(errorsList.isEmpty()){
            builder.append("No hay errores\n\n");
        } else {
            for (String error : errorsList) {
                builder.append(error);
                builder.append("\n");
            }
        }
        return builder.toString();
    }
    
    private void resetGlobalDec(){
        this.functionsGlobal = null;
        this.proceduresGlobal = null;
        this.statementsGlobal = null;
    }
    
    public void pascalSemanticAnalysis(List<DefAst> definitions){
        this.pascalSymbolTable =  new SymbolTable();
        genSymbolTab.addPascalData(
                pascalSymbolTable, typeTable, definitions, semanticErrors
        );
    }
    
    public void javaSemanticAnalysis(List<JClass> classes){
        javaSymbolTable = new JSymbolTable();
        genSymbolTab.addJavaClases(javaSymbolTable, typeTable, classes, semanticErrors);
        genSymbolTab.addJavaInternalClasses(
                javaSymbolTable, typeTable, classes, semanticErrors
        );
        genSymbolTab.internajJavaSemanticValitations(
                javaSymbolTable, typeTable, classes, semanticErrors
        );
        System.out.println("fin");
    }
    
    public void mainCSemanticAnalysis(CMain cmain){
        cSymbolTable = new SymbolTable();
    }
    
    /**
     *  Valida el codigo generado, a partir de los ast
     * @param types
     * @param consts
     * @param variables
     * @param functions
     * @param procedures
     * @param statements
     */
    public void semanticAnalysis(List<DefAst> types, List<DefAst> consts, List<DefAst> variables, 
            List<FunctionDec> functions, List<ProcedureDec> procedures, List<Statement> statements){
        /*genSymbolTab.addData(symbolTable, typeTable, consts, semanticErrors);
        genSymbolTab.addData(symbolTable, typeTable, variables, semanticErrors);
        genSymbolTab.addData(symbolTable, typeTable, functions, semanticErrors);
        genSymbolTab.addData(symbolTable, typeTable, procedures, semanticErrors);
        StmtsAnalizator stmtsAnalizator = new StmtsAnalizator();
        stmtsAnalizator.validateInternalStmts(
                symbolTable, 
                typeTable, 
                semanticErrors, 
                new SemanticRestrictions(
                        false, 
                        false, 
                        null, 
                        null
                ),
                statements
        );
        this.functionsGlobal = functions;
        this.proceduresGlobal = procedures;
        this.statementsGlobal = statements;
        System.out.println("Realizar el analisis semantico");*/
    }
    
    
}
