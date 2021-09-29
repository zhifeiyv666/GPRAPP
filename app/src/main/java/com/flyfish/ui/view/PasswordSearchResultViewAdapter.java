package com.flyfish.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.flyfish.R;
import com.flyfish.bean.Password;
import com.flyfish.common.GlobalApp;
import com.flyfish.common.utils.ClipUtil;
import com.flyfish.common.utils.DateFormatUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class PasswordSearchResultViewAdapter extends ArrayAdapter<Password> {

    private int resourceId;
    private List<Password> passwords;

    public PasswordSearchResultViewAdapter(@NonNull Context context, int viewResourceId, List<Password> passwords) {
        super(context, viewResourceId, passwords);
        resourceId = viewResourceId;
        this.passwords = passwords;
    }

    private void remove(int index) {
        if (passwords.size() >= index) {
            passwords.remove(index);
            notifyDataSetChanged();
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Password password = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);

        TextView passwordText = view.findViewById(R.id.passwordSearched);
        passwordText.setText(password.getValue());

        TextView passwordCreatedtimeView = view.findViewById(R.id.passwordCreatedTime);
        long timestamp = password.getCreateTimeStamp();
        passwordCreatedtimeView.setText(DateFormatUtil.format(timestamp, "yyyy-MM-dd hh:mm:ss"));
        Button deleteButton = view.findViewById(R.id.passwordDelete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // todo confirm
                remove(position);
                GlobalApp.getInstance().updatePassword();
            }
        });
        Button copyButton = view.findViewById(R.id.passwordCopy);

        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = "";
                if (password != null) {
                    value = password.getValue();
                }
                boolean copyResult = ClipUtil.copyToClipboard(view.getContext(), value, "");
                if (copyResult) {
                    Toast.makeText(getContext(), R.string.success_copy_password, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), R.string.error_copy_failed, Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}
