package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends Activity {

	public static final String EXTRA_ANSWER_IS_TRUE =
			"com.bignerdranch.android.geoquize.answer_is_true";
	public static final String EXTRA_ANSWER_IS_SHOWN =
			"com.bignerdranch.android.geoquize.answer_is_shown";
	
	
	private boolean mAnswerIsTrue;
	private boolean mAnswerIsShown = false;
	
	private TextView mAnswerTextView;
	private Button mShowAnswer;
	
	private void setAnswerShownResult(boolean isAnswerShown) {
		Intent data = new Intent();
		data.putExtra(EXTRA_ANSWER_IS_SHOWN, isAnswerShown);
		setResult(RESULT_OK, data);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {


		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cheat);
		
		if (savedInstanceState != null) {
			mAnswerIsShown = savedInstanceState.getBoolean(EXTRA_ANSWER_IS_SHOWN);
		}
		setAnswerShownResult(mAnswerIsShown);
		mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE,false);

		mAnswerTextView = (TextView)findViewById(R.id.answerTextView);

		mShowAnswer = (Button)findViewById(R.id.showAnswerButton);
		mShowAnswer.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mAnswerIsTrue) {
					mAnswerTextView.setText(R.string.true_button);
				} else {
					mAnswerTextView.setText(R.string.false_button);
				}
				mAnswerIsShown = true;
				setAnswerShownResult(mAnswerIsShown);
			}
		});
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putBoolean(EXTRA_ANSWER_IS_SHOWN, mAnswerIsShown);
	}
}
