package com.example.chatapp.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.chatapp.Fragments.ChatsFragment;
import com.example.chatapp.Fragments.UserFragment;

public class FragmentsAdapter  extends FragmentPagerAdapter {
    public FragmentsAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0 : return new UserFragment();
            case 1 : return new ChatsFragment();
            default: return new UserFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0 )
        {
            title = "Users";
        }
        if (position == 1 )
        {
            title = "Chats";
        }
        return title;
    }
}
