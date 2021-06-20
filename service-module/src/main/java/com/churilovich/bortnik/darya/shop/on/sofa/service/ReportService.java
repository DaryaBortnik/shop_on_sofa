package com.churilovich.bortnik.darya.shop.on.sofa.service;

import com.churilovich.bortnik.darya.shop.on.sofa.service.model.element.ReportDTO;

import java.util.List;

public interface ReportService {
    List<ReportDTO> get(Long userId);
}
