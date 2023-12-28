package fpt.project.datn.controller;

import fpt.project.datn.object.entity.Product;
import fpt.project.datn.service.AbsGeneralCRUDService;
import fpt.project.datn.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController extends AbsGeneralCRUDController<Product, Product, Product, Integer> {

    protected ProductController(ProductService service) {
        super(service);
    }

}
