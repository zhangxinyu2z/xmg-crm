package com._520it.crm.service.impl;

import com._520it.crm.domain.Know;
import com._520it.crm.mapper.KnowMapper;
import com._520it.crm.page.PageResult;
import com._520it.crm.query.KnowQueryObject;
import com._520it.crm.service.IKnowService;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class KnowServiceImpl implements IKnowService{
    @Autowired
	private KnowMapper dao;
	//删除
	@Override
	public int delete(Long id) {
		
		return dao.deleteByPrimaryKey(id);
	}

	//保存
	@Override
	public int save(Know record) {
		
		return dao.insert(record);
	}

	//查询单条
	@Override
	public Know selectByPrimaryKey(Long id) {
	
		return dao.selectByPrimaryKey(id);
	}

	//查询全部
	@Override
	public List<Know> selectAll() {
		
		return dao.selectAll();
	}

	//修改
	@Override
	public int update(Know record) {
		
		return dao.updateByPrimaryKey(record);
	}
	
	//从数据库中查询出所有,写入到lucene库中
  @Override
  public void indexWriterReload(){
	  //查询出所有的数据
	  List<Know> knows = dao.selectAll();
	  IndexWriter writer = null;
	  try {
		  //创建目录文件,存储索引文件
		  Directory d = FSDirectory.open(new  File("C:/lucene"));
		  //语言分析接口
		  Analyzer analyzer  = new IKAnalyzer();
		//配置对象(版本,语言分析接口)
		  IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_4_10_4, analyzer  );
		//索引写入对象  
		 writer = new IndexWriter(d, conf );
		 FieldType type = new FieldType();
		 type.setIndexed(true);
		 type.setStored(true);
		 //创建文档
		 for (int i = 0; i < knows.size(); i++) {
			 Know know = knows.get(i);
			 Document doc = new Document();
			 doc.add(new Field("id", know.getId()+"", type));
			 doc.add(new Field("context",know.getContext() , type));
			 
			 //把对象写入文档中
			 writer.addDocument(doc);
			 //将写入内容提交
			 writer.commit();
		}
	} catch (IOException e) {
		
		e.printStackTrace();
	}finally{
		if(writer != null){
			try {
				writer.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
	}
  }  
  
  
  //从lucene库中查询数据
   @Override
   public  PageResult querySearch(KnowQueryObject qo){
	   Directory directory =  null;
	   IndexReader r = null;;
	   //查询条件
	   String keyword = qo.getKeyword();
	   String[] keywords = {keyword};
	   List<Know> knowList = new ArrayList<>();
	try {
		 //读取索引库的路径
		directory = FSDirectory.open(new File("C:/lucene"));
		//需要读取索引库中的内容
		r = DirectoryReader.open(directory);
		//创建索引读取对象
	   IndexSearcher searcher = new IndexSearcher(r);
	   //分词器
	   Analyzer analyzer =  new  IKAnalyzer();
	   //查询那些字段
	   String[] fileds = {"context"};
	   //查询规则
	   Occur[] fiags = {Occur.SHOULD};
	  //创建语验分析接口,分词器
	   Query query = MultiFieldQueryParser.parse(keywords, fileds, fiags, analyzer);
	   //返回符合条件的结果
	   TopDocs search = searcher.search(query, 1000);//只查询符合条件的1000条记录
	   
	   //高亮显示查询的结果
	   Formatter formatter = new SimpleHTMLFormatter("<font color = \"red\">","</font>");
	   Scorer scorer = new QueryScorer(query);
	   Highlighter h = new Highlighter(formatter, scorer);
	   //设置高亮显示多少个字符
	   h.setMaxDocCharsToAnalyze(1000);
	   ScoreDoc[] sds = search.scoreDocs;
	   ScoreDoc scoreDoc = null;
	   Document doc = null;
	   String hContext = null;
	   for (int i = 0; i < sds.length; i++) {
		   scoreDoc = sds[i];
		   doc = searcher.doc(scoreDoc.doc);
		   //如果需要查找的内容中没有找到内容,所以先判断你一下,如果找到则返回找到的内容,否则则返回之前的内容
		   String fieldName = "context";
		   hContext = h.getBestFragment(analyzer, fieldName , doc.get("context"));
//		   String context = doc.get("context");
//		   hContext = hContext != null? hContext:context;
		   //封装到对象中
		   Know know = new Know();
		   know.setId(Long.parseLong(doc.get("id")));
		   know.setContext(hContext);
		   knowList.add(know);
	}
	} catch (Exception e) {
		e.printStackTrace();
	}
		return new PageResult(Long.parseLong(knowList.size() + ""),knowList);
	
   }
   
   
@Override
public PageResult query(KnowQueryObject qo) {
	  // 根据查询条件查询出总条数
        List<Know> listData = dao.queryByCondition(qo);
        return new PageResult(Long.parseLong(listData.size()+""), listData);
    }

@Override
public Know queryContextById(Long id) {
	
	return dao.queryContextById(id);
}
}
