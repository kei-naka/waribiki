package com.example.waribiki;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity extends Activity {
	
	private EditText beforePrice;
	private EditText rate;
	private EditText afterPrice;
	
	private int numbers = 10;
	private Button[] numButtons;
	private Button buttonReset;
	private Button buttonCalc;
	private Button buttonTax;
	
	private SeekBar seekBar;
	
	private int initProgress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Log.d("main_create", "onCreate start");
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        beforePrice = (EditText) findViewById(R.id.beforePrice);
        rate = (EditText) findViewById(R.id.rate);
        afterPrice = (EditText) findViewById(R.id.afterPrice);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        
        // new buttons and init rate
        newComponents();
        initProgress = seekBar.getProgress();
        rate.setText(getRate(initProgress) + "%");
        
        // set button actions
        setButtonClickListeners();

        // set seekBar action
        seekBar.setOnSeekBarChangeListener(_SeekBarChangeListener);
        
        Log.d("main_create", "setOnSeekBarChangeListener done.");
    }
    
    // new all components in this activity, which are buttons and seekBar
    private void newComponents() {
    	Log.d("main_newComponents", "newComponents start");
    	
    	if (numButtons != null)
    		return;

    	numButtons = new Button[numbers];
    	numButtons[0] = (Button) findViewById(R.id.button0);
    	numButtons[1] = (Button) findViewById(R.id.button1);
    	numButtons[2] = (Button) findViewById(R.id.button2);
    	numButtons[3] = (Button) findViewById(R.id.button3);
    	numButtons[4] = (Button) findViewById(R.id.button4);
    	numButtons[5] = (Button) findViewById(R.id.button5);
    	numButtons[6] = (Button) findViewById(R.id.button6);
    	numButtons[7] = (Button) findViewById(R.id.button7);
    	numButtons[8] = (Button) findViewById(R.id.button8);
    	numButtons[9] = (Button) findViewById(R.id.button9);

    	buttonTax = (Button) findViewById(R.id.buttonTax);
    	buttonReset = (Button) findViewById(R.id.buttonReset);
    	buttonCalc = (Button) findViewById(R.id.buttonCalc);
    	
    	seekBar = (SeekBar) findViewById(R.id.seekBar);

    	Log.d("main_newComponents", "newComponents done");
    }
    
    // set button actions
    private void setButtonClickListeners () {
    	Log.d("main_setButtonClickListeners", "setButtonClickListeners start");
    	
    	for (int i = 0; i < numbers; ++i) {
    		numButtons[i].setOnClickListener(_OnClickListenerButtonNum);
    	}

    	// setOnClickListener for Reset
    	buttonReset.setOnClickListener(_OnClickListenerButtonReset);
    	
    	// setOnClickLister for Calculate
    	buttonCalc.setOnClickListener(_OnClickListenerButtonCalc);

    	// setOnClickListener for tax
    	buttonTax.setOnClickListener(_OnClickListenerButtonTax);
    	
    	Log.d("main_setButtonClickListeners", "setButtonClickListeners done");
    }

    // set sub-action of number buttons
    private OnClickListener _OnClickListenerButtonNum = new OnClickListener() {
    	@Override
    	public void onClick(View v) {
    		String _text = beforePrice.getText().toString();
    		if ("0".equals(_text)) {
    			_text = ((Button) v).getText().toString();
    		} else {
    			_text += ((Button) v).getText().toString();
    		}
   			beforePrice.setText(_text);
    		Log.v("main_ListenerButtonNum", ((Button) v).getText().toString() + " is clicked => " + _text);
    	}
    };

    // set sub-action of reset button
    private OnClickListener _OnClickListenerButtonReset = new OnClickListener() {    	
    	@Override
    	public void onClick (View v) {
    		beforePrice.setText(R.string.initNum);
    		seekBar.setProgress(initProgress);
    		rate.setText(getRate(seekBar.getProgress()) + "%");
    		afterPrice.setText(R.string.initNum);
    		Log.v("main_ListenerButonReset", "reset button is clicked.");
    	}
    };
    
    // set sub-action of calculation button
    private OnClickListener _OnClickListenerButtonCalc = new OnClickListener() {
    	@Override
    	public void onClick(View v) {
    		afterPrice.setText(String.valueOf(_calculate()));
    		Log.v("main_ListenerButtonCalc", "calc button is clicked");
    	}
    };
    
    // set sub-action of tax button
    private OnClickListener _OnClickListenerButtonTax = new OnClickListener() {
    	@Override
    	public void onClick(View v) {
    		rate.setText("105%");
    		afterPrice.setText(String.valueOf(_calculate()));
    		Log.v("main_ListenerButtonTax", "tax button is clicked");
    	}
    };

    // set seeBar action
    private OnSeekBarChangeListener _SeekBarChangeListener = new OnSeekBarChangeListener() {
    	@Override
    	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
    		rate.setText(String.valueOf(getRate(progress)) + "%");
    		afterPrice.setText(String.valueOf(_calculate()));
    		Log.v("main", "progress changed => " + String.valueOf(progress));
    	}

    	@Override
    	public void onStartTrackingTouch(SeekBar seekBar) {
    		Log.v("main", "start touch");
    	}

    	@Override
    	public void onStopTrackingTouch(SeekBar seekBar) {
    		Log.v("main", "stop touch");
    		afterPrice.setText(String.valueOf(_calculate()));
    	}
    };
    
    // calculation of beforePrice x rate
    private int _calculate() {
    	CharSequence _beforePrice = beforePrice.getText();
    	CharSequence _rate = rate.getText().toString().replace("%", "");
    	int _afterPrice = 0;
    	
    	Log.v("main_calculate", "beforePrice: " + _beforePrice);
    	Log.v("main_calculate", "rate: " + _rate);
    	
		try {
			_afterPrice = (int)(Double.parseDouble(_beforePrice.toString()) * Double.parseDouble(_rate.toString()) / 100.0);
			Log.v("main_calculate", "afterPrice: " + _afterPrice);
		} catch (Exception e) {
			_afterPrice = 0;
			Log.v("main_calculate", "Exception => afterPrice == 0");
		}

    	return _afterPrice;
    }

    // get rate from seekbar progress
    private int getRate(int progress) {
    	return progress * 5;
    }

}
