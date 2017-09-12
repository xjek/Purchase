package ru.groupstp.procurementcommission.ui.holder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import ru.groupstp.procurementcommission.R;

public class SupplierHolder extends RecyclerView.ViewHolder {

    private static Drawable icUp;
    private static Drawable icDown;

    private boolean mTableIsVisible;

    public View mainView;
    public TextView name;
    public TextView price;
    public View trustSupplier;
    public View showInfo;
    public ImageView arrow;
    public TableLayout tableLayout;
    public LinearLayout listVotes;
    public Button vote;
    public View cycleTop;
    public View cycleBottom;

    private Context mContext;

    public SupplierHolder(View itemView, Context context) {
        super(itemView);
        mContext = context;
        mainView = itemView;
        name = (TextView) itemView.findViewById(R.id.name);
        price = (TextView) itemView.findViewById(R.id.price);
        trustSupplier =  itemView.findViewById(R.id.trust_supplier);
        showInfo = itemView.findViewById(R.id.show_info);
        arrow = (ImageView) itemView.findViewById(R.id.arrow);
        tableLayout = (TableLayout) itemView.findViewById(R.id.tableLayout);
        listVotes = (LinearLayout) itemView.findViewById(R.id.list_votes);
        vote = (Button) itemView.findViewById(R.id.vote);
        icUp = VectorDrawableCompat.create(context.getResources(), R.drawable.ic_up, context.getTheme());
        icDown = VectorDrawableCompat.create(context.getResources(), R.drawable.ic_down, context.getTheme());
        cycleTop = itemView.findViewById(R.id.cycle_top);
        cycleBottom = itemView.findViewById(R.id.cycle_bottom);

        mainView.setOnClickListener(this::showTable);
    }

    public void showTrustSupplier(boolean show) {
        if (show)
            trustSupplier.setVisibility(View.VISIBLE);
        else
            trustSupplier.setVisibility(View.GONE);
    }

    public void createTable(String[][] data) {
        int countRow = data.length / 3;
        tableLayout.removeAllViews();

        for (int i = 0; i < countRow; i++) {
            TableRow tableRow = (TableRow) LayoutInflater.from(mContext).inflate(R.layout.row_table_supplier, null);
            for (int j = 0; j < tableRow.getChildCount(); j++) {
                LinearLayout cellTable = (LinearLayout) tableRow.getChildAt(j);
                int index = (i + 1) * j;
                if (data.length > index) {
                    ((TextView) cellTable.getChildAt(0)).setText(data[index][0]);
                    ((TextView) cellTable.getChildAt(1)).setText(data[index][1]);
                }
            }
            tableLayout.addView(tableRow);
        }
    }

    public void showTable(View view) {
        if (mTableIsVisible) {
            arrow.setImageDrawable(icDown);
            tableLayout.setVisibility(View.GONE);
        } else {
            arrow.setImageDrawable(icUp);
            tableLayout.setVisibility(View.VISIBLE);
        }
        mTableIsVisible = !mTableIsVisible;
    }

    public void showUsersVote(List<String> users) {
        listVotes.removeAllViews();
        for (String user : users) {
            if (!(user.isEmpty() || user.equals("user"))) {
                LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.item_user_vote, null);
                ((TextView) linearLayout.findViewById(R.id.user)).setText(user);
                listVotes.addView(linearLayout);
            }
        }
    }

    public void highlightVotedItem(int votedPosition, int currentPosition) {
        if (votedPosition != -1) {
            vote.setVisibility(View.GONE);
        } else {
            vote.setVisibility(View.VISIBLE);
        }
        if (votedPosition == currentPosition) {
            highlightOn();
        } else {
            highlightOff();
        }
    }

    public void highlightOn() {
        mainView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorAccent));
    }

    public void highlightOff() {
        mainView.setBackground(ContextCompat.getDrawable(mContext, android.R.color.transparent));
    }

    public void resetVoted() {
        vote.setVisibility(View.VISIBLE);
        highlightOff();
    }

    public void showCycleTop() {
        cycleTop.setVisibility(View.VISIBLE);
    }

    public void hideCycleTop() {
        cycleTop.setVisibility(View.GONE);
    }

}
