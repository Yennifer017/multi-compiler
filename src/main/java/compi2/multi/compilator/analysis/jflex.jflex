/* codigo de usuario */
package compi2.pascal.valitations.analysis;

import java_cup.runtime.*;
import java.util.*;

%% //separador de area

/* opciones y declaraciones de jflex */

%public
%unicode
%class Lexer
%cup
%line
%column
%column
%ignorecase
%init{
    errorsList = new LinkedList<>();
    string = new StringBuilder();
%init}

%state CHARLITERAL

LineTerminator = \r|\n|\r\n 

WhiteSpace = {LineTerminator} | [ \t\f]
Identifier = [:jletter:] ([:jletterdigit:]|_)*
Boolean = 0|1
DecIntegerLiteral = [0-9]+
DecFloatLiteral = {DecIntegerLiteral}\.{DecIntegerLiteral}
DecCharLiteral = \'[\x00-\x7F]\'

Comment = {SingleComment} | {MultilineComment}
SingleComment = "{"[^}\n]*"}"
MultilineComment   = "(*" [^*] ~"*)" | "(*" "*" + ")"

/* string and character literals */
SingleCharacter = [^\r\n\'\\]

OctDigit          = [0-7]

%{
    StringBuilder string;
  /*--------------------------------------------
    CODIGO PARA EL MANEJO DE ERRORES
  ----------------------------------------------*/
    private List<String> errorsList;
    public List<String> symbols = new ArrayList();

    public List<String> getErrors(){
        return this.errorsList;
    }

    /*--------------------------------------------
        CODIGO PARA EL PARSER
    ----------------------------------------------*/
    private Symbol symbol(int type) {
        symbols.add(yytext());
        return new Symbol(type, yyline+1, yycolumn+1);
    }

    private Symbol symbol(int type, Object value) {
        symbols.add(value.toString());
        return new Symbol(type, yyline+1, yycolumn+1, value);
    }

    private void error(String message) {
        errorsList.add("Error en la linea: " + (yyline+1) + ", columna: " + (yycolumn+1) + " : "+message);
    }

%}

%% // separador de areas

/* reglas lexicas */

    /*-----------------------------------------------------
                    simbolos reservados
    ------------------------------------------------------*/
    /*Operadores*/
    "+"             { return symbol(sym.PLUS); }
    "-"             { return symbol(sym.MINUS); }
    "*"             { return symbol(sym.TIMES); }
    "/"             { return symbol(sym.BARRA); }

    /*Delimitadores*/
    "{"             { return symbol(sym.LLAVE_L); }
    "}"             { return symbol(sym.LLAVE_R); }
    "("             { return symbol(sym.PARENTESIS_L); }
    ")"             { return symbol(sym.PARENTESIS_R); }
    "["             { return symbol(sym.CORCHETE_L); }
    "]"             { return symbol(sym.CORCHETE_R); }
    "`"             { return symbol(sym.ACENT); }

    /*comparators*/
    "="             { return symbol(sym.EQUALS); }
    "<>"            { return symbol(sym.DIFFERENT); }
    ">"             { return symbol(sym.GRATER); }
    "<"             { return symbol(sym.LESS); }
    "<="            { return symbol(sym.GRATER_EQUALS); }
    ">="            { return symbol(sym.LESS_EQUALS); }

    /*others*/
    ":="            { return symbol(sym.ASSIGNATION); }
    ","             { return symbol(sym.COMA); }
    "."             { return symbol(sym.DOT); }
    ";"             { return symbol(sym.SEMICOLON); }
    ":"             { return symbol(sym.COLON); }


    /* keywords */
    <YYINITIAL> "and"       { return symbol(sym.AND);      }
    <YYINITIAL> "array"     { return symbol(sym.ARRAY);     }
    <YYINITIAL> "begin"     { return symbol(sym.BEGIN);       }
    <YYINITIAL> "case"      { return symbol(sym.CASE);   }
    <YYINITIAL> "const"     { return symbol(sym.CONST);    }
    <YYINITIAL> "div"       { return symbol(sym.DIV);   }
    <YYINITIAL> "do"        { return symbol(sym.DO);   }
    <YYINITIAL> "downto"    { return symbol(sym.DOWNTO);   }
    <YYINITIAL> "else"      { return symbol(sym.ELSE);   }
    <YYINITIAL> "end"      { return symbol(sym.END);   }
    <YYINITIAL> "file"      { return symbol(sym.FILE);   }
    <YYINITIAL> "for"       { return symbol(sym.FOR);   }
    <YYINITIAL> "function"  { return symbol(sym.FUNCTION);   }
    <YYINITIAL> "goto"      { return symbol(sym.GOTO);   }
    <YYINITIAL> "if"        { return symbol(sym.IF);   }
    <YYINITIAL> "in"        { return symbol(sym.IN);   }
    <YYINITIAL> "label"     { return symbol(sym.LABEL);   }
    <YYINITIAL> "mod"       { return symbol(sym.MOD);   }
    <YYINITIAL> "nil"       { return symbol(sym.NIL);   }
    <YYINITIAL> "not"       { return symbol(sym.NOT);   }
    <YYINITIAL> "of"        { return symbol(sym.OF);   }
    <YYINITIAL> "or"        { return symbol(sym.OR);   }
    <YYINITIAL> "packed"    { return symbol(sym.PACKED);   }
    <YYINITIAL> "procedure" { return symbol(sym.PROCEDURE);   }
    <YYINITIAL> "program"   { return symbol(sym.PROGRAM);   }
    <YYINITIAL> "record"    { return symbol(sym.RECORD);   }
    <YYINITIAL> "repeat"    { return symbol(sym.REPEAT);   }
    <YYINITIAL> "set"       { return symbol(sym.SET);   }
    <YYINITIAL> "then"      { return symbol(sym.THEN);   }
    <YYINITIAL> "to"        { return symbol(sym.TO);   }
    <YYINITIAL> "type"      { return symbol(sym.TYPE);   }
    <YYINITIAL> "until"     { return symbol(sym.UNTIL);   }
    <YYINITIAL> "var"       { return symbol(sym.VAR);   }
    <YYINITIAL> "while"     { return symbol(sym.WHILE);   }
    <YYINITIAL> "with"      { return symbol(sym.WITH);   }

    <YYINITIAL> "writeln"   { return symbol(sym.WRITELN);   }
    <YYINITIAL> "readln"    { return symbol(sym.READLN);   }
    <YYINITIAL> "break"     { return symbol(sym.BREAK);   }
    <YYINITIAL> "return"    { return symbol(sym.RETURN);   }
    <YYINITIAL> "continue"  { return symbol(sym.CONTINUE);   }

    /* type of data */
    <YYINITIAL> "integer"   { return symbol(sym.INTEGER);   }
    <YYINITIAL> "real"      { return symbol(sym.REAL);   }
    <YYINITIAL> "boolean"   { return symbol(sym.BOOLEAN);   }
    <YYINITIAL> "char"      { return symbol(sym.CHAR);   }
    <YYINITIAL> "string"    { return symbol(sym.STRING);   }
    <YYINITIAL> "longint"    { return symbol(sym.LONGINT);   }

    <YYINITIAL> {
        /* identifiers */ 
        {Identifier}                   { return symbol(sym.ID, yytext().toLowerCase()); }
     
        /* literals */
        {Boolean}                      { return symbol(sym.BOOLEAN_LIT, Boolean.valueOf(yytext()));}
        {DecIntegerLiteral}            { return symbol(sym.INTEGER_LIT, Integer.valueOf(yytext())); }
        {DecFloatLiteral}              { return symbol(sym.REAL_LIT, Float.parseFloat(yytext()));}
        {DecCharLiteral}               { return symbol(sym.CHAR_LIT, yytext().charAt(1));}


        /* character literal */
        \'                             { string.setLength(0); yybegin(CHARLITERAL); }
    }

    <CHARLITERAL> {
        {SingleCharacter}\'            { yybegin(YYINITIAL); return symbol(sym.CHAR_LIT, yytext().charAt(0)); }

        /* escape sequences */
        "\\b"\'                        { yybegin(YYINITIAL); return symbol(sym.CHAR_LIT, '\b');}
        "\\t"\'                        { yybegin(YYINITIAL); return symbol(sym.CHAR_LIT, '\t');}
        "\\n"\'                        { yybegin(YYINITIAL); return symbol(sym.CHAR_LIT, '\n');}
        "\\f"\'                        { yybegin(YYINITIAL); return symbol(sym.CHAR_LIT, '\f');}
        "\\r"\'                        { yybegin(YYINITIAL); return symbol(sym.CHAR_LIT, '\r');}
        "\\\""\'                       { yybegin(YYINITIAL); return symbol(sym.CHAR_LIT, '\"');}
        "\\'"\'                        { yybegin(YYINITIAL); return symbol(sym.CHAR_LIT, '\'');}
        "\\\\"\'                       { yybegin(YYINITIAL); return symbol(sym.CHAR_LIT, '\\'); }
        \\[0-3]?{OctDigit}?{OctDigit}\' { yybegin(YYINITIAL);
                                                            int val = Integer.parseInt(yytext().substring(1,yylength()-1),8);
                                                          return symbol(sym.CHAR_LIT, (char)val); }


        \'                             { yybegin(YYINITIAL); return symbol(sym.STRING_LIT, string.toString()); }

        {SingleCharacter}+             { string.append( yytext() ); }
        /* escape sequences */
        "\\b"                          { string.append( '\b' ); }
        "\\t"                          { string.append( '\t' ); }
        "\\n"                          { string.append( '\n' ); }
        "\\f"                          { string.append( '\f' ); }
        "\\r"                          { string.append( '\r' ); }
        "\\\""                         { string.append( '\"' ); }
        "\\'"                          { string.append( '\'' ); }
        "\\\\"                         { string.append( '\\' ); }
        \\[0-3]?{OctDigit}?{OctDigit}  { char val = (char) Integer.parseInt(yytext().substring(1),8);
                                                                   string.append( val ); }

        /* error cases */
        \\.                            { error("Secuencia ilegal de escape \""+yytext()+"\""); }
        {LineTerminator}               { error("Literal de carácter sin terminar al final de la línea"); }
    }


    /*lo ignorado*/
    {WhiteSpace}     {/* ignoramos */}
    {Comment}        {/* ignoramos */}

    /* error fallback */
    .               { error("Simbolo invalido <"+ yytext()+">");}
    <<EOF>>         { return symbol(sym.EOF); }

