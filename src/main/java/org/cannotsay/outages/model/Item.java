package org.cannotsay.outages.model;

import com.fasterxml.jackson.annotation.*;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Item implements Serializable {

    @JsonProperty("description")
    private String description;
    @JsonProperty("category")
    private String category;
    @JsonProperty("expectedEndDate")
    private Long expectedEndDate;
    @JsonProperty("locations")
    private String locations;
    @JsonProperty("postalCodes")
    private String postalCodes;
    @JsonProperty("ticketNumber")
    private String ticketNumber;
    @JsonProperty("link")
    private String link;
    @JsonProperty("title")
    private String title;
    @JsonProperty("status")
    private String status;
    @JsonProperty("startDate")
    private String startDate;
    @JsonProperty("endDate")
    private String endDate;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getExpectedEndDate() {
        return expectedEndDate;
    }

    public void setExpectedEndDate(Long expectedEndDate) {
        this.expectedEndDate = expectedEndDate;
    }

    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }

    public String getPostalCodes() {
        return postalCodes;
    }

    public void setPostalCodes(String postalCodes) {
        this.postalCodes = postalCodes;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}