package com.example.bancomvc;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bancomvc.dao.AlunoDAO;
import com.example.bancomvc.model.Aluno;

public class Manutencao extends AppCompatActivity {

    private EditText edtId;
    private EditText edtNome;
    private EditText edtCpf;
    private EditText edtTelefone;
    private Button btnConsultar;
    private Button btnAtualizar;
    private Button btnExcluir;
    private Button btnLimpar;
    private Button btnVoltar;
    private AlunoDAO dao;
    private Aluno aluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manutencao);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtId = findViewById(R.id.edtId);
        edtNome = findViewById(R.id.edtNome);
        edtCpf = findViewById(R.id.edtCpf);
        edtTelefone = findViewById(R.id.edtTelefone);
        btnConsultar = findViewById(R.id.btnConsultar);
        btnAtualizar = findViewById(R.id.btnAtualizar);
        btnExcluir = findViewById(R.id.btnExcluir);
        btnLimpar = findViewById(R.id.btnLimpar);
        btnVoltar = findViewById(R.id.btnVoltar);

        // botão consultar
        btnConsultar.setOnClickListener(view -> {
            String idText = edtId.getText().toString().trim();
            if (idText.isEmpty()) {
                Toast.makeText(Manutencao.this, "Por favor, insira um ID para consultar os dados.", Toast.LENGTH_SHORT).show();
            } else {
                dao = new AlunoDAO(Manutencao.this);
                aluno = dao.read(Integer.parseInt(idText));
                if (aluno != null) {
                    edtNome.setText(aluno.getNome());
                    edtCpf.setText(aluno.getCpf());
                    edtTelefone.setText(aluno.getTelefone());
                    Toast.makeText(Manutencao.this, "Consulta realizada com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Manutencao.this, "ID não encontrado.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // botão atualizar
        btnAtualizar.setOnClickListener(view -> {
            String idText = edtId.getText().toString().trim();
            if (idText.isEmpty()) {
                Toast.makeText(Manutencao.this, "Por favor, insira um ID para atualizar os dados.", Toast.LENGTH_SHORT).show();
            } else {
                dao = new AlunoDAO(Manutencao.this);

                // Verifica se o ID existe no banco
                aluno = dao.read(Integer.parseInt(idText));

                // Se o aluno não for encontrado, exibe uma mensagem
                if (aluno.getId() == null) {
                    Toast.makeText(Manutencao.this, "ID não encontrado", Toast.LENGTH_SHORT).show();
                } else {
                    // Se o aluno for encontrado, atualiza os dados
                    aluno.setNome(edtNome.getText().toString());
                    aluno.setCpf(edtCpf.getText().toString());
                    aluno.setTelefone(edtTelefone.getText().toString());
                    dao.update(aluno);
                    Toast.makeText(Manutencao.this, "Dados atualizados com sucesso!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // botão excluir
        btnExcluir.setOnClickListener(view -> {
            String idText = edtId.getText().toString().trim();
            if (idText.isEmpty()) {
                Toast.makeText(Manutencao.this, "Por favor, insira um ID para excluir os dados.", Toast.LENGTH_SHORT).show();
            } else {
                dao = new AlunoDAO(Manutencao.this);

                // Verifica se o ID existe no banco
                aluno = dao.read(Integer.parseInt(idText));

                // Se o aluno não for encontrado, exibe uma mensagem
                if (aluno.getId() == null) {
                    Toast.makeText(Manutencao.this, "ID não encontrado", Toast.LENGTH_SHORT).show();
                } else {
                    // Se o aluno for encontrado, exclui os dados
                    dao.delete(aluno);
                    edtId.setText(null);
                    edtNome.setText(null);
                    edtCpf.setText(null);
                    edtTelefone.setText(null);
                    Toast.makeText(Manutencao.this, "Exclusão realizada com sucesso!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // botão limpar
        btnLimpar.setOnClickListener(view ->{
            edtId.setText(null);
            edtNome.setText(null);
            edtCpf.setText(null);
            edtTelefone.setText(null);
        });

        // botão voltar
        btnVoltar.setOnClickListener(view ->{
            finish();
        });
    }
}