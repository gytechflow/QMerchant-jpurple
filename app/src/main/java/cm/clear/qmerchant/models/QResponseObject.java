package cm.clear.qmerchant.models;

public class QResponseObject {
    private String response;
    private String requiredParameter;
    private String detailedResponse;
    private boolean parameter;

    public QResponseObject() {
    }

    public String getResponse() {
        return response;
    }

    public QResponseObject setResponse(String response) {
        this.response = response;
        return this;
    }

    public String getRequiredParameter() {
        return requiredParameter;
    }

    public QResponseObject setRequiredParameter(String requiredParameter) {
        this.requiredParameter = requiredParameter;
        return this;
    }

    public String getDetailedResponse() {
        return detailedResponse;
    }

    public QResponseObject setDetailedResponse(String detailedResponse) {
        this.detailedResponse = detailedResponse;
        return this;
    }

    public boolean hasParameter() {
        return parameter;
    }

    public QResponseObject setParameter(boolean parameter) {
        this.parameter = parameter;
        return this;
    }
}
