package ru.groupstp.procurementcommission.app;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import ru.groupstp.procurementcommission.R;

/**
 * Диалоговое окно с прогресс баром
 */
public class DialogLoad extends DialogFragment {

    private String mTextMessage;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_loading, container, false);
        if (mTextMessage != null)
            ((TextView) view.findViewById(R.id.textLoading)).setText(mTextMessage);
        return view;
    }

    public void setTextLoading(String textLoading) {
        mTextMessage = textLoading + "...";
    }
}
