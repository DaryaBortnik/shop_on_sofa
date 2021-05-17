package com.churilovich.bortnik.darya.shop.on.sofa.service.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.service.ItemService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.ReportService;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ItemDTO;
import com.churilovich.bortnik.darya.shop.on.sofa.service.model.ReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {
    private final ItemService itemService;

    @Autowired
    public ReportServiceImpl(ItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    public List<ReportDTO> get() {
        List<ItemDTO> items = itemService.findAll();
        return items.stream()
                .distinct()
                .map(itemDTO -> {
                    ReportDTO report = new ReportDTO();
                    report.setItemDTO(itemDTO);
                    return report;
                })
                .collect(Collectors.toList());
    }
}
