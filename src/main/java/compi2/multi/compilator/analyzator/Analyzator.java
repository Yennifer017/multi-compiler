
package compi2.multi.compilator.analyzator;

import compi2.multi.compilator.analysis.Lexer;
import compi2.multi.compilator.analysis.Parser;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.semantic.module.FunctionDec;
import compi2.multi.compilator.semantic.module.ProcedureDec;
import compi2.multi.compilator.semantic.SemanticRestrictions;
import compi2.multi.compilator.semantic.ast.Statement;
import compi2.multi.compilator.semantic.obj.DefAst;
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
    
    private SymbolTable symbolTable;
    private TypeTable typeTable;
    
    private List<FunctionDec> functionsGlobal; 
    private List<ProcedureDec> proceduresGlobal;
    private List<Statement> statementsGlobal;
    
    
    public Analyzator(){
        semanticErrors = new ArrayList<>();
        genTypeTab = new GenTypeTab();
        genSymbolTab = new GenSymbolTab();
        
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
        semanticErrors = new ArrayList<>();
        //typeTable = genTypeTab.generateTable(types, semanticErrors);
        typeTable = new TypeTable(true);
        symbolTable = new SymbolTable();
        genSymbolTab.addData(symbolTable, typeTable, consts, semanticErrors);
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
        System.out.println("Realizar el analisis semantico");
    }
    
    
}
