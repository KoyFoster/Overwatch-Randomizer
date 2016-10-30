package com.example.koyfoster.helloworldv2;

//Min API Level 16: 8 API Min Violations
//Max API Level 23: Released Nov 6, 2015

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
{
    /*App Fields*/
    Drawable _drwSelection, _drwDisabled;
    HeroSelectController _HSC = null;
    TextView _yourHero;
    TextToSpeech _TTS;
    CheckBox _cbPlayAll, _cbMute;
    Button buttonRandHero = null;
    //Declare ImageButton Array of Heroes
    ImageButton _ibHeros[] =
            {
                    null, null, null, null,
                    null, null, null, null,
                    null, null, null, null,
                    null, null, null, null,
                    null, null, null, null,
                    null, null
            };


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        _HSC = new HeroSelectController();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ///Text to Speech setup
        _TTS=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener()
        {
            @Override
            public void onInit(int status)
            {
                if(status != TextToSpeech.ERROR)
                {
                    //_TTS.setLanguage(Locale.US);
                    _TTS.setLanguage(Locale.ENGLISH);
                }
            }
        });

        /*Link Resources*/
        _cbPlayAll = (CheckBox) findViewById(R.id.checkBoxPlayAll);
        _cbMute = (CheckBox) findViewById(R.id.checkBoxMute);
        _yourHero = (TextView) findViewById(R.id.textViewHero);
        _drwSelection = (Drawable) getDrawable(R.drawable.selected);///API 21
        _drwDisabled = (Drawable) getDrawable(R.drawable.disabled);///API 21
        /*Define Hero Image Array*/
        _ibHeros[0] = (ImageButton) findViewById(R.id.imageButton01);
        _ibHeros[1] = (ImageButton) findViewById(R.id.imageButton02);
        _ibHeros[2] = (ImageButton) findViewById(R.id.imageButton03);
        _ibHeros[3] = (ImageButton) findViewById(R.id.imageButton04);
        _ibHeros[4] = (ImageButton) findViewById(R.id.imageButton05);

        _ibHeros[5] = (ImageButton) findViewById(R.id.imageButton06);
        _ibHeros[6] = (ImageButton) findViewById(R.id.imageButton07);
        _ibHeros[7] = (ImageButton) findViewById(R.id.imageButton08);
        _ibHeros[8] = (ImageButton) findViewById(R.id.imageButton09);
        _ibHeros[9] = (ImageButton) findViewById(R.id.imageButton10);

        _ibHeros[10] = (ImageButton) findViewById(R.id.imageButton11);
        _ibHeros[11] = (ImageButton) findViewById(R.id.imageButton12);
        _ibHeros[12] = (ImageButton) findViewById(R.id.imageButton13);
        _ibHeros[13] = (ImageButton) findViewById(R.id.imageButton14);
        _ibHeros[14] = (ImageButton) findViewById(R.id.imageButton15);

        _ibHeros[15] = (ImageButton) findViewById(R.id.imageButton16);
        _ibHeros[16] = (ImageButton) findViewById(R.id.imageButton17);
        _ibHeros[17] = (ImageButton) findViewById(R.id.imageButton18);
        _ibHeros[18] = (ImageButton) findViewById(R.id.imageButton19);
        _ibHeros[19] = (ImageButton) findViewById(R.id.imageButton20);

        _ibHeros[20] = (ImageButton) findViewById(R.id.imageButton21);
        _ibHeros[21] = (ImageButton) findViewById(R.id.imageButton22);

        ///Set Hero Button Listener Events
        for(int i=0; i < 22; i++)
        {
            ///Repurpose setTransitionName as a means to pass the pronunciation of the hero name around.
            _ibHeros[i].setTransitionName(_HSC._sHeroesPro[i]);///API 21
            _ibHeros[i].setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    sndVoiceWords(view.getTransitionName());///API 21
                }
            });
        }

        /*Create Listener/Events*/
        buttonRandHero = (Button) findViewById(R.id.buttonRandHero);
        buttonRandHero.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Disable Last Selected Hero
                if(_HSC._iLastHero != -1)
                {
                    _ibHeros[_HSC._iLastHero].setForeground( _drwDisabled );///API 23
                }

                //Get Random Hero
                int iResult = 0;
                if(_cbPlayAll.isChecked())
                {
                    //Update Last Hero
                    if(_HSC.AllHeroesPlayed())
                    {
                        ClearHeroes();
                    }
                    iResult = _HSC.GetRandomHero()-1;
                }
                else
                {
                    iResult = _HSC.GetRandomInt(1,22)-1;
                }

                _HSC._iLastHero = iResult;
                _yourHero.setText(_HSC._sHeroes[_HSC._iLastHero]);

                //Indicate Chosen Hero
                _ibHeros[_HSC._iLastHero].setForeground( _drwSelection  );///API 22

                sndCurrentHero();
            }
        });
        Button buttonReset = (Button) findViewById(R.id.buttonReset);
        buttonReset.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ClearHeroes();
            }
        });
        _cbPlayAll.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                UpdateRemainingHeroList();
            }
        });

        //About Information Display
        Button buttonAbout = (Button) findViewById(R.id.buttonAbout);
        buttonAbout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ShowAbout(view);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Orientation Event
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            //buttonRandHero; //Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        }
        else
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            //Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }

    public void sndCurrentHero()
    {
        //tv.DoThing(_sHeroes[_iLastHero]);
        //Toast.makeText(main, words,Toast.LENGTH_SHORT).show();
        if(_cbMute.isChecked() == false)
        {
            String utteranceId = this.hashCode() + "";
            _TTS.speak(_HSC._sHeroesPro[_HSC._iLastHero], TextToSpeech.QUEUE_FLUSH, null, utteranceId);
        }
    }
    public void sndVoiceWords(String words)
    {
        //tv.DoThing(_sHeroes[_iLastHero]);
        //Toast.makeText(main, words,Toast.LENGTH_SHORT).show();
        if(_cbMute.isChecked() == false)
        {
            String utteranceId = this.hashCode() + "";
            _TTS.speak(words, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
        }
    }

    //To be used if change in Randomizer Settings
    public void UpdateRemainingHeroList()
    {
        //Create List from nulled foreground Heros
        _HSC._iRemainingHeroesSize = 0;
        for(int i=0; i<_HSC._iMaxHeroes; i++)
        {
            if(_ibHeros[i].getForeground() == null)///API 23
            {
                _HSC._iRemainingHeroes[_HSC._iRemainingHeroesSize] = i+1;
                _HSC._iRemainingHeroesSize++;
            }
        }
    }

    public void ClearHeroes()
    {
        _yourHero.setText("No Hero Selected");
        //Clear Foregrounds
        for(int i=0; i<_HSC._iMaxHeroes; i++)
        {
            _ibHeros[i].setForeground(null);///API 23
        }
        _HSC.ClearHeroList();
    }

    public void ShowAbout(View view)
    {
        AlertDialog.Builder aboutDialog = new AlertDialog.Builder(this);
        aboutDialog.setMessage("I, Koy Foster, started working on this as a 'Hello World' project for Android Studio and a desire to easily play random heroes in Overwatch since this feature is currently missing ingame.\n\n I claim no rights to Blizzard Entertainment assets or intellectual property.").setPositiveButton("Close",new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        //}).setTitle("About 'Overwatch: Hero Randomizer'(Build 1.2016.09.09)").setIcon(R.drawable.koigo64).create();
        }).setTitle("About 'Overwatch: Hero Randomizer'(Build 1.2016.10.29)").setIcon(R.drawable.koigo64).create();
        //Show
        aboutDialog.show();
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
}//End of Class

