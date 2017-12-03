package projects.nyinyihtunlwin.news.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import projects.nyinyihtunlwin.news.R;
import projects.nyinyihtunlwin.news.delegates.LoginRegisterDeligate;

/**
 * Created by Dell on 11/26/2017.
 */

public class RegisterFragment extends BaseFragment {

    private LoginRegisterDeligate registerDeligate;

    public static RegisterFragment newInstance() {
        RegisterFragment registerFragment = new RegisterFragment();
        return registerFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        registerDeligate = (LoginRegisterDeligate) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View registerView = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, registerView);
        return registerView;
    }

    @Override
    public void onStart() {
        super.onStart();
        registerDeligate.setScreenTitle("Register");
    }
}
