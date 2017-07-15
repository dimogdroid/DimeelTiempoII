package dimogdroid.com.introduction;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dimogdroid.com.dimeeltiempo.R;

public class IntroFragment_2 extends Fragment {

    public IntroFragment_2() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_intro_fragment_2, container, false);
        return rootView;
    }


}
