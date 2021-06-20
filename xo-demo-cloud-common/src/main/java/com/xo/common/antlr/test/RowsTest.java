package com.xo.common.antlr.test;

import com.xo.common.antlr.config.rows.RowsLexer;
import com.xo.common.antlr.config.rows.RowsParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class RowsTest {
    public static void main(String[] args) throws IOException {
        //101 tombu	Tom Burns	020 bke
        //102 tombu	Tom Burns	020 bke
        //103 tombu	Tom Burns	020 bke
        //104 tombu	Tom Burns	020 bke
        //105 tombu	Tom Burns	020 bke
        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\abel\\Desktop\\新建文本文档.txt");
        System.out.println();
        ANTLRInputStream input = new ANTLRInputStream(fileInputStream);
        RowsLexer lexer = new RowsLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        //如果将col的1换成2，你会看到rows.txt文件的第2列内容被输出；如果换成3，那么rows.txt文件的第3列内容将会被输出。
        RowsParser parser = new RowsParser(tokens, 2);
        parser.setBuildParseTree(false);    // don't waste time bulding a tree
        System.out.println();

        parser.file();//类似加了监听器,会输出监听

    }
}
