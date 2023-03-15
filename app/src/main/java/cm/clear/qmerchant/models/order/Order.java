package cm.clear.qmerchant.models.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cm.clear.qmerchant.common.listprovider.ListObject;
import cm.clear.qmerchant.models.Line;

public class Order implements Serializable, ListObject {
    @SerializedName("socid")
    @Expose
    private String socid;
    @SerializedName("ref_client")
    @Expose
    private String refClient;
    @SerializedName("contactid")
    @Expose
    private String contactid;
    @SerializedName("statut")
    @Expose
    private String statut;
    @SerializedName("billed")
    @Expose
    private String billed;
    @SerializedName("brouillon")
    @Expose
    private String brouillon;
    @SerializedName("cond_reglement_code")
    @Expose
    private String condReglementCode;
    @SerializedName("fk_account")
    @Expose
    private String fkAccount;
    @SerializedName("mode_reglement")
    @Expose
    private String modeReglement;
    @SerializedName("mode_reglement_id")
    @Expose
    private String modeReglementId;
    @SerializedName("mode_reglement_code")
    @Expose
    private String modeReglementCode;
    @SerializedName("availability_id")
    @Expose
    private String availabilityId;
    @SerializedName("availability_code")
    @Expose
    private String availabilityCode;
    @SerializedName("availability")
    @Expose
    private String availability;
    @SerializedName("demand_reason_id")
    @Expose
    private String demandReasonId;
    @SerializedName("demand_reason_code")
    @Expose
    private String demandReasonCode;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("ref_ext")
    @Expose
    private String refExt;
    @SerializedName("date_commande")
    @Expose
    private String dateCommande;
    @SerializedName("date_livraison")
    @Expose
    private String dateLivraison;
    @SerializedName("delivery_date")
    @Expose
    private String deliveryDate;
    @SerializedName("fk_remise_except")
    @Expose
    private String fkRemiseExcept;
    @SerializedName("remise_percent")
    @Expose
    private String remisePercent;
    @SerializedName("remise_absolue")
    @Expose
    private String remiseAbsolue;
    @SerializedName("info_bits")
    @Expose
    private String infoBits;
    @SerializedName("rang")
    @Expose
    private String rang;
    @SerializedName("special_code")
    @Expose
    private String specialCode;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("extraparams")
    @Expose
    private List<String> extraparams = new ArrayList<String>();
    @SerializedName("linked_Strings")
    @Expose
    private List<String> linkedStrings = new ArrayList<String>();
    @SerializedName("user_author_id")
    @Expose
    private String userAuthorId;
    @SerializedName("user_valid")
    @Expose
    private String userValid;
    @SerializedName("lines")
    @Expose
    private List<Line> lines = new ArrayList<Line>();
    @SerializedName("fk_multicurrency")
    @Expose
    private String fkMulticurrency;
    @SerializedName("multicurrency_code")
    @Expose
    private String multicurrencyCode;
    @SerializedName("multicurrency_tx")
    @Expose
    private String multicurrencyTx;
    @SerializedName("multicurrency_total_ht")
    @Expose
    private String multicurrencyTotalHt;
    @SerializedName("multicurrency_total_tva")
    @Expose
    private String multicurrencyTotalTva;
    @SerializedName("multicurrency_total_ttc")
    @Expose
    private String multicurrencyTotalTtc;
    @SerializedName("module_source")
    @Expose
    private String moduleSource;
    @SerializedName("pos_source")
    @Expose
    private String posSource;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("entity")
    @Expose
    private String entity;
    @SerializedName("import_key")
    @Expose
    private String importKey;
    @SerializedName("array_options")
    @Expose
    private List<String> arrayOptions = new ArrayList<String>();
    @SerializedName("array_languages")
    @Expose
    private String arrayLanguages;
    @SerializedName("linkedStringsIds")
    @Expose
    private String linkedStringsIds;
    @SerializedName("canvas")
    @Expose
    private String canvas;
    @SerializedName("fk_project")
    @Expose
    private String fkProject;
    @SerializedName("contact")
    @Expose
    private String contact;
    @SerializedName("contact_id")
    @Expose
    private String contactId;
    @SerializedName("thirdparty")
    @Expose
    private String thirdparty;
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("origin")
    @Expose
    private String origin;
    @SerializedName("origin_id")
    @Expose
    private String originId;
    @SerializedName("ref")
    @Expose
    private String ref;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("country_id")
    @Expose
    private String countryId;
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("state_id")
    @Expose
    private String stateId;
    @SerializedName("state_code")
    @Expose
    private String stateCode;
    @SerializedName("region_id")
    @Expose
    private String regionId;
    @SerializedName("cond_reglement_id")
    @Expose
    private String condReglementId;
    @SerializedName("transport_mode_id")
    @Expose
    private String transportModeId;
    @SerializedName("cond_reglement")
    @Expose
    private String condReglement;
    @SerializedName("shipping_method_id")
    @Expose
    private String shippingMethodId;
    @SerializedName("model_pdf")
    @Expose
    private String modelPdf;
    @SerializedName("modelpdf")
    @Expose
    private String modelpdf;
    @SerializedName("last_main_doc")
    @Expose
    private String lastMainDoc;
    @SerializedName("fk_bank")
    @Expose
    private String fkBank;
    @SerializedName("openid")
    @Expose
    private String openid;
    @SerializedName("note_public")
    @Expose
    private String notePublic;
    @SerializedName("note_private")
    @Expose
    private String notePrivate;
    @SerializedName("total_ht")
    @Expose
    private String totalHt;
    @SerializedName("total_tva")
    @Expose
    private String totalTva;
    @SerializedName("total_localtax1")
    @Expose
    private String totalLocaltax1;
    @SerializedName("total_localtax2")
    @Expose
    private String totalLocaltax2;
    @SerializedName("total_ttc")
    @Expose
    private String totalTtc;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("civility_id")
    @Expose
    private String civilityId;
    @SerializedName("date_creation")
    @Expose
    private String dateCreation;
    @SerializedName("date_validation")
    @Expose
    private String dateValidation;
    @SerializedName("date_modification")
    @Expose
    private String dateModification;
    @SerializedName("specimen")
    @Expose
    private String specimen;
    @SerializedName("alreadypaid")
    @Expose
    private String alreadypaid;
    @SerializedName("fk_incoterms")
    @Expose
    private String fkIncoterms;
    @SerializedName("label_incoterms")
    @Expose
    private String labelIncoterms;
    @SerializedName("location_incoterms")
    @Expose
    private String locationIncoterms;
    @SerializedName("remise")
    @Expose
    private String remise;
    @SerializedName("ref_customer")
    @Expose
    private String refCustomer;
    @SerializedName("cond_reglement_doc")
    @Expose
    private String condReglementDoc;
    @SerializedName("warehouse_id")
    @Expose
    private String warehouseId;
    @SerializedName("contacts_ids")
    @Expose
    private List<String> contactsIds = new ArrayList<String>();
    private final static long serialVersionUID = 8401041989635506470L;

