package com.softserve.osbb.tenants.service;

import com.softserve.osbb.tenants.model.Report;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by nazar.dovhyy on 09.07.2016.
 */

@Service
public interface ReportService {

    Report addReport(Report report) throws Exception;

    Report updateReport(Integer reportId, Report report) throws Exception;

    Report getReportById(Integer reportId) throws Exception;

    Report getOneReportBySearchTerm(String name) throws Exception;

    List<Report> getAlReportsBySearchParameter(String searchTerm) throws Exception;

    List<Report> getAllReportsBetweenDates(LocalDate from, LocalDate to) throws Exception;

    List<Report> findByDate(LocalDate dateToFind);

    List<LocalDate> findDistinctCreationDates();

    void deleteAll() throws Exception;

    boolean deleteReportById(Integer reportId) throws Exception;

    List<Report> getAllReports();

    Page<Report> getAllReports(Integer pageNumber, Integer rowNum, String sortedBy, Boolean ascOrder);
}
