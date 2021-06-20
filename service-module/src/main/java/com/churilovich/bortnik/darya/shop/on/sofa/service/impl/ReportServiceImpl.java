package com.churilovich.bortnik.darya.shop.on.sofa.service.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.ItemRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.element.Report;
import com.churilovich.bortnik.darya.shop.on.sofa.service.ReportService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.element.ReportDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final ConversionService conversionService;
    private final ItemRepository itemRepository;

    @Override
    public List<ReportDTO> get(Long userId) {
        List<Report> reports = itemRepository.findAllReportGroupByNameAndPrice(userId);
        return reports.stream()
                .map(report -> conversionService.convert(report, ReportDTO.class))
                .collect(Collectors.toList());
    }
}
