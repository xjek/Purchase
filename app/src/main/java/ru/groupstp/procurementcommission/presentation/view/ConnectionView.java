package ru.groupstp.procurementcommission.presentation.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface ConnectionView extends MvpView {

    void showLoading(String text);

    void showLoading(int text);
    void showLoading();

    void hideLoading();

    void showError(String text);

    void showError(int text);

    void errorAuth();

    void noNetwork();
}
