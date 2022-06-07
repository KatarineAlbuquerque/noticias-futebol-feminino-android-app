package me.dio.web.esportefeminino.views.detalhes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import me.dio.web.esportefeminino.MainActivity;
import me.dio.web.esportefeminino.R;
import me.dio.web.esportefeminino.data.Configuracao;
import me.dio.web.esportefeminino.model.Noticia;
/*
* Tela de Detalhes das Notícias
* */
public class DetalhesActivity extends AppCompatActivity {

    ImageView imagemDescricao, imagemCompartilhar;
    TextView tvtitulo, tvdescricao;
    Button btnVejaMais;
    String imagem, titulo, descricao;
    private Noticia noticia;
    private Configuracao configuracao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true); //Ativar o botão
        getSupportActionBar().setTitle(R.string.title_detalhes_noticia);  //Titulo para ser exibido na sua Action Bar em frente à seta

        imagemDescricao = findViewById(R.id.imagemActivityDetalhe);
        imagemCompartilhar = findViewById(R.id.imagemCompartilhamentoActivityDetalhe);
        tvtitulo = findViewById(R.id.tituloActivityDetalhe);
        tvdescricao = findViewById(R.id.descricaoActivityDetalhe);
        btnVejaMais = findViewById(R.id.buttonVejaMais);

        // Pega os dados clicados do Fragment
        Intent intent = getIntent();
        imagem = intent.getStringExtra("imagem");
        titulo = intent.getStringExtra("titulo");
        descricao = intent.getStringExtra("descricao");
        // Seta os dados na tela
        Picasso.get().load(imagem).fit().centerCrop(50).into(imagemDescricao);
        tvtitulo.setText(titulo);
        tvdescricao.setText(descricao);

        // Compartilha os dados
        imagemCompartilhar.setOnClickListener(view -> {
            Intent enviarIntent = new Intent(Intent.ACTION_SEND);
            enviarIntent.putExtra(Intent.EXTRA_TITLE, tvtitulo.getText());
            enviarIntent.setType("text/plain");
            startActivity(Intent.createChooser(enviarIntent, null));
        });
        // Abre o link da Notícia
        btnVejaMais.setOnClickListener(view -> {
            configuracao = new Configuracao(this);
            configuracao.configurar();
            configuracao.getNoticiasLink();
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        abrirYelaPrincipal();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Ao precionar voltar
        abrirYelaPrincipal();
    }

    private void abrirYelaPrincipal() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}