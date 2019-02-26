/**
 * @author Long
 * QQ:584614151
 * version:1.0
 */
package com.sboot.study.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.nio.file.Paths;

/**
 * Lucene进行分页处理
 */
public class LuceneByPage {

    /**
     * @param args
     */
    public static void main(String[] args) {
        //添加索引
        //addIndex();
        //进行分页查询    分页查询需要   指定当前页码  以及  每页显示的记录数
        //第一个参数表示当前页码   第二个参数：每页显示的记录数
        searchIndex(2, 2);
    }


    /**
     * 全文检索
     */
    private static void searchIndex(int pageIndex, int pageSize) {
        try {
            //指定索引库的目录
            Directory directory = FSDirectory.open(Paths.get("E:\\MyProject\\faraway\\server\\luceneDB\\productDB"));
            //DirectoryReader的open方法指定需要读取的索引库信息，并返回相应的实例
            IndexReader indexReader = DirectoryReader.open(directory);
            //创建IndexSearcher实例，通过IndexSearcher实例进行全文检索
            IndexSearcher indexSearcher = new IndexSearcher(indexReader);
            //创建智能分词器
            Analyzer analyzer = new SmartChineseAnalyzer();
            //创建QueryParse实例   通过QueryParse可以指定需要查询的字段以及分词器信息
            QueryParser queryParser = new MultiFieldQueryParser(new String[]{"title", "content"}, analyzer);
            //通过QueryParser得到query实例,将指定的关键字
            Query query = queryParser.parse("广州");
            //查询索引表，最终数据都被封装在 TopDocs的实例中
            TopDocs topDocs = indexSearcher.search(query, 10);
            //通过topDocs获取匹配全部记录
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            System.out.println("获取到的记录数：" + scoreDocs.length);
            //获取查询到的记录数
            int totalNum = scoreDocs.length;
            //定义查询的起始行号以及结束的行号   1页  0   2页：2
            int startSize = (pageIndex - 1) * pageSize;
            int endSize = pageIndex * pageSize > totalNum ? totalNum : pageIndex * pageSize;
            for (int i = startSize; i < endSize; i++) {
                //获取记录的id
                int id = scoreDocs[i].doc;
                //文章的得分
                float score = scoreDocs[i].score;
                System.out.println("id:" + id + " 分章的得分：" + score);
                //查询数据表
                Document document = indexSearcher.doc(id);
                String articleId = document.get("articleId");
                String title = document.get("title");
                String content = document.get("content");
                System.out.println("articleId:" + articleId + " title:" + title + " content:" + content);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 添加索引
     */
    private static void addIndex() {
        try {
            //指定索引库的目录
            Directory directory = FSDirectory.open(Paths.get("E:\\MyProject\\faraway\\server\\luceneDB\\productDB"));

            //创建分词器  采用智能分词器
            Analyzer analyzer = new SmartChineseAnalyzer();
            //创建IndexWriterConfig实例，通过IndexWriterConfig实例来指定创建索引的相关信息，比如指定分词器
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            //指定索引的创建方式
            config.setOpenMode(OpenMode.CREATE_OR_APPEND);

            //创建索引  更新索引   删除索引都是IndexWriter来实现
            IndexWriter indexWriter = new IndexWriter(directory, config);


            //一个Document实例代表一条记录
            Document document = new Document();
            /**
             * StringField不会对关键字进行分词
             * Store.YES：会对数据进行存储并分词，如果为NO则不会对数据进行存储，索引还是会创建
             *
             * */
            document.add(new StringField("articleId", "0002", Store.YES));
            document.add(new TextField("title", "广州好玩的地方", Store.YES));
            document.add(new TextField("content", "广州白云山，5A级景区！", Store.YES));

            //通过indexWriter将数据写入至索引库
            indexWriter.addDocument(document);
            //提交事务
            indexWriter.commit();
            //关闭流资源
            indexWriter.close();
            System.out.println("=======索引创建成功======");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
