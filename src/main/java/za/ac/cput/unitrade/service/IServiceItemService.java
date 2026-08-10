package za.ac.cput.unitrade.service;

import za.ac.cput.unitrade.dto.ServiceItemDTO;
import java.util.List;

public interface IServiceItemService {

    ServiceItemDTO create(ServiceItemDTO item);

    ServiceItemDTO read(Integer id);

    ServiceItemDTO update(ServiceItemDTO item);

    boolean delete(Integer id);

    List<ServiceItemDTO> getAll();
}