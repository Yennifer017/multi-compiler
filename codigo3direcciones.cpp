#include <string>
#include <iostream>
int h = 0;
int ptr = 0;
int stackinteger[5000];
std::string stackstring[5000];
float stackreal[5000];
char stackchar[5000];
int stackboolean[5000];
int heapinteger[5000];
std::string heapstring[5000];
float heapreal[5000];
char heapchar[5000];
int heapboolean[5000];
int AX_INT;
int BX_INT;
int CX_INT;
std::string AX_STRING;
std::string BX_STRING;
std::string CX_STRING;
float AX_FLOAT;
float BX_FLOAT;
float CX_FLOAT;
char AX_CHAR;
char BX_CHAR;
char CX_CHAR;
void Operation();
void imprimir();
void Operation(){
int internalinteger[2];
std::string internalstring[0];
float internalreal[0];
char internalchar[0];
int internalboolean[0];
internalinteger[0] = h;
h = h+1;
AX_INT = ptr+2;
internalinteger[1] = AX_INT;
BX_INT = stack[0];
stack[AX_INT] = BX_INT;
}
void imprimir(){
int internalinteger[0];
std::string internalstring[0];
float internalreal[0];
char internalchar[0];
int internalboolean[0];
}
