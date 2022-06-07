package me.dio.web.esportefeminino.data;

import java.util.List;

import me.dio.web.esportefeminino.model.Noticia;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;

public interface ConfiguracaoRetrofit {
    @GET("noticias.json")
    Call<List<Noticia>> getServico();
}
