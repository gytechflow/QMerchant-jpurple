package cm.clear.qmerchant.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Line implements Serializable
{

    @SerializedName("fk_commande")
    @Expose
    private String fkCommande;
    @SerializedName("commande_id")
    @Expose
    private String commandeId;
    @SerializedName("fk_parent_line")
    @Expose
    private String fkParentLine;
    @SerializedName("fk_facture")
    @Expose
    private String fkFacture;
    @SerializedName("ref_ext")
    @Expose
    private String refExt;
    @SerializedName("fk_remise_except")
    @Expose
    private String fkRemiseExcept;
    @SerializedName("rang")
    @Expose
    private String rang;
    @SerializedName("fk_fournprice")
    @Expose
    private String fkFournprice;
    @SerializedName("pa_ht")
    @Expose
    private String paHt;
    @SerializedName("marge_tx")
    @Expose
    private String margeTx;
    @SerializedName("marque_tx")
    @Expose
    private String marqueTx;
    @SerializedName("remise")
    @Expose
    private String remise;
    @SerializedName("date_start")
    @Expose
    private String dateStart;
    @SerializedName("date_end")
    @Expose
    private String dateEnd;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("ref")
    @Expose
    private String ref;
    @SerializedName("libelle")
    @Expose
    private String libelle;
    @SerializedName("product_ref")
    @Expose
    private String productRef;
    @SerializedName("product_label")
    @Expose
    private String productLabel;
    @SerializedName("product_desc")
    @Expose
    private String productDesc;
    @SerializedName("product_tobatch")
    @Expose
    private String productTobatch;
    @SerializedName("product_barcode")
    @Expose
    private String productBarcode;
    @SerializedName("qty")
    @Expose
    private String qty;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("subprice")
    @Expose
    private String subprice;
    @SerializedName("product_type")
    @Expose
    private String productType;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("fk_product")
    @Expose
    private String fkProduct;
    @SerializedName("remise_percent")
    @Expose
    private String remisePercent;
    @SerializedName("vat_src_code")
    @Expose
    private String vatSrcCode;
    @SerializedName("tva_tx")
    @Expose
    private String tvaTx;
    @SerializedName("localtax1_tx")
    @Expose
    private String localtax1Tx;
    @SerializedName("localtax2_tx")
    @Expose
    private String localtax2Tx;
    @SerializedName("localtax1_type")
    @Expose
    private String localtax1Type;
    @SerializedName("localtax2_type")
    @Expose
    private String localtax2Type;
    @SerializedName("info_bits")
    @Expose
    private String infoBits;
    @SerializedName("special_code")
    @Expose
    private String specialCode;
    @SerializedName("multicurrency_subprice")
    @Expose
    private String multicurrencySubprice;
    @SerializedName("multicurrency_total_ht")
    @Expose
    private String multicurrencyTotalHt;
    @SerializedName("multicurrency_total_tva")
    @Expose
    private String multicurrencyTotalTva;
    @SerializedName("multicurrency_total_ttc")
    @Expose
    private String multicurrencyTotalTtc;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("rowid")
    @Expose
    private String rowid;
    @SerializedName("fk_unit")
    @Expose
    private String fkUnit;
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
    @SerializedName("origin")
    @Expose
    private String origin;
    @SerializedName("origin_id")
    @Expose
    private String originId;
    @SerializedName("statut")
    @Expose
    private String statut;
    @SerializedName("status")
    @Expose
    private String status;
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
    @SerializedName("demand_reason_id")
    @Expose
    private String demandReasonId;
    @SerializedName("transport_mode_id")
    @Expose
    private String transportModeId;
    @SerializedName("last_main_doc")
    @Expose
    private String lastMainDoc;
    @SerializedName("fk_bank")
    @Expose
    private String fkBank;
    @SerializedName("fk_account")
    @Expose
    private String fkAccount;
    @SerializedName("openid")
    @Expose
    private String openid;
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
    @SerializedName("lines")
    @Expose
    private String lines;
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
    private int specimen;
    @SerializedName("alreadypaid")
    @Expose
    private String alreadypaid;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("product_tosell")
    @Expose
    private String productTosell;
    @SerializedName("product_tobuy")
    @Expose
    private String productTobuy;
    @SerializedName("fk_product_type")
    @Expose
    private String fkProductType;
    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("weight_units")
    @Expose
    private String weightUnits;
    @SerializedName("volume")
    @Expose
    private String volume;
    @SerializedName("volume_units")
    @Expose
    private String volumeUnits;
    private final static long serialVersionUID = 8176704935271129837L;

    public String getFkCommande() {
        return fkCommande;
    }

    public void setFkCommande(String fkCommande) {
        this.fkCommande = fkCommande;
    }

    public String getCommandeId() {
        return commandeId;
    }

    public void setCommandeId(String commandeId) {
        this.commandeId = commandeId;
    }

    public String getFkParentLine() {
        return fkParentLine;
    }

    public void setFkParentLine(String fkParentLine) {
        this.fkParentLine = fkParentLine;
    }

    public String getFkFacture() {
        return fkFacture;
    }

    public void setFkFacture(String fkFacture) {
        this.fkFacture = fkFacture;
    }

    public String getRefExt() {
        return refExt;
    }

    public void setRefExt(String refExt) {
        this.refExt = refExt;
    }

    public String getFkRemiseExcept() {
        return fkRemiseExcept;
    }

    public void setFkRemiseExcept(String fkRemiseExcept) {
        this.fkRemiseExcept = fkRemiseExcept;
    }

    public String getRang() {
        return rang;
    }

    public void setRang(String rang) {
        this.rang = rang;
    }

    public String getFkFournprice() {
        return fkFournprice;
    }

    public void setFkFournprice(String fkFournprice) {
        this.fkFournprice = fkFournprice;
    }

    public String getPaHt() {
        return paHt;
    }

    public void setPaHt(String paHt) {
        this.paHt = paHt;
    }

    public String getMargeTx() {
        return margeTx;
    }

    public void setMargeTx(String margeTx) {
        this.margeTx = margeTx;
    }

    public String getMarqueTx() {
        return marqueTx;
    }

    public void setMarqueTx(String marqueTx) {
        this.marqueTx = marqueTx;
    }

    public String getRemise() {
        return remise;
    }

    public void setRemise(String remise) {
        this.remise = remise;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getProductRef() {
        return productRef;
    }

    public void setProductRef(String productRef) {
        this.productRef = productRef;
    }

    public String getProductLabel() {
        return productLabel;
    }

    public void setProductLabel(String productLabel) {
        this.productLabel = productLabel;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getProductTobatch() {
        return productTobatch;
    }

    public void setProductTobatch(String productTobatch) {
        this.productTobatch = productTobatch;
    }

    public String getProductBarcode() {
        return productBarcode;
    }

    public void setProductBarcode(String productBarcode) {
        this.productBarcode = productBarcode;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSubprice() {
        return subprice;
    }

    public void setSubprice(String subprice) {
        this.subprice = subprice;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFkProduct() {
        return fkProduct;
    }

    public void setFkProduct(String fkProduct) {
        this.fkProduct = fkProduct;
    }

    public String getRemisePercent() {
        return remisePercent;
    }

    public void setRemisePercent(String remisePercent) {
        this.remisePercent = remisePercent;
    }

    public String getVatSrcCode() {
        return vatSrcCode;
    }

    public void setVatSrcCode(String vatSrcCode) {
        this.vatSrcCode = vatSrcCode;
    }

    public String getTvaTx() {
        return tvaTx;
    }

    public void setTvaTx(String tvaTx) {
        this.tvaTx = tvaTx;
    }

    public String getLocaltax1Tx() {
        return localtax1Tx;
    }

    public void setLocaltax1Tx(String localtax1Tx) {
        this.localtax1Tx = localtax1Tx;
    }

    public String getLocaltax2Tx() {
        return localtax2Tx;
    }

    public void setLocaltax2Tx(String localtax2Tx) {
        this.localtax2Tx = localtax2Tx;
    }

    public String getLocaltax1Type() {
        return localtax1Type;
    }

    public void setLocaltax1Type(String localtax1Type) {
        this.localtax1Type = localtax1Type;
    }

    public String getLocaltax2Type() {
        return localtax2Type;
    }

    public void setLocaltax2Type(String localtax2Type) {
        this.localtax2Type = localtax2Type;
    }

    public String getInfoBits() {
        return infoBits;
    }

    public void setInfoBits(String infoBits) {
        this.infoBits = infoBits;
    }

    public String getSpecialCode() {
        return specialCode;
    }

    public void setSpecialCode(String specialCode) {
        this.specialCode = specialCode;
    }

    public String getMulticurrencySubprice() {
        return multicurrencySubprice;
    }

    public void setMulticurrencySubprice(String multicurrencySubprice) {
        this.multicurrencySubprice = multicurrencySubprice;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRowid() {
        return rowid;
    }

    public void setRowid(String rowid) {
        this.rowid = rowid;
    }

    public String getFkUnit() {
        return fkUnit;
    }

    public void setFkUnit(String fkUnit) {
        this.fkUnit = fkUnit;
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

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getDemandReasonId() {
        return demandReasonId;
    }

    public void setDemandReasonId(String demandReasonId) {
        this.demandReasonId = demandReasonId;
    }

    public String getTransportModeId() {
        return transportModeId;
    }

    public void setTransportModeId(String transportModeId) {
        this.transportModeId = transportModeId;
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

    public String getFkAccount() {
        return fkAccount;
    }

    public void setFkAccount(String fkAccount) {
        this.fkAccount = fkAccount;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
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

    public String getLines() {
        return lines;
    }

    public void setLines(String lines) {
        this.lines = lines;
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

    public int getSpecimen() {
        return specimen;
    }

    public void setSpecimen(int specimen) {
        this.specimen = specimen;
    }

    public String getAlreadypaid() {
        return alreadypaid;
    }

    public void setAlreadypaid(String alreadypaid) {
        this.alreadypaid = alreadypaid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductTosell() {
        return productTosell;
    }

    public void setProductTosell(String productTosell) {
        this.productTosell = productTosell;
    }

    public String getProductTobuy() {
        return productTobuy;
    }

    public void setProductTobuy(String productTobuy) {
        this.productTobuy = productTobuy;
    }

    public String getFkProductType() {
        return fkProductType;
    }

    public void setFkProductType(String fkProductType) {
        this.fkProductType = fkProductType;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getWeightUnits() {
        return weightUnits;
    }

    public void setWeightUnits(String weightUnits) {
        this.weightUnits = weightUnits;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getVolumeUnits() {
        return volumeUnits;
    }

    public void setVolumeUnits(String volumeUnits) {
        this.volumeUnits = volumeUnits;
    }

}
