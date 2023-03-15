package cm.clear.qmerchant.models;

import androidx.annotation.NonNull;

import cm.clear.qmerchant.interfaces.QDropDownItem;

public class DropDownItem implements QDropDownItem {
    private Integer id;
    private String desc;

    public DropDownItem(@NonNull Integer id, @NonNull String desc) {
        this.id = id;
        this.desc = desc;
    }

    public DropDownItem() {
    }



    @NonNull
    public String getId() {
        return String.valueOf(id);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return desc;
    }
}
