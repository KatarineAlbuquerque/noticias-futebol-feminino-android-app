package me.dio.web.esportefeminino.data;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import me.dio.web.esportefeminino.R;
import me.dio.web.esportefeminino.adapter.TelaInicioAdapter;
import me.dio.web.esportefeminino.adapter.TelaNoticiasAdapter;
import me.dio.web.esportefeminino.model.Noticia;
import me.dio.web.esportefeminino.views.detalhes.DetalhesActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/*
* Configuração e chamadas Retrofit
* */
public class Configuracao {

    private Retrofit retrofit;
    private ConfiguracaoRetrofit servico;
    private Noticia noticia;
    private Context contexto;

    public Configuracao(Context contexto) {
        this.contexto = contexto;
    }

    // Configura o Retrofit
    public void configurar() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("https://katarinealbuquerque.github.io/noticias-api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        servico = retrofit.create(ConfiguracaoRetrofit.class);
    }
    // Lista os Detalhes da Fragment Início
    public void getDestaquesInicio(RecyclerView recyclerView) {
        this.servico.getServico().enqueue(new Callback<List<Noticia>>() {
            @Override
            public void onResponse(Call<List<Noticia>> call, Response<List<Noticia>> response) {
                List<Noticia> noticias = response.body();
                Collections.reverse(noticias);
                recyclerView.setAdapter(new TelaInicioAdapter(noticias));
            }

            @Override
            public void onFailure(Call<List<Noticia>> call, Throwable t) {
                mensagemErro();
            }
        });
    }
    // Lista as Notícias do Fragment Início
    public void getNoticiasInicio(RecyclerView recyclerView) {
        this.servico.getServico().enqueue(new Callback<List<Noticia>>() {
            @Override
            public void onResponse(Call<List<Noticia>> call, Response<List<Noticia>> response) {
                List<Noticia> noticias = response.body();
                recyclerView.setAdapter(new TelaInicioAdapter(noticias));
            }

            @Override
            public void onFailure(Call<List<Noticia>> call, Throwable t) {
                mensagemErro();
            }
        });
    }
    // Lista as Notícias do Fragment Notícias
    public void getNoticias(RecyclerView recyclerView) {
        this.servico.getServico().enqueue(new Callback<List<Noticia>>() {
            @Override
            public void onResponse(Call<List<Noticia>> call, Response<List<Noticia>> response) {
                List<Noticia> noticias = response.body();
                recyclerView.setAdapter(new TelaNoticiasAdapter(noticias));
            }

            @Override
            public void onFailure(Call<List<Noticia>> call, Throwable t) {
                mensagemErro();
            }
        });
    }
    // Lista as Notícias em Destaque do Fragment Notícias
    public void getNoticiasPorId(TextView textViewTitulo, TextView textViewDescricao, ImageView imageView) {
        this.servico.getServico().enqueue(new Callback<List<Noticia>>() {
            @Override
            public void onResponse(Call<List<Noticia>> call, Response<List<Noticia>> response) {

                List<Noticia> noticiaBody = response.body();
                for(int i=0; i < noticiaBody.size(); i++) {
                    String noticiaImagem = noticiaBody.get(i).getImagem();
                    String noticiaTitulo = noticiaBody.get(i).getTitulo();
                    String noticiaDescricao = noticiaBody.get(i).getDescricao();

                    Picasso.get().load(noticiaImagem).fit().centerCrop(150).into(imageView);
                    textViewTitulo.setText(noticiaTitulo);
                    textViewDescricao.setText(noticiaDescricao);
                }
            }

            @Override
            public void onFailure(Call<List<Noticia>> call, Throwable t) {
                mensagemErro();
            }
        });
    }
    // Transfere dados para a Activity Detalhes
    public void getTranferirDadosParaDetalhes(TextView textViewTitulo, TextView textViewDescricao, ImageView imageView) {
        this.servico.getServico().enqueue(new Callback<List<Noticia>>() {
            @Override
            public void onResponse(Call<List<Noticia>> call, Response<List<Noticia>> response) {
                List<Noticia> noticiaBody = response.body();
                for(int i=0; i < noticiaBody.size(); i++) {
                    String noticiaImagem = noticiaBody.get(i).getImagem();
                    String noticiaTitulo = noticiaBody.get(i).getTitulo();
                    String noticiaDescricao = noticiaBody.get(i).getDescricao();

                    Intent intent = new Intent(contexto, DetalhesActivity.class);
                    intent.putExtra("imagem", noticiaImagem);
                    intent.putExtra("titulo", noticiaTitulo);
                    intent.putExtra("descricao", noticiaDescricao);
                    contexto.startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<List<Noticia>> call, Throwable t) {
                mensagemErro();
            }
        });
    }
    // Abre os Links das Notícias
    public void getNoticiasLink() {
        this.servico.getServico().enqueue(new Callback<List<Noticia>>() {
            @Override
            public void onResponse(Call<List<Noticia>> call, Response<List<Noticia>> response) {
                List<Noticia> noticiaBody = response.body();
                for(int i=0; i < noticiaBody.size(); i++) {
                    String link = noticiaBody.get(i).getLink();
                    Intent enviarIntent = new Intent(Intent.ACTION_VIEW);
                    enviarIntent.setData(Uri.parse(link));
                    contexto.startActivity(enviarIntent);
                }
            }

            @Override
            public void onFailure(Call<List<Noticia>> call, Throwable t) {
                mensagemErro();
            }
        });
    }

    private void mensagemErro() {
        String mensagem = contexto.getString(R.string.mensagem_erro);
        Toast.makeText(contexto.getApplicationContext(), mensagem, Toast.LENGTH_LONG).show();
    }
}
