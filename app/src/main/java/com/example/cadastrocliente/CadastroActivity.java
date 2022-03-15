package com.example.cadastrocliente;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CadastroActivity extends AppCompatActivity {
    EditText editTextNome;
    EditText editTextSobrenome;
    EditText editTextIdade;
    EditText editTextObservacao;
    Button botao;
    SQLiteDatabase bancoDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        editTextNome = (EditText) findViewById(R.id.editTextNome);
        editTextSobrenome = (EditText) findViewById(R.id.editTextSobrenome);
        editTextIdade = (EditText) findViewById(R.id.editTextIdade);
        editTextObservacao = (EditText) findViewById(R.id.editTextObservacao);
        botao = (Button) findViewById(R.id.buttonConfirma);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrar();
            }
        });
    }

    public void cadastrar(){
        try {
                bancoDados = openOrCreateDatabase("cadastroClientes", MODE_PRIVATE, null);
                String sql = "INSERT INTO clientes(nome, sobrenome, idade, observacao) VALUES (?,?,?,?)";
                SQLiteStatement stmt = bancoDados.compileStatement(sql);

                stmt.bindString(1, editTextNome.getText().toString());
                stmt.bindString(2, editTextSobrenome.getText().toString());
                stmt.bindString(3, editTextIdade.getText().toString());
                stmt.bindString(4, editTextObservacao.getText().toString());
                stmt.executeInsert();
                bancoDados.close();
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}