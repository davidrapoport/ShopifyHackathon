package com.McGillShopify.DMA.math140;

import android.app.Activity;
import android.os.Bundle;
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
		final ImageView iv = (ImageView) findViewById(R.id.problem);
		final PictureDatabase pd = new PictureDatabase(getApplicationContext());
		String[] s =pd.getUnseenRandom(this.getApplicationContext(), "RationalPolyLimits");
		iv.setImageBitmap(Methods.StringToBitMap(s[0]));
		
		if(s.equals(null)) System.out.println("String is null");
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
		final Button next = (Button) findViewById(R.id.next);
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
		next.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				String[] nextPic = pd.getUnseenRandom(getApplicationContext(), "RationalPolyLimits");
				iv.setImageBitmap(Methods.StringToBitMap(nextPic[0]));
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
