package dimogdroid.com.dimeeltiempo;

/**
 * Created by DDAVILA on 19/06/2017.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TabFavoritos tab1 = new TabFavoritos();
                return tab1;
            case 1:
                TabSpania tab2 = new TabSpania();
                return tab2;
            case 2:
                TabResto tab3 = new TabResto();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}