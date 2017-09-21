package com.example.pierre.movieapplicaiton;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements FilmListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    // Deze functie pas toevoegen wanneer je het tegenkomt in het stappenplan (DetailListener)
    @Override
    public void setFieldsAndPicture(int position) {

        /*
        * De comments met sterretjes weghalen en de rest hieronder in comments zetten indien je
        * niet wilt dat landscape mode werkt, en enkel wilt werken met portrait mode
        * Wanneer je het stappenplan volgt, kan je best deze eerst uncommenten en onderstaande code
        * in comments zetten.
        // Onderstaande code is nodig om de nieuwe Activity (met de nieuwe Fragment) te openen
        // De aangeklikte positie wordt doorgegeven naar de volgende Activity
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
        */

        // Controleer of 'fragment_container2' (de fragment met 'DetailFragment')
        if (findViewById(R.id.fragment_container2) != null) {
            // Indien het bestaat haal je deze op via de Fragmentmanager, belangrijk hier is dat je het id van de container met het fragment in
            // ophaalt en niet het id van het fragment zelf!
            FilmLayout detailFragment = (FilmLayout) getFragmentManager().findFragmentById(R.id.fragment_container2);
            // Roep de functie in DetailFragment aan
            detailFragment.setAllFieldsAndImageFromPosition(position);
        } else {
            // Indien het niet bestaat, stuur je door naar DetailActivity, die op zijn beurt fragment_container2 bevat
            // Dit is dezelfde code als boven in comments staat
            Intent intent = new Intent(this, FilmActivity.class);
            intent.putExtra("position", position);
            startActivity(intent);
        }
    }

}
