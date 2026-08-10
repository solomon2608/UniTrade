package za.ac.cput.unitrade;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.cput.unitrade.dto.ServiceItemDTO;
import za.ac.cput.unitrade.repository.IServiceItemRepository;
import za.ac.cput.unitrade.repository.ServiceItemRepository;
import za.ac.cput.unitrade.service.IServiceItemService;
import za.ac.cput.unitrade.service.ServiceItemService;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServiceItemTest {

    private IServiceItemRepository repository;
    private IServiceItemService service;

    @BeforeEach
    void setUp() throws Exception {
        Field repositoryField = ServiceItemRepository.class.getDeclaredField("repository");
        repositoryField.setAccessible(true);
        repositoryField.set(null, null);

        Field serviceField = ServiceItemService.class.getDeclaredField("service");
        serviceField.setAccessible(true);
        serviceField.set(null, null);

        repository = ServiceItemRepository.getRepository();
        service = ServiceItemService.getService();
    }

    @Test
    void testDTO() {
        ServiceItemDTO item = new ServiceItemDTO();

        item.setId(1);
        item.setName("Laptop Repair");
        item.setDescription("Fixing student laptops");
        item.setPrice(250.00);
        item.setUserId(10);

        assertEquals(1, item.getId());
        assertEquals("Laptop Repair", item.getName());
        assertEquals("Fixing student laptops", item.getDescription());
        assertEquals(250.00, item.getPrice());
        assertEquals(10, item.getUserId());

        ServiceItemDTO item2 = new ServiceItemDTO(
                2,
                "Textbook",
                "Second-hand Java textbook",
                150.00,
                5
        );

        assertEquals(2, item2.getId());
        assertEquals("Textbook", item2.getName());
        assertEquals("Second-hand Java textbook", item2.getDescription());
        assertEquals(150.00, item2.getPrice());
        assertEquals(5, item2.getUserId());
    }

    @Test
    void testRepositoryCreateAndRead() {
        ServiceItemDTO item = new ServiceItemDTO(1, "Laptop", "Used laptop", 3000.00, 2);

        ServiceItemDTO created = repository.create(item);
        ServiceItemDTO found = repository.read(1);

        assertNotNull(created);
        assertNotNull(found);
        assertEquals(1, found.getId());
        assertEquals("Laptop", found.getName());
        assertEquals("Used laptop", found.getDescription());
        assertEquals(3000.00, found.getPrice());
        assertEquals(2, found.getUserId());
    }

    @Test
    void testRepositoryReadReturnsNullWhenItemDoesNotExist() {
        ServiceItemDTO found = repository.read(99);

        assertNull(found);
    }

    @Test
    void testRepositoryUpdate() {
        ServiceItemDTO item = new ServiceItemDTO(1, "Book", "Old description", 100.00, 4);
        repository.create(item);

        ServiceItemDTO updatedItem = new ServiceItemDTO(1, "Book", "Updated description", 120.00, 4);
        ServiceItemDTO result = repository.update(updatedItem);

        assertNotNull(result);
        assertEquals("Updated description", result.getDescription());
        assertEquals(120.00, result.getPrice());
    }

    @Test
    void testRepositoryUpdateReturnsNullWhenItemDoesNotExist() {
        ServiceItemDTO item = new ServiceItemDTO(99, "Desk", "Study desk", 500.00, 6);

        ServiceItemDTO result = repository.update(item);

        assertNull(result);
    }

    @Test
    void testRepositoryDelete() {
        ServiceItemDTO item = new ServiceItemDTO(1, "Chair", "Study chair", 200.00, 7);
        repository.create(item);

        boolean deleted = repository.delete(1);

        assertTrue(deleted);
        assertNull(repository.read(1));
    }

    @Test
    void testRepositoryDeleteReturnsFalseWhenItemDoesNotExist() {
        boolean deleted = repository.delete(99);

        assertFalse(deleted);
    }

    @Test
    void testRepositoryGetAll() {
        repository.create(new ServiceItemDTO(1, "Item 1", "Description 1", 100.00, 1));
        repository.create(new ServiceItemDTO(2, "Item 2", "Description 2", 200.00, 2));

        List<ServiceItemDTO> items = repository.getAll();

        assertEquals(2, items.size());
    }

    @Test
    void testServiceCreateValidItem() {
        ServiceItemDTO item = new ServiceItemDTO(1, "Tutoring", "Math tutoring service", 150.00, 10);

        ServiceItemDTO created = service.create(item);

        assertNotNull(created);
        assertEquals("Tutoring", created.getName());
    }

    @Test
    void testServiceCreateFailsWithInvalidData() {
        assertNull(service.create(null));

        assertNull(service.create(new ServiceItemDTO(
                1,
                "",
                "Description",
                100.00,
                10
        )));

        assertNull(service.create(new ServiceItemDTO(
                2,
                "Item",
                "",
                100.00,
                10
        )));

        assertNull(service.create(new ServiceItemDTO(
                3,
                "Item",
                "Description",
                0.00,
                10
        )));

        assertNull(service.create(new ServiceItemDTO(
                4,
                "Item",
                "Description",
                -50.00,
                10
        )));

        assertNull(service.create(new ServiceItemDTO(
                5,
                "Item",
                "Description",
                100.00,
                0
        )));
    }

    @Test
    void testServiceReadValidItem() {
        ServiceItemDTO item = new ServiceItemDTO(1, "Calculator", "Scientific calculator", 180.00, 2);
        service.create(item);

        ServiceItemDTO found = service.read(1);

        assertNotNull(found);
        assertEquals("Calculator", found.getName());
    }

    @Test
    void testServiceReadReturnsNullForInvalidId() {
        assertNull(service.read(0));
        assertNull(service.read(-1));
        assertNull(service.read(null));
    }

    @Test
    void testServiceUpdateValidItem() {
        ServiceItemDTO item = new ServiceItemDTO(1, "Book", "Old description", 100.00, 3);
        service.create(item);

        ServiceItemDTO updatedItem = new ServiceItemDTO(1, "Book", "New description", 120.00, 3);
        ServiceItemDTO updated = service.update(updatedItem);

        assertNotNull(updated);
        assertEquals("New description", updated.getDescription());
        assertEquals(120.00, updated.getPrice());
    }

    @Test
    void testServiceUpdateFailsWhenItemDoesNotExist() {
        ServiceItemDTO item = new ServiceItemDTO(99, "Table", "Study table", 400.00, 3);

        ServiceItemDTO updated = service.update(item);

        assertNull(updated);
    }

    @Test
    void testServiceUpdateFailsWithInvalidData() {
        ServiceItemDTO item = new ServiceItemDTO(1, "Book", "Description", 100.00, 3);
        service.create(item);

        ServiceItemDTO invalidUpdate = new ServiceItemDTO(1, "", "Description", 100.00, 3);

        ServiceItemDTO updated = service.update(invalidUpdate);

        assertNull(updated);
    }

    @Test
    void testServiceDeleteValidItem() {
        ServiceItemDTO item = new ServiceItemDTO(1, "Headphones", "Wireless headphones", 250.00, 4);
        service.create(item);

        boolean deleted = service.delete(1);

        assertTrue(deleted);
        assertNull(service.read(1));
    }

    @Test
    void testServiceDeleteFailsForInvalidId() {
        assertFalse(service.delete(0));
        assertFalse(service.delete(-1));
        assertFalse(service.delete(null));
    }

    @Test
    void testServiceGetAll() {
        service.create(new ServiceItemDTO(1, "Item 1", "Description 1", 100.00, 1));
        service.create(new ServiceItemDTO(2, "Item 2", "Description 2", 200.00, 2));

        List<ServiceItemDTO> items = service.getAll();

        assertEquals(2, items.size());
    }
}