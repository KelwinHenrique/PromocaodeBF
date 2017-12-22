package kelwin.primeiro.promobf.Adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import kelwin.primeiro.promobf.Fragmento.CuponsFragment;
import kelwin.primeiro.promobf.Fragmento.MaisRecentes;
import kelwin.primeiro.promobf.Fragmento.MaisVistos24Horas;


/**
 * Created by dbugs on 22/08/2017.
 */

public class TabAdapter extends FragmentStatePagerAdapter {
    private String[] titulosAbas = {"RECENTES","+ VISTOS  24 Horas", "CUPONS"} ;





    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 1:
                fragment = new MaisVistos24Horas();

                break;
            case 0:
                fragment = new MaisRecentes();
                break;
            case 2:
                fragment = new CuponsFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return titulosAbas.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titulosAbas[position];
    }
}
