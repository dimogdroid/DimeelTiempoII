package dimogdroid.com.introduction;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dimogdroid.com.dimeeltiempo.R;


/**
 * Created by zerones-hardik on 10/9/16.
 */
public class IntroFragment_4  extends Fragment {


    public IntroFragment_4() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_intro_fragment_4, container, false);
        return rootView;
    }


}
