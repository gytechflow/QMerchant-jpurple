package cm.clear.qmerchant.models.booking;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import cm.clear.qmerchant.common.listprovider.ListObject;
import cm.clear.qmerchant.models.UserAssigned;

public class Booking implements Serializable, ListObject {

    public static final String DEFAULT_PERCENTAGE = "0";
    public static final String PERCENT_0 = "0";
    public static final String PERCENT_25 = "25";
    public static final String PERCENT_50 = "50";
    public static final String PERCENT_100 = "100";
    public static final String ALL_BOOKINGS = "-1";

    public static final String [] OPTIONS_VALUES = { PERCENT_0, PERCENT_25, PERCENT_50, PERCENT_100 };

    @NonNull
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("ref")
    @Expose
    private String ref;

    @SerializedName("type_id")
    @Expose
    private String type_id;

    @SerializedName("type_code")
    @Expose
    private String type_code;

    @SerializedName("type_label")
    @Expose
    private String type_label;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("type_color")
    @Expose
    private String type_color;

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("label")
    @Expose
    private String label;

    @SerializedName("datec")
    @Expose
    private Long datec;

    @SerializedName("datef")
    @Expose
    private Long datef;

    @SerializedName("duree")
    @Expose
    private Long duree;

    @SerializedName("datem")
    @Expose
    private Long datem;

    @SerializedName("authorid")
    @Expose
    private String authorid;

    @SerializedName("usermodid")
    @Expose
    private String usermodid;

    @SerializedName("datep")
    @Expose
    private Long datep;

    @SerializedName("datep2")
    @Expose
    private Long datep2;

    @SerializedName("durationp")
    @Expose
    private Integer durationp;

    @SerializedName("fulldayevent")
    @Expose
    private String fulldayevent;

    @SerializedName("percentage")
    @Expose
    private String percentage;

    @SerializedName("location")
    @Expose
    private String location;

    @SerializedName("transparency")
    @Expose
    private String transparency;

    @SerializedName("priority")
    @Expose
    private String priority;

    // Dictionary Object here
    @SerializedName("userassigned")
    @Expose
    private UserAssigned userassigned;

    @SerializedName("userownerid")
    @Expose
    private String userownerid;

    @SerializedName("userdoneid")
    @Expose
    private String userdoneid;

    @SerializedName("socpeopleassigned")
    @Expose
    private List<String> socpeopleassigned;

    @SerializedName("otherassigned")
    @Expose
    private List<String> otherassigned;

    @SerializedName("reminders")
    @Expose
    private List<String> reminders;

    @SerializedName("usertodo")
    @Expose
    private String usertodo;

    @SerializedName("userdone")
    @Expose
    private String userdone;

    @SerializedName("socid")
    @Expose
    private String socid;

    @SerializedName("contactid")
    @Expose
    private String contactid;

    @SerializedName("elementid")
    @Expose
    private String elementid;

    @SerializedName("elementtype")
    @Expose
    private String elementtype;

    @SerializedName("icalname")
    @Expose
    private String icalname;

    @SerializedName("icalcolor")
    @Expose
    private String icalcolor;

    @SerializedName("extraparams")
    @Expose
    private String extraparams;

    @SerializedName("email_msgid")
    @Expose
    private String email_msgid;

    @SerializedName("email_from")
    @Expose
    private String email_from;

    @SerializedName("email_sender")
    @Expose
    private String email_sender;

    @SerializedName("email_to")
    @Expose
    private String email_to;

    @SerializedName("email_tocc")
    @Expose
    private String email_tocc;

    @SerializedName("email_tobcc")
    @Expose
    private String email_tobcc;

    @SerializedName("email_subject")
    @Expose
    private String email_subject;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("nbplace")
    @Expose
    private String nbplace;

    @SerializedName("names")
    @Expose
    private String names;

    @SerializedName("errors_to")
    @Expose
    private String errors_to;

    @SerializedName("entity")
    @Expose
    private String entity;

    @SerializedName("import_key")
    @Expose
    private String import_key;

    @SerializedName("array_options")
    @Expose
    private List<String> array_options;

    @SerializedName("array_languages")
    @Expose
    private String array_languages;

    @SerializedName("linkedObjectsIds")
    @Expose
    private List<String> linkedObjectsIds;

    @SerializedName("fk_project")
    @Expose
    private String fk_project;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("region_id")
    @Expose
    private Integer region_id;

