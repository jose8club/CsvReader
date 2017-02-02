package jose.csvreader.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import jose.csvreader.R;
import jose.csvreader.service.BD;

public class NewActivity extends AppCompatActivity {

    Button btn_uno, btn_dos;
    ListView list_uno, list_dos;
    BD db;
    ArrayList<String> id;
    String[] ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        btn_uno = (Button)findViewById(R.id.btn_pag);
        btn_dos = (Button)findViewById(R.id.btn_sqlite);
        list_uno = (ListView)findViewById(R.id.list_txt);
        list_dos = (ListView)findViewById(R.id.list_sqlite);
        db = new BD(getApplicationContext(),null,null,1);
        btn_uno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    CargaDatos(view);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btn_dos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CargarSQlite();
            }
        });
    }

    private void CargaDatos(View view) throws IOException{
        List<String> listado = new ArrayList<String>();
        String linea;

        InputStream is = this.getResources().openRawResource(R.raw.data);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        if(is!=null){
            while ((linea=reader.readLine())!=null){
                listado.add(linea.split(";")[0]);
                // Uso de SQLite

                String mensaje =db.guardar(linea.split(";")[0],
                        linea.split(";")[1],
                        linea.split(";")[2],
                        linea.split(";")[3],
                        linea.split(";")[4],
                        linea.split(";")[5],
                        linea.split(";")[6],
                        linea.split(";")[7],
                        linea.split(";")[8],
                        linea.split(";")[9],
                        linea.split(";")[10],
                        linea.split(";")[11],
                        linea.split(";")[12]);
                Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_SHORT).show();

            }
        }
        is.close();
        Toast.makeText(this,"Carga: "+listado.size(),Toast.LENGTH_LONG).show();
        String [] datos = listado.toArray(new String[listado.size()]);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,datos);
        list_uno.setAdapter(adaptador);
    }

    /**
     * Cargar datos SQLite
     */
    private void CargarSQlite() {
        id = db.lista_id();
        String [] ids = id.toArray(new String[id.size()]);
        ArrayAdapter<String> adaptadordos = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,ids);
        list_dos.setAdapter(adaptadordos);

        list_dos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object listItem = list_dos.getItemAtPosition(i);
                Toast.makeText(getApplicationContext(), listItem.toString(), Toast.LENGTH_SHORT).show();
                String numero = listItem.toString();
                Intent z = new Intent(getApplicationContext(), SQLiteActivity.class);
                z.putExtra("idobra", numero);
                startActivity(z);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
