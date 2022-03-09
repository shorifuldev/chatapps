package com.example.ichat.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.ichat.fragment.ChatFragment;
import com.example.ichat.fragment.FeedFragment;
import com.example.ichat.fragment.UserFragment;

public class FragmentAdapter extends FragmentPagerAdapter {

    String [] name= {"Chat","Feed","User"};

    public FragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ChatFragment();
            case 1:
                return new FeedFragment();
            case 2:
                return new UserFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return name.length;
    }

}
