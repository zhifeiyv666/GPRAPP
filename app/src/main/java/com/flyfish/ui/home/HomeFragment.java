package com.flyfish.ui.home;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.flyfish.R;
import com.flyfish.common.GlobalApp;
import com.flyfish.common.utils.ClipUtil;

import org.apache.commons.lang3.StringUtils;

public class HomeFragment extends Fragment {

    private View root;
    private TextView currentPasswordView;
    private EditText appNameEditText;
    EditText passwordLengthText;

    Button generatePasswordButton;
    Button copyAndStorePasswordButton;

    HomeViewModel pageData;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        pageData = HomeViewModel.getInstance();
        root = inflater.inflate(R.layout.fragment_home, container, false);

        currentPasswordView = root.findViewById(R.id.currentPassword);
        if (pageData.getPassword() != null) {
            currentPasswordView.setText(pageData.getPassword().getValue());
        }
        appNameEditText = root.findViewById(R.id.appName);
        if (pageData.getAppName() != null) {
            appNameEditText.setText(pageData.getAppName());
        }
        appNameEditText.addTextChangedListener(new TextWatcher() {
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

        passwordLengthText = root.findViewById(R.id.passwordLength);
        if (pageData.getPassWordLen() != null) {
            passwordLengthText.setText(String.valueOf(pageData.getPassWordLen()));
        }
        passwordLengthText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    String value = editable.toString();
                    value = value.replaceAll("[^0-9]", "");
                    if (value.length() == 0) {
                        pageData.setPassWordLen(null);
                        return;
                    }
                    Integer len = Integer.valueOf(value);
                    pageData.setPassWordLen(len);
                } catch (NumberFormatException e) {

                }
            }
        });
        generatePasswordButton = root.findViewById(R.id.generatePassword);
        generatePasswordButton.setOnClickListener(new GeneratePasswordButtonOnClickListener());

        copyAndStorePasswordButton = root.findViewById(R.id.copyAndStorePassword);
        copyAndStorePasswordButton.setOnClickListener(new CopyAndStorePasswordButtonListener());

        return root;
    }

    private class GeneratePasswordButtonOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            pageData.setAppName(appNameEditText.getText().toString());
            if (StringUtils.isAllBlank(pageData.getAppName())) {
                Toast.makeText(getContext(), R.string.error_no_app_name, Toast.LENGTH_SHORT).show();
                return;
            }
            String passwordLength = passwordLengthText.getText().toString();
            if (StringUtils.isAllBlank(passwordLength)) {
                Toast.makeText(getContext(), R.string.error_no_password_length, Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                pageData.setPassWordLen(Integer.valueOf(passwordLength));
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), R.string.error_wrong_password_length_not_number, Toast.LENGTH_SHORT).show();
                return;
            }

            if (pageData.getPassWordLen() <= 0) {
                Toast.makeText(getContext(), R.string.error_wrong_password_length_number_error, Toast.LENGTH_SHORT).show();
                return;
            }
            pageData.setPassword(GlobalApp.getInstance().genPassword(pageData.getPassWordLen()));
            currentPasswordView.setText(pageData.getPassword().getValue());
        }
    }

    private class CopyAndStorePasswordButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (pageData.getPassword() == null) {
                Toast.makeText(getContext(), R.string.error_no_password, Toast.LENGTH_SHORT).show();
                return;
            }
            boolean copyResult = ClipUtil.copyToClipboard(getActivity(), pageData.getPassword().getValue(), "simple text");
            if (!copyResult) {
                Toast.makeText(getContext(), R.string.error_copy_failed, Toast.LENGTH_SHORT);
                return;
            }
            boolean addResult = GlobalApp.getInstance().addPassword(pageData.getAppName(), pageData.getPassword());
            if (!addResult) {
                Toast.makeText(getContext(), R.string.error_unknown, Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(getContext(), R.string.success_copy_password, Toast.LENGTH_SHORT).show();
        }
    }
}
