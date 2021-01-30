package app.controller.impl;

import app.service.SupplierService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "api/suppliers")
public class SupplierControllerImpl {
    private final SupplierService supplierService;

    public SupplierControllerImpl(SupplierService supplierService) {
        this.supplierService = supplierService;
    }
}
