// Generated from practica/compi2/Grammar.g4 by ANTLR 4.13.2
package practica.compi2;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link GrammarParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface GrammarVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link GrammarParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(GrammarParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#declaracionVarablesOpcionales}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaracionVarablesOpcionales(GrammarParser.DeclaracionVarablesOpcionalesContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#declaracionFuncionesOpcionales}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaracionFuncionesOpcionales(GrammarParser.DeclaracionFuncionesOpcionalesContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#declaracionPrgrama}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaracionPrgrama(GrammarParser.DeclaracionPrgramaContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#declaracionVariable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaracionVariable(GrammarParser.DeclaracionVariableContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#inicializador}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInicializador(GrammarParser.InicializadorContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#inicializadorArray}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInicializadorArray(GrammarParser.InicializadorArrayContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#tipoVariable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTipoVariable(GrammarParser.TipoVariableContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#tipo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTipo(GrammarParser.TipoContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#booleano}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleano(GrammarParser.BooleanoContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#definicionFuncion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefinicionFuncion(GrammarParser.DefinicionFuncionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#funcionSinRetorno}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncionSinRetorno(GrammarParser.FuncionSinRetornoContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#funcionConRetorno}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncionConRetorno(GrammarParser.FuncionConRetornoContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#parametros}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParametros(GrammarParser.ParametrosContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#bloqueFuncion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBloqueFuncion(GrammarParser.BloqueFuncionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#seccionVariablesFuncion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSeccionVariablesFuncion(GrammarParser.SeccionVariablesFuncionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#definicionEstructura}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefinicionEstructura(GrammarParser.DefinicionEstructuraContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#atributoEstructura}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtributoEstructura(GrammarParser.AtributoEstructuraContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#inicializadorEstructura}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInicializadorEstructura(GrammarParser.InicializadorEstructuraContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#asignacionAtributo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAsignacionAtributo(GrammarParser.AsignacionAtributoContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#bloque}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBloque(GrammarParser.BloqueContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#instruccion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstruccion(GrammarParser.InstruccionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#asignacion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAsignacion(GrammarParser.AsignacionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#incrementoInstruccion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIncrementoInstruccion(GrammarParser.IncrementoInstruccionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#accesoVariable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAccesoVariable(GrammarParser.AccesoVariableContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#accesoSufijo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAccesoSufijo(GrammarParser.AccesoSufijoContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#condicional}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondicional(GrammarParser.CondicionalContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#ramas}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRamas(GrammarParser.RamasContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#cicloMientras}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCicloMientras(GrammarParser.CicloMientrasContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#cicloHacerMientras}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCicloHacerMientras(GrammarParser.CicloHacerMientrasContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#cicloIterador}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCicloIterador(GrammarParser.CicloIteradorContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#incremento}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIncremento(GrammarParser.IncrementoContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#controlFlujo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitControlFlujo(GrammarParser.ControlFlujoContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#lectura}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLectura(GrammarParser.LecturaContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#escritura}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEscritura(GrammarParser.EscrituraContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#retorno}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRetorno(GrammarParser.RetornoContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#llamadaFuncion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLlamadaFuncion(GrammarParser.LlamadaFuncionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#expresion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpresion(GrammarParser.ExpresionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#expresionLogicaOr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpresionLogicaOr(GrammarParser.ExpresionLogicaOrContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#expresionLogicaAnd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpresionLogicaAnd(GrammarParser.ExpresionLogicaAndContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#expresionIgualdad}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpresionIgualdad(GrammarParser.ExpresionIgualdadContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#expresionRelacional}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpresionRelacional(GrammarParser.ExpresionRelacionalContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#expresionAditiva}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpresionAditiva(GrammarParser.ExpresionAditivaContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#expresionMultiplicativa}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpresionMultiplicativa(GrammarParser.ExpresionMultiplicativaContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#expresionUnaria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpresionUnaria(GrammarParser.ExpresionUnariaContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#expresionPostfija}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpresionPostfija(GrammarParser.ExpresionPostfijaContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#expresionPrimaria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpresionPrimaria(GrammarParser.ExpresionPrimariaContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#inicializadorEstructuraAnonimo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInicializadorEstructuraAnonimo(GrammarParser.InicializadorEstructuraAnonimoContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#identificador}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentificador(GrammarParser.IdentificadorContext ctx);
}