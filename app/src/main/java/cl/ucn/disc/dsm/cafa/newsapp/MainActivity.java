package cl.ucn.disc.dsm.cafa.newsapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import cl.ucn.disc.dsm.cafa.newsapp.model.Article;
import cl.ucn.disc.dsm.cafa.newsapp.room.ArticleListAdapter;
import cl.ucn.disc.dsm.cafa.newsapp.room.ArticleViewModel;

public class MainActivity extends AppCompatActivity {

    private ArticleViewModel articleViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final ArticleListAdapter adapter = new ArticleListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        articleViewModel = ViewModelProviders.of(this).get(ArticleViewModel.class);

        articleViewModel.getAllArticles().observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable final List<Article> words) {
                // Update the cached copy of the words in the adapter.
                adapter.setArticles(words);
            }
        });

    }
}
