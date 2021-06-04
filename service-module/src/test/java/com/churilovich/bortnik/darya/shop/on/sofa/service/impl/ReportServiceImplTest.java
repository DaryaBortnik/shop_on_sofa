package com.churilovich.bortnik.darya.shop.on.sofa.service.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.ItemRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.element.Report;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.element.ReportDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportServiceImplTest {
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private ConversionService conversionService;
    @InjectMocks
    private ReportServiceImpl reportService;

    @Test
    public void shouldGetAllReports() {
        List<Report> reports = new ArrayList<>();
        Report report = new Report();
        report.setName("name");
        report.setAmount(1L);
        reports.add(report);
        when(itemRepository.findAllReportGroupByNameAndPrice()).thenReturn(reports);
        ReportDTO reportDTO = ReportDTO.builder()
                .name("name")
                .amount(1L)
                .build();
        when(conversionService.convert(report, ReportDTO.class)).thenReturn(reportDTO);
        List<ReportDTO> expectedReports = new ArrayList<>();
        expectedReports.add(reportDTO);

        List<ReportDTO> actualReports = reportService.get();
        assertEquals(expectedReports.size(),actualReports.size());
    }

    @Test
    public void shouldReturnEmptyListIfCantGetReport(){
        List<ReportDTO> actualReports = reportService.get();
        assertTrue(actualReports.isEmpty());
    }
}