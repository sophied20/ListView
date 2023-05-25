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

    public static final String TABLE_ARTICLES = "articles";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITRE = "titre";
    public static final String COLUMN_AUTEUR = "auteur";
    public static final String COLUMN_CONTENU = "contenu";
    public static final String COLUMN_DATE = "date";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_ARTICLES + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_TITRE + " TEXT," +
                    COLUMN_CONTENU + " TEXT," +
                    COLUMN_AUTEUR + " TEXT," +
                    COLUMN_DATE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_ARTICLES;

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

        Cursor cursor = db.query(TABLE_ARTICLES, columns, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(COLUMN_ID);
            int titleIndex = cursor.getColumnIndex(COLUMN_TITRE);
            int authorIndex = cursor.getColumnIndex(COLUMN_AUTEUR);
            int contentIndex = cursor.getColumnIndex(COLUMN_CONTENU);
            int dateIndex = cursor.getColumnIndex(COLUMN_DATE);

            do {
                int id = cursor.getInt(idIndex);
                String title = cursor.getString(titleIndex);
                String author = cursor.getString(authorIndex);
                String content = cursor.getString(contentIndex);
                String date = cursor.getString(dateIndex);

                Article article = new Article(id, title, author, content, date);
                articles.add(article);
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();

        return articles;
    }

}



