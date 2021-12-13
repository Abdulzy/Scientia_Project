package edu.neu.course.project;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Audio extends AppCompatActivity {
    private int usedOptions = -1;
    private List<String> germanOptions;
    private List<String> koreanOptions;
    private List<String> frenchOptions;
    private List<String> englishOptions;
    private List<String> otherOptions;
    private TextToSpeech mTTS;
    private TextView englishWord;
    private TextView otherWord;
    private Button mSpeak;
    private Button mChange;
    private String currentUser;
    private String currentToken;
    private String currentLanguage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        Intent intent = getIntent();
        currentUser = intent.getStringExtra("sender");
        currentToken = intent.getStringExtra("token");
        currentLanguage = intent.getStringExtra("language");
        mSpeak = findViewById(R.id.button_speak);
        mChange = findViewById(R.id.button_change);


        englishOptions = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.english)));
        englishWord = findViewById(R.id.englishText);
        otherWord = findViewById(R.id.otherText);
        TextView foreign = findViewById(R.id.otherHeader);
        foreign.setText(currentLanguage);

        languageSelection();

        change();

        mSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });

        mChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change();
            }
        });
    }

    private void languageSelection() {
        switch (currentLanguage){
            case "korean":
                otherOptions = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.korean)));
                koreanSpeak();
                break;
            case "french":
                otherOptions = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.french)));
                frenchSpeak();
                break;
            case "german":
                otherOptions = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.german)));
                germanSpeak();
                break;
        }
    }

    private void koreanSpeak(){
        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTS.setLanguage(Locale.KOREAN);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    } else {
                        mSpeak.setEnabled(true);
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });
    }

    private void germanSpeak(){
        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTS.setLanguage(Locale.GERMANY);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    } else {
                        mSpeak.setEnabled(true);
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });
    }

    private void frenchSpeak(){
        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTS.setLanguage(Locale.FRENCH);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    } else {
                        mSpeak.setEnabled(true);
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });
    }

    private void speak() {
        String text = otherWord.getText().toString();
        mTTS.speak(text,TextToSpeech.QUEUE_FLUSH,null);
    }


    private void change() {
        Log.i(TAG, "this is the used options: " + usedOptions);
        if(usedOptions < englishOptions.size()-1){
            usedOptions++;
        }
        else{
            usedOptions = 0;
        }
        Log.i(TAG, "this is the english options: " + englishOptions.get(usedOptions));
        Log.i(TAG, "this is the" + currentLanguage + " options: " + otherOptions.get(usedOptions));
        englishWord.setText(englishOptions.get(usedOptions));
        otherWord.setText(otherOptions.get(usedOptions));

    }

    @Override
    protected void onDestroy() {
        if (mTTS != null) {
            mTTS.stop();
            mTTS.shutdown();
        }

        super.onDestroy();
    }

}