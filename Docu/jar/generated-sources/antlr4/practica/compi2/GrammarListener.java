// Generated from practica/compi2/Grammar.g4 by ANTLR 4.13.2
package practica.compi2;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link GrammarParser}.
 */
public interface GrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link GrammarParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(GrammarParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(GrammarParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#declaracionVarablesOpcionales}.
	 * @param ctx the parse tree
	 */
	void enterDeclaracionVarablesOpcionales(GrammarParser.DeclaracionVarablesOpcionalesContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#declaracionVarablesOpcionales}.
	 * @param ctx the parse tree
	 */
	void exitDeclaracionVarablesOpcionales(GrammarParser.DeclaracionVarablesOpcionalesContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#declaracionFuncionesOpcionales}.
	 * @param ctx the parse tree
	 */
	void enterDeclaracionFuncionesOpcionales(GrammarParser.DeclaracionFuncionesOpcionalesContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#declaracionFuncionesOpcionales}.
	 * @param ctx the parse tree
	 */
	void exitDeclaracionFuncionesOpcionales(GrammarParser.DeclaracionFuncionesOpcionalesContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#declaracionPrgrama}.
	 * @param ctx the parse tree
	 */
	void enterDeclaracionPrgrama(GrammarParser.DeclaracionPrgramaContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#declaracionPrgrama}.
	 * @param ctx the parse tree
	 */
	void exitDeclaracionPrgrama(GrammarParser.DeclaracionPrgramaContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#declaracionVariable}.
	 * @param ctx the parse tree
	 */
	void enterDeclaracionVariable(GrammarParser.DeclaracionVariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#declaracionVariable}.
	 * @param ctx the parse tree
	 */
	void exitDeclaracionVariable(GrammarParser.DeclaracionVariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#inicializador}.
	 * @param ctx the parse tree
	 */
	void enterInicializador(GrammarParser.InicializadorContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#inicializador}.
	 * @param ctx the parse tree
	 */
	void exitInicializador(GrammarParser.InicializadorContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#inicializadorArray}.
	 * @param ctx the parse tree
	 */
	void enterInicializadorArray(GrammarParser.InicializadorArrayContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#inicializadorArray}.
	 * @param ctx the parse tree
	 */
	void exitInicializadorArray(GrammarParser.InicializadorArrayContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#tipoVariable}.
	 * @param ctx the parse tree
	 */
	void enterTipoVariable(GrammarParser.TipoVariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#tipoVariable}.
	 * @param ctx the parse tree
	 */
	void exitTipoVariable(GrammarParser.TipoVariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#tipo}.
	 * @param ctx the parse tree
	 */
	void enterTipo(GrammarParser.TipoContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#tipo}.
	 * @param ctx the parse tree
	 */
	void exitTipo(GrammarParser.TipoContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#booleano}.
	 * @param ctx the parse tree
	 */
	void enterBooleano(GrammarParser.BooleanoContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#booleano}.
	 * @param ctx the parse tree
	 */
	void exitBooleano(GrammarParser.BooleanoContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#definicionFuncion}.
	 * @param ctx the parse tree
	 */
	void enterDefinicionFuncion(GrammarParser.DefinicionFuncionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#definicionFuncion}.
	 * @param ctx the parse tree
	 */
	void exitDefinicionFuncion(GrammarParser.DefinicionFuncionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#funcionSinRetorno}.
	 * @param ctx the parse tree
	 */
	void enterFuncionSinRetorno(GrammarParser.FuncionSinRetornoContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#funcionSinRetorno}.
	 * @param ctx the parse tree
	 */
	void exitFuncionSinRetorno(GrammarParser.FuncionSinRetornoContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#funcionConRetorno}.
	 * @param ctx the parse tree
	 */
	void enterFuncionConRetorno(GrammarParser.FuncionConRetornoContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#funcionConRetorno}.
	 * @param ctx the parse tree
	 */
	void exitFuncionConRetorno(GrammarParser.FuncionConRetornoContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#parametros}.
	 * @param ctx the parse tree
	 */
	void enterParametros(GrammarParser.ParametrosContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#parametros}.
	 * @param ctx the parse tree
	 */
	void exitParametros(GrammarParser.ParametrosContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#bloqueFuncion}.
	 * @param ctx the parse tree
	 */
	void enterBloqueFuncion(GrammarParser.BloqueFuncionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#bloqueFuncion}.
	 * @param ctx the parse tree
	 */
	void exitBloqueFuncion(GrammarParser.BloqueFuncionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#seccionVariablesFuncion}.
	 * @param ctx the parse tree
	 */
	void enterSeccionVariablesFuncion(GrammarParser.SeccionVariablesFuncionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#seccionVariablesFuncion}.
	 * @param ctx the parse tree
	 */
	void exitSeccionVariablesFuncion(GrammarParser.SeccionVariablesFuncionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#definicionEstructura}.
	 * @param ctx the parse tree
	 */
	void enterDefinicionEstructura(GrammarParser.DefinicionEstructuraContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#definicionEstructura}.
	 * @param ctx the parse tree
	 */
	void exitDefinicionEstructura(GrammarParser.DefinicionEstructuraContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#atributoEstructura}.
	 * @param ctx the parse tree
	 */
	void enterAtributoEstructura(GrammarParser.AtributoEstructuraContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#atributoEstructura}.
	 * @param ctx the parse tree
	 */
	void exitAtributoEstructura(GrammarParser.AtributoEstructuraContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#inicializadorEstructura}.
	 * @param ctx the parse tree
	 */
	void enterInicializadorEstructura(GrammarParser.InicializadorEstructuraContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#inicializadorEstructura}.
	 * @param ctx the parse tree
	 */
	void exitInicializadorEstructura(GrammarParser.InicializadorEstructuraContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#asignacionAtributo}.
	 * @param ctx the parse tree
	 */
	void enterAsignacionAtributo(GrammarParser.AsignacionAtributoContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#asignacionAtributo}.
	 * @param ctx the parse tree
	 */
	void exitAsignacionAtributo(GrammarParser.AsignacionAtributoContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#bloque}.
	 * @param ctx the parse tree
	 */
	void enterBloque(GrammarParser.BloqueContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#bloque}.
	 * @param ctx the parse tree
	 */
	void exitBloque(GrammarParser.BloqueContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#instruccion}.
	 * @param ctx the parse tree
	 */
	void enterInstruccion(GrammarParser.InstruccionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#instruccion}.
	 * @param ctx the parse tree
	 */
	void exitInstruccion(GrammarParser.InstruccionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#asignacion}.
	 * @param ctx the parse tree
	 */
	void enterAsignacion(GrammarParser.AsignacionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#asignacion}.
	 * @param ctx the parse tree
	 */
	void exitAsignacion(GrammarParser.AsignacionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#incrementoInstruccion}.
	 * @param ctx the parse tree
	 */
	void enterIncrementoInstruccion(GrammarParser.IncrementoInstruccionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#incrementoInstruccion}.
	 * @param ctx the parse tree
	 */
	void exitIncrementoInstruccion(GrammarParser.IncrementoInstruccionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#accesoVariable}.
	 * @param ctx the parse tree
	 */
	void enterAccesoVariable(GrammarParser.AccesoVariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#accesoVariable}.
	 * @param ctx the parse tree
	 */
	void exitAccesoVariable(GrammarParser.AccesoVariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#accesoSufijo}.
	 * @param ctx the parse tree
	 */
	void enterAccesoSufijo(GrammarParser.AccesoSufijoContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#accesoSufijo}.
	 * @param ctx the parse tree
	 */
	void exitAccesoSufijo(GrammarParser.AccesoSufijoContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#condicional}.
	 * @param ctx the parse tree
	 */
	void enterCondicional(GrammarParser.CondicionalContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#condicional}.
	 * @param ctx the parse tree
	 */
	void exitCondicional(GrammarParser.CondicionalContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#ramas}.
	 * @param ctx the parse tree
	 */
	void enterRamas(GrammarParser.RamasContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#ramas}.
	 * @param ctx the parse tree
	 */
	void exitRamas(GrammarParser.RamasContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#cicloMientras}.
	 * @param ctx the parse tree
	 */
	void enterCicloMientras(GrammarParser.CicloMientrasContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#cicloMientras}.
	 * @param ctx the parse tree
	 */
	void exitCicloMientras(GrammarParser.CicloMientrasContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#cicloHacerMientras}.
	 * @param ctx the parse tree
	 */
	void enterCicloHacerMientras(GrammarParser.CicloHacerMientrasContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#cicloHacerMientras}.
	 * @param ctx the parse tree
	 */
	void exitCicloHacerMientras(GrammarParser.CicloHacerMientrasContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#cicloIterador}.
	 * @param ctx the parse tree
	 */
	void enterCicloIterador(GrammarParser.CicloIteradorContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#cicloIterador}.
	 * @param ctx the parse tree
	 */
	void exitCicloIterador(GrammarParser.CicloIteradorContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#incremento}.
	 * @param ctx the parse tree
	 */
	void enterIncremento(GrammarParser.IncrementoContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#incremento}.
	 * @param ctx the parse tree
	 */
	void exitIncremento(GrammarParser.IncrementoContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#controlFlujo}.
	 * @param ctx the parse tree
	 */
	void enterControlFlujo(GrammarParser.ControlFlujoContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#controlFlujo}.
	 * @param ctx the parse tree
	 */
	void exitControlFlujo(GrammarParser.ControlFlujoContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#lectura}.
	 * @param ctx the parse tree
	 */
	void enterLectura(GrammarParser.LecturaContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#lectura}.
	 * @param ctx the parse tree
	 */
	void exitLectura(GrammarParser.LecturaContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#escritura}.
	 * @param ctx the parse tree
	 */
	void enterEscritura(GrammarParser.EscrituraContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#escritura}.
	 * @param ctx the parse tree
	 */
	void exitEscritura(GrammarParser.EscrituraContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#retorno}.
	 * @param ctx the parse tree
	 */
	void enterRetorno(GrammarParser.RetornoContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#retorno}.
	 * @param ctx the parse tree
	 */
	void exitRetorno(GrammarParser.RetornoContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#llamadaFuncion}.
	 * @param ctx the parse tree
	 */
	void enterLlamadaFuncion(GrammarParser.LlamadaFuncionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#llamadaFuncion}.
	 * @param ctx the parse tree
	 */
	void exitLlamadaFuncion(GrammarParser.LlamadaFuncionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#expresion}.
	 * @param ctx the parse tree
	 */
	void enterExpresion(GrammarParser.ExpresionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#expresion}.
	 * @param ctx the parse tree
	 */
	void exitExpresion(GrammarParser.ExpresionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#expresionLogicaOr}.
	 * @param ctx the parse tree
	 */
	void enterExpresionLogicaOr(GrammarParser.ExpresionLogicaOrContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#expresionLogicaOr}.
	 * @param ctx the parse tree
	 */
	void exitExpresionLogicaOr(GrammarParser.ExpresionLogicaOrContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#expresionLogicaAnd}.
	 * @param ctx the parse tree
	 */
	void enterExpresionLogicaAnd(GrammarParser.ExpresionLogicaAndContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#expresionLogicaAnd}.
	 * @param ctx the parse tree
	 */
	void exitExpresionLogicaAnd(GrammarParser.ExpresionLogicaAndContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#expresionIgualdad}.
	 * @param ctx the parse tree
	 */
	void enterExpresionIgualdad(GrammarParser.ExpresionIgualdadContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#expresionIgualdad}.
	 * @param ctx the parse tree
	 */
	void exitExpresionIgualdad(GrammarParser.ExpresionIgualdadContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#expresionRelacional}.
	 * @param ctx the parse tree
	 */
	void enterExpresionRelacional(GrammarParser.ExpresionRelacionalContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#expresionRelacional}.
	 * @param ctx the parse tree
	 */
	void exitExpresionRelacional(GrammarParser.ExpresionRelacionalContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#expresionAditiva}.
	 * @param ctx the parse tree
	 */
	void enterExpresionAditiva(GrammarParser.ExpresionAditivaContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#expresionAditiva}.
	 * @param ctx the parse tree
	 */
	void exitExpresionAditiva(GrammarParser.ExpresionAditivaContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#expresionMultiplicativa}.
	 * @param ctx the parse tree
	 */
	void enterExpresionMultiplicativa(GrammarParser.ExpresionMultiplicativaContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#expresionMultiplicativa}.
	 * @param ctx the parse tree
	 */
	void exitExpresionMultiplicativa(GrammarParser.ExpresionMultiplicativaContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#expresionUnaria}.
	 * @param ctx the parse tree
	 */
	void enterExpresionUnaria(GrammarParser.ExpresionUnariaContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#expresionUnaria}.
	 * @param ctx the parse tree
	 */
	void exitExpresionUnaria(GrammarParser.ExpresionUnariaContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#expresionPostfija}.
	 * @param ctx the parse tree
	 */
	void enterExpresionPostfija(GrammarParser.ExpresionPostfijaContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#expresionPostfija}.
	 * @param ctx the parse tree
	 */
	void exitExpresionPostfija(GrammarParser.ExpresionPostfijaContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#expresionPrimaria}.
	 * @param ctx the parse tree
	 */
	void enterExpresionPrimaria(GrammarParser.ExpresionPrimariaContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#expresionPrimaria}.
	 * @param ctx the parse tree
	 */
	void exitExpresionPrimaria(GrammarParser.ExpresionPrimariaContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#inicializadorEstructuraAnonimo}.
	 * @param ctx the parse tree
	 */
	void enterInicializadorEstructuraAnonimo(GrammarParser.InicializadorEstructuraAnonimoContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#inicializadorEstructuraAnonimo}.
	 * @param ctx the parse tree
	 */
	void exitInicializadorEstructuraAnonimo(GrammarParser.InicializadorEstructuraAnonimoContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#identificador}.
	 * @param ctx the parse tree
	 */
	void enterIdentificador(GrammarParser.IdentificadorContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#identificador}.
	 * @param ctx the parse tree
	 */
	void exitIdentificador(GrammarParser.IdentificadorContext ctx);
}