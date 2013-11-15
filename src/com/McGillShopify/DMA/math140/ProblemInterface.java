package com.McGillShopify.DMA.math140;

import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ProblemInterface extends Activity{
	@Override
	// Find out what level the person is at
	// show answer, show hint
	//submit button w/ listener
	//accept input as picture (place holder for now), maybe implement constructor.
	//Ihsan topaloglu
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.problem_inter);
		final Button hint = (Button) findViewById(R.id.hint);
		//InputStream in = am.open("mathematica_rationalpolylimits_answers.txt");
		//BufferedReader br = new BufferedReader(new InputStreamReader(in));
		ImageView iv = (ImageView) findViewById(R.id.problem);
		PictureDatabase pd = new PictureDatabase(getApplicationContext());
		String s =pd.getTest();
		if(s.equals(null)) System.out.println("String is null");
		byte[] b = Base64.decode(pd.getTest(), Base64.DEFAULT);
		Bitmap bm = BitmapFactory.decodeByteArray(b,0,b.length);
		iv.setImageBitmap(bm);
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
				dataBaseAnswer=dataBaseAnswer.toUpperCase();
				if(dataBaseAnswer.contains("Exp")){
					dataBaseAnswer=dataBaseAnswer.replace("Exp[", "e^");
					dataBaseAnswer=dataBaseAnswer.replace("]", "");
				}
				String userInput = answer.getText().toString();
				if(userInput.equals(dataBaseAnswer)); //Show something
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
