package ru.groupstp.procurementcommission.ui.dialog;


import android.content.Context;
import android.view.View;
import android.widget.TextView;

import ru.groupstp.procurementcommission.R;

/**
 * Диалоговые окна с комментариями позиций
 */
public class CommentDialog extends BaseDialog {

    private String[] mComments;

    public CommentDialog(Context context, String[] comments) {
        super(context, false);
        mComments = comments;
    }

    @Override
    public int layout() {
        return R.layout.dialog_comments;
    }

    @Override
    public int title() {
        return R.string.dialog_comment_title;
    }

    @Override
    void content(View contentView) {
        TextView tech = (TextView) contentView.findViewById(R.id.text_tech);
        TextView comm = (TextView) contentView.findViewById(R.id.text_comment);
        View viewComment = contentView.findViewById(R.id.view_comment);
        if (mComments[1] == null || mComments[1].isEmpty())
            viewComment.setVisibility(View.GONE);
        else
            comm.setText(mComments[1]);
        if (mComments[0] != null && !mComments[0].isEmpty()) {
            tech.setText(mComments[0]);
        }
    }
}


