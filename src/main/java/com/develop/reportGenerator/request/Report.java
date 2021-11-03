
package com.develop.reportGenerator.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;


public class Report {

    @JsonProperty("prj_title")
    private String prjTitle;
    @JsonProperty("repeat_page")
    private List<RepeatPage> repeatPage = null;

    @JsonProperty("prj_title")
    public String getPrjTitle() {
        return prjTitle;
    }

    @JsonProperty("prj_title")
    public void setPrjTitle(String prjTitle) {
        this.prjTitle = prjTitle;
    }

    @JsonProperty("repeat_page")
    public List<RepeatPage> getRepeatPage() {
        return repeatPage;
    }

    @JsonProperty("repeat_page")
    public void setRepeatPage(List<RepeatPage> repeatPage) {
        this.repeatPage = repeatPage;
    }

}
