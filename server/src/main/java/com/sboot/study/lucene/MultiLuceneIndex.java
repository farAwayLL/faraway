package com.sboot.study.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author faraway
 * @date 2019/2/26 11:12
 * 全文检索(多字段查询)  包含全文检索和高亮显示
 * <p>
 * 操作：1.先删除索引
 * 2.新增三条(id：10，title：广州好玩的地方，content：广州白云山，5A级景区，是广州的热门景点
 * id：11，title：广州好玩的地方，content：白云山，5A级景区，是热门景点
 * id：12，title：好玩的地方，content：广州白云山，5A级景区，是广州的热门景点)
 * 3.输入"广州"关键字，应该三个都能查出来
 */
@RestController
@RequestMapping("/lucene")
public class MultiLuceneIndex {

//    public static void main(String[] args) {
////        deleteIndex();
////        addIndex();
//        searchIndex();
//    }

    /**
     * 全文检索
     */
    @GetMapping("/search")
    private String searchIndex() {
        try {
            //指定要检索的索引库的位置
            Directory directory = FSDirectory.open(Paths.get("E:\\MyProject\\faraway\\server\\luceneDB\\productDB"));
            //DirectoryReader的open方法指定需要读取的索引库信息，并返回相应的实例
            IndexReader indexReader = DirectoryReader.open(directory);
            //创建IndexSearcher实例，通过IndexSearcher实例进行全文检索
            IndexSearcher indexSearcher = new IndexSearcher(indexReader);
            /**TermQuery:中指定了查询的关键字以及查询哪一个字段，TermQuery不会对关键字进行分词*/
//弃用          Query query = new TermQuery(new Term("title", "lucene"));

            /**替换为*/
            //创建智能分词器
            Analyzer analyzer = new SmartChineseAnalyzer();
            //创建QueryParse实例
            QueryParser queryParser = new MultiFieldQueryParser(new String[]{"title", "content"}, analyzer);
            //通过QueryParser得到Query实例
            Query query = queryParser.parse("广州好玩的地方");
            /**替换结束*/
            /**问题，当前只能查询标题，并不能全文检索，因此还需要优化
             * 解决在创建QueryParser对象时候创建为MultiFieldQueryParser对象，第一个参数为String数组
             */

            /*******************************高亮相关代码*************************************/
            //格式器，通过格式器指定怎样对关键字进行处理
            Formatter formatter = new SimpleHTMLFormatter("<font color='red'>", "</font>");

            //通过Scorer包装query实例
            Scorer fragmentScorer = new QueryScorer(query);

            //创建高亮器
            Highlighter highlighter = new Highlighter(formatter, fragmentScorer);


            //创建格式化片段
            Fragmenter fragmenter = new SimpleFragmenter(10);
            //将格式化片段实例与高亮器关联      因为最终是通过 高亮器Highlighter对文本信息进行处理
            highlighter.setTextFragmenter(fragmenter);

            /*******************************高亮相关代码*************************************/


            /**
             * 通过indexSearcher进行检索并指定两个参数
             *  第一个参数：封装查询的相关信息，比如说查询的关键字，是否需要分词或者需要分词的话采取什么分词器
             * 	第二个参数：最多只要多少条记录
             */
            TopDocs topDocs = indexSearcher.search(query, 20);
            //通过topDocs获取匹配全部记录
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            System.out.println("获取到的记录数：" + scoreDocs.length);
            List<String> higtLightList = new ArrayList<>();
            for (int i = 0; i < scoreDocs.length; i++) {
                //获取记录的id
                int id = scoreDocs[i].doc;
                //文章的得分
                float score = scoreDocs[i].score;
                System.out.println("数据表id:" + id + " 分章的得分：" + score);
                //查询数据表
                Document document = indexSearcher.doc(id);
                String articleId = document.get("articleId");
                String title = document.get("title");
                String content = document.get("content");
                System.out.println("数据表id对应的articleId:" + articleId + " title:" + title + " content" + content);
                /**
                 *通过高亮器对title和content进行高亮处理
                 *getBestFragment方法中三个参数的含义：1、分词器 2、哪一个字段需要进行高亮 3、需要进行高亮的文本信息
                 *备注：如果需要进行高亮的文本信息，不包括查询的关键字则getBestFragment会返回null出去
                 *
                 * */
                String postTitle = highlighter.getBestFragment(analyzer, "title", title);
                String postContent = highlighter.getBestFragment(analyzer, "content", content);

                System.out.println(" 高亮后title:" + postTitle + " 高亮后content:" + postContent);

                higtLightList.add(" 高亮后title:" + postTitle + " 高亮后content:" + postContent);

            }
            return higtLightList.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 新增索引
     */
    private static void addIndex() {
        try {
            //指定索引库的目录
            Directory directory = FSDirectory.open(Paths.get("E:\\MyProject\\faraway\\server\\luceneDB\\productDB"));
            //单字分词器，待改善
            //Analyzer analyzer = new StandardAnalyzer();
            /**词库分词，智能分词器，使用默认的停用词*/
            Analyzer analyzer = new SmartChineseAnalyzer();
            //创建IndexWriterConfig实例，通过IndexWriterConfig实例配置索引创建模式
            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
            //设置索引的创建模式(索引不存在就创建，索引存在就追加)
            indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
            //lucene中是通过IndexWriter进行索引的CRUD
            IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
            //在lucene中一个Document实例代表一条记录
            Document document = new Document();
            //StringField不会对value(关键字)进行拆分；Store.YES会对数据进行储存，如果为NO不会进行存储，但仍会创建索引
            document.add(new StringField("articleId", "12", Store.YES));
            document.add(new TextField("title", "好玩的地方", Store.YES));
            document.add(new TextField("content", "广州白云山，5A级景区，是广州的热门景点", Store.YES));
            //将索引写入索引库
            indexWriter.addDocument(document);
            //提交事务
            indexWriter.commit();
            //关闭流资源
            indexWriter.close();
            System.out.println("=======索引新增成功======");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除索引
     */
    private static void deleteIndex() {
        try {
            //指定索引库的目录
            Directory directory = FSDirectory.open(Paths.get("E:\\MyProject\\faraway\\server\\luceneDB\\productDB"));
            //单字分词器，待改善
            //Analyzer analyzer = new StandardAnalyzer();
            /**词库分词，智能分词器，使用默认的停用词*/
            Analyzer analyzer = new SmartChineseAnalyzer();
            //创建IndexWriterConfig实例，通过IndexWriterConfig实例来指定创建索引的相关信息，比如指定分词器
            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
            //指定索引的创建方式
            indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
            //创建索引/更新索引/删除索引都是IndexWriter来实现
            IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
            //删除指定的索引
            indexWriter.deleteDocuments(new Term("articleId", "10"));
            //删除索引库中全部的索引
            indexWriter.deleteAll();
            //提交事务
            indexWriter.commit();
            //关闭流资源
            indexWriter.close();
            System.out.println("=======索引删除成功======");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
