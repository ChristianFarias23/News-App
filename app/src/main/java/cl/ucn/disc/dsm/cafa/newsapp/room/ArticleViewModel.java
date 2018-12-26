package cl.ucn.disc.dsm.cafa.newsapp.room;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cl.ucn.disc.dsm.cafa.newsapp.controllers.NewsController;
import cl.ucn.disc.dsm.cafa.newsapp.model.Article;

public class ArticleViewModel extends AndroidViewModel {

    // Nos comunicamos con la base de datos a traves del repositorio!
    private ArticleRepository repository;

    private LiveData<List<Article>> allArticles;

    public ArticleViewModel(Application application) {
        super(application);
        repository = new ArticleRepository(application);
        allArticles = repository.getAllArticles();
    }

    /**
     * Obtener los articulos guardados en la base de datos.
     *
     * @return
     */
    public LiveData<List<Article>> getAllArticles() {
        return allArticles;
    }

    /**
     * Agregar un articulo a la base de datos.
     *
     * @param article
     */
    public void insert(Article article) {
        repository.insert(article);
    }

    /**
     * Obtener la cantidad de articulos en la base de datos.
     *
     * @return
     */
    public int count() {
        return repository.count();
    }


    public void downloadArticles() {

        Log.d("TAG", "downloadArticles");


        // Obtener los articulos desde NewsController.
        AsyncTask.execute(() -> {

            Log.d("TAG", "-------------------");
            Log.d("TAG", "Descargando articulos...");
            Log.d("TAG", "-------------------");

            List<Article> listaArticulos = null;

            try {
                listaArticulos = NewsController.getArticles();
            } catch (Exception e) {
                // Ocurrio un error. No se pudieron obtener los articulos.
                Log.d("TAG", "-------------------");
                Log.d("TAG", "Ocurrio un error al obtener los articulos...");
                Log.d("TAG", "ERROR: " + e.getMessage() + "\n" + e.getStackTrace());
                Log.d("TAG", "-------------------");
            }

            if (listaArticulos != null) {
                // Guardar cada articulo en la base de datos. La lista allArticles se actualiza automaticamente.
                for (Article article : listaArticulos) {
                    Log.d("TAG", "-------------------");
                    Log.d("TAG", article.getTitle());
                    insert(article);
                }
                Log.d("TAG", "-------------------");
            }
        });
    }


    /**
     * Carga todos los articulos que estan en la base de datos
     */
    public void loadAllArticles(){
        this.allArticles = repository.getAllArticles();
    }

}
