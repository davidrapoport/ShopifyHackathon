package com.McGillShopify.DMA.math140;

import java.io.IOException;
import java.util.Set;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class ProblemInterface extends Activity{
	static boolean neverClicked= true;
	static boolean allDone;
	static int pointer;
	String[] picAndAnswer;
	@Override
	// Find out what level the person is at
	// show answer, show hint
	//submit button w/ listener
	//accept input as picture (place holder for now), maybe implement constructor.
	//Ihsan topaloglu
	protected void onCreate(Bundle savedInstanceState) {
		pointer = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getInt("LevelCounter",-1);
		Set<String> problemLevel= getSharedPreferences("PREFERENCE",MODE_PRIVATE).getStringSet("Levels", null);
		allDone= getSharedPreferences("PREFERENCE",MODE_PRIVATE).getBoolean("CompletedAll", false);
		final String[] thisllDoForNow= (String[]) problemLevel.toArray();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.problem_inter);
		final Button hint = (Button) findViewById(R.id.hint);
		final ImageView iv = (ImageView) findViewById(R.id.problem);
		final PictureDatabase pd = new PictureDatabase(getApplicationContext());
		try{
			if(!allDone){
				picAndAnswer =pd.getUnseenRandom(this.getApplicationContext(), thisllDoForNow[pointer]);
				iv.setImageDrawable(Drawable.createFromStream(getAssets().open(picAndAnswer[0]), ""));
			}
			else{
				picAndAnswer =pd.getAllRandom(this.getApplicationContext());
				iv.setImageDrawable(Drawable.createFromStream(getAssets().open(picAndAnswer[0]), ""));
			}
		} catch (Exception e){System.out.println("You fucked up");}
	//	if(s.equals(null)) System.out.println("String is null");
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
				String dataBaseAnswer = picAndAnswer[1];
				dataBaseAnswer=dataBaseAnswer.toUpperCase();
				if(dataBaseAnswer.contains("E")){
					dataBaseAnswer=dataBaseAnswer.replace("E^", "e^");
				}
                if(dataBaseAnswer.contains("Sin")){
					dataBaseAnswer=dataBaseAnswer.replace("Sin[","sin(");
					dataBaseAnswer=dataBaseAnswer.replace("]", ")");
				}
				if(dataBaseAnswer.contains("Cos")){
					dataBaseAnswer=dataBaseAnswer.replace("Cos[", "cos(");
					dataBaseAnswer=dataBaseAnswer.replace("]", ")");
                }
				String userInput = answer.getText().toString();
				if(userInput.equals(dataBaseAnswer)){
					CharSequence text = "Good Job!";
					int duration = Toast.LENGTH_SHORT;
					Toast toast = Toast.makeText(getApplicationContext(), text, duration);
					toast.show();
   
                    try{
                    	String [] nextPic=pd.getUnseenRandom(getApplicationContext(),"RationalPolyLimits");
                        iv.setImageDrawable(Drawable.createFromStream(getAssets().open(nextPic[0]), ""));
                    }
                    catch(Exception e){}
				}
			}
				});

		
		next.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				try{
					if(!allDone){
						picAndAnswer = pd.getUnseenRandom(getApplicationContext(), thisllDoForNow[pointer]);
						iv.setImageDrawable(Drawable.createFromStream(getAssets().open(picAndAnswer[0]), ""));
					}
					else{
						picAndAnswer = pd.getAllRandom(getApplicationContext());
						iv.setImageDrawable(Drawable.createFromStream(getAssets().open(picAndAnswer[0]), ""));
					}
				} catch(DoneWithProblemType e){
					Log.w("error ",e.getMessage());
					if(pointer==thisllDoForNow.length-1){
						//tell them congrats theyre done!!!
						getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("CompletedAll",true).commit();
					}
					else{
						//Display congrats you're done with "Insert problem type"
						pointer++;
						getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putInt("LevelCounter",pointer).commit();
						try{
							picAndAnswer = pd.getUnseenRandom(getApplicationContext(), thisllDoForNow[pointer]);
							iv.setImageDrawable(Drawable.createFromStream(getAssets().open(picAndAnswer[0]), ""));
						} catch(Exception a){}
					}
				}catch(IOException e){Log.w("IOException", "IOException occured trying to getUnseenRandom from DB and creating the Drawable");}
				
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
