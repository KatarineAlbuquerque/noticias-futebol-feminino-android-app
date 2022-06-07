package me.dio.web.esportefeminino.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import me.dio.web.esportefeminino.R;
import me.dio.web.esportefeminino.model.Noticia;
import me.dio.web.esportefeminino.views.detalhes.DetalhesActivity;
/*
* Adapter da lista da Tela de Notícias
* */
public class TelaNoticiasAdapter extends RecyclerView.Adapter<TelaNoticiasAdapter.ViewHolder> {

    private List<Noticia> noticias;
    private Context contexto;

    public TelaNoticiasAdapter(List<Noticia> noticias) {
        this.noticias = noticias;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.noticia_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Noticia noticia = noticias.get(position);

        Picasso.get().load(noticia.getImagem()).fit().centerCrop(250).into(holder.getImagemDestaque());
        holder.getTitulo().setText(noticia.getTitulo());
        holder.getDescricao().setText(noticia.getDescricao());

        // Compartilha dados
        holder.getImagemCompartilhar().setOnClickListener(view -> {
            Intent enviarIntent = new Intent(Intent.ACTION_SEND);
            enviarIntent.putExtra(Intent.EXTRA_TITLE, noticia.getTitulo());
            enviarIntent.setType("text/plain");
            holder.itemView.getContext().startActivity(Intent.createChooser(enviarIntent, null));
        });

        // Envia dados para a Activity Detalhes
        holder.getConstraintLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), DetalhesActivity.class);
                i.putExtra("imagem", noticia.getImagem());
                i.putExtra("titulo", noticia.getTitulo());
                i.putExtra("descricao", noticia.getDescricao());
                view.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return noticias.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView titulo;
        private TextView descricao;
        private ImageView imagemDestaque;
        private ImageView imagemCompartilhar;
        private ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagemDestaque = itemView.findViewById(R.id.imageItemNoticiaCard);
            imagemCompartilhar = itemView.findViewById(R.id.imagemCompartilharNoticiasCard);
            titulo = itemView.findViewById(R.id.tituloItemNoticiaCard);
            descricao = itemView.findViewById(R.id.descricaoItemNoticiasCard);
            constraintLayout = itemView.findViewById(R.id.layoutNoticias);
        }

        public TextView getTitulo() {
            return titulo;
        }
        public TextView getDescricao() {
            return descricao;
        }
        public ImageView getImagemDestaque() {
            return imagemDestaque;
        }
        public ImageView getImagemCompartilhar() {
            return imagemCompartilhar;
        }
        public ConstraintLayout getConstraintLayout() {
            return constraintLayout;
        }
    }
}
