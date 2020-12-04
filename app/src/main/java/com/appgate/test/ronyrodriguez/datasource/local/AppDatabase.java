package com.appgate.test.ronyrodriguez.datasource.local;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Attempt.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract AttemptDao attemptDao();

    private static volatile AppDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "appgatedb")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final UserDao userDao;
        private final AttemptDao attemptDao;


        PopulateDbAsync(AppDatabase db) {
            userDao = db.userDao();
            attemptDao = db.attemptDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            userDao.deleteAll();
            attemptDao.deleteAll();

            User user = new User("admin@appgate.com", "Pruebas1");
            long id = userDao.insert(user);

            Attempt attempt = new Attempt(id, "2020-12-01 12:33", "Valid");
            attemptDao.insert(attempt);

            Attempt attempt2 = new Attempt(id, "2020-12-24 04:15", "Not Valid");
            attemptDao.insert(attempt2);

            return null;
        }
    }
}

/*
  AppDatabase db = Room.databaseBuilder(getApplicationContext(),
            AppDatabase.class, "database-name").build();
 */