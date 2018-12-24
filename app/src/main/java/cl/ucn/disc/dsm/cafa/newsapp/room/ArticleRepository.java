package cl.ucn.disc.dsm.cafa.newsapp.room;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import cl.ucn.disc.dsm.cafa.newsapp.dao.ArticleDao;
import cl.ucn.disc.dsm.cafa.newsapp.model.Article;

public class ArticleRepository {

    private ArticleDao articleDao;
    private LiveData<List<Article>> allArticles;

    ArticleRepository(Application application) {
        ArticleRoomDatabase db = ArticleRoomDatabase.getDatabase(application);
        articleDao = db.articleDao();
        allArticles = articleDao.getAllArticles();
    }

    LiveData<List<Article>> getAllArticles() {
        return allArticles;
    }


    public void insert (Article article) {
        new insertAsyncTask(articleDao).execute(article);
    }

    private static class insertAsyncTask extends AsyncTask<Article, Void, Void> {

        private ArticleDao mAsyncTaskDao;

        insertAsyncTask(ArticleDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Article... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public int count(){ return articleDao.count();}
}
