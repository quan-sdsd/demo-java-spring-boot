package fpt.project.datn.service;

import fpt.project.datn.object.entity.Product;
import fpt.project.datn.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends AbsGeneralCRUDService<Product, Product, Product, Integer> {

    public ProductService(ProductRepository repository, ModelMapper modelMapper, Class<Product> RESClass, Class<Product> EClass, ApplicationEventPublisher publisher) {
        super(repository, modelMapper, RESClass, EClass, publisher);
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
