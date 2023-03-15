package cm.clear.qmerchant.models;

public class AffectTable {
    private String resource_id;
    private String event_id;

    public AffectTable() {
    }

    public AffectTable(String resource_id, String event_id) {
        this.resource_id = resource_id;
        this.event_id = event_id;
    }

    public String getResource_id() {
        return resource_id;
    }

    public void setResource_id(String resource_id) {
        this.resource_id = resource_id;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }
}
