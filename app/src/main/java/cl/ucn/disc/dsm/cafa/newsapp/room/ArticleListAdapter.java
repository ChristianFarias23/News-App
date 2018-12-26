package cl.ucn.disc.dsm.cafa.newsapp.room;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cl.ucn.disc.dsm.cafa.newsapp.R;
import cl.ucn.disc.dsm.cafa.newsapp.model.Article;

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder>{

    class ArticleViewHolder extends RecyclerView.ViewHolder {
        private final TextView author;
        private final TextView title;
        private final TextView description;
        private final TextView publishedAt;
        private final SimpleDraweeView image;

        private ArticleViewHolder(View itemView, View.OnClickListener clickListener) {
            super(itemView);
            author = itemView.findViewById(R.id.tv_author);
            title = itemView.findViewById(R.id.tv_title);
            description = itemView.findViewById(R.id.tv_description);
            publishedAt = itemView.findViewById(R.id.tv_published_at);
            image = itemView.findViewById(R.id.sdv_image);

            itemView.setOnClickListener(clickListener);
        }
    }

    private final LayoutInflater inflater;
    private List<Article> articles; // Cached copy of words
    private View.OnClickListener myClickListener;


    public ArticleListAdapter(Context context, View.OnClickListener clickListener) {
        inflater = LayoutInflater.from(context);
        myClickListener = clickListener;
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recyclerview_article, parent, false);
        return new ArticleViewHolder(itemView, myClickListener);
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        if (articles != null) {
            Article current = articles.get(position);

            // Custom:
            String parsedDate = parsePublishedAt(current.getPublishedAt());

            // Vista personalizada de la fuente:

            String autor = current.getAuthor();
            String fuente = current.getSource().getName();
            String src;

            if (autor != null){
                src = autor;

                if (fuente != null && !autor.equals(fuente)) {
                    src = new StringBuilder().append(src).append(", ").append(fuente).toString();
                }
            } else {
                src = fuente;
            }

            // Mostrar la imagen usando fresco. (Solo si el articulo posee una url a la imagen!)
            if (current.getUrlToImage() != null) {
                Uri uri = Uri.parse(current.getUrlToImage());
                holder.image.setImageURI(uri);
            }


            // Setters:
            holder.author.setText(src);
            holder.title.setText(current.getTitle());
            holder.description.setText(current.getDescription());
            holder.publishedAt.setText(parsedDate);

            // Podemos guardar lo que sea en el tag, asi que guardaremos la url del articulo.
            holder.itemView.setTag(current.getUrl());

        } else {
            // Covers the case of data not being ready yet.
            holder.title.setText("No Article");
        }
    }

    static Locale localeES = new Locale("es", "ES");
    static SimpleDateFormat format = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy '-' HH:mm", localeES);

    //FIXME
    public static String parsePublishedAt(String toParseDate) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse(toParseDate);
            return format.format(date);

        } catch (ParseException e) {
            //e.printStackTrace();
        }

        return toParseDate;
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
        return 0;
    }
}
