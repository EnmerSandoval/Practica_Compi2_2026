grammar Grammar;

program: declaracionVarablesOpcionales? declaracionFuncionesOpcionales? declaracionPrgrama EOF
        ;

declaracionVarablesOpcionales: VARIABILES MAYOR_QUE (declaracionVariable | definicionEstructura)*
                        ;

declaracionFuncionesOpcionales: MUNERA MAYOR_QUE definicionFuncion*
                ;


declaracionPrgrama: MAIOR MAYOR_QUE bloque FIN_PROGRAMA PUNTO_COMA
                ;

declaracionVariable: ESTO identificador DOS_PUNTOS tipoVariable inicializador? PUNTO_COMA
                   | SERIES identificador CORCHETE_IZQUIERDO NUMERO CORCHETE_DERECHO DOS_PUNTOS tipo inicializadorArray? PUNTO_COMA
                   ;

inicializador: expresion
             ;

inicializadorArray: LLAVE_IZQUIERDA (expresion (COMA expresion)*)? LLAVE_DERECHA
                  ;
// Aca nos guiamos del enunciado de acuerdo a la jerarquia
tipoVariable: NUMERUS
            | TEXTUM
            | DECIMALIS
            | LITTERA
            | VERUM
            | FALSUS
            | tipo
            ;

tipo: NUMERUS
    | TEXTUM
    | DECIMALIS
    | LITTERA
    | BOOL
    | booleano
    | identificador
    ;

booleano: VERUM
        | FALSUS
        ;

definicionFuncion: funcionSinRetorno
                 | funcionConRetorno
                 ;

funcionSinRetorno: ACTIO identificador PARENTESIS_IZQUIERDO parametros? PARENTESIS_DERECHO LLAVE_IZQUIERDA bloqueFuncion LLAVE_DERECHA FINIS PUNTO_COMA
                 ;

funcionConRetorno: RATIO tipo identificador PARENTESIS_IZQUIERDO parametros? PARENTESIS_DERECHO LLAVE_IZQUIERDA bloqueFuncion LLAVE_DERECHA FINIS PUNTO_COMA
                 ;

parametros: ESTO identificador DOS_PUNTOS tipo (COMA ESTO identificador DOS_PUNTOS tipo)*
           ;

bloqueFuncion: seccionVariablesFuncion? instruccion*
             ;

seccionVariablesFuncion: VARIABILES CORCHETE_IZQUIERDO declaracionVariable* CORCHETE_DERECHO
                       ;

definicionEstructura: STRUCTURA identificador LLAVE_IZQUIERDA atributoEstructura* LLAVE_DERECHA FINIS PUNTO_COMA
                    ;


atributoEstructura: ESTO identificador DOS_PUNTOS tipo (COMA | PUNTO_COMA)?
                  | SERIES identificador DOS_PUNTOS tipo (COMA | PUNTO_COMA)?
                  ;

inicializadorEstructura: identificador LLAVE_IZQUIERDA (asignacionAtributo (COMA asignacionAtributo)*)? LLAVE_DERECHA
                        ;

asignacionAtributo: identificador DOS_PUNTOS tipo CORCHETE_IZQUIERDO NUMERO CORCHETE_DERECHO
                  | identificador DOS_PUNTOS expresion
                  ;

bloque: instruccion*
      ;

instruccion: declaracionVariable
           | definicionEstructura
           | asignacion
           | incrementoInstruccion
           | condicional
           | cicloMientras
           | cicloHacerMientras
           | cicloIterador
           | llamadaFuncion PUNTO_COMA
           | lectura
           | escritura
           | retorno
           | controlFlujo
           ;

asignacion: accesoVariable IGUAL expresion PUNTO_COMA
          ;

incrementoInstruccion: accesoVariable (MAS_MAS | MENOS_MENOS) PUNTO_COMA
                    ;

accesoVariable: identificador accesoSufijo*
              ;

accesoSufijo: PUNTO identificador
            | CORCHETE_IZQUIERDO expresion CORCHETE_DERECHO
            ;

condicional: SI PARENTESIS_IZQUIERDO expresion PARENTESIS_DERECHO LLAVE_IZQUIERDA bloque LLAVE_DERECHA (ramas)? FINIS PUNTO_COMA
            ;

ramas: ALITER PARENTESIS_IZQUIERDO expresion PARENTESIS_DERECHO LLAVE_IZQUIERDA bloque LLAVE_DERECHA ramas?
    | ALITER LLAVE_IZQUIERDA bloque LLAVE_DERECHA
    ;

cicloMientras: DUM PARENTESIS_IZQUIERDO expresion PARENTESIS_DERECHO LLAVE_IZQUIERDA bloque LLAVE_DERECHA FINIS PUNTO_COMA
             ;

cicloHacerMientras: FACERE LLAVE_IZQUIERDA bloque LLAVE_DERECHA DUM PARENTESIS_IZQUIERDO expresion PARENTESIS_DERECHO PUNTO_COMA
                  ;

cicloIterador: PER PARENTESIS_IZQUIERDO ESTO identificador DOS_PUNTOS NUMERUS expresion PUNTO_COMA
      expresion PUNTO_COMA incremento PARENTESIS_DERECHO
      LLAVE_IZQUIERDA bloque LLAVE_DERECHA
    ;

