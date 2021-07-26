///****************************************************
// * 创建人：  @author xieguocheng
// * 创建时间: 2021/06/10 15:02
// * 项目名称:  yh-risk-parent
// * 文件名称: EvalMathVisitor
// * 文件描述: EvalMathVisitor
// * 公司名称: 深圳市赢和信息技术有限公司
// *
// * All rights Reserved, Designed By 深圳市赢和信息技术有限公司
// * @Copyright:2016-2019
// *
// ********************************************************/
//package com.xo.common.antlr.visior.visitor;
//
//import com.xo.web.antlr.config.MathBaseVisitor;
//import com.xo.web.antlr.config.MathParser;
//
//import java.math.BigDecimal;
//
///**
// * 包名称： com.xo.web.antlr.visitor
// * 类名称：EvalMathVisitor
// * 类描述：EvalMathVisitor
// * 创建人： xieguocheng
// * 创建时间：2021/06/10 15:02
// */
//public class MyMathVisitor extends MathBaseVisitor<BigDecimal> {
//
//    @Override
//    public BigDecimal visitProg(MathParser.ProgContext ctx) {
//        return super.visitProg(ctx);
//    }
//
//    @Override
//    public BigDecimal visitPrintExpr(MathParser.PrintExprContext ctx) {
//        return super.visitPrintExpr(ctx);
//    }
//
//    @Override
//    public BigDecimal visitAssign(MathParser.AssignContext ctx) {
//        return super.visitAssign(ctx);
//    }
//
//    @Override
//    public BigDecimal visitBlank(MathParser.BlankContext ctx) {
//        return super.visitBlank(ctx);
//    }
//
//    @Override
//    public BigDecimal visitParens(MathParser.ParensContext ctx) {
//        return visit(ctx.expr());
//    }
//
//    @Override
//    public BigDecimal visitMulDiv(MathParser.MulDivContext ctx) {
//        // *或者/
//        ctx.expr();
//        BigDecimal left = visit(ctx.expr(0));
//        BigDecimal right = visit(ctx.expr(1));
//        if (ctx.MUL() != null) {
//            return left.multiply(right);
//        }
//        return left.divide(right, 20, BigDecimal.ROUND_HALF_UP);
//    }
//
//    @Override
//    public BigDecimal visitAddSub(MathParser.AddSubContext ctx) {
//        //＋或者-
//        ctx.expr();
//        BigDecimal left = visit(ctx.expr(0));
//        BigDecimal right = visit(ctx.expr(1));
//        if (ctx.ADD() != null) {
//            return left.add(right);
//        }
//        return left.subtract(right);
//    }
//
//    @Override
//    public BigDecimal visitId(MathParser.IdContext ctx) {
//        return super.visitId(ctx);
//    }
//
//    @Override
//    public BigDecimal visitInt(MathParser.IntContext ctx) {
//        return super.visitInt(ctx);
//    }
//}
