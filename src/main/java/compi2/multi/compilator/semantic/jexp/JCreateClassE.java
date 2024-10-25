
package compi2.multi.compilator.semantic.jexp;

import compi2.multi.compilator.analysis.jerarquia.NodeJerarTree;
import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.ClassST;
import compi2.multi.compilator.analysis.symbolt.clases.ConstructorST;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.analyzator.Analyzator;
import compi2.multi.compilator.analyzator.FunctionRefAnalyzator;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.c3d.util.RetParamsC3D;
import compi2.multi.compilator.semantic.j.JExpression;
import compi2.multi.compilator.semantic.util.Label;
import compi2.multi.compilator.semantic.util.SemanticRestrictions;
import compi2.multi.compilator.util.Position;
import java.util.LinkedList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class JCreateClassE extends JExpression{
    private List<JExpression> params;
    private String name;
    
    private FunctionRefAnalyzator refFun;
    
    private ConstructorST constructorST;
    
    public JCreateClassE(Position pos, String name, List<JExpression> params){
        super(pos);
        this.name = name;
        this.params = params;
    }
    
    public JCreateClassE(Position pos, String name){
        super(pos);
        this.name = name;
        this.params = new LinkedList<>();
    }

    @Override
    public Label validateData(JSymbolTable globalST, SymbolTable symbolTable, 
            TypeTable typeTable, NodeJerarTree jerar, List<String> semanticErrors, 
            SemanticRestrictions restrictions) {
        constructorST = validateExistence(globalST, symbolTable, typeTable, jerar, semanticErrors, restrictions);
        return new Label(name, pos);
    }

    @Override
    public Label validateSimpleData(List<String> semanticErrors) {
        semanticErrors.add(errorsRep.ilegalUseError(name, pos));
        return new Label(Analyzator.ERROR_TYPE, pos);
    }
    
    private ConstructorST validateExistence(JSymbolTable globalST, SymbolTable symbolTable, 
            TypeTable typeTable, NodeJerarTree jerar, List<String> semanticErrors, 
            SemanticRestrictions restrictions){
        if(globalST.containsKey(name)){
            ClassST classST = globalST.get(name);
            SymbolTable st = classST.getMethodsST();
            List<String> paramsList = getTypeParams(
                    globalST, symbolTable, typeTable, jerar, semanticErrors, restrictions
            );
            if(st.containsKey(name)){
                RowST rowST = st.get(name);
                if(rowST instanceof ConstructorST){ //y tiene los mismos parametros
                    ConstructorST construct = (ConstructorST) rowST;
                    if(refFun.hasTheSameArgs(construct.getTypesParams(), paramsList)){
                        return construct;
                    } else {
                        return searchConstruct(st, paramsList, semanticErrors);
                    }
                } else {
                    return searchConstruct(st, paramsList, semanticErrors);
                }
            } else {
                semanticErrors.add(errorsRep.undefiniteConstructorError(pos));
            }
        } else {
            semanticErrors.add(errorsRep.undefiniteConstructorError(pos));
        }
        return null;
    }
    
    private ConstructorST searchConstruct(SymbolTable symbolTable, List<String> args,
            List<String> semanticErrors){
        int index = 1;
        while (symbolTable.containsKey(
                refFun.getSTName(name, index))) {
            RowST anotherRowST = symbolTable.get(refFun.getSTName(this.name, index));
            if (anotherRowST instanceof ConstructorST) {
                ConstructorST f1 = (ConstructorST) anotherRowST;
                if (refFun.hasTheSameArgs(f1.getTypesParams(), args)) {
                    return f1;
                }
            }
            index++;
        }
        semanticErrors.add(errorsRep.undefiniteConstructorError(pos));
        return null;
    }
    
    
    
    private List<String> getTypeParams(JSymbolTable globalST, SymbolTable symbolTable, 
            TypeTable typeTable, NodeJerarTree jerar, List<String> semanticErrors, 
            SemanticRestrictions restrictions){
        List<String> list = new LinkedList<>();
        if(params != null && !params.isEmpty()){
            for (JExpression param : params) {
                Label type = param.validateData(
                        globalST, symbolTable, typeTable, jerar, semanticErrors, restrictions
                );
                list.add(type.getName());
            }
        }
        return list;
    }

    @Override
    public RetParamsC3D generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, Memory temporals, C3Dpass pass) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
