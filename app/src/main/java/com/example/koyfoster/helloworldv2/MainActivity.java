package com.example.koyfoster.helloworldv2;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewDebug;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
{
    /*App Fields*/
    TextView _yourHero;
    int _iLastHero = -1;
    int _iMaxHeroes = 22;
    int _iRemainingHeroesSize = 22;
    int _iRemainingHeroes[] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22};
    Drawable _drwSelection, _drwDisabled;
    CheckBox _cbPlayAll;
    Button buttonRandHero = null;
    //Hero List
    String _sHeroes[] =
            {"Genji","McCree","Pharah","Reaper","Soldier: 76", "Tracer"
            ,"Bastion","Hanzo","JunkRat","Mei","Torbjörn","WidowMaker"
            ,"D.Va","Reinhardt","Roadhog","Winston","Zarya"
            ,"Ana","Lúcio","Mercy","Symmetra","Zenyatta"
            ,"Sombra","Doomfist","?????"};
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        /*Link Resources*/
        _cbPlayAll = (CheckBox) findViewById(R.id.checkBoxPlayAll);
        _yourHero = (TextView) findViewById(R.id.textViewHero);
        _drwSelection = (Drawable) getDrawable(R.drawable.selected);
        _drwDisabled = (Drawable) getDrawable(R.drawable.disabled);
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


        /*Create Listener/Events*/
        buttonRandHero = (Button) findViewById(R.id.buttonRandHero);
        buttonRandHero.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Disable Last Selected Hero
                if(_iLastHero != -1)
                {
                    _ibHeros[_iLastHero].setForeground( _drwDisabled );
                }

                //Get Random Hero
                int iResult = 0;
                //Update Last Hero
                if(_cbPlayAll.isChecked())
                {
                    iResult = GetRandomHero()-1;
                }
                else
                {
                    iResult = GetRandomInt(1,22)-1;
                }

                _iLastHero = iResult;
                _yourHero.setText(_sHeroes[_iLastHero]);

                //Indicate Chosen Hero
                _ibHeros[_iLastHero].setForeground( _drwSelection  );
                //sndCurrentHero();
            }
        });
        Button buttonReset = (Button) findViewById(R.id.buttonReset);
        buttonReset.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ClearHeroList();
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

    public int GetRandomInt(int min, int max)
    {
        int iResult = 1;
        Random rand = new Random();

        iResult = rand.nextInt(max+1 - min) + min;

        return iResult;
    }

    public void sndCurrentHero()
    {
        TextVoice tv = new TextVoice();
        tv.Speak(_sHeroes[_iLastHero]);
        //tv.DoThing(_sHeroes[_iLastHero]);
    }

    //To be used if change in Randomizer Settings
    public void UpdateRemainingHeroList()
    {
        //Create List from nulled foreground Heros
        _iRemainingHeroesSize = 0;
        for(int i=0; i<_iMaxHeroes; i++)
        {
            if(_ibHeros[i].getForeground() == null)
            {
                _iRemainingHeroes[_iRemainingHeroesSize] = i+1;
                _iRemainingHeroesSize++;
            }
        }
    }

    public void ClearHeroList()
    {
        _iLastHero = -1;
        _yourHero.setText("No Hero Selected");
        //Clear Foregrounds
        for(int i=0; i<_iMaxHeroes; i++)
        {
            _ibHeros[i].setForeground(null);
        }

        //RESET List
        for(int i=0; i<_iMaxHeroes; i++)
        {_iRemainingHeroes[i] = i+1;}
        _iRemainingHeroesSize = 22;
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
        }).setTitle("About 'Overwatch: Hero Randomizer'(Build 1.2016.09.09)").setIcon(R.drawable.koigo64).create();
        //Show
        aboutDialog.show();
    }

    public int GetRandomHero()
    {
        //Validate Hero List
        //If No Heroes, RESET
        if((_iRemainingHeroesSize == 0) || (_iRemainingHeroesSize > _iMaxHeroes))
        {
            ClearHeroList();
        }

        int iRandNumber = GetRandomInt(1,_iRemainingHeroesSize)-1;
        int iResult = _iRemainingHeroes[iRandNumber];
        //Clear Hero
        _iRemainingHeroes[iRandNumber] = -1;
        _iRemainingHeroesSize--;

        //Recompile List
        boolean bEndSearch = false;
        for(int i=0; i<_iRemainingHeroesSize; i++)
        {
            if(_iRemainingHeroes[i] == -1)
            {
                for(int ii=_iRemainingHeroesSize; ii<_iMaxHeroes; ii++)
                {
                    if(_iRemainingHeroes[ii] != -1)
                    {
                        _iRemainingHeroes[i] = _iRemainingHeroes[ii];
                        _iRemainingHeroes[ii] = -1;
                        break;
                    }
                    else//If end if Reached, break
                    if(ii == _iMaxHeroes-1)
                    {
                        bEndSearch = true;
                    }
                }//End of Sort FOR loop
                //End if No Heroes Left to sort
                if(bEndSearch)
                {
                    break;
                }
            }
            //Increase Hero Count
        }//End of Hero Sorting

        return iResult;
    }
}

