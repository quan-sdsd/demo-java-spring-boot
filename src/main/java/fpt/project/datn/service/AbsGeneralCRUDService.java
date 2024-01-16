package fpt.project.datn.service;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @param <RES> response object
 * @param <REQ> request object
 * @param <E> Entity
 * @param <ID> Data type of E's id
 */
@Service
@AllArgsConstructor
public abstract class AbsGeneralCRUDService<RES, REQ, E, ID> {

    protected final JpaRepository<E, ID> repository;
    private final ModelMapper modelMapper;
    private Class<RES> RESClass;
    private Class<E> EClass;
    protected final ApplicationEventPublisher publisher;

    public List<RES> getAllData() {
        return repository.findAll()
                .stream()
                .map(e -> modelMapper.map(e, RESClass))
                .toList();
    }

    public List<RES> getAllData(int page) {
        return repository.findAll(getPageable(page))
                .stream()
                .map(e -> modelMapper.map(e, RESClass))
                .toList();
    }

    public void save(REQ obj) {
        repository.save(modelMapper.map(obj, EClass));
    }

    public void save(List<REQ> objs) {
        List<E> entities = objs
                .stream()
                .map(obj -> modelMapper.map(obj, EClass))
                .toList();
        repository.saveAll(entities);
    }

    public void deleteById(String id) {
        repository.deleteById(getEId(id));
    }

    public void batchDeleteById(List<String> ids) {
        repository.deleteAllByIdInBatch(ids.stream()
                .map(this::getEId)
                .toList());
    }

    public RES findById(String id) {
        E entity = repository.findById(getEId(id)).orElseThrow(RuntimeException::new);
        return modelMapper.map(entity, RESClass);
    }

    protected abstract Pageable getPageable(int page);
    protected abstract ID getEId(Object id);
}
