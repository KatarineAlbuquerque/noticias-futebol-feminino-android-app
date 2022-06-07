package me.dio.web.esportefeminino.views.inicio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import me.dio.web.esportefeminino.data.Configuracao;
import me.dio.web.esportefeminino.databinding.FragmentInicioBinding;
import me.dio.web.esportefeminino.model.Noticia;

/*
* Fragmento da Tela Início
* */

public class InicioFragment extends Fragment {

    private FragmentInicioBinding binding;
    private Configuracao configuracao;

    private List<Noticia> noticias;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentInicioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerViewDestaques = getRecyclerViewDestaque();

        RecyclerView recyclerViewNoticias = getRecyclerViewNoticias();

        configuracoesRetrofit(recyclerViewDestaques, recyclerViewNoticias);

        return root;
    }

    private void configuracoesRetrofit(RecyclerView recyclerViewDestaques, RecyclerView recyclerViewNoticias) {
        configuracao = new Configuracao(getContext());
        configuracao.configurar();
        configuracao.getDestaquesInicio(recyclerViewDestaques);
        configuracao.getNoticiasInicio(recyclerViewNoticias);
    }

    @NonNull
    private RecyclerView getRecyclerViewDestaque() {
        LinearLayoutManager linear = new LinearLayoutManager(getContext());
        linear.setOrientation(RecyclerView.HORIZONTAL);

        RecyclerView recyclerViewDestaques = binding.recyclerViewDestaqueInicio;
        recyclerViewDestaques.setLayoutManager(linear);

        return recyclerViewDestaques;
    }

    @NonNull
    private RecyclerView getRecyclerViewNoticias() {
        LinearLayoutManager linear2 = new LinearLayoutManager(getContext());
        linear2.setOrientation(RecyclerView.HORIZONTAL);

        RecyclerView recyclerViewNoticias = binding.recyclerViewNoticiasInicio;
        recyclerViewNoticias.setLayoutManager(linear2);
        return recyclerViewNoticias;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}