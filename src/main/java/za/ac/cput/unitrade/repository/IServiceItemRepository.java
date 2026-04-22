package za.ac.cput.unitrade.repository;

import za.ac.cput.unitrade.dto.ServiceItemDTO;
import java.util.List;

public interface IServiceItemRepository {

    ServiceItemDTO create(ServiceItemDTO item);

    ServiceItemDTO read(Integer id);

    ServiceItemDTO update(ServiceItemDTO item);

    boolean delete(Integer id);

    List<ServiceItemDTO> getAll();
}