package com.example.koyfoster.helloworldv2;

/**
 * Created by Koy Foster on 10/7/2016.
 */

/*Text to Speech*/
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Debug;
import android.speech.tts.TextToSpeech;
import java.util.Locale;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.widget.Toast;

//Text to Speech class
public class TextVoice extends AppCompatActivity
{
    TextToSpeech _TTS;

    //Constructor
    public TextVoice()
    {
        String utteranceId=this.hashCode() + "";
        _TTS.speak("butts", TextToSpeech.QUEUE_FLUSH, null,utteranceId);
    }

    //Create
    public void onCreate(Bundle savedInstanceState)
    {
        //setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        _TTS=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener()
        {
            @Override
            public void onInit(int status)
            {

                if(status != TextToSpeech.ERROR)
                {
                    _TTS.setLanguage(Locale.US);
                }
            }
        });
    }

    public void Speak(String words, MainActivity main)
    {
        Toast.makeText(main, words,Toast.LENGTH_SHORT).show();
        String utteranceId=this.hashCode() + "";
        _TTS.speak(words, TextToSpeech.QUEUE_FLUSH, null,utteranceId);

        //if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        //{
        //tv.ttsGreater21(_sHeroes[_iLastHero]);
        //    ttsGreater21(words);
        //}
        //else
        //{
        //tv.ttsUnder20(_sHeroes[_iLastHero]);
        //    ttsUnder20(words);
        //}
    }

    public void onPause()
    {
        if(_TTS !=null)
        {
            _TTS.stop();
            _TTS.shutdown();
        }
        super.onPause();
    }
}
