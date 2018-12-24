package cl.ucn.disc.dsm.cafa.newsapp.room;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import cl.ucn.disc.dsm.cafa.newsapp.model.Article;

public class ArticleViewModel extends AndroidViewModel {

    private ArticleRepository repository;

    private LiveData<List<Article>> allArticles;

    public ArticleViewModel (Application application) {
        super(application);
        repository = new ArticleRepository(application);
        allArticles = repository.getAllArticles();
    }

    public LiveData<List<Article>> getAllArticles() {
        return allArticles;
    }

    public void insert(Article article) {
        repository.insert(article);
    }
}
