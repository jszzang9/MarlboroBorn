package com.marlboro.model;

import org.hibernate.Session;

import com.marlboro.model.dto.Article;

public class ArticleManager {
	public Article insertArticle(String title, String content) {
		Session session = HibernateUtil.getCurrentSession();

		Article article = new Article();
		article.setTitle(title);
		article.setContent(content);
		session.save(article);

		return article;
	}

	public Article updateArticle(Long id, String newTitle, String newContent) {  
		Article article = getArticle(id);  
		article.setTitle(newTitle);  
		article.setContent(newContent);  

		return article;  
	}  

	public void deleteArticle(Long id) {  
		Session session = HibernateUtil.getCurrentSession();  
		Article article = getArticle(id);  
		session.delete(article);  
	}  

	public Article getArticle(Long id) {  
		Session session = HibernateUtil.getCurrentSession();  
		return (Article) session.get(Article.class, id);  
	}  
}
