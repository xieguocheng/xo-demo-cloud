///****************************************************
// * 创建人：  @author xieguocheng
// * 创建时间: 2021/06/08 15:33
// * 项目名称:  yh-risk-parent
// * 文件名称: test
// * 文件描述: test
// * 公司名称: 深圳市赢和信息技术有限公司
// *
// * All rights Reserved, Designed By 深圳市赢和信息技术有限公司
// * @Copyright:2016-2019
// *
// ********************************************************/
//package com.xo.common.antlr.test;
//
//import com.xo.web.antlr.visitor.MyMathVisitor;
//import org.antlr.v4.runtime.CharStream;
//import org.antlr.v4.runtime.CharStreams;
//import org.antlr.v4.runtime.CommonTokenStream;
//
///**
// * 包名称： com.xo.web.antlr.test
// * 类名称：test
// * 类描述：test
// * 创建人： xieguocheng
// * 创建时间：2021/06/08 15:33
// */
//public class MathVisitorTest {
//    public static void main(String[] args){
//
//
//        CharStream input = CharStreams.fromString("12*2+12");
//        MathLexer lexer=new MathLexer(input);
//        CommonTokenStream tokens = new CommonTokenStream(lexer);
//        MathParser parser = new MathParser(tokens);
//
//        //查看
////        ParseTree tree = parser.prog(); // parse
////        String s = tree.toStringTree(parser);
////        System.out.println(s);
//
//        MathVisitor visitor = new MyMathVisitor();
//        //测试
//        MathParser.StatContext statContext = parser.stat();
//        Object visit = visitor.visit(statContext);
//        System.out.println(visit);
//
//        Object visit1 = visitor.visit(parser.expr());
//        System.out.println(visit1);
//
//    }
//}
