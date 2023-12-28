package fpt.project.datn.controller;

import fpt.project.datn.service.AbsGeneralCRUDService;
import fpt.project.datn.utility.Utility;
import fpt.project.datn.utility.Validation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class AbsGeneralCRUDController<RES, REQ, E, ID> {

    private final AbsGeneralCRUDService<RES, REQ, E, ID> service;

    protected AbsGeneralCRUDController(AbsGeneralCRUDService service) {
        this.service = service;
    }

    @GetMapping("/get")
    public ResponseEntity<?> get() {
        return ResponseEntity.ok(service.getAllData());
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<?> get(@RequestAttribute int page) {
        return ResponseEntity.ok(service.getAllData(page));
    }

    @PostMapping("/save")
    public void save(@RequestBody @Valid REQ obj, BindingResult rs) {
        Validation.valid(rs);
        service.save(obj);
    }

    @PostMapping("/save-all")
    public void save(@RequestBody List<REQ> objs) {
        service.save(objs);
    }

    @PostMapping("/find-by-id")
    public ResponseEntity<?> findById(@RequestBody String body) {
        return ResponseEntity.ok(service.findById(Utility.getAttributeFromJson(body, "id")));
    }
}
