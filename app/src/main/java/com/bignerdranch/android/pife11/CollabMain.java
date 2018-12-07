package com.bignerdranch.android.pife11;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import java.util.ArrayList;


public class CollabMain extends AppCompatActivity {
    private Button bApplyFilter;

    //Checkboxes to Select All of Them
    private CheckBox checkall;

    //Checkboxes for Genres
    private CheckBox blues;
    private CheckBox classical;
    private CheckBox country;
    private CheckBox edm;
    private CheckBox hip_hop;
    private CheckBox jazz;
    private CheckBox pop;
    private CheckBox rock;

    //Checkboxes for Instruments
    private CheckBox bass;
    private CheckBox drums;
    private CheckBox flute;
    private CheckBox guitar;
    private CheckBox piano;
    private CheckBox sing;
    private CheckBox viola;
    private CheckBox violin;

    //return variables
    ArrayList<String> Genres = new ArrayList<String>();
    ArrayList<String> Instruments = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collab_main);

        //Declaring the Button to initiate the filters
        bApplyFilter = findViewById(R.id.apply_filter);

        //Declaring the CheckBox Filter for Genres
        blues = findViewById(R.id.blues_check);
        classical = findViewById(R.id.classical_check);
        country = findViewById(R.id.country_check);
        edm = findViewById(R.id.edm_check);
        hip_hop = findViewById(R.id.hiphop_check);
        jazz = findViewById(R.id.jazz_check);
        pop = findViewById(R.id.pop_check);
        rock = findViewById(R.id.rock_check);

        //Declaring the Checkbox Filters for Intruments
        bass = findViewById(R.id.bass_check);
        drums = findViewById(R.id.drums_check);
        flute = findViewById(R.id.flute_check);
        guitar = findViewById(R.id.guitar_check);
        piano = findViewById(R.id.piano_check);
        sing = findViewById(R.id.sing_check);
        viola = findViewById(R.id.viola_check);
        violin = findViewById(R.id.violin_check);

        //Checking all of the buttons
        checkall = findViewById(R.id.select_all);

        checkall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkall.isChecked()){
                    blues.setChecked(true);
                    classical.setChecked(true);
                    country.setChecked(true);
                    edm.setChecked(true);
                    hip_hop.setChecked(true);
                    jazz.setChecked(true);
                    pop.setChecked(true);
                    rock.setChecked(true);
                    bass.setChecked(true);
                    drums.setChecked(true);
                    flute.setChecked(true);
                    guitar.setChecked(true);
                    piano.setChecked(true);
                    sing.setChecked(true);
                    viola.setChecked(true);
                    violin.setChecked(true);
                } else {
                    blues.setChecked(false);
                    classical.setChecked(false);
                    country.setChecked(false);
                    edm.setChecked(false);
                    hip_hop.setChecked(false);
                    jazz.setChecked(false);
                    pop.setChecked(false);
                    rock.setChecked(false);
                    bass.setChecked(false);
                    drums.setChecked(false);
                    flute.setChecked(false);
                    guitar.setChecked(false);
                    piano.setChecked(false);
                    sing.setChecked(false);
                    viola.setChecked(false);
                    violin.setChecked(false);
                }

            }
        });

        /*
            Next set of methods will determine which checks are checked in order to carry out the filter
            feature for the Search Collab in the next method
         */

        bApplyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check to see which boxes are checked within the realm of Genres
                if (blues.isChecked()){
                    Genres.add("blues");
                }
                if (classical.isChecked()){
                    Genres.add("classical");
                }
                if (country.isChecked()){
                    Genres.add("country");
                }
                if (edm.isChecked()){
                    Genres.add("edm");
                }
                if (hip_hop.isChecked()){
                    Genres.add("hiphop");
                }
                if (jazz.isChecked()){
                    Genres.add("jazz");
                }
                if (pop.isChecked()){
                    Genres.add("pop");
                }
                if (rock.isChecked()){
                    Genres.add("rock");
                }

                //check to see which boxes are checked within the Instruments side of things
                if (bass.isChecked()){
                    Instruments.add("bass");
                }
                if (drums.isChecked()){
                    Instruments.add("drums");
                }
                if (flute.isChecked()){
                    Instruments.add("flute");
                }
                if (guitar.isChecked()){
                    Instruments.add("guitar");
                }
                if (piano.isChecked()){
                    Instruments.add("piano");
                }
                if (sing.isChecked()){
                    Instruments.add("sing");
                }
                if (viola.isChecked()){
                    Instruments.add("viola");
                }
                if (violin.isChecked()){
                    Instruments.add("violin");
                }
                
                Intent applyfilter_intent = new Intent(CollabMain.this, SearchCollab.class).putStringArrayListExtra("GENRES", Genres).putStringArrayListExtra("INSTRUMENTS", Instruments);
                startActivity(applyfilter_intent);
            }
        });

    }
}
