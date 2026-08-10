package za.ac.cput.unitrade.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.unitrade.dto.ServiceItemDTO;
import za.ac.cput.unitrade.service.IServiceItemService;
import za.ac.cput.unitrade.service.ServiceItemService;

import java.util.List;

@RestController
@RequestMapping("/api/service-items")
public class ServiceItemController {

    private final IServiceItemService service;

    public ServiceItemController() {
        this.service = ServiceItemService.getService();
    }

    @PostMapping("/create")
    public ResponseEntity<ServiceItemDTO> create(@RequestBody ServiceItemDTO item) {
        ServiceItemDTO createdItem = service.create(item);

        if (createdItem == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(createdItem);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<ServiceItemDTO> read(@PathVariable Integer id) {
        ServiceItemDTO item = service.read(id);

        if (item == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(item);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ServiceItemDTO> update(
            @PathVariable Integer id,
            @RequestBody ServiceItemDTO item) {

        item.setId(id);
        ServiceItemDTO updatedItem = service.update(item);

        if (updatedItem == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id) {
        boolean deleted = service.delete(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(true);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ServiceItemDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
}