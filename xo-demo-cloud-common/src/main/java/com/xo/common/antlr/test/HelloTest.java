package com.xo.common.antlr.test;

import com.xo.common.antlr.config.hello.HelloLexer;
import com.xo.common.antlr.config.hello.HelloParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;

public class HelloTest {

    public static void main(String[] args) {
        //对每一个输入的字符串，构造一个 CodePointCharStream
        String str = "hello world";
        CodePointCharStream cpcs = CharStreams.fromString(str);
        //用 cpcs 构造词法分析器 lexer，词法分析的作用是产生记号
        HelloLexer lexer = new HelloLexer(cpcs);
        //用词法分析器 lexer 构造一个记号流 tokens
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        //再使用 tokens 构造语法分析器 parser,至此已经完成词法分析和语法分析的准备工作
        HelloParser parser = new HelloParser(tokens);

        //最终调用语法分析器的规则 prog，完成对表达式的验证
        HelloParser.ExprContext expr = parser.expr();
        HelloParser.SContext s = parser.s();
        System.out.println(s);
//        HelloParser.StatContext statContext = parser.stat();
        System.out.println(s.toStringTree(parser));
    }





}
