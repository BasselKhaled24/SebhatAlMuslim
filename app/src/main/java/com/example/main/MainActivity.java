package com.example.main;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView word;
    Button counter;
    Button reset;
    Button audio;
    Button theme;
    ConstraintLayout background;
    String e = "اضغط\nهنا";
    String s="سُبْحَانَ اللَّه";
    String h="الحَمْدُ للَّه";
    String a="اللَّهُ أكْبَر";
    String l="لا الهَ الَّا اللَّهُ وحده لا شريكَ له, له المُلكُ وله الحمدُ, وهو على كلِّ شئٍ قدير";
    Animation rotateOnClickAnimation;
    Animation rotateOnEndAnimation;
    Animation clickAnimation;
    boolean audio_on;
    boolean theme_light;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String THEME_PREF = "theme";
    public static final String AUDIO_PREF = "audio";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_main);

        word = (TextView) findViewById(R.id.word);
        counter = (Button) findViewById(R.id.counter);
        reset = (Button) findViewById(R.id.reset);
        audio = (Button) findViewById(R.id.audio);
        theme = (Button) findViewById(R.id.theme);
        background = (ConstraintLayout) findViewById(R.id.background);
        final Vibrator vibrator = (Vibrator) MainActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
        final MediaPlayer ting = MediaPlayer.create(this,R.raw.ting);
        //VibrationEffect shortVibration = VibrationEffect.createOneShot(50, 50);
        //VibrationEffect longVibration = VibrationEffect.createOneShot(100, 100);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        theme_light = sharedPreferences.getBoolean(THEME_PREF, false);
        ActionBar actionBar = getSupportActionBar();
        if(!theme_light)
        {
            background.setBackgroundResource(R.color.grey);
            theme.setBackgroundResource(R.drawable.light_mode);
        }
        else
        {
            background.setBackgroundResource(R.color.white);
            theme.setBackgroundResource(R.drawable.dark_mode);
        }
        audio_on = sharedPreferences.getBoolean(AUDIO_PREF, true);
        if(!audio_on)
            audio.setBackgroundResource(R.drawable.audio_off);
        else
            audio.setBackgroundResource(R.drawable.audio_on);

        counter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counter.getText().equals(e))
                {
                    counter.setText("1");
                    counter.setTextSize(50);
                }
                else
                    counter.setText(Integer.parseInt(counter.getText().toString())+1+"");
                vibrator.vibrate(50);
                if(Integer.parseInt(counter.getText().toString())<33)
                    word.setText(s);
                else if(Integer.parseInt(counter.getText().toString())==33)
                {
                    word.setText(s);
                    if(audio_on)
                        ting.start();
                }
                else if(Integer.parseInt(counter.getText().toString())==34)
                {
                    word.setText(h);
                    rotateOnClickAnimation();
                    vibrator.vibrate(100);
                }
                else if(Integer.parseInt(counter.getText().toString())<66)
                    word.setText(h);
                else if(Integer.parseInt(counter.getText().toString())==66)
                {
                    word.setText(h);
                    if(audio_on)
                        ting.start();
                }
                else if(Integer.parseInt(counter.getText().toString())==67)
                {
                    word.setText(a);
                    rotateOnClickAnimation();
                    vibrator.vibrate(100);
                }
                else if(Integer.parseInt(counter.getText().toString())<99)
                    word.setText(a);
                else if(Integer.parseInt(counter.getText().toString())==99)
                {
                    word.setText(a);
                    if(audio_on)
                        ting.start();
                }
                else if(Integer.parseInt(counter.getText().toString())==100)
                {
                    word.setText(l);
                    rotateOnClickAnimation();
                    vibrator.vibrate(100);
                }
                else if(Integer.parseInt(counter.getText().toString())>100)
                {
                    counter.setText(e);
                    counter.setTextSize(40);
                    word.setText(s);
                    rotateOnEndAnimation();
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset_click_animation();
                counter.setText(e);
                counter.setTextSize(40);
                word.setText(s);
                vibrator.vibrate(100);
            }
        });

        audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(100);
                audio_click_animation();
                if(audio_on)
                    audio.setBackgroundResource(R.drawable.audio_off);
                else
                    audio.setBackgroundResource(R.drawable.audio_on);
                audio_on=!audio_on;
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(AUDIO_PREF, audio_on);
                editor.apply();
            }
        });

        theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(100);
                theme_click_animation();
                if(theme_light)
                {
                    background.setBackgroundResource(R.color.grey);
                    theme.setBackgroundResource(R.drawable.light_mode);
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                else
                {
                    background.setBackgroundResource(R.color.white);
                    theme.setBackgroundResource(R.drawable.dark_mode);
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                theme_light=!theme_light;
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(THEME_PREF, theme_light);
                editor.apply();
            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.settings, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        final Vibrator vibrator = (Vibrator) MainActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
//        switch (item.getItemId())
//        {
//            case R.id.reset:
//            {
//                counter.setText(e);
//                counter.setTextSize(40);
//                word.setText(s);
//                vibrator.vibrate(100);
//                return true;
//            }
//            case R.id.theme:
//            {
//                vibrator.vibrate(100);
//                ActionBar actionBar = getSupportActionBar();
//                if(theme_light)
//                {
//                    item.setIcon(R.drawable.light_mode);
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                }
//                else
//                {
//                    item.setIcon(R.drawable.dark_mode);
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                }
//                theme_light=!theme_light;
//                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putBoolean(THEME_PREF, theme_light);
//                editor.apply();
//                return true;
//            }
//            case R.id.audio:
//            {
//                vibrator.vibrate(100);
//                if(audio_on)
//                    audio.setBackgroundResource(R.drawable.audio_off);
//                else
//                    audio.setBackgroundResource(R.drawable.audio_on);
//                audio_on=!audio_on;
//                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putBoolean(AUDIO_PREF, audio_on);
//                editor.apply();
//                return true;
//            }
//        }
//        return super.onOptionsItemSelected(item);
//    }

    private void audio_click_animation()
    {
        clickAnimation = AnimationUtils.loadAnimation(this, R.anim.click_animation);
        audio.startAnimation(clickAnimation);
    }

    private void reset_click_animation()
    {
        clickAnimation = AnimationUtils.loadAnimation(this, R.anim.click_animation);
        reset.startAnimation(clickAnimation);
    }

    private void theme_click_animation()
    {
        clickAnimation = AnimationUtils.loadAnimation(this, R.anim.click_animation);
        theme.startAnimation(clickAnimation);
    }

    private void rotateOnClickAnimation()
    {
        rotateOnClickAnimation = AnimationUtils.loadAnimation(this, R.anim.rotation_on_click_animation);
        counter.startAnimation(rotateOnClickAnimation);
    }

    private void rotateOnEndAnimation()
    {
        rotateOnEndAnimation = AnimationUtils.loadAnimation(this, R.anim.rotation_on_end_animation);
        counter.startAnimation(rotateOnEndAnimation);
    }
}