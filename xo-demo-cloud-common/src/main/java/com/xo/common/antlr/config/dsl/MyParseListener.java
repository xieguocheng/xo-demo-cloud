package com.xo.common.antlr.config.dsl;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.util.List;

public class MyParseListener extends DslBaseListener {

    @Override
    public void enterSql(DslParser.SqlContext ctx) {
        String keyword = ctx.children.get(0).getText();  //获取sql规则的第一个元素，为select或者load
        if("select".equalsIgnoreCase(keyword)){
            execSelect(ctx);   //第一个元素为selece的时候执行select
        }else if("load".equalsIgnoreCase(keyword)){
            execLoad(ctx);  //第一个元素为load的时候执行load
        }

    }

    public void execLoad(DslParser.SqlContext ctx){
        List<ParseTree> children = ctx.children;   //获取该规则树的所有子节点
        String format = "";
        String path = "";
        String tableName = "";
        for (ParseTree c :children) {
            if(c instanceof DslParser.FormatContext){
                format = c.getText();
            }else if(c instanceof DslParser.PathContext){
                path = c.getText().substring(1,c.getText().length()-1);
            }else if(c instanceof DslParser.TableNameContext){
                tableName = c.getText();
            }
        }
        System.out.println(format);
        System.out.println(path);
        System.out.println(tableName);
        // spark load实现，省略
    }

    public void execSelect(DslParser.SqlContext ctx){

    }

    public static void main(String[] args) throws IOException {
        String len = "load json.`F:\\tmp\\user` as temp;";
        ANTLRInputStream input = new ANTLRInputStream(len);
        DslLexer lexer = new DslLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        DslParser parser = new DslParser(tokens);
        DslParser.SqlContext tree = parser.sql();
        MyParseListener listener = new MyParseListener();
        ParseTreeWalker.DEFAULT.walk(listener,tree);  //规则树遍历
    }
}
