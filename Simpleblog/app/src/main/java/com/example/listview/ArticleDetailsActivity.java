package com.example.listview;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.text.Html;

public class ArticleDetailsActivity extends AppCompatActivity {

    private TextView titreTextView, contenuTextView, auteurTextView, auteurDateTextView, dateTextView;
    private ImageButton retourButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);

        titreTextView = findViewById(R.id.title);
        contenuTextView = findViewById(R.id.ContenuArticle);
        auteurTextView = findViewById(R.id.auteur);
        auteurTextView = findViewById(R.id.auteurDate);
        dateTextView = findViewById(R.id.date);
        retourButton = findViewById(R.id.buttonRetour);

        // Récupération des données passées depuis MainActivity2
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String titre = extras.getString("titre");
            String contenu = extras.getString("contenu");
            String auteur = extras.getString("auteur");
            String date = extras.getString("date");


            String titreEnGras = "<b>" + titre + "</b>";
            titreTextView.setText(Html.fromHtml(titreEnGras));


            contenuTextView.setText(contenu);


            String auteurDate = "Écrit par : " + "<b>" + auteur + "</b>" + "\t\t\t\t\t\t\t\t\t\t\t" + date;
            auteurDateTextView.setText(Html.fromHtml(auteurDate));
        }

        retourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
