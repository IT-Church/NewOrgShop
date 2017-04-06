package com.paramonod.kikos;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static android.content.Context.MODE_PRIVATE;
import static android.provider.Telephony.Mms.Part.FILENAME;
import static com.paramonod.kikos.MainActivity.main;

/**
 * Created by KiKoS on 06.04.2017.
 */

public class Favorites {
    public SharedPreferences sPref;
    void write(String s) {
        sPref = main.getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("Const", s);
        ed.commit();
        Toast.makeText(main, "Text saved", Toast.LENGTH_SHORT).show();
    }
}
