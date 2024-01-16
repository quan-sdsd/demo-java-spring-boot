package fpt.project.datn.service;

import fpt.project.datn.object.entity.Product;
import fpt.project.datn.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends AbsGeneralCRUDService<Product, Product, Product, Integer> {

    public ProductService(ProductRepository repository, ModelMapper modelMapper, ApplicationEventPublisher publisher) {
        super(repository, modelMapper, Product.class, Product.class, publisher);
    }

    @Override
    protected Pageable getPageable(int page) {
        return null;
    }

    @Override
    protected Integer getEId(Object id) {
        return Integer.parseInt((String) id);
    }
}
