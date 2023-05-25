package com.example.listview;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Html;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity<ArticleViewHolder> extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<String> articles;
    private ArticleAdapter adapter;
    private DatabaseHelper databaseHelper;
    private Button returnButton;
    private ImageButton add_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //recyclerView = findViewById(R.id.recyclerView);
        Button add_Button = findViewById(R.id.addButton);
        add_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AjoutActivity.class);
                startActivity(intent);
            }
        });
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseHelper = new DatabaseHelper(this);
        private void loadArticles;() {
            List<Article> articles = databaseHelper.getAllArticles();
        };

        adapter = new ArticleAdapter(articles);
        recyclerView.setAdapter(adapter);

    private List<String> loadArticles() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query(ArticleAdapter.ArticleEntry.TABLE_NAME, null, null, null, null, null, null);
        List<String> articles = new ArrayList<>();

        while (cursor.moveToNext()) {
            int titreIndex = cursor.getColumnIndex(ArticleAdapter.ArticleEntry.COLUMN_TITRE);
            int contenuIndex = cursor.getColumnIndex(ArticleAdapter.ArticleEntry.COLUMN_CONTENU);
            int auteurIndex = cursor.getColumnIndex(ArticleAdapter.ArticleEntry.COLUMN_AUTEUR);
            int dateIndex = cursor.getColumnIndex(ArticleAdapter.ArticleEntry.COLUMN_DATE);

            String titre = cursor.getString(titreIndex);
            String contenu = cursor.getString(contenuIndex);
            String auteur = cursor.getString(auteurIndex);
            String date = cursor.getString(dateIndex);

            String titreEnGras = "<b>" + titre + "</b>";
            String article = titreEnGras + "<br>" + contenu + "<br>" + auteur + "<br>" + date;
            articles.add(article);
        }

        cursor.close();
        db.close();

        return articles;
    }

    // Classe ArticleAdapter
    private class ArticleAdapter extends RecyclerView.Adapter<ArticleViewHolder> {

        private List<String> articles;

        public ArticleAdapter(List<String> articles) {
            this.articles = articles;
        }

        @Override
        public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_all_articles, parent, false);
            return new ArticleViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ArticleViewHolder holder, int position) {
            String article = articles.get(position);
            holder.bind(article);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Séparer la chaîne de l'article en titre, contenu, auteur et date
                    String[] parts = article.split("<br>", 4);
                    String titre = parts[0];
                    String contenu = parts[1];
                    String auteur = parts[2];
                    String date = parts[3];

                    Intent intent = new Intent(MainActivity.this, ArticleDetailsActivity.class);
                    intent.putExtra("titre", titre);
                    intent.putExtra("contenu", contenu);
                    intent.putExtra("auteur", auteur);
                    intent.putExtra("date", date);
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return articles.size();
        }
    }

    // Classe ArticleViewHolder
    public class ArticleViewHolder extends RecyclerView.ViewHolder {

        private TextView articleTextView;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            articleTextView = itemView.findViewById(R.id.articleTextView);
        }

        public void bind(String article) {
            int maxLength = 100; // Limite de longueur du texte (nombre de caractères)
            if (article.length() > maxLength) {
                String truncatedText = article.substring(0, maxLength) + "...";
                articleTextView.setText(Html.fromHtml(truncatedText));
            } else {
                articleTextView.setText(Html.fromHtml(article));
            }
        }
    }


}