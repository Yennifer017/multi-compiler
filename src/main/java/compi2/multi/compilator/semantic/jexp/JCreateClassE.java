
package compi2.multi.compilator.semantic.jexp;

import compi2.multi.compilator.analysis.jerarquia.NodeJerarTree;
import compi2.multi.compilator.analysis.symbolt.InfParam;
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
import compi2.multi.compilator.c3d.generators.ConstructorC3DGen;
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
@Getter
@Setter
public class JCreateClassE extends JExpression {

    private List<JExpression> params;
    private String name;

    private FunctionRefAnalyzator refFun;
    private ConstructorC3DGen constructorC3DGen;

    private ConstructorST constructorST;
    private SymbolTable st;

    public JCreateClassE(Position pos, String name, List<JExpression> params) {
        super(pos);
        this.name = name;
        this.params = params;
        this.refFun = new FunctionRefAnalyzator();
        this.constructorC3DGen = new ConstructorC3DGen();
    }

    public JCreateClassE(Position pos, String name) {
        super(pos);
        this.name = name;
        this.params = new LinkedList<>();
        this.refFun = new FunctionRefAnalyzator();
        this.constructorC3DGen = new ConstructorC3DGen();
    }

    @Override
    public Label validateData(JSymbolTable globalST, SymbolTable symbolTable,
            TypeTable typeTable, NodeJerarTree jerar, List<String> semanticErrors,
            SemanticRestrictions restrictions) {
        this.st = symbolTable;
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
            SemanticRestrictions restrictions) {
        if (globalST.containsKey(name)) {
            ClassST classST = globalST.get(name);
            SymbolTable st = classST.getMethodsST();
            List<InfParam> paramsList = getTypeParams(
                    globalST, symbolTable, typeTable, jerar, semanticErrors, restrictions
            );
            if (st.containsKey(name)) {
                RowST rowST = st.get(name);
                if (rowST instanceof ConstructorST) { //y tiene los mismos parametros
                    ConstructorST construct = (ConstructorST) rowST;
                    if (refFun.hasTheSameArgs(construct.getParams(), paramsList)) {
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

    private ConstructorST searchConstruct(SymbolTable symbolTable, List<InfParam> args,
            List<String> semanticErrors) {
        int index = 1;
        while (symbolTable.containsKey(
                refFun.getSTName(name, index))) {
            RowST anotherRowST = symbolTable.get(refFun.getSTName(this.name, index));
            if (anotherRowST instanceof ConstructorST) {
                ConstructorST f1 = (ConstructorST) anotherRowST;
                if (refFun.hasTheSameArgs(f1.getParams(), args)) {
                    return f1;
                }
            }
            index++;
        }
        semanticErrors.add(errorsRep.undefiniteConstructorError(pos));
        return null;
    }

    private List<InfParam> getTypeParams(JSymbolTable globalST, SymbolTable symbolTable,
            TypeTable typeTable, NodeJerarTree jerar, List<String> semanticErrors,
            SemanticRestrictions restrictions) {
        List<InfParam> list = new LinkedList<>();
        if (params != null && !params.isEmpty()) {
            for (JExpression param : params) {
                Label type = param.validateData(
                        globalST, symbolTable, typeTable, jerar, semanticErrors, restrictions
                );
                //list.add(type.getName());
                list.add(new InfParam(type.getName()));
            }
        }
        return list;
    }

    @Override
    public RetParamsC3D generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, 
            Memory temporals, C3Dpass pass) {
        return constructorC3DGen.generateCuartetas(
                params, 
                admiMemory, 
                internalCuartetas, 
                temporals, 
                st, 
                constructorST
        );
    }

}
