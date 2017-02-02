package jose.csvreader.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import jose.csvreader.R;
import jose.csvreader.service.BD;

public class SQLiteActivity extends AppCompatActivity {

    EditText edt_id, edt_nombre, edt_autor, edt_creacion;
    ImageView imga;

    /**
     * URL del servidor web a usar
     */
    private static final String URL = "http://jose8android.esy.es/";

    /**
     * URL de la Imagen
     */
    private static final String IMG = URL + "obras/imagenes/";

    /**
     * Instancia de la base de datos sqlite
     */
    BD db;

    /**
     * Arreglo de todos los retornos de datos por fila
     */
    String [] fila;
    String URI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);

        edt_id = (EditText)findViewById(R.id.act_serie);
        edt_nombre = (EditText)findViewById(R.id.act_nombre);
        edt_autor = (EditText)findViewById(R.id.act_autir);
        edt_creacion = (EditText)findViewById(R.id.act_resumen);
        imga = (ImageView)findViewById(R.id.im_obra);

        // se obtiene el intent que paso a esta etapa y lo cambia
        Intent intento = getIntent();
        String idobra = intento.getExtras().getString("idobra");
        edt_id.setText(idobra);

        //Buscar todos los datos del sistema acorde al id de la obra
        db = new BD(getApplicationContext(),null,null,1);
        fila = db.buscar(edt_id.getText().toString());
        //Se rellena todos los campos respectivos
        edt_nombre.setText(fila[1]);
        edt_autor.setText(fila[2]);
        edt_creacion.setText(fila[4]);
        //se obtiene los datos para obtener la imagen
        URI = fila[12].toString();

        // Uso de Glide
        // load: se carga la imagen
        // crossFade: animacion de entrada
        // centerCrop: ocupa toda la pantalla de imgview
        // into: carga la imagen en la imageview
        Glide.with(this)
                .load(IMG + URI)
                .crossFade()
                .centerCrop()
                .into(imga);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sqlite, menu);
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
