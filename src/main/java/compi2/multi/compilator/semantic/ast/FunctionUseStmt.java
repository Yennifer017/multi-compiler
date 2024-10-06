
package compi2.multi.compilator.semantic.ast;


import compi2.multi.compilator.semantic.Statement;
import compi2.multi.compilator.analysis.symbolt.FunctionST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.analyzator.FunctionRefAnalyzator;
import compi2.multi.compilator.semantic.ReturnCase;
import compi2.multi.compilator.semantic.SemanticRestrictions;
import compi2.multi.compilator.semantic.exp.Expression;
import compi2.multi.compilator.semantic.obj.Label;
import compi2.multi.compilator.util.Position;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class FunctionUseStmt extends Statement{
    
    private final static FunctionST SPECIAL_FUNTION = new FunctionST(
            "writeln/readln", null, null);
    
    private boolean isSpecialFun;
    private Label nameFun;
    private List<Expression> args;
    private FunctionRefAnalyzator refFun;
    
    private FunctionST functionST;

    public FunctionUseStmt(Label nameFun, List<Expression> arguments) {
        super(nameFun.getPosition());
        this.nameFun = nameFun;
        this.args = arguments;
        isSpecialFun = false;
        refFun = new FunctionRefAnalyzator();
    }
    
    public FunctionUseStmt(List<Expression> arguments, Position initPos){
        super(initPos);
        this.args = arguments;
        this.isSpecialFun = true;
        refFun = new FunctionRefAnalyzator();
    }

    @Override
    public ReturnCase validate(SymbolTable symbolTable, TypeTable typeTable, 
            List<String> semanticErrors, SemanticRestrictions restrictions) {
        if(isSpecialFun){
            functionST = SPECIAL_FUNTION;
            refFun.validateArgs(this.args, symbolTable, typeTable, semanticErrors);
        } else {
            List<String> argsStringList = refFun.validateArgs(
                    this.args, symbolTable, typeTable, semanticErrors
            );
            functionST = refFun.existReference(
                    nameFun, symbolTable, typeTable, semanticErrors, argsStringList
            );
        }
        return new ReturnCase(false);
    }

    
}
