package br.com.wesley.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import br.com.wesley.agenda.dao.ContatoDAO;
import br.com.wesley.agenda.modelo.Contato;

public class FormularioActivity extends AppCompatActivity {

    private FormularioGetter Getter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        Getter = new FormularioGetter(this);

        Intent intent = getIntent();
        Contato contato = (Contato) intent.getSerializableExtra("contato");
        if (contato != null){
            Getter.preencheFormulario(contato);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_formulariok:
                Contato contato = Getter.getAluno();
                ContatoDAO dao = new ContatoDAO(this);
                if (contato.getId() != null){
                    dao.altera(contato);
                }else{
                    dao.insert(contato);
                }
                dao.close();
                Toast.makeText(FormularioActivity.this,"Contato Salvo", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
