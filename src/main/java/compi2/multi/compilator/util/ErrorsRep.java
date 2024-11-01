
package compi2.multi.compilator.util;

import compi2.multi.compilator.analysis.symbolt.InfParam;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class ErrorsRep {
    
    public String repeatedTypeError(String type, Position position){
        return "El tipo " + type + " ya se ha declarado anteriormente" + report(position);
                
    }
    
    public String repeatedDeclarationError(String name, Position position){
        return "Se esta redefiniendo la variable/constante " + name + report(position);
    }
    
    public String undefiniteTypeError(String type, Position position){
        return "El tipo " + type + " no se ha definido aun" + report(position);
                
    }
    
    /**
     * @param currentType
     * @param correctType
     * @param position
     * @return error que muestra que un tipo no es adecuado para una situacion
     */
    public String incorrectTypeError(String currentType, String correctType, Position position){
        return "El tipo " + currentType + " no es valido, se esperaba: " + correctType + report(position);
    }
    
    /**
     * @param currentType
     * @param position
     * @return Error que muestra que un tipo no es compatible con una operacion
     */
    public String incorrectTypeError(String currentType, Position position){
        return "El tipo: " + currentType + " no es compatible con la operacion" + report(position);
    }
    
    public String incorrectTypeError(String type1, String type2, String operation, Position pos){
        return "Los tipos " + type1 + " y " + type2 + " no se pueden operar con " 
                + operation + report(pos);
    }
    
    public String invalidCategoryAccessError(String name, String category, Position pos){
        return "No se puede acceder a " + name + " (" + category + ")" + report(pos);
    }
    
    public String invalidCategoryAccessError(String name, String category, 
            String esperateCategory, Position pos){
        return "No se puede acceder a " + name + " (" + category + ") como "
                + esperateCategory + report(pos);
    }
    
    public String inesperateTypeError(String esperateType, Position position){
        return "Se esperaba el tipo " + esperateType + report(position);
    }
    
    public String ilegalUseError(String variable, Position pos){
        return "Expresion invalida, variable/funcion/record/arreglo/objeto no permitido <" 
                + variable + ">" + report(pos);
    }
    
    public String ilegalAssignation(String variable, String categoryST, Position pos){
        return "No se puede asignar a " + variable + " (" + categoryST + ") " + report(pos);
    }
    
    public String negativeIndexError(String variable, Position pos){
        return "No se puede inicializar el arreglo " + variable + " con indices negativos" + report(pos);
    }
    
    public String undefiniteVarUseError(String variable, Position pos){
        return "La variable " + variable + " no se ha declarado " + report(pos);
    }
    
    public String undefiniteClassError(String variable, Position pos){
        return "La clase " + variable + " no existe " + report(pos);
    }
    
    public String undefiniteVarRecordError(String variable, Position pos){
        return "El acceso a " + variable + " no se pueden completar" + report(pos);
    }
    
    public String undefiniteFunctionError(String name, Position pos){
        return "La funcion " + name + " no ha sido declarada " + report(pos);
    }
    
    private String report(Position position){
        return "-- Linea: " + position.getLine() + ", columna: " + position.getCol();
    }
    
    public String incorrectVarTypeError(String varName, String esperatedType, Position pos){
        return "La variable " + varName + " no es del tipo " + esperatedType + report(pos);
    }
    
    public String ilegalStmt(String nameStmt, Position pos){
        return "No se esperaba " + nameStmt + report(pos);
    }
    
    public String unrachableCodeError(Position pos){
        return "El codigo no se puede ejecutar " + report(pos);
    }
    
    public String missingReturnError(String functionName, String type, Position pos){
        return "La function " + functionName + " necesita el retorno de tipo " + type + report(pos);
    }
    
    public String ObjectClassError(Position pos, String supername){
        return "No se puede definir una clase con el nombre " + supername + ", esta reservada";
    }
    
    public String redeclareFunctionError(String name, List<InfParam> args, Position pos){
        StringBuilder builder = new StringBuilder("Se esta redeclarando la function ")
                .append(name)
                .append(" con los tipos [ ");
        if(args != null){
            for (int i = 0; i < args.size(); i++) {
                String arg = args.get(i).getType();
                builder.append(arg);
                if(i != args.size() - 1){
                    builder.append(", ");
                }
            }
        }
        builder.append("]");
        builder.append(report(pos));
        return builder.toString();
    }
    
    public String undefiniteConstructorError(Position pos){
        return "El contructor no se encontro " + report(pos);
    }
    
    public String noSuitableFunctionError(String name, List<InfParam> args, Position pos){
        StringBuilder builder = new StringBuilder(
                "No se pudo encontrar una llamada a la funccion ")
                .append(name)
                .append(" con los parametros tipo [");
        if(args != null){
            for (int i = 0; i < args.size(); i++) {
                String arg = args.get(i).getType();
                builder.append(arg);
                if(i != args.size() - 1){
                    builder.append(", ");
                }
            }
        }
        builder.append("]");
        builder.append(report(pos));
        return builder.toString();
    }
    
    public String noConstructorError(String className, String construcName, Position pos){
        return "El constructor" + construcName 
                + " no tiene el mismo nombre de la clase " + className + report(pos);
    }
    
    public String inesperateVoid(Position pos){
        return "No se esperaba un void como tipo: " + report(pos);
    }
    
    public String mascaraError(Position pos){
        return "La mascara es invalida, se esperaba %d, %c, %f o %s " + report(pos);
    }
    
    public String notExpressionError(Position pos){
        return "No se pudo recuperar el valor de una invocacion, se esperaba una expression " + report(pos);
    }
    
    public String notStatementError(Position pos){
        return "Se esperaba una instrucccion " + report(pos);
    }
    
    public String invalidInvocationError(String supertype, String field, Position pos){
        return "No se encontro el atributo " + field +  "en la clase " + supertype + report(pos);
    }
}
