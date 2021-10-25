
package com.develop.reportGenerator.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Report {

    @SerializedName("prj_title")
    @Expose
    private String prjTitle;
    @SerializedName("repeat_page")
    @Expose
    private List<RepeatPage> repeatPage = null;

    public String getPrjTitle() {
        return prjTitle;
    }

    public void setPrjTitle(String prjTitle) {
        this.prjTitle = prjTitle;
    }

    public List<RepeatPage> getRepeatPage() {
        return repeatPage;
    }

    public void setRepeatPage(List<RepeatPage> repeatPage) {
        this.repeatPage = repeatPage;
    }

}
