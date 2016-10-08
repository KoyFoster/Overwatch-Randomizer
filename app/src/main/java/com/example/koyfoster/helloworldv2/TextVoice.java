package com.example.koyfoster.helloworldv2;

/**
 * Created by Koy Foster on 10/7/2016.
 */

/*Text to Speech*/

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.speech.tts.TextToSpeech;

import java.util.HashMap;
import java.util.Locale;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

//Text to Speech class
public class TextVoice extends AppCompatActivity implements TextToSpeech.OnInitListener
{
    private TextToSpeech myTTS;
    private int MY_DATA_CHECK_CODE = 0;

    public void onCreate(Bundle savedInstanceState)
    {
        Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);
    }

    public void onInit(int initStatus)
    {
        if (initStatus == TextToSpeech.SUCCESS)
        {
            myTTS.setLanguage(Locale.US);
        }
        else if (initStatus == TextToSpeech.ERROR)
        {
            Toast.makeText(this, "Sorry! Text To Speech failed...", Toast.LENGTH_LONG).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS)
        {
            myTTS = new TextToSpeech(this, this);
            if (myTTS.isLanguageAvailable(Locale.US) == TextToSpeech.LANG_AVAILABLE)
            {
                myTTS.setLanguage(Locale.US);
            }
        }
        else
        {
            Intent installTTSIntent = new Intent();
            installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
            startActivity(installTTSIntent);
        }
    }

    @SuppressWarnings("deprecation")
    public void ttsUnder20(String text)
    {
        HashMap<String, String> map = new HashMap<>();
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "MessageId");
        myTTS.speak(text, TextToSpeech.QUEUE_FLUSH, map);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void ttsGreater21(String text)
    {
        String utteranceId=this.hashCode() + "";
        myTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
    }

    public void Speak(String words)
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            //tv.ttsGreater21(_sHeroes[_iLastHero]);
            ttsGreater21(words);
        }
        else
        {
            //tv.ttsUnder20(_sHeroes[_iLastHero]);
            ttsUnder20(words);
        }
    }

    public void DoThing(String words)
    {
        AlertDialog.Builder aboutDialog = new AlertDialog.Builder(this);
        aboutDialog.setMessage(words).setPositiveButton("Close",new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        }).setTitle(words);
        //Show
        aboutDialog.show();
    }
}
