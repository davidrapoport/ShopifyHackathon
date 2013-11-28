package com.McGillShopify.DMA.math140;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;
import android.os.CountDownTimer;


public class DisplayTimer extends Activity {
	public TextView textView1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(textView1);
		Intent intent = getIntent();
		int milliSecondsCount = (Integer.parseInt(intent.getStringExtra(Timer.EXTRA_MESSAGE))) * 60000;
        MyCount counter = new MyCount(milliSecondsCount,1000);
        counter.start();
	}
	
    public class MyCount extends CountDownTimer{
        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            }

        @Override
        public void onFinish() {
    		Intent intent = new Intent(getApplicationContext(), AlarmProblemInterface.class);
    		startActivity(intent);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            textView1.setText((int) (millisUntilFinished/1000));

        }
    }

	
	

/*	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_timer, menu);
		return true;
	}*/

}
