package com.roulettes.projectmusicplayer;

import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends Activity {

    public TextView songName, duration;
    private MediaPlayer mediaPlayer;
    private double timeElapsed = 0, finalTime = 0;
    private int forwardTime = 2000, backwardTime = 2000;
    private Handler durationHandler = new Handler();
    private SeekBar seekbar;
    //handler to change seekBarTime
    private Runnable updateSeekBarTime = new Runnable() {
        public void run() {
            //get current position
            timeElapsed = mediaPlayer.getCurrentPosition();
            //set seekbar progress
            seekbar.setProgress((int) timeElapsed);
            //set time remaing
            double timeRemaining = finalTime - timeElapsed;
            duration.setText(String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining), TimeUnit.MILLISECONDS.toSeconds((long) timeRemaining) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining))));

            //repeat yourself that again in 100 miliseconds
            durationHandler.postDelayed(this, 100);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set the layout of the Activity
        setContentView(R.layout.activity_main);

        //initialize views
        initializeViews();
    }

    public void initializeViews() {
        songName = (TextView) findViewById(R.id.songName);
        mediaPlayer = MediaPlayer.create(this, R.raw.valse_des_lilas);
        finalTime = mediaPlayer.getDuration();
        duration = (TextView) findViewById(R.id.songDuration);
        seekbar = (SeekBar) findViewById(R.id.seekBar);
        songName.setText("valse_des_lilas.mp3");

        seekbar.setMax((int) finalTime);
        seekbar.setClickable(false);
    }

    // play mp3 song
    public void play(View view) {
        mediaPlayer.start();
        timeElapsed = mediaPlayer.getCurrentPosition();
        seekbar.setProgress((int) timeElapsed);
        durationHandler.postDelayed(updateSeekBarTime, 100);
    }

    // pause mp3 song
    public void pause(View view) {
        mediaPlayer.pause();
    }


    // go forward at forwardTime seconds
    public void forward(View view) {
        //check if we can go forward at forwardTime seconds before song endes
        if ((timeElapsed + forwardTime) <= finalTime) {
            timeElapsed = timeElapsed + forwardTime;

            //seek to the exact second of the track
            mediaPlayer.seekTo((int) timeElapsed);
        }
    }
    //C:\Users\kevgo\AndroidStudioProjects\MusicProject
}
/*import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.*;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> files = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.listView);
        Button btn_go = (Button)findViewById(R.id.btn_go);
        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File[] rootFiles = File.listRoots();
                for(int i=0;i<rootFiles.length;i++){
                    File[] directory = rootFiles [i].listFiles();
                    for(int j=0;j<directory.length;j++){
                        files.add(directory [j].getAbsolutePath());
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_expandable_list_item_1, files);
                listView.setAdapter(adapter);
            }
        });
    }

   /* //1ere méthode : lister les fichiers du répertoire "repertoire"

    public static File[] listeDeFichiers(String repertoire) {
        File directoryToScan = new File(repertoire);
        File[] fichiers = null;
        System.out.println("Repertoire \"" + repertoire + "\" chargé.");
        //Liste tous les fichiers contenue dans le dossier spécifier :
        fichiers = directoryToScan.listFiles();
        return fichiers;
    }


//2eme Méthode : filtrage de la liste de recherche

    public static File[] listerRepertoire(File[] fichiers, String fichierAChercher) {

        File[] listeDesFichiersTempo = new File[fichiers.length];
        boolean trouve = false;
        int nbreTrouve = 0;
        float pourcentageRecherche = 0;
        int i;
        //boucle de recherche de fichier correspondant au filtre :
        for (i = 0; i < fichiers.length; i++) {
            //calcul du pourcentage de progression :
            pourcentageRecherche = (float)Math.round(((float)(100 * i) / fichiers.length)* 100) / 100;
            //vérifie si le fichier actuel correspond a la recherche
            //la variable fichierAChercher a pour valeur possible :
            //  *  "(?i)blabla.*" => fichier commençant par "blabla"
            //  *  "(?i).*blabla.*" => fichier contenant "blabla"
            //  *  "(?i).*blabla" => fichier finissant par "blabla"
            if (fichiers[i].toString().matches(fichierAChercher) == true) {
                listeDesFichiersTempo[nbreTrouve] = fichiers[i];
                trouve = true;
                System.out.println(pourcentageRecherche + "% Fichier trouvé : " + listeDesFichiersTempo[nbreTrouve]);
                nbreTrouve++;

            } else {
                System.out.println(pourcentageRecherche + "%");
            }
        }
        //Création du tableau contenant seulement les fichiers filtré :
        File[] listeDesFichiers = new File[nbreTrouve];
        for (i = 0; i < listeDesFichiers.length; i++) {
            listeDesFichiers[i] = listeDesFichiersTempo[i];
        }
        if (trouve) {
            System.out.println("100%\n " + nbreTrouve + " fichier(s) trouvé.");
        } else {
            System.out.println("100%\nAucun fichier trouvé.");
        }
        //retourne la liste des fichiers recherchés
        return listeDesFichiers;
    }/

}*/

