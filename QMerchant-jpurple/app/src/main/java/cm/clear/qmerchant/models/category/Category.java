package cm.clear.qmerchant.models.category;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Category {

    /*public static final int ARRIVED = 41;
    public static final int ABSENT = 42;
    public static final int CANCELED = 43;
    public static final int SATISFIED = 44;
    public static final int DISSATISFIED = 45;
    public static final int NULL = -1;*/

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("fk_parent")
    @Expose
    private Integer fkParent;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("socid")
    @Expose
    private Integer socid;
    @SerializedName("ref_ext")
    @Expose
    private String refExt;
    @SerializedName("visible")
    @Expose
    private Integer visible;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("entity")
    @Expose
    private Integer entity;
    @SerializedName("array_options")
    @Expose
    private List<Object> arrayOptions;

    /**
     * No args constructor for use in serialization
     *
     */
    public Category() {
    }

    /**
     *
     * @param arrayOptions
     * @param visible
     * @param color
     * @param description
     * @param refExt
     * @param socid
     * @param id
     * @param label
     * @param type
     * @param fkParent
     * @param entity
     */
    public Category(String id, Integer fkParent, String label,
                    String description, String color, Integer socid,
                    String refExt, Integer visible, Integer type,
                    Integer entity, List<Object> arrayOptions) {
        super();
        this.id = id;
        this.fkParent = fkParent;
        this.label = label;
        this.description = description;
        this.color = color;
        this.socid = socid;
        this.refExt = refExt;
        this.visible = visible;
        this.type = type;
        this.entity = entity;
        this.arrayOptions = arrayOptions;
    }

    public Category(@NonNull Category other) {
        this.id = other.id;
        this.fkParent = other.fkParent;
        this.label = other.label;
        this.description = other.description;
        this.color = other.color;
        this.socid = other.socid;
        this.refExt = other.refExt;
        this.visible = other.visible;
        this.type = other.type;
        this.entity = other.entity;
        this.arrayOptions = other.arrayOptions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getFkParent() {
        return fkParent;
    }

    public void setFkParent(Integer fkParent) {
        this.fkParent = fkParent;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getSocid() {
        return socid;
    }

    public void setSocid(Integer socid) {
        this.socid = socid;
    }

    public String getRefExt() {
        return refExt;
    }

    public void setRefExt(String refExt) {
        this.refExt = refExt;
    }

    public Integer getVisible() {
        return visible;
    }

    public void setVisible(Integer visible) {
        this.visible = visible;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getEntity() {
        return entity;
    }

    public void setEntity(Integer entity) {
        this.entity = entity;
    }

    public List<Object> getArrayOptions() {
        return arrayOptions;
    }

    public void setArrayOptions(List<Object> arrayOptions) {
        this.arrayOptions = arrayOptions;
    }

    @NonNull
    public static Category getNullCategory(){
        Category category = new Category();
        category.id = "-1";
        category.fkParent = -1;
        category.label = "No Label";
        category.description = "No Description";
        category.color = "grey";
        category.socid = -1;
        category.refExt = "No refExt";
        category.visible = -1;
        category.type = -1;
        category.entity = -1;
        category.arrayOptions = new ArrayList<>();
        return category;
    }

}
