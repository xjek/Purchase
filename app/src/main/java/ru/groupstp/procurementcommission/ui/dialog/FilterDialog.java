package ru.groupstp.procurementcommission.ui.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.groupstp.procurementcommission.R;

import static ru.groupstp.procurementcommission.model.Nomenclature.ORG;
import static ru.groupstp.procurementcommission.model.Nomenclature.SUB;
import static ru.groupstp.procurementcommission.model.Nomenclature.USER;

/**
 * Диалоговое окно с фильтром
 */
public class FilterDialog extends BaseDialog {

    private List<String> mItemList;
    private int mTitle;
    private Map<String, List<String>> mSelectedItem = new HashMap<>(); // массив в ранее выбранныи элементами
    private String mKey;

    private OnDialogFilterListener mOnDialogFilterListener;

    public FilterDialog(Context context, OnDialogFilterListener listener) {
        super(context, false);
        mOnDialogFilterListener = listener;
    }

    public void initDialog(Map<String, List<String>> dataForFilter, String key) {
        mItemList = dataForFilter.get(key);
        List<String> selectedItems = mSelectedItem.get(key);
        if (selectedItems == null)
            selectedItems = new ArrayList<>();
        mSelectedItem.put(key, selectedItems);
        mTitle = getTitleDialog(key);
        mKey = key;
    }

    @Override
    void content(View contentView) {
        contentView.findViewById(R.id.filter_reset).setOnClickListener(this::onClickReset);
        LinearLayout contentFilter = (LinearLayout) contentView.findViewById(R.id.filter_content);
        for (String field : mItemList)
            contentFilter.addView(getCheckBoxField(field));

    }

    @Override
    public int title() {
        return mTitle;
    }

    private int getTitleDialog(String key) {
        switch (key) {
            case SUB:
                return R.string.filter_by_subdivision;
            case ORG:
                return R.string.filter_by_organization;
            case USER:
                return R.string.filter_by_initiator;
        }
        return 0;
    }

    @Override
    public int layout() {
        return R.layout.dialog_filter;
    }

    /**
     * Чекбокс для диалогового окна
     */
    private CheckBox getCheckBoxField(String textField) {
        CheckBox checkBox  = (CheckBox) LayoutInflater.from(getContext()).inflate(R.layout.item_filter, null);
        checkBox.setChecked(mSelectedItem.get(mKey).indexOf(textField) != -1);
        checkBox.setText(textField);
        checkBox.setOnCheckedChangeListener((compoundButton, b) ->  checkValueField(textField, b));
        return checkBox;
    }

    @Override
    void onClickCloseDialog() {
        mOnDialogFilterListener.onApply(mSelectedItem.get(mKey), mKey);
        super.onClickCloseDialog();
    }

    private void onClickReset(View view) {
        mSelectedItem.put(mKey, new ArrayList<>());
        mOnDialogFilterListener.onReset(mKey);
        super.onClickCloseDialog();
    }

    public void resetAll() {
        for (String key : mSelectedItem.keySet())
            mSelectedItem.put(key, new ArrayList<>());
    }

    public Map<String, List<String>> getSelectedItem() {
        return mSelectedItem;
    }

    /**
     * Во временной массив добавить/удалить значения фильтров
     */
    private void checkValueField(String value, boolean b) {
        if (b)
            mSelectedItem.get(mKey).add(value);
        else
            mSelectedItem.get(mKey).remove(value);
    }

    public interface OnDialogFilterListener {
        void onApply(List<String> listItems, String key);
        void onReset(String key);
    }
}
