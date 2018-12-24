package cl.ucn.disc.dsm.cafa.newsapp.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import cl.ucn.disc.dsm.cafa.newsapp.model.Article;
import cl.ucn.disc.dsm.cafa.newsapp.model.NewsAPIResponse;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public class NewsController {

    /**
     * El servicio que se conecta a la api de noticias NewsAPIResponse.
     */
    public interface NewsService {

        /**
         * Obtiene los articulos mas destacados de CNN en Español.
         * @return
         */
        @Headers({"X-Api-Key: eb2f81591fc0443d8af6f4085724dc4a"})
        @GET("top-headlines?language=es")
        Call<NewsAPIResponse> getTopHeadlines();

        //TODO: Agregar mas metodos!

        @Headers({"X-Api-Key: eb2f81591fc0443d8af6f4085724dc4a"})
        @GET("everything?language=es")
        Call<NewsAPIResponse> getEverything(@Query("q") String query);
    }

    /**
     * ...
     */
    private static final HttpLoggingInterceptor interceptor  = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.HEADERS);

    /**
     * ...
     */
    private static final OkHttpClient cliente = new OkHttpClient.Builder()
            .addInterceptor(interceptor).build();

    /**
     * Instancia de retrofit.
     */
    private static final Retrofit retro = new Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(cliente)
            .build();

    /**
     * Crea mi servicio declarado en la interface a traves de retrofit.
     */
    private static final NewsService servicio = retro.create(NewsService.class);

    /**
     * Metodo que retorna los articulos mas destacados de CNN en Español.
     * TODO: Modificar este metodo (o crear nuevos) para obtener resultados personalizados.
     * @return Lista de articulos.
     * @throws IOException
     */
    public static List<Article> getArticles() throws IOException {
        Call<NewsAPIResponse> callNews = servicio.getTopHeadlines();
        NewsAPIResponse news = callNews.execute().body();
        return news.getArticles();
    }

    public static List<Article> getArticles_Everything(String query) throws IOException {
        Call<NewsAPIResponse> callNews = servicio.getEverything(query);
        NewsAPIResponse news = callNews.execute().body();
        return news.getArticles();
    }
}
