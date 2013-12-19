package com.marlboro.core.model.manager;

import org.hibernate.Session;

import com.marlboro.core.model.HibernateUtil;
import com.marlboro.core.model.dto.Article;
import com.marlboro.util.Generator;

public class ArticleManager {
	public Article insertArticle(String marlboro_Id, String marlboro_Pw, String marlboro_Content) {
		Session session = HibernateUtil.getCurrentSession();
		
		String primaryKey = Generator.makeSynthesisKey(marlboro_Id);
		
		Article article = new Article();
		article.setPrimaryKey(primaryKey);
		article.setMarlboro_Id(marlboro_Id);
		article.setMarlboro_Pw(marlboro_Pw);
		article.setMarlboro_Content(marlboro_Content);
		session.save(article);
		return article;
	}

	public Article updateArticle(String primaryKey, String newPw, String newContent) {  
		Article article = getArticle(primaryKey);  
		article.setMarlboro_Pw(newPw);  
		article.setMarlboro_Content(newContent);  

		return article;  
	}  

	public void deleteArticle(String primaryKey) {  
		Session session = HibernateUtil.getCurrentSession();  
		Article article = getArticle(primaryKey);  
		session.delete(article);  
	}  

	public Article getArticle(String primaryKey) {  
		Session session = HibernateUtil.getCurrentSession();  
		return (Article) session.get(Article.class, primaryKey);  
	}  
}
