package com.McGillShopify.DMA.math140;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		final Button b = (Button) findViewById(R.id.Practice);
		b.setText("Click here for problem interface");
		String firstrun =getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("firstTime", "first run");
		TextView tv = (TextView) findViewById(R.id.textView1);
		tv.setText(firstrun);
		PictureDatabase pd= new PictureDatabase(this.getApplicationContext());
		try{
			if(firstrun.equals("first run"))
			pd.populate(this);
			//pd.getUnseenRandom(this.getApplicationContext(), "RationalPolyLimits");
			pd.close();
		
		} catch(Exception e){Log.w("Error",e.toString());tv.setText(e.toString());}
	    if (firstrun.equals("first run")){
	   
	    b.setText("FIRST TIME YO");
	    // Save the state
	    getSharedPreferences("PREFERENCE", MODE_PRIVATE)
	        .edit()
	        .putString("firstTime", "NOT FIRST RUN")
	        .commit();
	    }
		
		b.setOnClickListener(new OnClickListener(){
		    	
		    	@Override
		    	public void onClick(View v){
		    		//SharedPreferences sp = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
		    		//b.setText(""+sp.getString("firstTime", "NOTHING SAVED"));
		    		Intent i = new Intent(getApplicationContext(), ProblemInterface.class);
		    		startActivity(i);
		    		
		    	}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onPause(){
		super.onPause();
	}

}
