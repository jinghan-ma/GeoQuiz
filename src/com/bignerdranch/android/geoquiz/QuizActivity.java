package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends Activity {

	private Button mTrueButton;
	private Button mFalseButton;
	private Button mNextButton;
	private Button mCheatButton;
	private TextView mQuestionTextView;
	
	//private static final String TAG = "QuizActivity";
	private static final String KEY_INDEX = "index";
	private static final String CHEATER_ARRAY = "is_cheater";

	private TrueFalse[] mQuestionBank = new TrueFalse[] {
			new TrueFalse(R.string.question_oceans, true),
			new TrueFalse(R.string.question_mideast, false),
			new TrueFalse(R.string.question_africa, false),
			new TrueFalse(R.string.question_americas, true),
			new TrueFalse(R.string.question_asia, true),
	};

	private int mCurrentIndex = 0;
	
	private boolean[] mIsCheater = new boolean[] {
			false,
			false,
			false,
			false,
			false
	};
	
	private void updateQuestion() {
		int question = mQuestionBank[mCurrentIndex].getQuestion();
		mQuestionTextView.setText(question);
	}

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();

        int messageResId = 0;
        if (mIsCheater[mCurrentIndex]) {
        	messageResId = R.string.judgement_toast;
        } else {
        	if (userPressedTrue == answerIsTrue) {
        		messageResId = R.string.correct_toast;
        	} else {
        		messageResId = R.string.incorrect_toast;
        	}
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
        .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (data == null) return;
    	mIsCheater[mCurrentIndex] = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_IS_SHOWN, false);
    }
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);

		mQuestionTextView = (TextView)findViewById(R.id.question_text_view);

		mTrueButton = (Button)findViewById(R.id.true_button);
		mTrueButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				checkAnswer(true);
			}
		});

		mFalseButton = (Button)findViewById(R.id.false_button);
		mFalseButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				checkAnswer(false);
			}
		});

		mNextButton = (Button)findViewById(R.id.next_button);
		mNextButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
				updateQuestion();
			}
		});

		if (savedInstanceState != null) {
			mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
			mIsCheater = savedInstanceState.getBooleanArray(CHEATER_ARRAY);
		}
		mCheatButton = (Button)findViewById(R.id.cheat_button);
		mCheatButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Start CheatActivity
				Intent i = new Intent(QuizActivity.this, CheatActivity.class);
				boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
				i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
				startActivityForResult(i,0);
			}
		});
		updateQuestion();
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
		savedInstanceState.putBooleanArray(CHEATER_ARRAY, mIsCheater);
	}
	@Override
	public void onStart() {
		super.onStart();
	}
	
	@Override
	public void onPause() {
		super.onPause();
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.quiz, menu);
		return true;
	}

}
