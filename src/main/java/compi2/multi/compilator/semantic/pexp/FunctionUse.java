
package compi2.multi.compilator.semantic.pexp;

import compi2.multi.compilator.analysis.symbolt.InfParam;
import compi2.multi.compilator.semantic.p.Expression;
import compi2.multi.compilator.analysis.symbolt.estruc.FunctionST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.analyzator.Analyzator;
import compi2.multi.compilator.analyzator.FunctionRefAnalyzator;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.c3d.util.RetParamsC3D;
import compi2.multi.compilator.semantic.util.Label;
import compi2.multi.compilator.util.Position;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class FunctionUse extends Expression{
    private String functionName;
    private List<Expression> params;
    private FunctionRefAnalyzator refFunc;
    
    private FunctionST functionST;

    public FunctionUse(String functionName, List<Expression> params, Position pos) {
        super();
        this.functionName = functionName;
        this.params = params;
        super.pos = pos;
        this.refFunc = new FunctionRefAnalyzator();
    }
    
    public FunctionUse(String functionName, Position pos){
        this.functionName = functionName;
        this.params = new ArrayList<>();
        super.pos = pos;
        this.refFunc = new FunctionRefAnalyzator();
    }

    @Override
    public Label validateSimpleData(SymbolTable symbolTable, List<String> semanticErrors) {
        semanticErrors.add(errorsRep.ilegalUseError(functionName, pos));
        for (int i = 0; i < params.size(); i++) {
            Expression param = params.get(i);
            param.validateSimpleData(symbolTable, semanticErrors);
        }
        return new Label(Analyzator.ERROR_TYPE, pos);
    }

    @Override
    public Label validateComplexData(SymbolTable symbolTable, TypeTable typeTable, 
            List<String> semanticErrors) {
        List<InfParam> argsStringList = refFunc.validateArgs(
                params, symbolTable, typeTable, semanticErrors
        );
        functionST = refFunc.existReference(
                new Label(functionName, pos), 
                symbolTable, 
                typeTable, 
                semanticErrors, argsStringList);
        if( functionST != null ) {
            return new Label(
                    refFunc.getTypeReturnFun(
                            new Label(functionName, pos), 
                            symbolTable, typeTable, argsStringList
                    ),
                    pos
            );
        }
        return new Label(Analyzator.ERROR_TYPE, pos);
    }

    @Override
    public RetParamsC3D generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, Memory temporals, C3Dpass pass) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
}
