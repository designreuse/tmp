package com.softserve.osbb.util;

import com.softserve.osbb.tenants.model.Report;
import org.springframework.hateoas.Resource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nazar.dovhyy on 08.08.2016.
 */
public class ReportPageCreator extends PageCreator<Resource<Report>> {

    private List<String> dates;

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<LocalDate> dates) {
        this.dates = new ArrayList<>();
        dates.forEach((date) -> this.dates.add(date.toString()));
    }
}
