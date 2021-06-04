package com.churilovich.bortnik.darya.shop.on.sofa.service.converter.report;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.element.Report;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.element.ReportDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ReportEntityToDTOConverter implements Converter<Report, ReportDTO> {

    @Override
    public ReportDTO convert(Report report) {
        return ReportDTO.builder()
                .name(report.getName())
                .amount(report.getAmount())
                .price(report.getPrice())
                .category(report.getCategory())
                .build();
    }
}
