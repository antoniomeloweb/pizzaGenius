package com.pizzagenius.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.pizzagenius.R;
import com.pizzagenius.base.ActivityBase;

public class MainActivity extends ActivityBase {

	ImageButton btIniciar;
	ImageButton btSair; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btIniciar = (ImageButton) findViewById(R.id.bt_iniciar);
		btSair = (ImageButton) findViewById(R.id.bt_sair);
		
		setClickListeners();

	}
	
	private void setClickListeners(){
		
		btIniciar.setOnClickListener( new OnClickListener() {

			@Override
			public void onClick(View v) {
				startGame(v);				
			}			
		});
		
		btSair.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				android.os.Process.killProcess(android.os.Process.myPid());
				System.exit(1);			
			}
			
		});		
	}
	
	public void startGame(View view) {
	    Intent intent = new Intent(this, MainPlayActivity.class);
	    startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
