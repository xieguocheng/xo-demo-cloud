/****************************************************
 * 创建人：  @author xieguocheng
 * 创建时间: 2021/06/10 16:34
 * 项目名称:  yh-risk-parent
 * 文件名称: CalculatorVisitorTest
 * 文件描述: CalculatorVisitorTest
 * 公司名称: 深圳市赢和信息技术有限公司
 *
 * All rights Reserved, Designed By 深圳市赢和信息技术有限公司
 * @Copyright:2016-2019
 *
 ********************************************************/
package com.xo.common.antlr.test;

import com.xo.common.antlr.config.calculator.CalculatorLexer;
import com.xo.common.antlr.config.calculator.CalculatorParser;
import com.xo.common.antlr.config.calculator.CalculatorVisitor;
import com.xo.common.antlr.visitor.MyCalculatorVisitor;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

/**
 * 包名称： com.xo.web.antlr.test
 * 类名称：CalculatorVisitorTest
 * 类描述：CalculatorVisitorTest
 * 创建人： xieguocheng
 * 创建时间：2021/06/10 16:34
 */
public class CalculatorVisitorTest {

    public static void main(String[] args) {
        //测试方案一可行
        String query = "3.1 * (6.3 - 4.51) + 5 * 4";
        CalculatorLexer lexer = new CalculatorLexer(new ANTLRInputStream(query));
        CalculatorParser parser = new CalculatorParser(new CommonTokenStream(lexer));
        CalculatorVisitor visitor = new MyCalculatorVisitor();
//        System.out.println(parser.expr().toStringTree(parser));
        System.out.println(visitor.visit(parser.expr()));  // 25.549

        //测试方案二
        CharStream input = CharStreams.fromString("a=12*2+12");
        CalculatorLexer lexer1=new CalculatorLexer(input);
        CommonTokenStream tokens1 = new CommonTokenStream(lexer1);
        CalculatorParser parser1 = new CalculatorParser(tokens1);
        CalculatorVisitor visitor1 = new MyCalculatorVisitor();
        System.out.println();
        System.out.println(visitor1.visit(parser1.expr()));  // 25.549

    }

}
