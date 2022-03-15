package com.example.cadastrocliente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
     private SQLiteDatabase bancoDados;
     public ListView listViewClientes;
     public Button botao;
     public Integer idSelecionado;
     public ArrayList<Integer> arrayIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewClientes = (ListView) findViewById(R.id.listViewClientes);
        botao = (Button) findViewById(R.id.buttonCadastrar);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirTelaCadastro();
            }
        });

        listViewClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                idSelecionado = arrayIds.get(i);
                abrirTelaAlterar();
            }
        });

        criarBancoDados();
        listarDados();
    }

    @Override
    protected void onResume(){
        super.onResume();
        listarDados();
    }

    public void criarBancoDados(){
        try{
            bancoDados = openOrCreateDatabase("cadastroClientes", MODE_PRIVATE,null);
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS clientes(" +
                    " id INTEGER PRIMARY KEY AUTOINCREMENT" +
                    " , nome VARCHAR" +
                    " , sobrenome VARCHAR" +
                    " , idade INTEGER" +
                    " , observacao VARCHAR)");
            bancoDados.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void listarDados(){
        try{
            arrayIds = new ArrayList<>();
            bancoDados = openOrCreateDatabase("cadastroClientes", MODE_PRIVATE,null);
            Cursor meuCursor = bancoDados.rawQuery("SELECT id, nome, sobrenome, idade, observacao FROm clientes", null);
            ArrayList<String> linhas = new ArrayList<String>();
            ArrayAdapter meuAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.activity_list_item,
                    android.R.id.text1,
                    linhas
            );
            listViewClientes.setAdapter(meuAdapter);
            meuCursor.moveToFirst();
            while(meuCursor!=null){
                linhas.add(meuCursor.getString(1));
                arrayIds.add(meuCursor.getInt(0));
                meuCursor.moveToNext();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void abrirTelaCadastro(){
        Intent intent = new Intent(this,CadastroActivity.class);
        startActivity(intent);
    }

    public void abrirTelaAlterar(){
        Intent intent = new Intent(this, AlterarActivity.class);
        intent.putExtra("id", idSelecionado);
        startActivity(intent);

    }
}