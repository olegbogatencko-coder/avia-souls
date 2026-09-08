package ru.netology;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AviaSoulsTest {

    // 1. Тест метода compareTo (сравнение по цене)
    @Test
    void testCompareTo() {
        Ticket cheaper = new Ticket("LED", "MOW", 5000, 10, 12);
        Ticket moreExpensive = new Ticket("LED", "MOW", 7000, 14, 16);
        assertTrue(cheaper.compareTo(moreExpensive) < 0);
        assertTrue(moreExpensive.compareTo(cheaper) > 0);
        assertEquals(0, cheaper.compareTo(cheaper));
    }

    // 2. Тест поиска с сортировкой по цене (по умолчанию)
    @Test
    void testSearchSortByPrice() {
        AviaSouls manager = new AviaSouls();
        manager.add(new Ticket("LED", "MOW", 6000, 10, 12));
        manager.add(new Ticket("LED", "MOW", 5000, 8, 10));
        manager.add(new Ticket("LED", "MOW", 7000, 14, 16));
        manager.add(new Ticket("MOW", "LED", 3000, 9, 11)); // другой маршрут

        Ticket[] result = manager.search("LED", "MOW");
        assertEquals(3, result.length);
        // Проверяем порядок: цены должны идти по возрастанию
        assertEquals(5000, result[0].getPrice());
        assertEquals(6000, result[1].getPrice());
        assertEquals(7000, result[2].getPrice());
    }

    // 3. Тест компаратора (сравнение по времени полёта)
    @Test
    void testTicketTimeComparator() {
        Ticket shorter = new Ticket("LED", "MOW", 6000, 10, 12); // 2 часа
        Ticket longer = new Ticket("LED", "MOW", 5000, 8, 13);   // 5 часов
        TicketTimeComparator comparator = new TicketTimeComparator();
        assertTrue(comparator.compare(shorter, longer) < 0);
        assertTrue(comparator.compare(longer, shorter) > 0);
        assertEquals(0, comparator.compare(shorter, shorter));
    }

    // 4. Тест searchAndSortBy с компаратором
    @Test
    void testSearchAndSortByTime() {
        AviaSouls manager = new AviaSouls();
        manager.add(new Ticket("LED", "MOW", 6000, 10, 12));   // 2 ч
        manager.add(new Ticket("LED", "MOW", 5000, 8, 13));    // 5 ч
        manager.add(new Ticket("LED", "MOW", 7000, 9, 11));    // 2 ч
        manager.add(new Ticket("MOW", "LED", 3000, 10, 11));   // другой маршрут

        TicketTimeComparator comparator = new TicketTimeComparator();
        Ticket[] result = manager.searchAndSortBy("LED", "MOW", comparator);

        assertEquals(3, result.length);
        // Проверяем, что отсортировано по времени полёта (возрастание)
        // Время полёта = timeTo - timeFrom
        assertEquals(2, result[0].getTimeTo() - result[0].getTimeFrom());
        assertEquals(2, result[1].getTimeTo() - result[1].getTimeFrom());
        assertEquals(5, result[2].getTimeTo() - result[2].getTimeFrom());
    }
}