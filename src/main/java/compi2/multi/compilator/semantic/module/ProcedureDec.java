
package compi2.multi.compilator.semantic.module;


import compi2.multi.compilator.analysis.symbolt.FunctionST;
import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.semantic.SemanticRestrictions;
import compi2.multi.compilator.semantic.ast.Statement;
import compi2.multi.compilator.semantic.obj.DefAst;
import compi2.multi.compilator.semantic.obj.Label;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class ProcedureDec extends ModuleDec {
    
    public ProcedureDec(Label name, List<Argument> args, 
            List<DefAst> varDef, List<Statement> statements){
        super();
        super.name = name;
        super.args = args;
        super.statements = statements;
        super.varDef = varDef;
    }


    @Override
    public RowST generateRowST(SymbolTable symbolTable, TypeTable typeTable, List<String> semanticErrors) {
        SymbolTable internalST = genSymbolTab.generateInternalTable(
                symbolTable, typeTable, args, semanticErrors
        );
        List<String> argsStringList = super.generateArgsStringList();
        String nameForST = super.getNameFunctionForST(symbolTable, argsStringList);
        if(nameForST != null){
            functionST = new FunctionST(
                    nameForST, 
                    internalST, 
                    argsStringList
            );
            return functionST;
        } else {
            semanticErrors.add(errorsRep.redeclareFunctionError(
                    name.getName(), 
                    argsStringList,
                    name.getPosition())
            );
        }
        return null;
    }

    @Override
    public void validate(TypeTable typeTable, List<String> semanticErrors) {
        SymbolTable internalST;
        if(functionST != null && functionST.getInternalST() != null){
            internalST = functionST.getInternalST();
        } else {
            internalST = new SymbolTable();
        }
        genSymbolTab.addData(internalST, typeTable, varDef, semanticErrors);
        stmtsAnalizator.validateInternalStmts(
                internalST, 
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
    }
    
}
