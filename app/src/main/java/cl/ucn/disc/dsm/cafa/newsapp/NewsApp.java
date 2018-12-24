package cl.ucn.disc.dsm.cafa.newsapp;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

public class NewsApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Inicializar Fresco.
        Fresco.initialize(this);
    }
}
