package com.flyfish.ui.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.flyfish.R;
import com.flyfish.bean.Password;
import com.flyfish.common.GlobalApp;

import java.util.List;

public class ViewFragment extends Fragment {

    View root;
    TextView appSearchNameView;
    Button searchPasswordButton;
    ListView passwordSearchResultView;

    private ViewViewModel pageData;

    public View onCreateView(@NonNull LayoutInflater inflater,
                                  ViewGroup container, Bundle savedInstanceState) {
        pageData =
                ViewModelProviders.of(this).get(ViewViewModel.class);
        root = inflater.inflate(R.layout.fragment_view, container, false);
        appSearchNameView = root.findViewById(R.id.appSearchName);
        appSearchNameView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                pageData.setAppName(editable.toString());
            }
        });
        searchPasswordButton = root.findViewById(R.id.searchPassword);
        passwordSearchResultView = root.findViewById(R.id.passwordSearchResultView);

        if (pageData.getAppName() != null) {
            appSearchNameView.setText(pageData.getAppName());
        }
        searchPasswordButton.setOnClickListener(new SearchButtonOnClickListener());
        return root;
    }

    private class SearchButtonOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            PasswordSearchResultViewAdapter adapter = (PasswordSearchResultViewAdapter) passwordSearchResultView.getAdapter();
            if (adapter != null) {
                passwordSearchResultView.setAdapter(null);
            }
            if (pageData.getAppName() == null) {
                Toast.makeText(getActivity(), R.string.error_no_app_name, Toast.LENGTH_SHORT).show();
                return;
            }
            List<Password> passwords = GlobalApp.getInstance().searchPasswords(pageData.getAppName());
            if (passwords == null || passwords.size() == 0) {
                Toast.makeText(getContext(), R.string.not_search_result, Toast.LENGTH_SHORT).show();
                return;
            }
            passwordSearchResultView.setAdapter(
                    new PasswordSearchResultViewAdapter(getContext(), R.layout.password_item, passwords)
            );
        }
    }
}
