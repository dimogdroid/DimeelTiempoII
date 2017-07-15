package dimogdroid.com.dimeeltiempo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.github.paolorotolo.appintro.AppIntro;

import dimogdroid.com.introduction.IntroFragment_1;
import dimogdroid.com.introduction.IntroFragment_2;
import dimogdroid.com.introduction.IntroFragment_3;
import dimogdroid.com.introduction.IntroFragment_4;
import dimogdroid.com.introduction.PreferenceHelper;

public class PresentacionActivity extends AppIntro {

    private PreferenceHelper preferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        preferenceHelper = new PreferenceHelper(this);

        if(preferenceHelper.getIntro().equals("no")){
            Intent intent = new Intent(PresentacionActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            this.finish();
        }

        addSlide(new IntroFragment_1());  //extend AppIntro and comment setContentView
        addSlide(new IntroFragment_2());
        addSlide(new IntroFragment_3());
        addSlide(new IntroFragment_4());


    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);

        //TODO Esto vale, es para pantalla ayuda inicial
        //preferenceHelper.putIntro("no");

        Intent intent = new Intent(PresentacionActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);

        //TODO Esto vale, es para pantalla ayuda inicial
        //preferenceHelper.putIntro("no");

        Intent intent = new Intent(PresentacionActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }

    @Override  // this method is used for removing top and bottom navbars(fullscreen)
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }

    }

}
