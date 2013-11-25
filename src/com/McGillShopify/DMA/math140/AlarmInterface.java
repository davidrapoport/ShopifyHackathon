package com.McGillShopify.DMA.math140;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AlarmInterface extends Activity {
	
//The same as problem interface but with sound playing 
//and sends you back to Main Activity after one correct answer
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.problem_inter);
		final Button hint = (Button) findViewById(R.id.hint);
		final MediaPlayer mediaPlayer = MediaPlayer.create(this.getApplicationContext(), R.raw.alarm_sound);
		mediaPlayer.start();
		hint.setOnClickListener(new OnClickListener(){
	    	
	    	@Override
	    	public void onClick(View v){
	    		hint.setVisibility(View.GONE);
	    		final TextView giveHint = (TextView) findViewById(R.id.hintText);
	    		giveHint.setVisibility(View.VISIBLE);
	    		giveHint.setText("Heres the hint dudes, hopefully it doesnt fill up the whole page and I hope it doesnt overlap the entry ya know?? Thatd be awful");
	    	}
	    	});
		final Button submit = (Button) findViewById(R.id.submit);
		final EditText answer = (EditText) findViewById(R.id.solution);
		submit.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v){
				String dataBaseAnswer = "";
				if(dataBaseAnswer.contains("Exp")){
					dataBaseAnswer=dataBaseAnswer.replace("Exp[", "e^");
					dataBaseAnswer=dataBaseAnswer.replace("]", "");
				}
				if(dataBaseAnswer.contains("Sin")){
					dataBaseAnswer=dataBaseAnswer.replace("Sin[","sin(");
					dataBaseAnswer=dataBaseAnswer.replace("]", ")");
				}
				if(dataBaseAnswer.contains("Cos")){
					dataBaseAnswer=dataBaseAnswer.replace("Cos[", "cos(");
					dataBaseAnswer=dataBaseAnswer.replace("]", ")");
				}
				dataBaseAnswer=dataBaseAnswer.toUpperCase();
				String userInput = answer.getText().toString().toUpperCase();
				if(userInput.equals(dataBaseAnswer)){
					mediaPlayer.reset();
					Intent back = new Intent(getApplicationContext(), MainActivity.class);
		    		startActivity(back);
				}
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
