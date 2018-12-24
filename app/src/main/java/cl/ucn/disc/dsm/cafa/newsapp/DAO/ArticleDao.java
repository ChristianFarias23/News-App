package cl.ucn.disc.dsm.cafa.newsapp.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import cl.ucn.disc.dsm.cafa.newsapp.model.Article;

@Dao
public interface ArticleDao {

    @Insert
    void insert(Article article);

    @Query("DELETE FROM article")
    void deleteAll();

    @Query("SELECT * from article ORDER BY publishedAt DESC")
    List<Article> getAllArticles();
}
