package za.ac.cput.unitrade.service;

import za.ac.cput.unitrade.dto.ServiceItemDTO;
import za.ac.cput.unitrade.repository.IServiceItemRepository;
import za.ac.cput.unitrade.repository.ServiceItemRepository;

import java.util.List;

public class ServiceItemService implements IServiceItemService {

    private static IServiceItemService service = null;
    private final IServiceItemRepository repository;

    private ServiceItemService() {
        repository = ServiceItemRepository.getRepository();
    }

    public static IServiceItemService getService() {
        if (service == null) {
            service = new ServiceItemService();
        }
        return service;
    }

    @Override
    public ServiceItemDTO create(ServiceItemDTO item) {
        if (!isValid(item)) {
            return null;
        }

        return repository.create(item);
    }

    @Override
    public ServiceItemDTO read(Integer id) {
        if (id == null || id <= 0) {
            return null;
        }

        return repository.read(id);
    }

    @Override
    public ServiceItemDTO update(ServiceItemDTO item) {
        if (!isValid(item)) {
            return null;
        }

        ServiceItemDTO existingItem = repository.read(item.getId());

        if (existingItem == null) {
            return null;
        }

        return repository.update(item);
    }

    @Override
    public boolean delete(Integer id) {
        if (id == null || id <= 0) {
            return false;
        }

        return repository.delete(id);
    }

    @Override
    public List<ServiceItemDTO> getAll() {
        return repository.getAll();
    }

    private boolean isValid(ServiceItemDTO item) {
        if (item == null) {
            return false;
        }

        if (item.getName() == null || item.getName().trim().isEmpty()) {
            return false;
        }

        if (item.getDescription() == null || item.getDescription().trim().isEmpty()) {
            return false;
        }

        if (item.getPrice() <= 0) {
            return false;
        }

        if (item.getUserId() <= 0) {
            return false;
        }

        return true;
    }
}