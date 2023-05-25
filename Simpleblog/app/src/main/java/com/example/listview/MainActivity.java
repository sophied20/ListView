package com.example.listview;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<String> articles;
    private ArticleAdapter adapter;
    private DatabaseHelper databaseHelper;
    private Button returnButton;
    private Button add_Button;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add_Button = findViewById(R.id.addButton);

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

        // Chargement des articles depuis la base de données
        List<Article> articles = loadArticles();

        // Création de l'adaptateur et affectation à RecyclerView
        articles = new ArrayList<>();
        adapter = new ArticleAdapter(articles);
        recyclerView.setAdapter(adapter);

    }

    private List<Article> loadArticles() {
        // Charger les articles depuis la base de données ou autre source de données
        List<Article> articles = databaseHelper.getAllArticles();

        // Mettre à jour l'affichage de la liste des articles
        adapter.setArticles(articles);
        adapter.notifyDataSetChanged();

        return articles;
    }


    // Classe ArticleAdapter
    private class ArticleAdapter extends RecyclerView.Adapter<ArticleViewHolder> {

        private List<Article> articles;

        public ArticleAdapter(List<Article> articles) {
            this.articles = articles;
        }

        @Override
        public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_all_articles, parent, false);
            return new ArticleViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ArticleViewHolder holder, int position) {
            String article = String.valueOf(articles.get(position));
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

        public void setArticles(List<Article> articles) {
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
