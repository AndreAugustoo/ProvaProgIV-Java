package com.example.cadastrocliente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AlterarActivity extends AppCompatActivity {

    private SQLiteDatabase bancoDados;
    public Button buttonAlterar;
    public Button buttonExcluir;
    public EditText editTextNome;
    public EditText editTextSobrenome;
    public EditText editTextIdade;
    public EditText editTextObservacao;
    public Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar);

        buttonAlterar = (Button)  findViewById(R.id.buttonAlterar);
        buttonExcluir = (Button)  findViewById(R.id.buttonExcluir);
        editTextNome = (EditText) findViewById(R.id.editTextNome);
        editTextSobrenome = (EditText) findViewById(R.id.editTextSobrenome);
        editTextIdade = (EditText) findViewById(R.id.editTextIdade);
        editTextObservacao = (EditText) findViewById(R.id.editTextObservacao);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        carregarDados();

        buttonAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alterar();
            }
        });

        buttonExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                excluir();
            }
        });
    }

    public void carregarDados(){
        try{
            bancoDados = openOrCreateDatabase("cadastroClientes", MODE_PRIVATE, null);
            Cursor cursor = bancoDados.rawQuery("SELECT id, nome, sobrenome, idade, observacao FROM clientes " +
                    "WHERE id = " + id.toString(), null);
            cursor.moveToFirst();
            editTextNome.setText(cursor.getString(1));
            editTextSobrenome.setText(cursor.getString(2));
            editTextIdade.setText(cursor.getString(3));
            editTextObservacao.setText(cursor.getString(4));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void alterar(){
        String valueNome;
        String valueSobrenome;
        String valueIdade;
        String valueObservacao;
        String valueId;
        valueNome = editTextNome.getText().toString();
        valueSobrenome = editTextSobrenome.getText().toString();
        valueIdade = editTextIdade.getText().toString();
        valueObservacao = editTextObservacao.getText().toString();
        valueId = Integer.toString(id);

        try{
            bancoDados = openOrCreateDatabase("cadastroClientes", MODE_PRIVATE, null);
            String sql = "UPDATE clientes SET nome=?, sobrenome=?, idade=?, observacao=? WHERE id=?";
            SQLiteStatement stmt = bancoDados.compileStatement(sql);
            stmt.bindString(1, valueNome);
            stmt.bindString(2, valueSobrenome);
            stmt.bindString(3, valueIdade);
            stmt.bindString(4, valueObservacao);
            stmt.bindString(5, valueId);
            stmt.executeUpdateDelete();

            bancoDados.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        finish();
    }

    public void excluir(){
        String valueId;
        valueId = Integer.toString(id);

        try{
            bancoDados = openOrCreateDatabase("cadastroClientes", MODE_PRIVATE, null);
            String sql = "DELETE FROM clientes WHERE id=?";
            SQLiteStatement stmt = bancoDados.compileStatement(sql);
            stmt.bindString(1, valueId);
            stmt.executeUpdateDelete();

            bancoDados.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        finish();
    }
}