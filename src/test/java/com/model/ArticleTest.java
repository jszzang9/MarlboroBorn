package com.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.marlboro.model.ArticleManager;
import com.marlboro.model.HibernateUtil;
import com.marlboro.model.dto.Article;

public class ArticleTest {
	private ArticleManager articleManager;
	private static Long id;
	
	@Before
	public void before() {
		articleManager = new ArticleManager();
		HibernateUtil.beginTransaction();
	}
	
	@After
	public void after() {
		HibernateUtil.closeSession();
	}
	
	@Test  
    public void insertArticle() {  
        try {  
            Article article = articleManager.insertArticle("제목", "내용");  
             
            assertNotSame(article.getId(), 0);  
            assertEquals(article.getTitle(), "제목");  
            assertEquals(article.getContent(), "내용");  
             
            id = article.getId();  
             
            HibernateUtil.commit();  
        } catch (Throwable ex) {  
            HibernateUtil.rollBack();  
            fail(ex.getMessage());  
        }  
    }  
     
	@Test  
    public void updateArtice() {  
        try {  
            Article article = articleManager.updateArticle(id, "제목 수정", "내용 수정");  
             
            assertEquals(article.getId(), id);  
            assertEquals(article.getTitle(), "제목 수정");  
            assertEquals(article.getContent(), "내용 수정");  
             
            HibernateUtil.commit();  
        } catch (Throwable ex) {  
            HibernateUtil.rollBack();  
            fail(ex.getMessage());  
        }  
    }  
     
    @Test  
    public void deleteArticle() {  
        try {  
            articleManager.deleteArticle(id);  
            Article article = articleManager.getArticle(id);  
             
            assertNull(article);  
             
            HibernateUtil.commit();  
        } catch (Throwable ex) {  
            HibernateUtil.rollBack();  
            fail(ex.getMessage());  
        }  
    }  
}