    @SerializedName("demand_reason_id")
    @Expose
    private Integer demand_reason_id;

    @SerializedName("transport_mode_id")
    @Expose
    private Integer transport_mode_id;

    @SerializedName("model_pdf")
    @Expose
    private String model_pdf;

    @SerializedName("modelpdf")
    @Expose
    private String modelpdf;

    @SerializedName("last_main_doc")
    @Expose
    private String last_main_doc;

    @SerializedName("fk_bank")
    @Expose
    private String fk_bank;

    @SerializedName("openid")
    @Expose
    private String openid;

    @SerializedName("note_public")
    @Expose
    private String note_public;

    @SerializedName("note_private")
    @Expose
    private String note_private;

    @SerializedName("date_creation")
    @Expose
    private Long date_creation;

    @SerializedName("date_validation")
    @Expose
    private Long date_validation;

    @SerializedName("date_modification")
    @Expose
    private Long date_modification;

    @SerializedName("specimen")
    @Expose
    private Integer specimen;

    @SerializedName("alreadypaid")
    @Expose
    private Boolean alreadypaid;

    @SerializedName("type_picto")
    @Expose
    private String type_picto;

    @SerializedName("type_short")
    @Expose
    private String type_short;



    public Booking() {
    }

    public Booking(@NonNull Booking other) {
        this.id = other.id;
        this.ref = other.ref;
        this.type_id = other.type_id;
        this.type_code = other.type_code;
        this.type_label = other.type_label;
        this.type = other.type;
        this.type_color = other.type_color;
        this.code = other.code;
        this.label = other.label;
        this.datec = other.datec;
        this.datef = other.datef;
        this.duree = other.duree;
        this.datem = other.datem;
        this.authorid = other.authorid;
        this.usermodid = other.usermodid;
        this.datep = other.datep;
        this.datep2 = other.datep2;
        this.durationp = other.durationp;
        this.fulldayevent = other.fulldayevent;
        this.percentage = other.percentage;
        this.location = other.location;
        this.transparency = other.transparency;
        this.priority = other.priority;
        this.userassigned = other.userassigned;
        this.userownerid = other.userownerid;
        this.userdoneid = other.userdoneid;
        this.socpeopleassigned = other.socpeopleassigned;
        this.otherassigned = other.otherassigned;
        this.reminders = other.reminders;
        this.usertodo = other.usertodo;
        this.userdone = other.userdone;
        this.socid = other.socid;
        this.contactid = other.contactid;
        this.elementid = other.elementid;
        this.elementtype = other.elementtype;
        this.icalname = other.icalname;
        this.icalcolor = other.icalcolor;
        this.extraparams = other.extraparams;
        this.email_msgid = other.email_msgid;
        this.email_from = other.email_from;
        this.email_sender = other.email_sender;
        this.email_to = other.email_to;
        this.email_tocc = other.email_tocc;
        this.email_tobcc = other.email_tobcc;
        this.email_subject = other.email_subject;
        this.email = other.email;
        this.phone = other.phone;
        this.nbplace = other.nbplace;
        this.names = other.names;
        this.errors_to = other.errors_to;
        this.entity = other.entity;
        this.import_key = other.import_key;
        this.array_options = other.array_options;
        this.array_languages = other.array_languages;
        this.linkedObjectsIds = other.linkedObjectsIds;
        this.fk_project = other.fk_project;
        this.status = other.status;
        this.region_id = other.region_id;
        this.demand_reason_id = other.demand_reason_id;
        this.transport_mode_id = other.transport_mode_id;
        this.model_pdf = other.model_pdf;
        this.modelpdf = other.modelpdf;
        this.last_main_doc = other.last_main_doc;
        this.fk_bank = other.fk_bank;
        this.openid = other.openid;
        this.note_public = other.note_public;
        this.note_private = other.note_private;
        this.date_creation = other.date_creation;
        this.date_validation = other.date_validation;
        this.date_modification = other.date_modification;
        this.specimen = other.specimen;
        this.alreadypaid = other.alreadypaid;
        this.type_picto = other.type_picto;
        this.type_short = other.type_short;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getType_code() {
        return type_code;
    }

    public void setType_code(String type_code) {
        this.type_code = type_code;
    }

    public String getType_label() {
        return type_label;
    }

    public void setType_label(String type_label) {
        this.type_label = type_label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType_color() {
        return type_color;
    }

    public void setType_color(String type_color) {
        this.type_color = type_color;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Long getDatec() {
        return datec;
    }

    public void setDatec(Long datec) {
        this.datec = datec;
    }

    public Long getDatef() {
        return datef;
    }

    public void setDatef(Long datef) {
        this.datef = datef;
    }

    public Long getDuree() {
        return duree;
    }

    public void setDuree(Long duree) {
        this.duree = duree;
    }

    public Long getDatem() {
        return datem;
    }

    public void setDatem(Long datem) {
        this.datem = datem;
    }

    public String getAuthorid() {
        return authorid;
    }

    public void setAuthorid(String authorid) {
        this.authorid = authorid;
    }

    public String getUsermodid() {
        return usermodid;
    }

    public void setUsermodid(String usermodid) {
        this.usermodid = usermodid;
    }

    public Long getDatep() {
        return datep;
    }

    public void setDatep(Long datep) {
        this.datep = datep;
    }

    public Long getDatep2() {
        return datep2;
    }

    public void setDatep2(Long datep2) {
        this.datep2 = datep2;
    }

    public Integer getDurationp() {
        return durationp;
    }

    public void setDurationp(Integer durationp) {
        this.durationp = durationp;
    }

    public String getFulldayevent() {
        return fulldayevent;
    }

    public void setFulldayevent(String fulldayevent) {
        this.fulldayevent = fulldayevent;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTransparency() {
        return transparency;
    }

    public void setTransparency(String transparency) {
        this.transparency = transparency;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public UserAssigned getUserassigned() {
        /*Gson gson = new Gson();
        Type empMapType = new TypeToken<UserAssigned>() {}.getType();
        UserAssigned nameEmployeeMap = gson.fromJson(userassigned, empMapType);*/
        return userassigned;
    }

    public void setUserassigned(UserAssigned userassigned) {
        /*Gson gson = new Gson();
        Type empMapType = new TypeToken<UserAssigned>() {}.getType();

        this.userassigned = gson.toJson(userassigned, empMapType);*/
        this.userassigned = userassigned;
    }

    public String getUserownerid() {
        return userownerid;
    }

    public void setUserownerid(String userownerid) {
        this.userownerid = userownerid;
    }

    public String getUserdoneid() {
        return userdoneid;
    }

    public void setUserdoneid(String userdoneid) {
        this.userdoneid = userdoneid;
    }

    public List<String> getSocpeopleassigned() {
        return socpeopleassigned;
    }

    public void setSocpeopleassigned(List<String> socpeopleassigned) {
        this.socpeopleassigned = socpeopleassigned;
    }

    public List<String> getOtherassigned() {
        return otherassigned;
    }

    public void setOtherassigned(List<String> otherassigned) {
        this.otherassigned = otherassigned;
    }

    public List<String> getReminders() {
        return reminders;
    }

    public void setReminders(List<String> reminders) {
        this.reminders = reminders;
    }

    public String getUsertodo() {
        return usertodo;
    }

    public void setUsertodo(String usertodo) {
        this.usertodo = usertodo;
    }

    public String getUserdone() {
        return userdone;
    }

    public void setUserdone(String userdone) {
        this.userdone = userdone;
    }

    public String getSocid() {
        return socid;
    }

    public void setSocid(String socid) {
        this.socid = socid;
    }

    public String getContactid() {
        return contactid;
    }

    public void setContactid(String contactid) {
        this.contactid = contactid;
    }

    public String getElementid() {
        return elementid;
    }

    public void setElementid(String elementid) {
        this.elementid = elementid;
    }

    public String getElementtype() {
        return elementtype;
    }

    public void setElementtype(String elementtype) {
        this.elementtype = elementtype;
    }

    public String getIcalname() {
        return icalname;
    }

    public void setIcalname(String icalname) {
        this.icalname = icalname;
    }

    public String getIcalcolor() {
        return icalcolor;
    }

    public void setIcalcolor(String icalcolor) {
        this.icalcolor = icalcolor;
    }

    public String getExtraparams() {
        return extraparams;
    }

    public void setExtraparams(String extraparams) {
        this.extraparams = extraparams;
    }

    public String getEmail_msgid() {
        return email_msgid;
    }

    public void setEmail_msgid(String email_msgid) {
        this.email_msgid = email_msgid;
    }

    public String getEmail_from() {
        return email_from;
    }

    public void setEmail_from(String email_from) {
        this.email_from = email_from;
    }

    public String getEmail_sender() {
        return email_sender;
    }

    public void setEmail_sender(String email_sender) {
        this.email_sender = email_sender;
    }

    public String getEmail_to() {
        return email_to;
    }

    public void setEmail_to(String email_to) {
        this.email_to = email_to;
    }

    public String getEmail_tocc() {
        return email_tocc;
    }

    public void setEmail_tocc(String email_tocc) {
        this.email_tocc = email_tocc;
    }

    public String getEmail_tobcc() {
        return email_tobcc;
    }

    public void setEmail_tobcc(String email_tobcc) {
        this.email_tobcc = email_tobcc;
    }

    public String getEmail_subject() {
        return email_subject;
    }

    public void setEmail_subject(String email_subject) {
        this.email_subject = email_subject;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNbplace() {
        return TextUtils.isEmpty(nbplace) ?"0":nbplace;
    }

    public void setNbplace(String nbplace) {
        this.nbplace = nbplace;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getErrors_to() {
        return errors_to;
    }

    public void setErrors_to(String errors_to) {
        this.errors_to = errors_to;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getImport_key() {
        return import_key;
    }

    public void setImport_key(String import_key) {
        this.import_key = import_key;
    }

    @NonNull
    public List<String> getArray_options() {
        return array_options;
    }

    public void setArray_options(@NonNull List<String> array_options) {
        this.array_options = array_options;
    }

    public String getArray_languages() {
        return array_languages;
    }

    public void setArray_languages(String array_languages) {
        this.array_languages = array_languages;
    }

    public List<String> getLinkedObjectsIds() {
        return linkedObjectsIds;
    }

    public void setLinkedObjectsIds(List<String> linkedObjectsIds) {
        this.linkedObjectsIds = linkedObjectsIds;
    }

    public String getFk_project() {
        return fk_project;
    }

    public void setFk_project(String fk_project) {
        this.fk_project = fk_project;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getRegion_id() {
        return region_id;
    }

    public void setRegion_id(Integer region_id) {
        this.region_id = region_id;
    }

    public Integer getDemand_reason_id() {
        return demand_reason_id;
    }

    public void setDemand_reason_id(Integer demand_reason_id) {
        this.demand_reason_id = demand_reason_id;
    }

    public Integer getTransport_mode_id() {
        return transport_mode_id;
    }

    public void setTransport_mode_id(Integer transport_mode_id) {
        this.transport_mode_id = transport_mode_id;
    }

    public String getModel_pdf() {
        return model_pdf;
    }

    public void setModel_pdf(String model_pdf) {
        this.model_pdf = model_pdf;
    }

    public String getModelpdf() {
        return modelpdf;
    }

    public void setModelpdf(String modelpdf) {
        this.modelpdf = modelpdf;
    }

    public String getLast_main_doc() {
        return last_main_doc;
    }

    public void setLast_main_doc(String last_main_doc) {
        this.last_main_doc = last_main_doc;
    }

    public String getFk_bank() {
        return fk_bank;
    }

    public void setFk_bank(String fk_bank) {
        this.fk_bank = fk_bank;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNote_public() {
        return note_public;
    }

    public void setNote_public(String note_public) {
        this.note_public = note_public;
    }

    public String getNote_private() {
        return note_private;
    }

    public void setNote_private(String note_private) {
        this.note_private = note_private;
    }

    public Long getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(Long date_creation) {
        this.date_creation = date_creation;
    }

    public Long getDate_validation() {
        return date_validation;
    }

    public void setDate_validation(Long date_validation) {
        this.date_validation = date_validation;
    }

    public Long getDate_modification() {
        return date_modification;
    }

    public void setDate_modification(Long date_modification) {
        this.date_modification = date_modification;
    }

    public Integer getSpecimen() {
        return specimen;
    }

    public void setSpecimen(Integer specimen) {
        this.specimen = specimen;
    }

    public Boolean getAlreadypaid() {
        return alreadypaid;
    }

    public void setAlreadypaid(Boolean alreadypaid) {
        this.alreadypaid = alreadypaid;
    }

    public String getType_picto() {
        return type_picto;
    }

    public void setType_picto(String type_picto) {
        this.type_picto = type_picto;
    }

    public String getType_short() {
        return type_short;
    }

    public void setType_short(String type_short) {
        this.type_short = type_short;
    }
}
