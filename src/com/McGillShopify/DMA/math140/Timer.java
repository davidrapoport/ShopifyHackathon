package com.McGillShopify.DMA.math140;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class Timer extends Activity {
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

	/** Called when the user clicks the start button */
	public void startTimer(View view) {
	    Intent intent = new Intent(this, DisplayTimer.class);
	    EditText editText = (EditText) findViewById(R.id.minutes_message);
	    String minutes = editText.getText().toString();
	    intent.putExtra(EXTRA_MESSAGE, minutes);
	    startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timer);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timer, menu);
		return true;
	}

}