    public String getSocid() {
        return socid;
    }

    public void setSocid(String socid) {
        this.socid = socid;
    }

    public String getRefClient() {
        return refClient;
    }

    public void setRefClient(String refClient) {
        this.refClient = refClient;
    }

    public String getContactid() {
        return contactid;
    }

    public void setContactid(String contactid) {
        this.contactid = contactid;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getBilled() {
        return billed;
    }

    public void setBilled(String billed) {
        this.billed = billed;
    }

    public String getBrouillon() {
        return brouillon;
    }

    public void setBrouillon(String brouillon) {
        this.brouillon = brouillon;
    }

    public String getCondReglementCode() {
        return condReglementCode;
    }

    public void setCondReglementCode(String condReglementCode) {
        this.condReglementCode = condReglementCode;
    }

    public String getFkAccount() {
        return fkAccount;
    }

    public void setFkAccount(String fkAccount) {
        this.fkAccount = fkAccount;
    }

    public String getModeReglement() {
        return modeReglement;
    }

    public void setModeReglement(String modeReglement) {
        this.modeReglement = modeReglement;
    }

    public String getModeReglementId() {
        return modeReglementId;
    }

    public void setModeReglementId(String modeReglementId) {
        this.modeReglementId = modeReglementId;
    }

    public String getModeReglementCode() {
        return modeReglementCode;
    }

    public void setModeReglementCode(String modeReglementCode) {
        this.modeReglementCode = modeReglementCode;
    }

    public String getAvailabilityId() {
        return availabilityId;
    }

    public void setAvailabilityId(String availabilityId) {
        this.availabilityId = availabilityId;
    }

    public String getAvailabilityCode() {
        return availabilityCode;
    }

    public void setAvailabilityCode(String availabilityCode) {
        this.availabilityCode = availabilityCode;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getDemandReasonId() {
        return demandReasonId;
    }

    public void setDemandReasonId(String demandReasonId) {
        this.demandReasonId = demandReasonId;
    }

    public String getDemandReasonCode() {
        return demandReasonCode;
    }

    public void setDemandReasonCode(String demandReasonCode) {
        this.demandReasonCode = demandReasonCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRefExt() {
        return refExt;
    }

    public void setRefExt(String refExt) {
        this.refExt = refExt;
    }

    public String getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(String dateCommande) {
        this.dateCommande = dateCommande;
    }

    public String getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(String dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getFkRemiseExcept() {
        return fkRemiseExcept;
    }

    public void setFkRemiseExcept(String fkRemiseExcept) {
        this.fkRemiseExcept = fkRemiseExcept;
    }

    public String getRemisePercent() {
        return remisePercent;
    }

    public void setRemisePercent(String remisePercent) {
        this.remisePercent = remisePercent;
    }

    public String getRemiseAbsolue() {
        return remiseAbsolue;
    }

    public void setRemiseAbsolue(String remiseAbsolue) {
        this.remiseAbsolue = remiseAbsolue;
    }

    public String getInfoBits() {
        return infoBits;
    }

    public void setInfoBits(String infoBits) {
        this.infoBits = infoBits;
    }

    public String getRang() {
        return rang;
    }

    public void setRang(String rang) {
        this.rang = rang;
    }

    public String getSpecialCode() {
        return specialCode;
    }

    public void setSpecialCode(String specialCode) {
        this.specialCode = specialCode;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<String> getExtraparams() {
        return extraparams;
    }

    public void setExtraparams(List<String> extraparams) {
        this.extraparams = extraparams;
    }

    public List<String> getLinkedStrings() {
        return linkedStrings;
    }

    public void setLinkedStrings(List<String> linkedStrings) {
        this.linkedStrings = linkedStrings;
    }

    public String getUserAuthorId() {
        return userAuthorId;
    }

    public void setUserAuthorId(String userAuthorId) {
        this.userAuthorId = userAuthorId;
    }

    public String getUserValid() {
        return userValid;
    }

    public void setUserValid(String userValid) {
        this.userValid = userValid;
    }

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    public String getFkMulticurrency() {
        return fkMulticurrency;
    }

    public void setFkMulticurrency(String fkMulticurrency) {
        this.fkMulticurrency = fkMulticurrency;
    }

    public String getMulticurrencyCode() {
        return multicurrencyCode;
    }

    public void setMulticurrencyCode(String multicurrencyCode) {
        this.multicurrencyCode = multicurrencyCode;
    }

    public String getMulticurrencyTx() {
        return multicurrencyTx;
    }

    public void setMulticurrencyTx(String multicurrencyTx) {
        this.multicurrencyTx = multicurrencyTx;
    }

    public String getMulticurrencyTotalHt() {
        return multicurrencyTotalHt;
    }

    public void setMulticurrencyTotalHt(String multicurrencyTotalHt) {
        this.multicurrencyTotalHt = multicurrencyTotalHt;
    }

    public String getMulticurrencyTotalTva() {
        return multicurrencyTotalTva;
    }

    public void setMulticurrencyTotalTva(String multicurrencyTotalTva) {
        this.multicurrencyTotalTva = multicurrencyTotalTva;
    }

    public String getMulticurrencyTotalTtc() {
        return multicurrencyTotalTtc;
    }

    public void setMulticurrencyTotalTtc(String multicurrencyTotalTtc) {
        this.multicurrencyTotalTtc = multicurrencyTotalTtc;
    }

    public String getModuleSource() {
        return moduleSource;
    }

    public void setModuleSource(String moduleSource) {
        this.moduleSource = moduleSource;
    }

    public String getPosSource() {
        return posSource;
    }

    public void setPosSource(String posSource) {
        this.posSource = posSource;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getImportKey() {
        return importKey;
    }

    public void setImportKey(String importKey) {
        this.importKey = importKey;
    }

    public List<String> getArrayOptions() {
        return arrayOptions;
    }

    public void setArrayOptions(List<String> arrayOptions) {
        this.arrayOptions = arrayOptions;
    }

    public String getArrayLanguages() {
        return arrayLanguages;
    }

    public void setArrayLanguages(String arrayLanguages) {
        this.arrayLanguages = arrayLanguages;
    }

    public String getLinkedStringsIds() {
        return linkedStringsIds;
    }

    public void setLinkedStringsIds(String linkedStringsIds) {
        this.linkedStringsIds = linkedStringsIds;
    }

    public String getCanvas() {
        return canvas;
    }

    public void setCanvas(String canvas) {
        this.canvas = canvas;
    }

    public String getFkProject() {
        return fkProject;
    }

    public void setFkProject(String fkProject) {
        this.fkProject = fkProject;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getThirdparty() {
        return thirdparty;
    }

    public void setThirdparty(String thirdparty) {
        this.thirdparty = thirdparty;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getStatus() {
        return status==""?"0":status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getCondReglementId() {
        return condReglementId;
    }

    public void setCondReglementId(String condReglementId) {
        this.condReglementId = condReglementId;
    }

    public String getTransportModeId() {
        return transportModeId;
    }

    public void setTransportModeId(String transportModeId) {
        this.transportModeId = transportModeId;
    }

    public String getCondReglement() {
        return condReglement;
    }

    public void setCondReglement(String condReglement) {
        this.condReglement = condReglement;
    }

    public String getShippingMethodId() {
        return shippingMethodId;
    }

    public void setShippingMethodId(String shippingMethodId) {
        this.shippingMethodId = shippingMethodId;
    }

    public String getModelPdf() {
        return modelPdf;
    }

    public void setModelPdf(String modelPdf) {
        this.modelPdf = modelPdf;
    }

    public String getModelpdf() {
        return modelpdf;
    }

    public void setModelpdf(String modelpdf) {
        this.modelpdf = modelpdf;
    }

    public String getLastMainDoc() {
        return lastMainDoc;
    }

    public void setLastMainDoc(String lastMainDoc) {
        this.lastMainDoc = lastMainDoc;
    }

    public String getFkBank() {
        return fkBank;
    }

    public void setFkBank(String fkBank) {
        this.fkBank = fkBank;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNotePublic() {
        return notePublic;
    }

    public void setNotePublic(String notePublic) {
        this.notePublic = notePublic;
    }

    public String getNotePrivate() {
        return notePrivate;
    }

    public void setNotePrivate(String notePrivate) {
        this.notePrivate = notePrivate;
    }

    public String getTotalHt() {
        return totalHt;
    }

    public void setTotalHt(String totalHt) {
        this.totalHt = totalHt;
    }

    public String getTotalTva() {
        return totalTva;
    }

    public void setTotalTva(String totalTva) {
        this.totalTva = totalTva;
    }

    public String getTotalLocaltax1() {
        return totalLocaltax1;
    }

    public void setTotalLocaltax1(String totalLocaltax1) {
        this.totalLocaltax1 = totalLocaltax1;
    }

    public String getTotalLocaltax2() {
        return totalLocaltax2;
    }

    public void setTotalLocaltax2(String totalLocaltax2) {
        this.totalLocaltax2 = totalLocaltax2;
    }

    public String getTotalTtc() {
        return totalTtc;
    }

    public void setTotalTtc(String totalTtc) {
        this.totalTtc = totalTtc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getCivilityId() {
        return civilityId;
    }

    public void setCivilityId(String civilityId) {
        this.civilityId = civilityId;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getDateValidation() {
        return dateValidation;
    }

    public void setDateValidation(String dateValidation) {
        this.dateValidation = dateValidation;
    }

    public String getDateModification() {
        return dateModification;
    }

    public void setDateModification(String dateModification) {
        this.dateModification = dateModification;
    }

    public String getSpecimen() {
        return specimen;
    }

    public void setSpecimen(String specimen) {
        this.specimen = specimen;
    }

    public String getAlreadypaid() {
        return alreadypaid;
    }

    public void setAlreadypaid(String alreadypaid) {
        this.alreadypaid = alreadypaid;
    }

    public String getFkIncoterms() {
        return fkIncoterms;
    }

    public void setFkIncoterms(String fkIncoterms) {
        this.fkIncoterms = fkIncoterms;
    }

    public String getLabelIncoterms() {
        return labelIncoterms;
    }

    public void setLabelIncoterms(String labelIncoterms) {
        this.labelIncoterms = labelIncoterms;
    }

    public String getLocationIncoterms() {
        return locationIncoterms;
    }

    public void setLocationIncoterms(String locationIncoterms) {
        this.locationIncoterms = locationIncoterms;
    }

    public String getRemise() {
        return remise;
    }

    public void setRemise(String remise) {
        this.remise = remise;
    }

    public String getRefCustomer() {
        return refCustomer;
    }

    public void setRefCustomer(String refCustomer) {
        this.refCustomer = refCustomer;
    }

    public String getCondReglementDoc() {
        return condReglementDoc;
    }

    public void setCondReglementDoc(String condReglementDoc) {
        this.condReglementDoc = condReglementDoc;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public List<String> getContactsIds() {
        return contactsIds;
    }

    public void setContactsIds(List<String> contactsIds) {
        this.contactsIds = contactsIds;
    }

}