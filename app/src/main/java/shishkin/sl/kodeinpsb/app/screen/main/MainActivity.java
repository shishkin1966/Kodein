package shishkin.sl.kodeinpsb.app.screen.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import shishkin.sl.kodeinpsb.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

    }
}
