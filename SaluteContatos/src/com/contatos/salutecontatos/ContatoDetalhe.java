package com.contatos.salutecontatos;



import com.util.DBAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ContatoDetalhe extends Activity {
	
	int idContacto;
	DBAdapter  datasource;
	com.contatos.entity.Contato contato;
	TextView edtNome;
	TextView edtEmail;
	TextView edtTelefone;
	Button btVoltar;
	Button btEliminar;
	ImageView ivFoto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detalhecontato);
			
		edtNome = (TextView) findViewById(R.detalha.labelNome);
		edtEmail = (TextView) findViewById(R.detalha.labelEmail);
		edtTelefone = (TextView) findViewById(R.detalha.labelTelafone);
		ivFoto = (ImageView) findViewById(R.detalha.foto);
		btEliminar = (Button) findViewById(R.id.btEliminar);
		btVoltar = (Button) findViewById(R.lista.btmenu);
		

		
		carregaDetalhesContacto();
		
	}

	private void carregaDetalhesContacto(){
		idContacto = getIntent().getIntExtra("idContacto", 0);
			
		datasource = new DBAdapter(this);
		datasource.open();
		contato = datasource.getContacto(idContacto);
		datasource.close();
			
		ivFoto.setImageBitmap(contato.getFoto());
		edtNome.setText(contato.getNome());
		edtEmail.setText(contato.getEmail());
		edtTelefone.setText(contato.getTelefone());
	}
	
	public void onClickDelete(View v) {
		datasource.open();
			datasource.EliminaContacto(idContacto);
		datasource.close();
		finish();
	} 
	
	public void onClickVoltar(View v) {
	    finish();
	}
	
	public void onClickEditar(View v) {
	    Intent intent = new Intent("com.contatos.salutecontatos.Contato");
	     intent.putExtra("idContacto",idContacto);
	     startActivity(intent);		
	}

	public com.contatos.entity.Contato getContato() {
	    return contato;
	}

	public void setContato(com.contatos.entity.Contato contato) {
	    this.contato = contato;
	}
}