incremento: identificador MAS_MAS
    | identificador MENOS_MENOS
    | accesoVariable IGUAL expresion
    ;

controlFlujo: PERGE PUNTO_COMA
    | INTERRUMPE PUNTO_COMA
    ;

lectura: accesoVariable DESPLAZAMIENTO_IZQUIERDO PUNTO_COMA?
    | DESPLAZAMIENTO_IZQUIERDO PUNTO_COMA?
    ;

escritura: DESPLAZAMIENTO_DERECHO expresion (DESPLAZAMIENTO_DERECHO expresion)* PUNTO_COMA
         ;

retorno: REDDERE expresion PUNTO_COMA
    | REDDERE PUNTO_COMA
    ;

llamadaFuncion: identificador PARENTESIS_IZQUIERDO (expresion (COMA expresion)*)? PARENTESIS_DERECHO
                ;

expresion: expresionLogicaOr
         ;

expresionLogicaOr: expresionLogicaAnd (OR expresionLogicaAnd)*
                ;

expresionLogicaAnd: expresionIgualdad (AND expresionIgualdad)*
                  ;

expresionIgualdad: expresionRelacional ((IGUAL_IGUAL | NO_IGUAL) expresionRelacional)*
                ;

expresionRelacional: expresionAditiva ((MENOR_QUE | MAYOR_QUE | MENOR_IGUAL | MAYOR_IGUAL) expresionAditiva)*
    ;

expresionAditiva: expresionMultiplicativa ((MAS | MENOS) expresionMultiplicativa)*
    ;

expresionMultiplicativa: expresionUnaria ((MULT | DIV) expresionUnaria)*
    ;

expresionUnaria: (NO | MENOS | MAS | MAS_MAS | MENOS_MENOS) expresionUnaria
    | expresionPostfija
    ;

expresionPostfija: expresionPrimaria (MAS_MAS | MENOS_MENOS)?
    ;

expresionPrimaria: NUMERO
    | DECIMAL
    | CADENA
    | CARACTER
    | VERUM
    | FALSUS
    | llamadaFuncion
    | identificador accesoSufijo*
    | PARENTESIS_IZQUIERDO expresion PARENTESIS_DERECHO
    | inicializadorEstructura
    | inicializadorEstructuraAnonimo
    ;

inicializadorEstructuraAnonimo: LLAVE_IZQUIERDA (asignacionAtributo (COMA asignacionAtributo)*)? LLAVE_DERECHA
    ;

identificador: IDENTIFICADOR
    ;

ESTO: 'esto';
SERIES: 'series';
NUMERUS: 'numerus';
TEXTUM: 'textum';
DECIMALIS: 'decimalis';
LITTERA: 'littera';
VERUM: 'verum';
FALSUS: 'falsus';
BOOL: 'bool';
STRUCTURA: 'structura';

SI: 'si';
ALITER: 'aliter';
DUM: 'dum';
FACERE: 'facere';
PER: 'per';
PERGE: 'perge';
INTERRUMPE: 'interrumpe';

ACTIO: 'actio';
RATIO: 'ratio';
REDDERE: 'reddere';

VARIABILES: 'VARIABILES';
MUNERA: 'MUNERA';
MAIOR: 'MAIOR';
FINIS: 'finis';
FIN_PROGRAMA: 'FINIS';

MAS: '+';
MENOS: '-';
MULT: '*';
DIV: '/';

IGUAL_IGUAL: '==';
NO_IGUAL: '!=';
MENOR_QUE: '<';
MAYOR_QUE: '>';
MENOR_IGUAL: '<=';
MAYOR_IGUAL: '>=';

AND: '&&';
OR: '||';
NO: 'non';

MAS_MAS: '++';
MENOS_MENOS: '--';

IGUAL: '=';
PUNTO_COMA: ';';
COMA: ',';
DOS_PUNTOS: ':';
PUNTO: '.';

PARENTESIS_IZQUIERDO: '(';
PARENTESIS_DERECHO: ')';
LLAVE_IZQUIERDA: '{';
LLAVE_DERECHA: '}';
CORCHETE_IZQUIERDO: '[';
CORCHETE_DERECHO: ']';

DESPLAZAMIENTO_IZQUIERDO: '<<';
DESPLAZAMIENTO_DERECHO: '>>';

NUMERO
    : [0-9]+
    ;

DECIMAL
    : [0-9]+ '.' [0-9]+
    ;

CADENA
    : '"' (~["\\\n\r] | '\\' .)* '"'
    ;

CARACTER
    : '\'' (~['\\\n\r] | '\\' .) '\''
    ;

IDENTIFICADOR
    : [a-zA-Z_] [a-zA-Z0-9_]*
    ;

COMENTARIO_LINEA
    : '//' ~[\n\r]* -> channel(HIDDEN)
    ;

COMENTARIO_BLOQUE
    : '/*' .*? '*/' -> channel(HIDDEN)
    ;

COMENTARIO_HASH
    : '##' .*? '##' -> channel(HIDDEN)
    ;

ESPACIO_EN_BLANCO
    : [ \t\n\r] -> skip
    ;
