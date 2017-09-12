package ru.groupstp.procurementcommission.presentation.presenter.supplier;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;

import ru.groupstp.procurementcommission.app.QueryPreferences;
import ru.groupstp.procurementcommission.connection.SupplierConnection;
import ru.groupstp.procurementcommission.connection.listener.OnSupplierConnectionListener;
import ru.groupstp.procurementcommission.model.db.UserDB;
import ru.groupstp.procurementcommission.model.db.SupplierDB;
import ru.groupstp.procurementcommission.model.User;
import ru.groupstp.procurementcommission.presentation.presenter.ConnectionPresenter;
import ru.groupstp.procurementcommission.presentation.view.supplier.NewSupplierView;

@InjectViewState
public class SupplierPresenter extends ConnectionPresenter<NewSupplierView> implements OnSupplierConnectionListener {

    public void load(String qid) {
        getViewState().showAppBarWithButton(SupplierDB.userIsVoted(qid, UserDB.getAuth().getId()));
        getViewState().updateUI(SupplierDB.getSuppliers(qid));
    }

    public User getAuth() {
        return UserDB.getAuth();
    }

    /**
     * Голосовать
     * @param context
     * @param nomenclatureId
     * @param supplierId
     * @param vote
     */
    public void vote(Context context, String nomenclatureId, String supplierId, String vote) {
        connection(context, new SupplierConnection(this), true, nomenclatureId, supplierId, vote, QueryPreferences.getToken(context));
    }

    @Override
    public void successVote(String nomenclatureId, String supplierId) {
        SupplierDB.setVote(nomenclatureId, supplierId, UserDB.getAuth());
        getViewState().returnPosition();
        getViewState().showAppBarWithButton(true);
        getViewState().updateUI(SupplierDB.getSuppliers(nomenclatureId));
    }
}
