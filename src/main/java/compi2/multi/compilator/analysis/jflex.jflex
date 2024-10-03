/******************** codigo de usuario *******************************/
package compi2.multi.compilator.analysis;

import java_cup.runtime.*;
import java.util.*;

%% //separador de area

/**************** opciones y declaraciones de jflex ******************/

%public
%unicode
%class Lexer
%cup
%line
%column
%init{
    errorsList = new LinkedList<>();
    string = new StringBuilder();
%init}

/**************************** states **********************************/
%state PASCAL
%state JAVA

%state CHARLITERAL
%state P_CHAR_STRING
%state JCHARLITERAL

%state STRING
%state JSTRING


/**************************** macros **********************************/

LineTerminator = \r|\n|\r\n 
InputCharacter = [^\r\n]

WhiteSpace = {LineTerminator} | [ \t\f]
Identifier = [:jletter:] ([:jletterdigit:]|_)*
SimpleBoolean = 0|1
DecIntegerLiteral = [0-9]+
DecFloatLiteral = {DecIntegerLiteral}\.{DecIntegerLiteral}
DecCharLiteral = \'[\x00-\x7F]\'

PascalComment = {PascalSingleComment} | {PascalMultilineComment}
PascalSingleComment = "{"[^}\n]*"}"
PascalMultilineComment   = "(*" [^*] ~"*)" | "(*" "*" + ")"

JComment = {JTraditionalComment} | {JEndOfLineComment}
JTraditionalComment = "/*" [^*] ~"*/" | "/*" "*"+ "/"
JEndOfLineComment = "//" {InputCharacter}* {LineTerminator}?

