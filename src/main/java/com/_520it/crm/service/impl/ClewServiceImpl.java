package com._520it.crm.service.impl;

import com._520it.crm.domain.Clew;
import com._520it.crm.mapper.ClewMapper;
import com._520it.crm.page.PageResult;
import com._520it.crm.query.ClewQueryObject;
import com._520it.crm.service.IClewService;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class ClewServiceImpl implements IClewService {
    private static int count = 0;

    @Autowired
    private ClewMapper dao;

    @Override
    public int save(Clew c) {
        return dao.insert(c);
    }

    @Override
    public int delete(Long id) {
        return dao.deleteByPrimaryKey(id);
    }

    @Override
    public Clew get(Long id) {
        return dao.selectByPrimaryKey(id);
    }

    @Override
    public int update(Clew c) {
        return dao.updateByPrimaryKey(c);
    }

    @Override
    public PageResult listAll() {
        return new PageResult(Long.parseLong(dao.selectAll().size() + ""), dao.selectAll());
    }

    @Override
    public void luceneWriteIndex() {
        System.out.println("同步数据库中的数据");
        // 查出所有
        List<Clew> clews = dao.selectAll();
        IndexWriter writer = null;
        try {
            //1:创建目录文件,存储索引文件
            Directory directory = FSDirectory.open(new File("D:/lucene/data2"));
            //2:创建语言分析接口(分词器)
            Analyzer analyzer = new IKAnalyzer();
            // 配置对象
            IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_4, analyzer);
            // 索引写入对象
            writer = new IndexWriter(directory, config);
            //----------------------------
            //删除之前的在lucene仓库中的数据
            int i1 = writer.numDocs();
            System.out.println("i1 = " + i1);
            writer.deleteAll();
            //----------------------------

            FieldType type = new FieldType();
            type.setIndexed(true);
            type.setStored(true);

            FieldType type2 = new FieldType();
            type2.setIndexed(true);
            type2.setStored(false);

            FieldType type3 = new FieldType();
            type3.setIndexed(false);
            type3.setStored(true);
            // 创建文档
            for (int i = 0; i < clews.size(); i++) {
                Clew c = clews.get(i);
                Document doc = new Document();
                doc.add(new Field("id", c.getId() + "", type));
                doc.add(new Field("inputtime", c.getInputtime() + "", type));
                doc.add(new Field("state", c.getState(), type));
                doc.add(new Field("name", c.getName(), type));
                doc.add(new Field("title", c.getTitle(), type));
                doc.add(new Field("url", c.getUrl(), type));
                doc.add(new Field("content", c.getContent(), type2));
                doc.add(new Field("content2", c.getContent().substring(0, 20), type3));
                // 把对象写入文档中
                writer.addDocument(doc);
                // 将写入内容提交
                writer.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public PageResult querybyLuceneAll(String keyword) {
        List<Clew> clewList = new ArrayList<>();
        //1:创建目录文件,存储索引文件
        try {
            Directory directory = FSDirectory.open(new File("D:/lucene/data2"));
            // 2:创建索引读取对象
            IndexReader r = DirectoryReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(r);
            // 3:创建语言分析接口(分词器)
            Analyzer analyzer = new IKAnalyzer();

//            String[] fields = {"id", "inputtime", "state", "name", "title", "url", "content"};
//            QueryParser queryParser = new MultiFieldQueryParser(Version.LUCENE_4_10_4, fields, analyzer);
            QueryParser queryParser = new QueryParser(keyword, analyzer);

            Query query = queryParser.parse(keyword);
            //TopDocs 搜索返回的结果
            TopDocs tds = searcher.search(query, 1000);//只返回前1000条记录


            ScoreDoc[] sds = tds.scoreDocs;
            ScoreDoc scoreDoc = null;
            Document doc = null;
            SimpleDateFormat sdf = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy",
                    Locale.ENGLISH);
            for (int i = 0; i < sds.length; i++) {
                Clew clew = new Clew();
                scoreDoc = sds[i];
                doc = searcher.doc(scoreDoc.doc);
                clew.setId(Long.parseLong(doc.get("id")));
                clew.setInputtime(sdf.parse(doc.get("inputtime")));
                clew.setState(doc.get("state"));
                clew.setContent(doc.get("content2"));
                clew.setName(doc.get("name"));
                clew.setUrl(doc.get("url"));
                clew.setTitle(doc.get("title"));
                clewList.add(clew);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return new PageResult(Long.parseLong(clewList.size() + ""), clewList);
    }

    @Override
    public PageResult querybyLuceneCondition(ClewQueryObject qo) {
        String keyword = qo.getKeyword();
        String[] keywords = {keyword, keyword};
        List<Clew> clewList = new ArrayList<>();
        //1:创建目录文件,存储索引文件
        try {
            Directory directory = FSDirectory.open(new File("D:/lucene/data2"));
            // 2:创建索引读取对象
            IndexReader r = DirectoryReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(r);
            // 3:创建语言分析接口(分词器)
            Analyzer analyzer = new IKAnalyzer();
            String[] fields = {"title", "content"};
            BooleanClause.Occur[] flags = {BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD};
            Query query = MultiFieldQueryParser.parse(keywords, fields, flags, analyzer);

            //TopDocs 搜索返回的结果
            TopDocs tds = searcher.search(query, 1000);//只返回前1000条记录

            /*高亮显示查询结果*/
            SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<font color='red'>", "</font>");
            QueryScorer scorer = new QueryScorer(query);
            Highlighter h = new Highlighter(formatter, scorer);
            // 限定前N个字符高亮显示,多余的会被忽略
            h.setMaxDocCharsToAnalyze(30);
            ScoreDoc[] sds = tds.scoreDocs;
            ScoreDoc scoreDoc = null;
            Document doc = null;
            String hTitle = null;
            String hContent = null;
            SimpleDateFormat sdf = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy",
                    Locale.ENGLISH);
            for (int i = 0; i < sds.length; i++) {
                scoreDoc = sds[i];
                doc = searcher.doc(scoreDoc.doc);
                // 需要注意的是，如果标题或者内容中没有需要高亮显示的关键字，那么标题或者内容是为null,所以做了下面的处理。判断标题或者内容是否空，如果空,则使用之前的数据
                hTitle = h.getBestFragment(analyzer, "title", doc.get("title"));
                String title = doc.get("title");
                hTitle = hTitle != null ? hTitle : title;
                hContent = h.getBestFragment(analyzer, "content", doc.get("content2"));
                String content = doc.get("content2");
                hContent = hContent != null ? hContent : content;
                // 封装到对象中
                Clew clew = new Clew();
                clew.setId(Long.parseLong(doc.get("id")));
                clew.setInputtime(sdf.parse(doc.get("inputtime")));
                clew.setState(doc.get("state"));
                clew.setContent(hContent);
                clew.setName(doc.get("name"));
                clew.setUrl(doc.get("url"));
                clew.setTitle(hTitle);
                clewList.add(clew);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new PageResult(Long.parseLong(clewList.size() + ""), clewList);
    }

    @Override
    public int deleteLuceneById(Long id) {
        Directory directory = null;
        IndexWriter writer = null;
        try {
            //1:创建目录文件,存储索引文件
            directory = FSDirectory.open(new File("D:/lucene/data2"));
            //2:创建语言分析接口(分词器)
            Analyzer analyzer = new IKAnalyzer();
            // 配置对象
            IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_4, analyzer);
            // 索引写入对象
            writer = new IndexWriter(directory, config);
            writer.deleteDocuments(new Term("id", String.valueOf(id)));
            writer.commit();

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return 1;
    }

    @Override
    public int reload() {
        try {
            luceneWriteIndex();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    @Override
    public String getContentById(Long id) {
        String content = dao.getContentById(id);
        return content;
    }


}
