package ru.netology;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        Ticket first = new Ticket("LED", "MOW", 6000, 10, 12);
        Ticket second = new Ticket("LED", "MOW", 5000, 8, 10);
        Ticket third = new Ticket("LED", "MOW", 7000, 14, 16);
        manager.add(first);
        manager.add(second);
        manager.add(third);
        manager.add(new Ticket("MOW", "LED", 3000, 9, 11)); // другой маршрут

        Ticket[] expected = {second, first, third}; // порядок по возрастанию цены
        Ticket[] actual = manager.search("LED", "MOW");

        assertArrayEquals(expected, actual);
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
        Ticket t1 = new Ticket("LED", "MOW", 6000, 10, 12); // 2 ч
        Ticket t2 = new Ticket("LED", "MOW", 5000, 8, 13);  // 5 ч
        Ticket t3 = new Ticket("LED", "MOW", 7000, 9, 11);  // 2 ч
        manager.add(t1);
        manager.add(t2);
        manager.add(t3);
        manager.add(new Ticket("MOW", "LED", 3000, 10, 11)); // другой маршрут

        TicketTimeComparator comparator = new TicketTimeComparator();
        Ticket[] expected = {t1, t3, t2}; // сначала 2-часовые, потом 5-часовой
        Ticket[] actual = manager.searchAndSortBy("LED", "MOW", comparator);

        assertArrayEquals(expected, actual);
    }
}