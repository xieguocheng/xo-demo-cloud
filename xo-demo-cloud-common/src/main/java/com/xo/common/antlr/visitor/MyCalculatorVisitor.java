/****************************************************
 * 创建人：  @author xieguocheng
 * 创建时间: 2021/06/10 16:32
 * 项目名称:  yh-risk-parent
 * 文件名称: MyCalculatorVisitor
 * 文件描述: MyCalculatorVisitor
 * 公司名称: 深圳市赢和信息技术有限公司
 *
 * All rights Reserved, Designed By 深圳市赢和信息技术有限公司
 * @Copyright:2016-2019
 *
 ********************************************************/
package com.xo.common.antlr.visitor;

import com.xo.common.antlr.config.calculator.CalculatorBaseVisitor;
import com.xo.common.antlr.config.calculator.CalculatorParser;

/**
 * 包名称： com.xo.web.antlr.visitor
 * 类名称：MyCalculatorVisitor
 * 类描述：MyCalculatorVisitor
 * 创建人： xieguocheng
 * 创建时间：2021/06/10 16:32
 */
public class MyCalculatorVisitor extends CalculatorBaseVisitor<Object> {
    @Override
    public Object visitParenExpr(CalculatorParser.ParenExprContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public Object visitMultOrDiv(CalculatorParser.MultOrDivContext ctx) {
        Object obj0 = ctx.expr(0).accept(this);
        Object obj1 = ctx.expr(1).accept(this);

        if ("*".equals(ctx.getChild(1).getText())) {
            return (Float) obj0 * (Float) obj1;
        } else if ("/".equals(ctx.getChild(1).getText())) {
            return (Float) obj0 / (Float) obj1;
        }
        return 0f;
    }

    @Override
    public Object visitAddOrSubstract(CalculatorParser.AddOrSubstractContext ctx) {
        Object obj0 = ctx.expr(0).accept(this);
        Object obj1 = ctx.expr(1).accept(this);
        if ("+".equals(ctx.getChild(1).getText())) {
            return (Float) obj0 + (Float) obj1;
        } else if ("-".equals(ctx.getChild(1).getText())) {
            return (Float) obj0 - (Float) obj1;
        }
        return 0f;
    }

    @Override
    public Object visitFloat(CalculatorParser.FloatContext ctx) {
        return Float.parseFloat(ctx.getText());
    }
}
