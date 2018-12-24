package cl.ucn.disc.dsm.cafa.newsapp.room;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cl.ucn.disc.dsm.cafa.newsapp.R;
import cl.ucn.disc.dsm.cafa.newsapp.model.Article;

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder>{

    class ArticleViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;

        private ArticleViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
        }
    }

    private final LayoutInflater inflater;
    private List<Article> articles; // Cached copy of words

    public ArticleListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recyclerview_article, parent, false);
        return new ArticleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        if (articles != null) {
            Article current = articles.get(position);
            //SETTERS:
            holder.title.setText(current.getTitle());
        } else {
            // Covers the case of data not being ready yet.
            holder.title.setText("No Word");
        }
    }

    public void setArticles(List<Article> articles){
        this.articles = articles;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // articles has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (articles != null)
            return articles.size();
        else return 0;
    }
}
