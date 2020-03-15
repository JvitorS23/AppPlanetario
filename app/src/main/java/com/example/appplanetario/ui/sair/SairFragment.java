package com.example.appplanetario.ui.sair;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.example.appplanetario.ui.ActLogin;

public class SairFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        FragmentActivity act = getActivity();
        if (act != null) {
            startActivity(new Intent(act, ActLogin.class));
            act.finish();
        }
        View view=null;
        return view;
    }
}