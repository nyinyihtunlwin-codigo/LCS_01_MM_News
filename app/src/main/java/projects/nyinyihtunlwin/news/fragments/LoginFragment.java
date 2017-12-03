package projects.nyinyihtunlwin.news.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import projects.nyinyihtunlwin.news.R;
import projects.nyinyihtunlwin.news.delegates.LoginRegisterDeligate;

/**
 * Created by Dell on 11/26/2017.
 */

public class LoginFragment extends BaseFragment {

    private LoginRegisterDeligate loginRegisterDeligate;

    public static LoginFragment newInstance() {
        LoginFragment loginFragment = new LoginFragment();
        return loginFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loginRegisterDeligate = (LoginRegisterDeligate) context;
    }

    @Override
    public void onStart() {
        super.onStart();
        loginRegisterDeligate.setScreenTitle("Login");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View loginView = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, loginView);
        return loginView;
    }

    @OnClick(R.id.btn_to_register)
    public void onClickToRegister(View view) {
        loginRegisterDeligate.onTapToRegister();
    }
}
