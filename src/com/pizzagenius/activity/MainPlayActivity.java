package com.pizzagenius.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.pizzagenius.R;
import com.pizzagenius.base.ActivityBase;
import com.util.Util;

public class MainPlayActivity extends ActivityBase  {
	
	String[] AUDIO_NAMES = {"loop2.mp3","la1.mp3","la2.mp3","la3.mp3","la4.mp3","mamma-mia.wav","loop1.mp3"};
	
	Timer timer;
	TimerTask timerTask;
	final Handler handler = new Handler();
	TextView scoreText;
	TextView suaVez;
	
	final MediaPlayer mp = new MediaPlayer();
	final MediaPlayer mpBg = new MediaPlayer();
	
	ImageView bt_finalizar;
	ImageView imageGameOver;
	ImageView bt_pizza1;
	ImageView bt_pizza2;
	ImageView bt_pizza3;
	ImageView bt_pizza4;
	
	Integer lastShown;
	Integer lastPlayed;
	Integer clicado;
	
	private Integer score;
	private List<Integer> sequencia;
	
	private boolean playerTime;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		
		playerTime = false;
		
		scoreText = (TextView) findViewById(R.id.score);
		suaVez = (TextView) findViewById(R.id.suaVez);
		
		setScore(0);
		
		zerarSequencia();
		
		iniciarRodada();
		
		setClickListeners();
		

