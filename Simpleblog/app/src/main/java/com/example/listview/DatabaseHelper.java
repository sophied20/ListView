package com.example.listview;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import java.util.List;
import java.util.Date;
import java.util.ArrayList;
import android.database.Cursor;
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "simple.db";

    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_ARTICLES = "articles";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITRE = "titre";
    private static final String COLUMN_AUTEUR = "auteur";
    private static final String COLUMN_CONTENU = "contenu";
    private static final String COLUMN_DATE = "date";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DatabaseContract.ArticleEntry.TABLE_ARTICLES + " (" +
                    DatabaseContract.ArticleEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DatabaseContract.ArticleEntry.COLUMN_TITRE + " TEXT," +
                    DatabaseContract.ArticleEntry.COLUMN_CONTENU + " TEXT," +
                    DatabaseContract.ArticleEntry.COLUMN_AUTEUR + " TEXT," +
                    DatabaseContract.ArticleEntry.COLUMN_DATE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DatabaseContract.ArticleEntry.TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public List<Article> getAllArticles() {
        List<Article> articles = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        String[] columns = {COLUMN_ID, COLUMN_TITRE, COLUMN_AUTEUR, COLUMN_CONTENU, COLUMN_DATE};

        Cursor cursor = db.query("articles", columns, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITRE));
                String author = cursor.getString(cursor.getColumnIndex(COLUMN_CONTENU));
                String content = cursor.getString(cursor.getColumnIndex(COLUMN_AUTEUR));
                String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));

                Article article = new Article(id, title, author, content, date);
                articles.add(article);
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();

        return articles;
    }

}


