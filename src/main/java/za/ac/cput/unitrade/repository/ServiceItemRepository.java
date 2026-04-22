package za.ac.cput.unitrade.repository;

import za.ac.cput.unitrade.dto.ServiceItemDTO;

import java.util.ArrayList;
import java.util.List;

public class ServiceItemRepository implements IServiceItemRepository {

    private static IServiceItemRepository repository = null;
    private final List<ServiceItemDTO> itemList;

    private ServiceItemRepository() {
        itemList = new ArrayList<>();
    }

    // singleton access
    public static IServiceItemRepository getRepository() {
        if (repository == null) {
            repository = new ServiceItemRepository();
        }
        return repository;
    }

    @Override
    public ServiceItemDTO create(ServiceItemDTO item) {
        itemList.add(item);
        return item;
    }

    @Override
    public ServiceItemDTO read(Integer id) {
        for (ServiceItemDTO item : itemList) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    @Override
    public ServiceItemDTO update(ServiceItemDTO item) {
        ServiceItemDTO existing = read(item.getId());

        if (existing != null) {
            itemList.remove(existing);
            itemList.add(item);
            return item;
        }
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        ServiceItemDTO item = read(id);

        if (item != null) {
            itemList.remove(item);
            return true;
        }
        return false;
    }

    @Override
    public List<ServiceItemDTO> getAll() {
        return new ArrayList<>(itemList);
    }
}