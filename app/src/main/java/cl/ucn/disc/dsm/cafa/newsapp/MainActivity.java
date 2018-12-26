package cl.ucn.disc.dsm.cafa.newsapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cl.ucn.disc.dsm.cafa.newsapp.controllers.NewsController;
import cl.ucn.disc.dsm.cafa.newsapp.model.Article;
import cl.ucn.disc.dsm.cafa.newsapp.room.ArticleListAdapter;
import cl.ucn.disc.dsm.cafa.newsapp.room.ArticleViewModel;

public class MainActivity extends AppCompatActivity {

    private ArticleListAdapter adapter;
    private ArticleViewModel articleViewModel;
    private TextView emptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emptyTextView = findViewById(R.id.tv_empty);
        emptyTextView.setText("Cargando...");

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Recycler View
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        adapter = new ArticleListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // View Model
        articleViewModel = ViewModelProviders.of(this).get(ArticleViewModel.class);

        articleViewModel.getAllArticles().observe(this, new Observer<List<Article>>() {

            @Override
            public void onChanged(@Nullable final List<Article> words) {
                // Update the cached copy of the words in the adapter.
                adapter.setArticles(words);

                if (adapter.getItemCount() == 0 ){
                    emptyTextView.setVisibility(View.VISIBLE);
                    emptyTextView.setText("Eso es todo por ahora.");
                }else {
                    emptyTextView.setVisibility(View.GONE);
                }
            }

        });

        Log.d("TAG","Created");

    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d("TAG","Started");

        // Intenta descargar articulos desde la api.
        articleViewModel.downloadArticles();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        // El buscador que se encuentra en el toolbar.
        MenuItem buscador = menu.findItem(R.id.item_buscar);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(buscador);

        // Listener para cuando se busque cierto texto.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Buscar...

                AsyncTask.execute(() -> {

                    List<Article> listaResultados = null;

                    try {
                        listaResultados = NewsController.getArticles_Everything(query);
                    } catch (Exception e) {
                        //e.printStackTrace();
                    }

                    mostrarResultados(listaResultados);


                });

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                adapter.setArticles(articleViewModel.getAllArticles().getValue());
                //articleViewModel.getAllArticles();
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.item_actualizar:
                //adapter.setArticles(articleViewModel.getAllArticles().getValue());
                Toast.makeText(this, "Actualizando...", Toast.LENGTH_SHORT).show();
                articleViewModel.downloadArticles();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    void mostrarResultados(final List<Article> listaResultados){
        runOnUiThread(() -> {

            adapter.setArticles(listaResultados);

            String resultado = null;
            if (adapter.getItemCount() > 0){
                Toast.makeText(this, "Se encontraron "+adapter.getItemCount() + " resultados.", Toast.LENGTH_SHORT).show();
                emptyTextView.setVisibility(View.GONE);
            }
            else {
                emptyTextView.setVisibility(View.VISIBLE);
                emptyTextView.setText("No se encontraron resultados para su busqueda.");
            }
        });
    }
}
