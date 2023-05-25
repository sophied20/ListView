package com.example.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.content.Intent;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

public class AjoutActivity extends AppCompatActivity {

    private EditText titreArticleEditText;
    private EditText contenuEditText;

    private EditText auteurEditText;
    private Button ajouterButton;

    private ImageButton piwbutton;
    private DatabaseHelper databaseHelper;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout);

        titreArticleEditText = findViewById(R.id.ajout_title);
        contenuEditText = findViewById(R.id.ajout_contenu);
        ajouterButton = findViewById(R.id.ajoute);
        piwbutton = findViewById(R.id.buttoncroix);

        databaseHelper = new DatabaseHelper(this);

        ajouterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajouterArticle();
            }
        });

        piwbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AjoutActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void ajouterArticle() {


        String titre = titreArticleEditText.getText().toString().trim();
        String contenu = contenuEditText.getText().toString().trim();
        String auteur = auteurEditText.getText().toString().trim();

        Calendar calendar = Calendar.getInstance();
                String date = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            date = String.valueOf(LocalDate.now());
        }

        if (!titre.isEmpty() && !contenu.isEmpty()) {
            SQLiteDatabase db = databaseHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(DatabaseContract.ArticleEntry.COLUMN_TITRE, titre);
            values.put(DatabaseContract.ArticleEntry.COLUMN_CONTENU, contenu);
            values.put(DatabaseContract.ArticleEntry.COLUMN_AUTEUR, auteur);
            values.put(DatabaseContract.ArticleEntry.COLUMN_DATE, date);

            long newRowId = db.insert(DatabaseContract.ArticleEntry.TABLE_NAME, null, values);

            if (newRowId != -1) {
                Toast.makeText(this, "Article ajouté avec succès", Toast.LENGTH_SHORT).show();
                titreArticleEditText.setText("");
                contenuEditText.setText("");
                auteurEditText.setText(""); // Réinitialiser le champ auteurEditText
            } else {
                Toast.makeText(this, "Erreur lors de l'ajout de l'article", Toast.LENGTH_SHORT).show();
            }

            db.close();
        } else {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();

        }

    }
}