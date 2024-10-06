
package compi2.multi.compilator.semantic.pmodule;


import compi2.multi.compilator.analysis.symbolt.FunctionST;
import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.semantic.util.ReturnCase;
import compi2.multi.compilator.semantic.util.SemanticRestrictions;
import compi2.multi.compilator.semantic.Statement;
import compi2.multi.compilator.semantic.DefAst;
import compi2.multi.compilator.semantic.util.Label;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class FunctionDec extends ModuleDec{
    
    private Label varType;
    
    public FunctionDec(Label name, Label varType, List<Argument> args, 
            List<DefAst> varDef, List<Statement> statements){
        super();
        super.name = name;
        this.varType = varType;
        super.args = args;
        super.statements = statements;
        super.varDef = varDef;
    }


    @Override
    public RowST generateRowST(SymbolTable symbolTable, TypeTable typeTable, List<String> semanticErrors) {        
        SymbolTable internalST = genSymbolTab.generateInternalTable(
                symbolTable, typeTable, args, semanticErrors
        );
        refAnalyzator.existReference(typeTable, semanticErrors, varType);
        
        //valida si la funcion no esta definida
        List<String> argsStringList = super.generateArgsStringList();
        String nameForST = super.getNameFunctionForST(symbolTable, argsStringList);
        if(nameForST != null){
            functionST = new FunctionST(
                    nameForST, 
                    varType.getName(), 
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
        ReturnCase returnCase = stmtsAnalizator.validateInternalStmts(
                internalST, 
                typeTable, 
                semanticErrors, 
                new SemanticRestrictions(
                        false, 
                        false, 
                        varType.getName(), 
                        name.getName()
                ),
                statements
        );
        if(!returnCase.isAllScenaries()){
            semanticErrors.add(errorsRep.missingReturnError(
                    this.name.getName(), 
                    varType.getName(), 
                    this.name.getPosition())
            );
        }
    }
    
    
    
}
