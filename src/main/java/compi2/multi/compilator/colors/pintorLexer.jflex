/***************************** paquetes *******************************/
package compi2.multi.compilator.colors;

import java.util.*;

%% //separador de area

/**************** opciones y declaraciones de jflex ******************/

%public
%unicode
%class PintorLexer
%char
%type Coloreado   // Especificar tipo de retorno

/*********************** codigo en el contructor ***********************/
%init{
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

%{   /*********************** CODIGO DE USUARIO ************************/
    StringBuilder string;
    private Coloreado coloreado(TypeTkn type){
        return new Coloreado(type, yychar, yylength());
    }

    private Coloreado coloreadoStr(TypeTkn type){
        return new Coloreado(type, yychar - string.toString().length() - 1, string.toString().length() + 2);
    }

%}

%% // separador de areas

/****************************** reglas lexicas *************************/

    /*-------------------------------------------------------------------------------
                                simbolos comunes
    ----------------------------------------------------------------------------------*/
    /* Operators */
        "+"             { return coloreado(TypeTkn.OPERATOR); }
        "-"             { return coloreado(TypeTkn.OPERATOR); }
        "*"             { return coloreado(TypeTkn.OPERATOR); }
        "/"             { return coloreado(TypeTkn.OPERATOR); }
        "%"             { return coloreado(TypeTkn.OPERATOR); }
        "^"             { return coloreado(TypeTkn.OPERATOR); }

    /* others */
        ";"             { return coloreado(TypeTkn.OTHERS); }
        ":"             { return coloreado(TypeTkn.OTHERS); }
        ","             { return coloreado(TypeTkn.OTHERS); }
        "."             { return coloreado(TypeTkn.OTHERS); }

    /* delimitators */
        "("             { return coloreado(TypeTkn.DELIMITATOR); }
        ")"             { return coloreado(TypeTkn.DELIMITATOR); }
        "{"             { return coloreado(TypeTkn.DELIMITATOR); }
        "}"             { return coloreado(TypeTkn.DELIMITATOR); }
        "["             { return coloreado(TypeTkn.DELIMITATOR); }
        "]"             { return coloreado(TypeTkn.DELIMITATOR); }

    /* comparators */
        ">"             { return coloreado(TypeTkn.COMPARATOR); }
        "<"             { return coloreado(TypeTkn.COMPARATOR); }
        "<="            { return coloreado(TypeTkn.COMPARATOR); }
        ">="            { return coloreado(TypeTkn.COMPARATOR); }


    /*-------------------------------------------------------------
     *                      MAIN C LANGUAJE
     --------------------------------------------------------------*/
    <YYINITIAL> {
        /* sections */ 
        "%%JAVA"        { yybegin(JAVA); return coloreado(TypeTkn.SECTION); }
        "%%PASCAL"      { yybegin(PASCAL); return coloreado(TypeTkn.SECTION); }
        "%%PROGRAMA"    { yybegin(YYINITIAL); return coloreado(TypeTkn.SECTION); }

        /* keywords */
        "arreglo"       { return coloreado(TypeTkn.RESERVED_WORD); }
        "break"         { return coloreado(TypeTkn.RESERVED_WORD); }
        "case"          { return coloreado(TypeTkn.RESERVED_WORD); }
        "char"          { return coloreado(TypeTkn.RESERVED_WORD); }
        "clrscr"        { return coloreado(TypeTkn.RESERVED_WORD); }
        "const"         { return coloreado(TypeTkn.RESERVED_WORD); }
        "continue"      { return coloreado(TypeTkn.RESERVED_WORD); }
        "do"            { return coloreado(TypeTkn.RESERVED_WORD); }
        "else"          { return coloreado(TypeTkn.RESERVED_WORD); }
        "float"         { return coloreado(TypeTkn.RESERVED_WORD); }
        "for"           { return coloreado(TypeTkn.RESERVED_WORD); }
        "getch"         { return coloreado(TypeTkn.RESERVED_WORD); }
        "if"            { return coloreado(TypeTkn.RESERVED_WORD); }
        "include"       { return coloreado(TypeTkn.RESERVED_WORD); }
        "int"           { return coloreado(TypeTkn.RESERVED_WORD); }
        "JAVA"          { return coloreado(TypeTkn.RESERVED_WORD); }
        "main"          { return coloreado(TypeTkn.RESERVED_WORD); }
        "PASCAL"        { return coloreado(TypeTkn.RESERVED_WORD); }
        "print"         { return coloreado(TypeTkn.RESERVED_WORD); }
        "printf"        { return coloreado(TypeTkn.RESERVED_WORD); }
        "scanf"         { return coloreado(TypeTkn.RESERVED_WORD); }
        "string"        { return coloreado(TypeTkn.RESERVED_WORD); }
        "switch"        { return coloreado(TypeTkn.RESERVED_WORD); }
        "void"          { return coloreado(TypeTkn.RESERVED_WORD); }
        "while"         { return coloreado(TypeTkn.RESERVED_WORD); }

        /* others */
        "=="            { return coloreado(TypeTkn.COMPARATOR); }
        "="             { return coloreado(TypeTkn.COMPARATOR); }
        "&&"            { return coloreado(TypeTkn.COMPARATOR); }
        "||"            { return coloreado(TypeTkn.COMPARATOR); }
        "!"             { return coloreado(TypeTkn.COMPARATOR); }
        "&"             { return coloreado(TypeTkn.OTHERS); }
        "#"             { return coloreado(TypeTkn.OTHERS); }

        /* literals */
        {Identifier}    { return coloreado(TypeTkn.ID); }
        {SimpleBoolean} { return coloreado(TypeTkn.BOOLEANS); }
        {DecIntegerLiteral}            { return coloreado(TypeTkn.LITERALS); }
        {DecFloatLiteral}              { return coloreado(TypeTkn.LITERALS);}

        \'              { string.setLength(0); yybegin(CHARLITERAL); }
        \"              { string.setLength(0); yybegin(STRING); }

        /* ignore */
        {JComment}      { return coloreado(TypeTkn.COMMENTARY); }
    }

    <CHARLITERAL> {
        {SingleCharacter}\'            { yybegin(YYINITIAL); return coloreadoStr(TypeTkn.STRINGS); }

        /* escape sequences */
        "\\b"\'                         { yybegin(YYINITIAL); return coloreadoStr(TypeTkn.STRINGS);}
        "\\t"\'                         { yybegin(YYINITIAL); return coloreadoStr(TypeTkn.STRINGS);}
        "\\n"\'                         { yybegin(YYINITIAL); return coloreadoStr(TypeTkn.STRINGS);}
        "\\f"\'                         { yybegin(YYINITIAL); return coloreadoStr(TypeTkn.STRINGS);}
        "\\r"\'                         { yybegin(YYINITIAL); return coloreadoStr(TypeTkn.STRINGS);}
        "\\\""\'                        { yybegin(YYINITIAL); return coloreadoStr(TypeTkn.STRINGS);}
        "\\'"\'                         { yybegin(YYINITIAL); return coloreadoStr(TypeTkn.STRINGS);}
        "\\\\"\'                        { yybegin(YYINITIAL); return coloreadoStr(TypeTkn.STRINGS);}
        \\[0-3]?{OctDigit}?{OctDigit}\' { yybegin(YYINITIAL);
                                                int val = Integer.parseInt(yytext().substring(1,yylength()-1),8);
                                                return coloreadoStr(TypeTkn.STRINGS); 
                                        }

        /* error cases */
        \\.                            { return coloreadoStr(TypeTkn.ERROR); }
        {LineTerminator}               { return coloreadoStr(TypeTkn.ERROR); }
    }

    <STRING> {
        \"                             { yybegin(YYINITIAL); return coloreadoStr(TypeTkn.STRINGS); }

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
        \\.                            { return coloreadoStr(TypeTkn.ERROR); }
        {LineTerminator}               { return coloreadoStr(TypeTkn.ERROR); }
    }

    /*----------------------------------------------------------
                            JAVA SECTION
     ----------------------------------------------------------*/
    <JAVA> {
        /* sections */
        "%%JAVA"        { yybegin(JAVA); return coloreado(TypeTkn.SECTION); }
        "%%PASCAL"      { yybegin(PASCAL); return coloreado(TypeTkn.SECTION); }
        "%%PROGRAMA"    { yybegin(YYINITIAL); return coloreado(TypeTkn.SECTION); }

        /* keywords */
        "break"         { return coloreado(TypeTkn.RESERVED_WORD); }
        "boolean"       { return coloreado(TypeTkn.RESERVED_WORD); }
        "char"          { return coloreado(TypeTkn.RESERVED_WORD); }
        "charinput"     { return coloreado(TypeTkn.RESERVED_WORD); }
        "case"          { return coloreado(TypeTkn.RESERVED_WORD); }
        "continue"      { return coloreado(TypeTkn.RESERVED_WORD); }
        "class"         { return coloreado(TypeTkn.RESERVED_WORD); }
        "default"       { return coloreado(TypeTkn.RESERVED_WORD); }
        "do"            { return coloreado(TypeTkn.RESERVED_WORD); }
        "else"          { return coloreado(TypeTkn.RESERVED_WORD); }
        "extends"       { return coloreado(TypeTkn.RESERVED_WORD); }
        "float"         { return coloreado(TypeTkn.RESERVED_WORD); }
        "floatinput"    { return coloreado(TypeTkn.RESERVED_WORD);}
        "for"           { return coloreado(TypeTkn.RESERVED_WORD); }
        "if"            { return coloreado(TypeTkn.RESERVED_WORD); }
        "int"           { return coloreado(TypeTkn.RESERVED_WORD); }
        "intinput"      { return coloreado(TypeTkn.RESERVED_WORD); }
        "return"        { return coloreado(TypeTkn.RESERVED_WORD); }
        "new"           { return coloreado(TypeTkn.RESERVED_WORD); }
        "null"          { return coloreado(TypeTkn.RESERVED_WORD); }
        "private"       { return coloreado(TypeTkn.RESERVED_WORD); }
        "protected"     { return coloreado(TypeTkn.RESERVED_WORD); }
        "public"        { return coloreado(TypeTkn.RESERVED_WORD); }
        "String"        { return coloreado(TypeTkn.RESERVED_WORD); }
        "switch"        { return coloreado(TypeTkn.RESERVED_WORD); }
        "super"         { return coloreado(TypeTkn.RESERVED_WORD); }
        "this"          { return coloreado(TypeTkn.RESERVED_WORD); }
        "void"          { return coloreado(TypeTkn.RESERVED_WORD); }
        "while"         { return coloreado(TypeTkn.RESERVED_WORD); }


        /* others */
        "=="            { return coloreado(TypeTkn.COMPARATOR); }
        "="             { return coloreado(TypeTkn.OPERATOR); }
        "&&"            { return coloreado(TypeTkn.COMPARATOR); }
        "||"            { return coloreado(TypeTkn.COMPARATOR); }
        "!"             { return coloreado(TypeTkn.COMPARATOR); }

        /* prints */
        "print"         { return coloreado(TypeTkn.RESERVED_WORD); }
        "println"       { return coloreado(TypeTkn.RESERVED_WORD); }

        /* literals */
        "true"          { return coloreado(TypeTkn.BOOLEANS);}
        "false"         { return coloreado(TypeTkn.BOOLEANS);}
        {Identifier}    { return coloreado(TypeTkn.ID); }
        {DecIntegerLiteral}            { return coloreado(TypeTkn.LITERALS); }
        {DecFloatLiteral}              { return coloreado(TypeTkn.LITERALS);}
        \'              { string.setLength(0); yybegin(JCHARLITERAL); }
        \"              { string.setLength(0); yybegin(JSTRING); }

        /* ignore */
        {JComment}      {return coloreado(TypeTkn.COMMENTARY);}

    }

    <JCHARLITERAL> {
        {SingleCharacter}\'            { yybegin(JAVA); return coloreado(TypeTkn.STRINGS); }

        /* escape sequences */
        "\\b"\'                        { yybegin(JAVA); return coloreadoStr(TypeTkn.STRINGS);}
        "\\t"\'                        { yybegin(JAVA); return coloreadoStr(TypeTkn.STRINGS);}
        "\\n"\'                        { yybegin(JAVA); return coloreadoStr(TypeTkn.STRINGS);}
        "\\f"\'                        { yybegin(JAVA); return coloreadoStr(TypeTkn.STRINGS);}
        "\\r"\'                        { yybegin(JAVA); return coloreadoStr(TypeTkn.STRINGS);}
        "\\\""\'                       { yybegin(JAVA); return coloreadoStr(TypeTkn.STRINGS);}
        "\\'"\'                        { yybegin(JAVA); return coloreadoStr(TypeTkn.STRINGS);}
        "\\\\"\'                       { yybegin(JAVA); return coloreadoStr(TypeTkn.STRINGS); }
        \\[0-3]?{OctDigit}?{OctDigit}\' { yybegin(JAVA);
                                                int val = Integer.parseInt(yytext().substring(1,yylength()-1),8);
                                                return coloreadoStr(TypeTkn.STRINGS); }

        /* error cases */
        \\.                            { return coloreadoStr(TypeTkn.ERROR); }
        {LineTerminator}               { return coloreadoStr(TypeTkn.ERROR); }
    }

    <JSTRING> {
        \"                             { yybegin(JAVA); return coloreadoStr(TypeTkn.STRINGS); }

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
        \\.                            { return coloreadoStr(TypeTkn.ERROR); }
        {LineTerminator}               { return coloreadoStr(TypeTkn.ERROR); }
    }


    /*----------------------------------------------------------
                           PASCAL SECTION
     ----------------------------------------------------------*/
    <PASCAL> {
        /* sections */
        "%%JAVA"        { yybegin(JAVA); return coloreado(TypeTkn.SECTION); }
        "%%PASCAL"      { yybegin(PASCAL); return coloreado(TypeTkn.SECTION); }
        "%%PROGRAMA"    { yybegin(YYINITIAL); return coloreado(TypeTkn.SECTION); }

        /* simbols */
        ":="            { return coloreado(TypeTkn.OPERATOR); }
        "="             { return coloreado(TypeTkn.COMPARATOR); }
        "<>"            { return coloreado(TypeTkn.COMPARATOR); }

        /* keywords */
        [aA][nN][dD]                    { return coloreado(TypeTkn.RESERVED_WORD); }
        [aA][rR][rR][aA][yY]            { return coloreado(TypeTkn.RESERVED_WORD); }
        [bB][eE][gG][iI][nN]            { return coloreado(TypeTkn.RESERVED_WORD); }
        [bB][oO][oO][lL][eE][aA][nN]    { return coloreado(TypeTkn.RESERVED_WORD); }
        [bB][rR][eE][aA][kK]            { return coloreado(TypeTkn.RESERVED_WORD); }
        [cC][aA][sS][eE]                { return coloreado(TypeTkn.RESERVED_WORD); }
        [cC][hH][aA][rR]                { return coloreado(TypeTkn.RESERVED_WORD); }
        [cC][oO][nN][sS][tT]            { return coloreado(TypeTkn.RESERVED_WORD); }
        [cC][oO][nN][tT][iI][nN][uU][eE]    { return coloreado(TypeTkn.RESERVED_WORD); }
        [dD][oO]                        { return coloreado(TypeTkn.RESERVED_WORD); }
        [dD][oO][wW][nN][tT][oO]        { return coloreado(TypeTkn.RESERVED_WORD); }
        [eE][lL][sS][eE]                { return coloreado(TypeTkn.RESERVED_WORD); }
        [eE][nN][dD]                    { return coloreado(TypeTkn.RESERVED_WORD); }
        [fF][oO][rR]                    { return coloreado(TypeTkn.RESERVED_WORD); }
        [fF][uU][nN][cC][tT][iI][oO][nN]    { return coloreado(TypeTkn.RESERVED_WORD); }
        [iI][fF]                        { return coloreado(TypeTkn.RESERVED_WORD); }
        [iI][nN]                        { return coloreado(TypeTkn.RESERVED_WORD); }
        [iI][nN][tT][eE][gG][eE][rR]    { return coloreado(TypeTkn.RESERVED_WORD); }
        [mM][oO][dD]                    { return coloreado(TypeTkn.RESERVED_WORD); }
        [nN][oO][tT]                    { return coloreado(TypeTkn.RESERVED_WORD); }
        [oO][fF]                        { return coloreado(TypeTkn.RESERVED_WORD); }
        [oO][rR]                        { return coloreado(TypeTkn.RESERVED_WORD); }
        [pP][aA][cC][kK][eE][dD]        { return coloreado(TypeTkn.RESERVED_WORD); }
        [pP][rR][oO][cC][eE][dD][uU][rR][eE]    { return coloreado(TypeTkn.RESERVED_WORD); }
        [pP][rR][oO][gG][rR][aA][mM]    { return coloreado(TypeTkn.RESERVED_WORD); }
        [rR][eE][aA][lL]                { return coloreado(TypeTkn.RESERVED_WORD); }
        [rR][eE][cC][oO][rR][dD]        { return coloreado(TypeTkn.RESERVED_WORD); }
        [rR][eE][pP][eE][aA][tT]        { return coloreado(TypeTkn.RESERVED_WORD); }
        [rR][eE][tT][uU][rR][nN]        { return coloreado(TypeTkn.RESERVED_WORD); }
        [sS][eE][tT]                    { return coloreado(TypeTkn.RESERVED_WORD); }
        [sS][tT][rR][iI][nN][gG]        { return coloreado(TypeTkn.RESERVED_WORD); }
        [sS][wW][iI][tT][cC][hH]        { return coloreado(TypeTkn.RESERVED_WORD); }
        [tT][hH][eE][nN]                { return coloreado(TypeTkn.RESERVED_WORD); }
        [tT][oO]                        { return coloreado(TypeTkn.RESERVED_WORD); }
        [tT][yY][pP][eE]                { return coloreado(TypeTkn.RESERVED_WORD); }
        [uU][nN][tT][iI][lL]            { return coloreado(TypeTkn.RESERVED_WORD); }
        [vV][aA][rR]                    { return coloreado(TypeTkn.RESERVED_WORD); }
        [wW][hH][iI][lL][eE]            { return coloreado(TypeTkn.RESERVED_WORD); }
        [wW][iI][tT][hH]                { return coloreado(TypeTkn.RESERVED_WORD); }

        /* prints */
        [pP][rR][iI][nN][tT]            { return coloreado(TypeTkn.RESERVED_WORD); }
        [pP][rR][iI][nN][tT][lL][nN]    { return coloreado(TypeTkn.RESERVED_WORD); }


        /* literals */
        {Identifier}    { return coloreado(TypeTkn.ID); }
        {SimpleBoolean} { return coloreado(TypeTkn.BOOLEANS); }
        {DecIntegerLiteral}            { return coloreado(TypeTkn.LITERALS); }
        {DecFloatLiteral}              { return coloreado(TypeTkn.LITERALS);}

        \'              { string.setLength(0); yybegin(P_CHAR_STRING); }

        /* ignore */
        {PascalComment}           { return coloreado(TypeTkn.COMMENTARY);}
    }


    <P_CHAR_STRING> {
        {SingleCharacter}\'            { yybegin(PASCAL); return coloreadoStr(TypeTkn.STRINGS); }

        /* escape sequences */
        "\\b"\'                        { yybegin(PASCAL); return coloreadoStr(TypeTkn.STRINGS);}
        "\\t"\'                        { yybegin(PASCAL); return coloreadoStr(TypeTkn.STRINGS);}
        "\\n"\'                        { yybegin(PASCAL); return coloreadoStr(TypeTkn.STRINGS);}
        "\\f"\'                        { yybegin(PASCAL); return coloreadoStr(TypeTkn.STRINGS);}
        "\\r"\'                        { yybegin(PASCAL); return coloreadoStr(TypeTkn.STRINGS);}
        "\\\""\'                       { yybegin(PASCAL); return coloreadoStr(TypeTkn.STRINGS);}
        "\\'"\'                        { yybegin(PASCAL); return coloreadoStr(TypeTkn.STRINGS);}
        "\\\\"\'                       { yybegin(PASCAL); return coloreadoStr(TypeTkn.STRINGS); }
        \\[0-3]?{OctDigit}?{OctDigit}\' { yybegin(PASCAL);
                                                            int val = Integer.parseInt(yytext().substring(1,yylength()-1),8);
                                                          return coloreadoStr(TypeTkn.STRINGS); }

        \'                             { yybegin(PASCAL); return coloreadoStr(TypeTkn.STRINGS); }

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
        \\.                            { return coloreadoStr(TypeTkn.ERROR); }
        {LineTerminator}               { return coloreadoStr(TypeTkn.ERROR); }
    }


    /*lo ignorado*/
    {WhiteSpace}     {/* ignoramos */}

    /* error fallback */
    .               { return coloreado(TypeTkn.ERROR);}


