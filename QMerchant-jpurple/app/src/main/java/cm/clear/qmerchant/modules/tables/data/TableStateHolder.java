package cm.clear.qmerchant.modules.tables.data;

import androidx.annotation.NonNull;

import cm.clear.qmerchant.common.listviewmanagement.IItemState;
import cm.clear.qmerchant.common.listviewmanagement.IItemStateHolder;

public class TableStateHolder implements IItemStateHolder {
    private TableState tableState;
    private int position = -1;
    private String TAG = TableStateHolder.class.getName();

    public TableStateHolder(@NonNull TableState tableState) {
        this.tableState = tableState;
    }

    public TableStateHolder(@NonNull TableState tableState, int position) {
        this.tableState = tableState;
        this.position = position;
    }

    @NonNull
    @Override
    public IItemState getState() {
        return tableState;
    }

    @Override
    public boolean isValueUpdated(@NonNull IItemState itemState) {
        return haveTablesChanged(itemState);
    }

    private boolean haveTablesChanged(IItemState newState) {
        //Log.d("JPurple-TablesChanged",TAG+ "::haveTablesChanged() called with: newState = [" + newState + "]");
        TableState oldState = new TableState(tableState);
        tableState = (TableState) newState;
        boolean result = false;
//        Log.d("J-Purple", "haveTablesChanged() called with: newSize = [" + tableState.getAssignedEvents().size() + "]");
//        Log.d("J-Purple", "haveTablesChanged() called with: oldSize = [" + oldState.getAssignedEvents().size() + "]");
        //Log.d("JPurple-TablesChanged",TAG+ "::haveTablesChanged() called with: "+oldState.getAssignedEvents().size()+"!="+tableState.getAssignedEvents().size()+" = ");
        if (oldState.getAssignedEvents().size() != tableState.getAssignedEvents().size()) {
            //Log.d("JPurple-TablesChanged", "[true]");
            result = true;
        } //else Log.d("JPurple-TablesChanged","[false]");
        //Log.d("JPurple-TablesChanged",TAG+ "::haveTablesChanged() called with: "+oldState.getAssignedEvents().size()+" == 0 || "+tableState.getAssignedEvents().size()+" == 0 = ");
        if (oldState.getAssignedEvents().size() == 0 || tableState.getAssignedEvents().size() == 0) {
            //Log.d("JPurple-TablesChanged", "[true]");
            result = true;
        } //else Log.d("JPurple-TablesChanged","[false]");

        for (int i = 0; i < oldState.getAssignedEvents().size(); i++) {
            //Log.d("JPurple-TablesChanged",TAG+ "::haveTablesChanged() called with: !"+oldState.getAssignedEvents().get(i).getDatem()+".equals("+tableState.getAssignedEvents().get(i).getDatem()+") = ");
            if (!oldState.getAssignedEvents().get(i).getDatem().equals(tableState.getAssignedEvents().get(i).getDatem())) {
                //Log.d("JPurple-TablesChanged", "[true]");
                result = true;
            } //else Log.d("JPurple-TablesChanged","[false]");

            //Log.d("JPurple-TablesChanged",TAG+ "::haveTablesChanged() called with: !"+oldState.getAssignedEvents().get(i).getId()+".equalsIgnoreCase("+tableState.getAssignedEvents().get(i).getId()+") = ");
            if (!oldState.getAssignedEvents().get(i).getId().equalsIgnoreCase(tableState.getAssignedEvents().get(i).getId())) {
                //Log.d("JPurple-TablesChanged", "[true]");
                result = true;
            } //else Log.d("JPurple-TablesChanged","[false]");
        }
        return true;
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void onReloadState() {
        tableState = null;
    }
}
