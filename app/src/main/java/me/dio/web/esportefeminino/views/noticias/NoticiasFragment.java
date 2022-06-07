package me.dio.web.esportefeminino.views.noticias;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import me.dio.web.esportefeminino.data.Configuracao;
import me.dio.web.esportefeminino.databinding.FragmentNoticiasBinding;
import me.dio.web.esportefeminino.model.Noticia;
import me.dio.web.esportefeminino.views.detalhes.DetalhesActivity;

/*
* Fragmento da Tela de Notícias
* */

public class NoticiasFragment extends Fragment {

    private FragmentNoticiasBinding binding;
    private Configuracao configuracao;
    private Noticia noticia;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNoticiasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        configuracao = new Configuracao(getContext());
        configuracao.configurar();

        configuracoesDestaque(root);
        configuracoesNoticias();

        return root;
    }

    private void configuracoesNoticias() {
        LinearLayoutManager linear = new LinearLayoutManager(getContext());
        linear.setOrientation(RecyclerView.VERTICAL);

        RecyclerView recyclerViewNoticias = binding.recyclerViewNoticiasCard;
        recyclerViewNoticias.setLayoutManager(linear);

        configuracao.getNoticias(recyclerViewNoticias);
    }

    private void configuracoesDestaque(View root) {
        TextView titulo = binding.tituloDestaqueNoticias;
        TextView descricao = binding.descricaoNoticias;
        ImageView imagemDestaque = binding.imagemDestaqueNoticias;
        ImageView imagemCompartilhar = binding.imagemCompartilhamentoNoticias;

        // Compartilha os dados
        imagemCompartilhar.setOnClickListener(view -> {
            Intent enviarIntent = new Intent(Intent.ACTION_SEND);
            noticia = new Noticia();
            noticia.setTitulo(titulo.getText().toString());
            enviarIntent.putExtra(Intent.EXTRA_TITLE, noticia.getTitulo());
            enviarIntent.setType("text/plain");
            root.getContext().startActivity(Intent.createChooser(enviarIntent, null));
        });

        configuracao.getNoticiasPorId(titulo,descricao, imagemDestaque);

        // Abre os dados detalhados na Activity Detalhes
        imagemDestaque.setOnClickListener(view -> {
            configuracao.getTranferirDadosParaDetalhes(titulo,descricao, imagemDestaque);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}