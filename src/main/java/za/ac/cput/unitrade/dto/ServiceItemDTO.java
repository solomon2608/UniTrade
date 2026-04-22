package za.ac.cput.unitrade.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceItemDTO {

    private int id;
    private String name;
    private String description;
    private double price;
    private int userId;
}