/* string and character literals */
SingleCharacter = [^\r\n\'\\]
StringCharacter = [^\r\n\"\\]

OctDigit          = [0-7]

%{
    StringBuilder string;
  /*-----------------------------------------------------------------
    CODIGO PARA EL MANEJO DE ERRORES
  -------------------------------------------------------------------*/
    private List<String> errorsList;
    public List<String> symbols = new ArrayList();

    public List<String> getErrors(){
        return this.errorsList;
    }

    /*----------------------------------------------------------------
        CODIGO PARA EL PARSER
    ------------------------------------------------------------------*/
    private Symbol symbol(int type) {
        symbols.add(yytext());
        return new Symbol(type, yyline+1, yycolumn+1);
    }

    private Symbol symbol(int type, Object value) {
        symbols.add(value.toString());
        return new Symbol(type, yyline+1, yycolumn+1, value);
    }

    private void error(String message) {
        errorsList.add("Error en la linea: " + (yyline+1) + ", columna: " + (yycolumn+1) + " : " + message);
    }

%}

%% // separador de areas

/* reglas lexicas */

    /*-------------------------------------------------------------------------------
                                simbolos comunes
    ----------------------------------------------------------------------------------*/
    /* Operators */
        "+"             { return symbol(sym.PLUS); }
        "-"             { return symbol(sym.MINUS); }
        "*"             { return symbol(sym.TIMES); }
        "/"             { return symbol(sym.DIV); }
        "%"             { return symbol(sym.MODULE); }
        "^"             { return symbol(sym.POWER); }

    /* others */
        ";"             { return symbol(sym.SEMICOLON); }
        ":"             { return symbol(sym.COLON); }
        ","             { return symbol(sym.COMMA); }
        "."             { return symbol(sym.DOT); }

    /* delimitators */
        "("             { return symbol(sym.LPAREN); }
        ")"             { return symbol(sym.RPAREN); }
        "{"             { return symbol(sym.LBRACE); }
        "}"             { return symbol(sym.RBRACE); }
        "["             { return symbol(sym.LBRACK); }
        "]"             { return symbol(sym.RBRACK); }

    /* comparators */
        ">"             { return symbol(sym.GRATER); }
        "<"             { return symbol(sym.LESS); }
        "<="            { return symbol(sym.GRATER_EQUALS); }
        ">="            { return symbol(sym.LESS_EQUALS); }


    /*-------------------------------------------------------------
     *                      MAIN C LANGUAJE
     --------------------------------------------------------------*/
    <YYINITIAL> {
        /* sections */ 
        "%%JAVA"        { yybegin(JAVA); return symbol(sym.JAVA_SECTION); }
        "%%PASCAL"      { yybegin(PASCAL); return symbol(sym.PASCAL_SECTION); }
        "%%PROGRAMA"    { yybegin(YYINITIAL); return symbol(sym.MAIN_SECTION); }

        /* keywords */
        "arreglo"       { return symbol(sym.ARRAY); }
        "break"         { return symbol(sym.BREAK); }
        "case"          { return symbol(sym.CASE); }
        "char"          { return symbol(sym.CHAR_TKN); }
        "clrscr"        { return symbol(sym.CLEAR); }
        "const"         { return symbol(sym.CONST); }
        "continue"      { return symbol(sym.CONTINUE); }
        "do"            { return symbol(sym.DO); }
        "else"          { return symbol(sym.ELSE); }
        "float"         { return symbol(sym.FLOAT_TKN); }
        "for"           { return symbol(sym.FLOAT_TKN); }
        "getch"         { return symbol(sym.GETCH); }
        "if"            { return symbol(sym.IF); }
        "include"       { return symbol(sym.INCLUDE); }
        "int"           { return symbol(sym.INT_TKN); }
        "JAVA"          { return symbol(sym.JAVA); }
        "main"          { return symbol(sym.MAIN); }
        "PASCAL"        { return symbol(sym.PASCAL); }
        "scanf"         { return symbol(sym.SCANF); }
        "string"        { return symbol(sym.STRING_TKN); } //**********WARNING HERE
        "switch"        { return symbol(sym.SWITCH); }
        "void"          { return symbol(sym.VOID); }
        "while"         { return symbol(sym.WHILE); }

        /* others */
        "=="             { return symbol(sym.EQUALS); }
        "="             { return symbol(sym.ASSIGNATION); }
        "&&"            { return symbol(sym.AND); }
        "||"            { return symbol(sym.OR); }
        "!"             { return symbol(sym.DIFFERENT); }
        "&"             { return symbol(sym.AMPERSAND); }

        /* literals */
        {Identifier}    { return symbol( sym.ID, yytext() ); }
        {SimpleBoolean} { return symbol(sym.BOOLEAN_LIT, Boolean.valueOf(yytext())); }
        {DecIntegerLiteral}            { return symbol(sym.INTEGER_LIT, Integer.valueOf(yytext())); }
        {DecFloatLiteral}              { return symbol(sym.FLOAT_LIT, Float.parseFloat(yytext()));}

        \'              { string.setLength(0); yybegin(CHARLITERAL); }
        \"              { string.setLength(0); yybegin(STRING); }
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

        /* error cases */
        \\.                            { error("Secuencia ilegal de escape \""+yytext()+"\""); }
        {LineTerminator}               { error("Literal de carácter sin terminar al final de la línea"); }
    }

    <STRING> {
        \"                             { yybegin(YYINITIAL); return symbol(sym.STRING_LIT, string.toString()); }

        {StringCharacter}+             { string.append( yytext() ); }

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
        {LineTerminator}               { error("Literal de cadena sin terminar al final de la línea"); }
    }

    /*----------------------------------------------------------
                            JAVA SECTION
     ----------------------------------------------------------*/
    <JAVA> {
        /* sections */
        "%%JAVA"        { yybegin(JAVA); return symbol(sym.JAVA_SECTION); }
        "%%PASCAL"      { yybegin(PASCAL); return symbol(sym.PASCAL_SECTION); }
        "%%PROGRAMA"    { yybegin(YYINITIAL); return symbol(sym.MAIN_SECTION); }

        /* keywords */
        "break"         { return symbol(sym.BREAK); }
        "boolean"         { return symbol(sym.BOOLEAN_TKN); }
        "case"          { return symbol(sym.CASE); }
        "continue"      { return symbol(sym.CONTINUE); }
        "class"         { return symbol(sym.CLASS); }
        "do"            { return symbol(sym.DO); }
        "else"          { return symbol(sym.ELSE); }
        "extends"       { return symbol(sym.EXTENDS); }
        "float"         { return symbol(sym.FLOAT_TKN); }
        "for"           { return symbol(sym.FOR); }
        "if"            { return symbol(sym.IF); }
        "return"          { return symbol(sym.RETURN); }
        "null"          { return symbol(sym.NULL_LIT); }
        "private"       { return symbol(sym.PRIVATE); }
        "protected"     { return symbol(sym.PROTECTED); }
        "public"        { return symbol(sym.PUBLIC); }
        "String"        { return symbol(sym.STRING_TKN); }
        "switch"        { return symbol(sym.SWITCH); }
        "super"         { return symbol(sym.SUPER); }
        "void"          { return symbol(sym.VOID); }
        "while"         { return symbol(sym.WHILE); }

        /* others */
        "=="             { return symbol(sym.EQUALS); }
        "="             { return symbol(sym.ASSIGNATION); }
        "&&"            { return symbol(sym.AND); }
        "||"            { return symbol(sym.OR); }
        "!"             { return symbol(sym.DIFFERENT); }

        /* prints */
        "print"         { return symbol(sym.PRINT); }
        "println"       { return symbol(sym.PRINTLN); }

        /* literals */
        "true"          { return symbol(sym.BOOLEAN_LIT, true); }
        "false"         { return symbol(sym.BOOLEAN_LIT, false); }
        {Identifier}    { return symbol( sym.ID, yytext() ); }
        {DecIntegerLiteral}            { return symbol(sym.INTEGER_LIT, Integer.valueOf(yytext())); }
        {DecFloatLiteral}              { return symbol(sym.FLOAT_LIT, Float.parseFloat(yytext()));}
        \'              { string.setLength(0); yybegin(JCHARLITERAL); }
        \"              { string.setLength(0); yybegin(JSTRING); }

        /* ignore */
        {JComment}      {/* ignore */}

    }

    <JCHARLITERAL> {
        {SingleCharacter}\'            { yybegin(JAVA); return symbol(sym.CHAR_LIT, yytext().charAt(0)); }

        /* escape sequences */
        "\\b"\'                        { yybegin(JAVA); return symbol(sym.CHAR_LIT, '\b');}
        "\\t"\'                        { yybegin(JAVA); return symbol(sym.CHAR_LIT, '\t');}
        "\\n"\'                        { yybegin(JAVA); return symbol(sym.CHAR_LIT, '\n');}
        "\\f"\'                        { yybegin(JAVA); return symbol(sym.CHAR_LIT, '\f');}
        "\\r"\'                        { yybegin(JAVA); return symbol(sym.CHAR_LIT, '\r');}
        "\\\""\'                       { yybegin(JAVA); return symbol(sym.CHAR_LIT, '\"');}
        "\\'"\'                        { yybegin(JAVA); return symbol(sym.CHAR_LIT, '\'');}
        "\\\\"\'                       { yybegin(JAVA); return symbol(sym.CHAR_LIT, '\\'); }
        \\[0-3]?{OctDigit}?{OctDigit}\' { yybegin(JAVA);
                                                int val = Integer.parseInt(yytext().substring(1,yylength()-1),8);
                                                return symbol(sym.CHAR_LIT, (char)val); }

        /* error cases */
        \\.                            { error("Secuencia ilegal de escape \""+yytext()+"\""); }
        {LineTerminator}               { error("Literal de carácter sin terminar al final de la línea"); }
    }

    <JSTRING> {
        \"                             { yybegin(JAVA); return symbol(sym.STRING_LIT, string.toString()); }

        {StringCharacter}+             { string.append( yytext() ); }

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
        {LineTerminator}               { error("Literal de cadena sin terminar al final de la línea"); }
    }


    /*----------------------------------------------------------
                           PASCAL SECTION
     ----------------------------------------------------------*/
    <PASCAL> {
        /* sections */
        "%%JAVA"        { yybegin(JAVA); return symbol(sym.JAVA_SECTION); }
        "%%PASCAL"      { yybegin(PASCAL); return symbol(sym.PASCAL_SECTION); }
        "%%PROGRAMA"    { yybegin(YYINITIAL); return symbol(sym.MAIN_SECTION); }

        /* simbols */
        ":="            { return symbol(sym.ASSIGNATION); }
        "="             { return symbol(sym.EQUALS); }
        "<>"            { return symbol(sym.DIFFERENT); }
        "="             { return symbol(sym.EQUALS); }

        /* keywords */
        [aA][nN][dD]                    { return symbol(sym.AND); }
        [aA][rR][rR][aA][yY]            { return symbol(sym.ARRAY); }
        [bB][eE][gG][iI][nN]            { return symbol(sym.BEGIN); }
        [bB][oO][oO][lL][eE][aA][nN]    { return symbol(sym.BOOLEAN_TKN); }
        [bB][rR][eE][aA][kK]            { return symbol(sym.BREAK); }
        [cC][aA][sS][eE]                { return symbol(sym.CASE); }
        [cC][hH][aA][rR]                { return symbol(sym.CHAR_TKN); }
        [cC][oO][nN][sS][tT]            { return symbol(sym.CONST); }
        [cC][oO][nN][tT][iI][nN][uU][eE]    { return symbol(sym.CONTINUE); }
        [dD][oO]                        { return symbol(sym.DO); }
        [dD][oO][wW][nN][tT][oO]        { return symbol(sym.DOWNTO); }
        [eE][lL][sS][eE]                { return symbol(sym.ELSE); }
        [eE][nN][dD]                    { return symbol(sym.END); }
        [fF][oO][rR]                    { return symbol(sym.FOR); }
        [fF][uU][nN][cC][tT][iI][oO][nN]    { return symbol(sym.FUNCTION); }
        [iI][fF]                        { return symbol(sym.IF); }
        [iI][nN]                        { return symbol(sym.IN); }
        [iI][nN][tT][eE][gG][eE][rR]    { return symbol(sym.INT_TKN); }
        [mM][oO][dD]                    { return symbol(sym.MOD); }
        [nN][oO][tT]                    { return symbol(sym.NOT); }
        [oO][fF]                        { return symbol(sym.OF); }
        [oO][rR]                        { return symbol(sym.OR); }
        [pP][aA][cC][kK][eE][dD]        { return symbol(sym.PACKED); }
        [pP][rR][oO][cC][eE][dD][uU][rR][eE]    { return symbol(sym.PROCEDURE); }
        [pP][rR][oO][gG][rR][aA][mM]    { return symbol(sym.PROGRAM); }
        [rR][eE][aA][lL]                { return symbol(sym.REAL_TKN); }
        [rR][eE][cC][oO][rR][dD]        { return symbol(sym.RECORD); }
        [rR][eE][pP][eE][aA][tT]        { return symbol(sym.REPEAT); }
        [rR][eE][tT][uU][rR][nN]        { return symbol(sym.RETURN); }
        [sS][eE][tT]                    { return symbol(sym.SET); }
        [sS][tT][rR][iI][nN][gG]        { return symbol(sym.STRING_TKN); }
        [sS][wW][iI][tT][cC][hH]        { return symbol(sym.SWITCH); }
        [tT][hH][eE][nN]                { return symbol(sym.THEN); }
        [tT][oO]                        { return symbol(sym.TO); }
        [tT][yY][pP][eE]                { return symbol(sym.TYPE); }
        [uU][nN][tT][iI][lL]            { return symbol(sym.UNTIL); }
        [vV][aA][rR]                    { return symbol(sym.VAR); }
        [wW][hH][iI][lL][eE]            { return symbol(sym.WHILE); }
        [wW][iI][tT][hH]                { return symbol(sym.WITH); }

        /* prints */
        [pP][rR][iI][nN][tT]            { return symbol(sym.PRINT); }
        [pP][rR][iI][nN][tT][lL][nN]    { return symbol(sym.PRINTLN); }

        /* literals */
        {Identifier}    { return symbol(sym.ID, yytext().toLowerCase()); }
        {SimpleBoolean} { return symbol(sym.BOOLEAN_LIT, Boolean.valueOf(yytext())); }
        {DecIntegerLiteral}            { return symbol(sym.INTEGER_LIT, Integer.valueOf(yytext())); }
        {DecFloatLiteral}              { return symbol(sym.FLOAT_LIT, Float.parseFloat(yytext()));}

        \'              { string.setLength(0); yybegin(P_CHAR_STRING); }

        /* ignore */
        {PascalComment}           {/* ignore */}
    }


    <P_CHAR_STRING> {
        {SingleCharacter}\'            { yybegin(PASCAL); return symbol(sym.CHAR_LIT, yytext().charAt(0)); }

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

    /* error fallback */
    .               { error("Simbolo invalido <"+ yytext()+">");}
    <<EOF>>         { return symbol(sym.EOF); }

