package com.example.koyfoster.helloworldv2;

/**
 * Created by Koy Foster on 10/28/2016.
 */
import java.util.Random;

public class HeroSelectController
{
    int _iLastHero = -1;
    int _iMaxHeroes = 23;
    int _iRemainingHeroesSize = 23;
    int _iRemainingHeroes[] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23};
    //Hero List
    String _sHeroes[] =
            {"Genji","McCree","Pharah","Reaper","Soldier: 76", "Sombra", "Tracer"
                    ,"Bastion","Hanzo","JunkRat","Mei","Torbjörn","WidowMaker"
                    ,"D.Va","Reinhardt","Roadhog","Winston","Zarya"
                    ,"Ana","Lúcio","Mercy","Symmetra","Zenyatta"
                    ,"Doomfist","?????"
            };
    String _sHeroesPro[] =
            {"Ghenji","Mik Cree","Pharah","Reaper","Soldier 76", "Sombra", "Tracer"
                    ,"Bastion","Honzo","JunkRat","Mei","Torbjörn","WidowMaker"
                    ,"DeeVah","Reinhardt","Roadhog","Winston","Zah-reah"
                    ,"Ana","Lúcio","Mercy","Symmetra","Zenyatta"
                    ,"Doomfist","?????"
            };

    //Constructor
    /*public HeroSelectController()
    {


    }*/

    public void ClearHeroList()
    {
        _iLastHero = -1;

        //RESET List
        for(int i=0; i<_iMaxHeroes; i++)
        {_iRemainingHeroes[i] = i+1;}
        _iRemainingHeroesSize = _iMaxHeroes;
    }

    public int GetRandomInt(int min, int max)
    {
        int iResult = 1;
        Random rand = new Random();

        iResult = rand.nextInt(max+1 - min) + min;

        return iResult;
    }//End of GetRandomInt()

    public Boolean AllHeroesPlayed()
    {
        //Validate Hero List
        //If No Heroes, RESET
        if((_iRemainingHeroesSize == 0) || (_iRemainingHeroesSize > _iMaxHeroes))
        {
            return true;
        }
        return false;
    }

    public int GetRandomHero()
    {
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
    }//end of GetRandomHero()
}
