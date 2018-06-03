package br.com.wesley.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.wesley.agenda.dao.ContatoDAO;
import br.com.wesley.agenda.modelo.Contato;

public class ListaAlunosActivity extends AppCompatActivity {

    private ListView listanomes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        listanomes = (ListView) findViewById(R.id.lista_nomes);

        listanomes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                Contato contato = (Contato) listanomes.getItemAtPosition(position);
                Intent intentGotoFormulario = new Intent(ListaAlunosActivity.this,FormularioActivity.class);
                intentGotoFormulario.putExtra("contato",contato);
                startActivity(intentGotoFormulario);
            }
        });




        Button novoContato = (Button) findViewById(R.id.lista_botao);
        novoContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGoToFurmulario = new Intent(ListaAlunosActivity.this,FormularioActivity.class);
                startActivity(intentGoToFurmulario);
            }
        });

        registerForContextMenu(listanomes);
    }

    private void carregaLista() {
        ContatoDAO dao = new ContatoDAO(this);
        List<Contato> contatos = dao.buscacontatos();
        dao.close();

        ArrayAdapter<Contato> adapter = new ArrayAdapter<Contato>(this, android.R.layout.simple_list_item_1,contatos);
        listanomes.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Contato contato = (Contato) listanomes.getItemAtPosition(info.position);
                ContatoDAO dao = new ContatoDAO(ListaAlunosActivity.this);
                dao.deleta(contato);
                dao.close();
                carregaLista();
                Toast.makeText(ListaAlunosActivity.this,"Deletatado o contato " + contato.getNome(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });{

        }
    }
}
