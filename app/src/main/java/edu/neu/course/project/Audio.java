package edu.neu.course.project;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class Audio extends AppCompatActivity {
    private int usedOptions = -1;
    private List<String> russianOptions;
    private List<String> koreanOptions;
    private List<String> hindiOptions;
    private List<String> englishOptions;
    private TextToSpeech mTTS;
    private TextView englishWord;
    private TextView otherWord;
    private Button mSpeak;
    private Button mChange;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        mSpeak = findViewById(R.id.button_speak);
        mChange = findViewById(R.id.button_change);
        russianOptions = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.russian)));
        koreanOptions = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.korean)));
        hindiOptions = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.hindi)));
        englishOptions = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.english)));

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

        englishWord = findViewById(R.id.englishText);
        otherWord = findViewById(R.id.otherText);

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

    private void speak() {
        String text = otherWord.getText().toString();
        mTTS.speak(text,TextToSpeech.QUEUE_FLUSH,null);
    }


    private void change() {
        if(usedOptions < 4){
            usedOptions++;
            englishWord.setText(englishOptions.get(usedOptions));
            otherWord.setText(koreanOptions.get(usedOptions));
        }
        else{
            usedOptions = 0;
            englishWord.setText(englishOptions.get(usedOptions));
            otherWord.setText(koreanOptions.get(usedOptions));
        }

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