package cm.clear.qmerchant.modules.bookings.createNewBooking.data;

public class BookingFormState {
    public final Integer TYPE_ERROR  = 0;
    public final Integer LABEL_ERROR  = 0;
    public final Integer DATE_ERROR  = 0;
    public final Integer NAMES_ERROR  = 0;
    public final Integer EMAIL_ERROR  = 0;
    public final Integer PHONE_ERROR  = 0;
    public final Integer PLACES_ERROR  = 0;

    private Integer typeError;
    private Integer labelError;
    private Integer dateError;
    private Integer namesError;
    private Integer emailError;
    private Integer phoneError;
    private Integer placesError;
    private boolean isDataValid;

    public Integer getTypeError() {
        return typeError;
    }

    public void setTypeError(Integer typeError) {
        this.typeError = typeError;
    }

    public Integer getLabelError() {
        return labelError;
    }

    public void setLabelError(Integer labelError) {
        this.labelError = labelError;
    }

    public Integer getDateError() {
        return dateError;
    }

    public void setDateError(Integer dateError) {
        this.dateError = dateError;
    }

    public Integer getNamesError() {
        return namesError;
    }

    public void setNamesError(Integer namesError) {
        this.namesError = namesError;
    }

    public Integer getEmailError() {
        return emailError;
    }

    public void setEmailError(Integer emailError) {
        this.emailError = emailError;
    }

    public Integer getPhoneError() {
        return phoneError;
    }

    public void setPhoneError(Integer phoneError) {
        this.phoneError = phoneError;
    }

    public Integer getPlacesError() {
        return placesError;
    }

    public void setPlacesError(Integer placesError) {
        this.placesError = placesError;
    }

    public boolean isDataValid() {
        return isDataValid;
    }

    public void setDataValid(boolean dataValid) {
        isDataValid = dataValid;
    }
}
