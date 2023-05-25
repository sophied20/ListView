package com.example.listview;

import android.widget.BaseAdapter;
import android.content.Context;
import java.util.List;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.TextView;
public class ArticleAdapter  extends BaseAdapter {
    private Context context;
    private List<Article> articleList;

    public ArticleAdapter(Context context, List<Article> articleList) {
        this.context = context;
        this.articleList = articleList;
    }

    @Override
    public int getCount() {
        return articleList.size();
    }

    @Override
    public Object getItem(int position) {
        return articleList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Récupérez les informations de l'article pour la position donnée
        Article article = articleList.get(position);

        // Créez ou réutilisez la vue de l'élément de la liste
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.activity_all_articles, parent, false);
        }

        // Effectuez la liaison des données de l'article avec les vues de l'élément de la liste
        TextView titleTextView = convertView.findViewById(R.id.title);
        TextView contenuTextView = convertView.findViewById(R.id.desc);
        // ... Associez d'autres vues si nécessaire

        titleTextView.setText(article.getTitre());
        contenuTextView.setText(article.getContenu());
        // ... Définissez les valeurs des autres vues

        return convertView;
    }
}
