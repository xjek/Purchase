package ru.groupstp.procurementcommission.ui.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import ru.groupstp.procurementcommission.R;

/**
 * Базовое диалоговое окно приложения
 */
public abstract class BaseDialog extends AlertDialog.Builder {

    private AlertDialog mAlertDialog;
    private View mContentView;

    private LinearLayout mMainView;


    public BaseDialog(Context context, boolean needInit) {
        super(context);
        if (needInit)
            initView();
        setCancelable(false);
    }

    void initView() {

        mMainView = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.dialog, null);
        setView(mMainView);
        closeDialog(mMainView);
        setTitle(mMainView);
        mContentView = LayoutInflater.from(getContext()).inflate(layout(), null);
        content(mContentView);
        mMainView.addView(mContentView);

    }

    private void closeDialog(View mainView) {
        mainView.findViewById(R.id.close_dialog).setOnClickListener(view ->{
            onClickCloseDialog();
        });
    }

    void onClickCloseDialog() {
        mAlertDialog.dismiss();
    }

    public void setTitle(View mainView) {
        ((TextView) mainView.findViewById(R.id.title_dialog)).setText(title());
    }

    abstract void content(View contentView);

    public abstract int title();

    public abstract int layout();

    public void showDialog() {
        initView();
       // content(mContentView);
        setCancelable(false);

        mAlertDialog = super.create();
        mAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        mAlertDialog.show();
    }

    @Override
    public AlertDialog show() {
        initView();
        mAlertDialog = super.show();
        mAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        return mAlertDialog;
    }

}
