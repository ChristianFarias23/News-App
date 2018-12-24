package cl.ucn.disc.dsm.cafa.newsapp.room;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import cl.ucn.disc.dsm.cafa.newsapp.dao.ArticleDao;
import cl.ucn.disc.dsm.cafa.newsapp.model.Article;

@Database(entities = {Article.class}, version = 1, exportSchema = false)
public abstract class ArticleRoomDatabase extends RoomDatabase {

    public abstract ArticleDao articleDao();

    private static volatile ArticleRoomDatabase INSTANCE;

    static ArticleRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ArticleRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ArticleRoomDatabase.class, "word_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final ArticleDao dao;

        PopulateDbAsync(ArticleRoomDatabase db) {
            dao = db.articleDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // La base de datos inicia vacia la primera vez.


            //Article a1 = new Article();
            //a1.setTitle("Este es otro titulo");

            //dao.insert(a1);
            return null;
        }
    }
}
