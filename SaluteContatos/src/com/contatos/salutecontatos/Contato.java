package com.contatos.salutecontatos;

import com.util.DBAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Contato extends Activity{
    
    private DBAdapter datasource;
    com.contatos.entity.Contato contato;
    int idContacto;
    Button btSalvar;
    Button btCapFoto;
    EditText editNome;
    EditText editEmail;
    EditText editTelefone;    
    ImageView imagem;
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contato);
        
	datasource = new DBAdapter(this);
	editNome = (EditText) findViewById(R.id.editNome);
	editEmail = (EditText) findViewById(R.id.editEmail);
	editTelefone = (EditText) findViewById(R.id.editTelefone);
	btSalvar =  (Button) findViewById(R.id.btSalvar);
	btCapFoto =  (Button) findViewById(R.id.btCapFoto);
        imagem = (ImageView) findViewById(R.id.image);
        
        carregaDetalhesContacto();
    }
    
    public void capturarFoto(View v){
	Intent i= new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
	startActivityForResult(i,0);
    }
    
    public void salvar(View v){
	datasource.open();
	com.contatos.entity.Contato c = datasource.createContacto( editNome.getText().toString(), editEmail.getText().toString(), editTelefone.getText().toString(), loadBitmapFromView(imagem));
	datasource.close();
	showToastMessage("Contato cadastrado com Sucesso!");

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	if(resultCode == RESULT_OK){
	    Bundle extras = data.getExtras();
	    Bitmap bmp = (Bitmap) extras.get("data");
	    Log.i("aaa", "Result Ok");
	    imagem.setImageBitmap(bmp);
	 }
    }
    
    public static Bitmap loadBitmapFromView (View v) {
	Bitmap b = Bitmap.createBitmap( v.getLayoutParams().width, v.getLayoutParams().height, Bitmap.Config.ARGB_8888);                
	Canvas c = new Canvas(b);
	v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
	v.draw(c);
	return b;
    }
    
    private void showToastMessage(String msg){
	Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
	toast.show();
    }
    
    private void carregaDetalhesContacto(){
	idContacto = getIntent().getIntExtra("idContacto", 0);
	try {
        	datasource = new DBAdapter(this);
        	datasource.open();
        	contato = datasource.getContacto(idContacto);
        	datasource.close();
	

	editNome.setText(contato.getNome());
	editEmail.setText(contato.getEmail());
	editTelefone.setText(contato.getTelefone());
	imagem.setImageBitmap(contato.getFoto());
	} catch (Exception e) {
	}
    }
}
