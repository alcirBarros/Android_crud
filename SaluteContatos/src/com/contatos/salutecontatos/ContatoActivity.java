package com.contatos.salutecontatos;

import com.util.DBAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
 
public class ContatoActivity extends ListActivity  {
    
    DBAdapter datasource;
    ListAdapter adapter;

    @SuppressWarnings("deprecation")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);    
        datasource = new DBAdapter(this);
        datasource.open();
        Cursor cursor = datasource.getContactos();
         
        String[] columns = new String[] { "nome","telefone" };
        int[] to = new int[] { R.id.nome, R.id.telefone};
         
        adapter = new SimpleCursorAdapter(
 			this, 
 			R.layout.contato_list, 
 			cursor, 
 			columns, 
 			to);        
 	this.setListAdapter(adapter);
 	datasource.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void onClickNovoContato(View v) {
	Intent novo = new Intent("com.contatos.salutecontatos.Contato");
	startActivity(novo);
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	Intent intent = new Intent("com.contatos.salutecontatos.ContatoDetalhe");
        Cursor cursor = (Cursor) adapter.getItem(position);
	intent.putExtra("idContacto",cursor.getInt(cursor.getColumnIndex("_id")));
        startActivity(intent);		
    }
}
