// Generated from C:/Users/abel/Desktop/xo/xo-demo-cloud/xo-demo-cloud-common/src/main/resources\Hello.g4 by ANTLR 4.9.1
 package com.xo.common.antlr.config.hello;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link HelloParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface HelloVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link HelloParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(HelloParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link HelloParser#s}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitS(HelloParser.SContext ctx);
}