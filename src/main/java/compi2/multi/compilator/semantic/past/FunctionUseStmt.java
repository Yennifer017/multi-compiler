
package compi2.multi.compilator.semantic.past;


import compi2.multi.compilator.analysis.symbolt.InfParam;
import compi2.multi.compilator.semantic.Statement;
import compi2.multi.compilator.analysis.symbolt.estruc.FunctionST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.analyzator.FunctionRefAnalyzator;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.semantic.util.ReturnCase;
import compi2.multi.compilator.semantic.util.SemanticRestrictions;
import compi2.multi.compilator.semantic.Expression;
import compi2.multi.compilator.semantic.util.Label;
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
            List<InfParam> argsStringList = refFun.validateArgs(
                    this.args, symbolTable, typeTable, semanticErrors
            );
            functionST = refFun.existReference(
                    nameFun, symbolTable, typeTable, semanticErrors, argsStringList
            );
        }
        return new ReturnCase(false);
    }

    @Override
    public void generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, Memory temporals, C3Dpass pass) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
}
