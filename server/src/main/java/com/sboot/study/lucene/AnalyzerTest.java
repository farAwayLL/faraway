package com.sboot.study.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.tokenattributes.TermToBytesRefAttribute;
import org.apache.lucene.util.BytesRef;

import java.util.Arrays;

/**
 * 分词效果演示，此代码了解即可
 */
public class AnalyzerTest {
    public static void main(String[] args) throws Exception {
        String str = "广东广州好玩的地方";

        // 单字分词，使用默认的停用词
//        Analyzer analyzer = new StandardAnalyzer();

        // 二分法分词，使用默认的停用词
//        Analyzer analyzer = new CJKAnalyzer();

        // 词库分词，智能分词器，使用默认的停用词
//        Analyzer analyzer = new SmartChineseAnalyzer();

        // 词库分词，智能分词器，并自定义的停用词集合
        Analyzer analyzer = new SmartChineseAnalyzer(new CharArraySet(Arrays.asList("的", "了", "啊"), true));

        // 对指定的字符串进行分词
        TokenStream tokenStream = analyzer.tokenStream(null, str);

        // 先要对tokenStream执行reset
        tokenStream.reset();

        // 采用循环，不断地获取下一个token
        while (tokenStream.incrementToken()) {
            // 获取其中token信息
            TermToBytesRefAttribute attr = tokenStream.getAttribute(TermToBytesRefAttribute.class);
            final BytesRef bytes = attr.getBytesRef();
            System.out.println(bytes.utf8ToString());
        }
        tokenStream.close();
    }
}
