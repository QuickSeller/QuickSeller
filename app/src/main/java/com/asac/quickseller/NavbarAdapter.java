package com.asac.quickseller;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.asac.quickseller.Fragments.shopping.HomeFragment;
import com.asac.quickseller.Fragments.shopping.ProfileFragment;
import com.asac.quickseller.Fragments.shopping.SettingsFragment;

public class NavbarAdapter extends FragmentStateAdapter {
    public NavbarAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:return new HomeFragment();
            case 1:return new ProfileFragment();
            case 2:return new SettingsFragment();
            default:return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
