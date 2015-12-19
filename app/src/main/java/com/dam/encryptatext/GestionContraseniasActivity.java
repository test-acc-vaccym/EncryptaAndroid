package com.dam.encryptatext;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import encrypta3.Encryptacion;

public class GestionContraseniasActivity extends AppCompatActivity {

    private TextView tVCajaDeTexto;
    private Button bGuardar,bVerPatron;
    private EditText eTEntradaUsuario;
    private Encryptacion e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_contrasenias);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tVCajaDeTexto= (TextView) findViewById(R.id.tVCajaDeTexto);
        bGuardar = (Button) findViewById(R.id.bGuardar);
        bVerPatron= (Button) findViewById(R.id.bVerPatron);
        eTEntradaUsuario= (EditText) findViewById(R.id.eTEntradaUsuario);


        bGuardar.setOnClickListener(listenetGuardar);
        bVerPatron.setOnClickListener(listenetVerPatron);
        Bundle b= this.getIntent().getExtras();
        if(b!=null){
            e= new Encryptacion(b.getString("nombreDelFichero"));
        }


    }

    private String formaListado(){
        int lista[][]=e.getaPatrones();
        String salida="";
        for(int i=0;i<lista.length;i++){
            for(int j=0;j<lista[0].length;j++){
                salida+=lista[i][j]+":";
            }
            salida+="\n";
        }
        return salida;
    }
    View.OnClickListener listenetGuardar= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                e.generaClaves(eTEntradaUsuario.getText().toString());
                e.guardarClavePublica();
                tVCajaDeTexto.setText(formaListado());
            } catch (Exception e1) {
                tVCajaDeTexto.setText(e1.getMessage());
            }
        }
    };
    View.OnClickListener listenetVerPatron= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            tVCajaDeTexto.setText(formaListado());
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.cambioClaves) {
            Intent i = new Intent(GestionContraseniasActivity.this,MainActivity.class);
            i.putExtra("nombreDelFichero",e.getNombreFichero());
            startActivity(i);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