		//playAudio(mpBg, 6, true);
		//playAudio(mpBg, 0);
		//playAudio(mpBg, 0, true);
		//mpBg.setVolume(1f,1f);
	}
	
	private void iniciarRodada(){

		timer = new Timer();
		
		new CountDownTimer(1500, 1500) {
		     public void onTick(long millisUntilFinished) {
		    	//mostraPizza(clicado);
		     }
	
		     public void onFinish() {
		 		aumentarSequencia();
				tocarSequencia();
		     }
		}.start();
		
	}

	private void setClickListeners(){
		bt_finalizar = (ImageView) findViewById(R.id.bt_finalizar);
		imageGameOver = (ImageView) findViewById(R.id.gameOver);
		bt_pizza1 = (ImageView) findViewById(R.id.imageView2);
		bt_pizza2 = (ImageView) findViewById(R.id.imageView3);
		bt_pizza3 = (ImageView) findViewById(R.id.imageView4);
		bt_pizza4 = (ImageView) findViewById(R.id.imageView5);
		
		suaVez.setText((CharSequence) "Aguarde...");
		
		bt_finalizar.setOnClickListener( new OnClickListener(){
			public void onClick(View v){
				backStart(v);
			}
		});
				
		imageGameOver.setOnClickListener( new OnClickListener(){
			public void onClick(View v){
				backStart(v);
			}
		});
		
		bt_pizza1.setOnClickListener( new OnClickListener(){
			public void onClick(View v){
				if(playerTime){
					pizza1Click(v);
				}
			}
		});	
		
		bt_pizza2.setOnClickListener( new OnClickListener(){
			public void onClick(View v){
				if(playerTime){
					pizza2Click(v);
				}
			}
		});	
		
		bt_pizza3.setOnClickListener( new OnClickListener(){
			public void onClick(View v){
				if(playerTime){
					pizza3Click(v);
				}
			}
		});	
		
		bt_pizza4.setOnClickListener( new OnClickListener(){
			public void onClick(View v){
				if(playerTime){
					pizza4Click(v);
				}
			}
		});	
	}
	
	private void zerarSequencia(){
		sequencia = new ArrayList<Integer>();
	}
	
	private void aumentarSequencia(){
		lastPlayed=0;
		Integer i = Util.randomize(1,4);
		sequencia.add(i);
	}
	
	public void tocarSequencia() {
		//initializeTimerTask();
		lastShown=0;
		
		mostraProximo();

	}
	
	private void mostraProximo(){
		new CountDownTimer(1000, 750) {

		     public void onTick(long millisUntilFinished) {
		    	 if(lastShown < sequencia.size()){
					//text.setText((CharSequence) sequencia.get(lastShown).toString());
		    		mostraPizzaImageUp(sequencia.get(lastShown));
		    	 }
		     }

		     public void onFinish() {
					if(lastShown<sequencia.size()){
						resetPizzaImage(sequencia.get(lastShown));
						voltaTodasImagens();
						//mostraPizza(sequencia.get(lastShown));
						mostraProximo();
					}else{
						voltaTodasImagens();
						//text.setText((CharSequence) "Acabou");
						lastPlayed=0;
						playerTime = true;
						suaVez.setText((CharSequence) "Jogue agora!");
					}
					
					lastShown++;
		     }
		  }.start();		
	}
	
	private void mostraClicado(){
		new CountDownTimer(1000, 750) {

		     public void onTick(long millisUntilFinished) {
		    	mostraPizzaImageUp(clicado);
		     }

		     public void onFinish() {
					voltaTodasImagens();
		     }
		  }.start();
		 
		if(lastPlayed<sequencia.size()-1){
			//playerTime = true;
			//suaVez.setText((CharSequence) "Jogue agora!");
		} else {
			aumentaScore();
			suaVez.setText((CharSequence) "Aguarde...");

			new CountDownTimer(1000, 1000) {
				public void onTick(long millisUntilFinished) {
				}

			    public void onFinish() {
			    	aumentarSequencia();
					tocarSequencia();
			    }
			}.start();
		} 
	}
	
	private void aumentaScore(){
		Integer score = getScore() + 1;
		setScore(score);
	}
	
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
		scoreText.setText((CharSequence) score.toString());
	}

	public void backStart(View view) {
	    Intent intent = new Intent(this, MainActivity.class);
	    startActivity(intent);
	}
	
	public void pizza1Click(View v) {
	    jogarNext(1);
	}
	
	public void pizza2Click(View v) {
	    jogarNext(2);
	}
	
	public void pizza3Click(View v) {
	    jogarNext(3);
	}
	
	public void pizza4Click(View v) {
	    jogarNext(4);
	}
	
	private void jogarNext(Integer i){
		//playerTime=false;
		if(lastPlayed>sequencia.size()-1 || !playerTime){
			return;
		}
		if (i==sequencia.get(lastPlayed)){
			clicado=i;
			mostraClicado();
			lastPlayed++;
			
			if(lastPlayed>=sequencia.size()){
				playerTime=false;
			}
		}else{
			gameOver();
		}
		//aumentarSequencia();
		//tocarSequencia();
	}
	
	private void mostraPizzaImageUp(Integer i){
		if(i==1){
			bt_pizza1.setImageResource(R.drawable.slice1_bb);
			playAudio(mp, 1, false);
		}else if(i==2){
			bt_pizza2.setImageResource(R.drawable.slice2_bb);
			playAudio(mp, 2, false);
		}else if(i==3){
			bt_pizza3.setImageResource(R.drawable.slice3_bb);
			playAudio(mp, 3, false);
		}else if(i==4){
			bt_pizza4.setImageResource(R.drawable.slice4_bb);
			playAudio(mp, 4, false);
		}
	}
	
	private void resetPizzaImage(Integer i){
		if(i==1){
			bt_pizza1.setImageResource(R.drawable.slice1_a);
		}else if(i==2){
			bt_pizza2.setImageResource(R.drawable.slice2_a);
		}else if(i==3){
			bt_pizza3.setImageResource(R.drawable.slice3_a);
		}else if(i==4){
			bt_pizza4.setImageResource(R.drawable.slice4_a);
		}
	}
	
	private void voltaTodasImagens(){
		bt_pizza1.setImageResource(R.drawable.slice1_a);
		bt_pizza2.setImageResource(R.drawable.slice2_a);
		bt_pizza3.setImageResource(R.drawable.slice3_a);
		bt_pizza4.setImageResource(R.drawable.slice4_a);
	}
	
	private void gameOver(){
		imageGameOver.setVisibility(1);
		
		playAudio(mp, 5, false);
	}
	
	private void playAudio(MediaPlayer m, Integer audioId, boolean looping){
		if(m.isPlaying())
        {  
            m.stop();
        } 

        try {
            m.reset();
            AssetFileDescriptor afd;
            afd = getAssets().openFd(AUDIO_NAMES[audioId]);
            m.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
    		m.setLooping(looping);
            m.prepare();
            m.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

}
