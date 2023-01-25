package ua.skorobahatyi.model;

import java.util.*;

public class OrderBook {
    private Map<Integer, Order> orderBook;

    public OrderBook() {
        orderBook = new TreeMap<>(Collections.reverseOrder());
    }

    public Map<Integer, Order> getOrderBook() {
        return orderBook;
    }

    public void updateOrderBook(Integer price, Order order) {
        if (order.getSize() == 0) {
            orderBook.remove(price);
            return;
        }
        orderBook.put(price, order);
    }

    public void removeAskOrders(int size) {
        int maxPrice = getMaxAskPrice();
        int minPrice = getBestAskPrice();

        for (int price = minPrice; price <= maxPrice; price++) {
            Order order = orderBook.getOrDefault(price, null);
            if (order == null)
                continue;
            int orderSize = order.getSize();
            if (orderSize > size) {
                order.setSize(orderSize - size);
                orderBook.put(price, order);
                break;
            } else {
                size -= orderSize;
                orderBook.remove(price);
            }
        }
    }

    public void removeBidOrders(int size) {
        int maxPrice = getBestBidPrice();
        int minPrice = getMinBidPrice();

        for (int price = maxPrice; price >= minPrice; price--) {
            Order order = orderBook.getOrDefault(price, null);
            if (order == null)
                continue;
            int orderSize = order.getSize();
            if (orderSize > size) {
                order.setSize(orderSize - size);
                orderBook.put(price, order);
                break;
            } else {
                size -= orderSize;
                orderBook.remove(price);
                System.out.println("");
            }
        }
    }

    public int getBestBidPrice() {
        int bestBidPrice = 0;

        for (Map.Entry<Integer, Order> item : orderBook.entrySet()) {
            if (item.getValue().getTypeOrder() == TypeOrder.BID && item.getKey() > bestBidPrice)
                bestBidPrice = item.getKey();
        }

        return bestBidPrice;
    }

    public int getMinBidPrice() {
        int minBidPrice = Integer.MAX_VALUE;

        for (Map.Entry<Integer, Order> item : orderBook.entrySet()) {
            if (item.getValue().getTypeOrder() == TypeOrder.BID && item.getKey() < minBidPrice)
                minBidPrice = item.getKey();
        }

        return minBidPrice;
    }

    public int getBestAskPrice() {

        int bestAskPrice = Integer.MAX_VALUE;

        for (Map.Entry<Integer, Order> item : orderBook.entrySet()) {
            if (item.getValue().getTypeOrder() == TypeOrder.ASK && item.getKey() < bestAskPrice)
                bestAskPrice = item.getKey();
        }

        return bestAskPrice;
    }


    public int getMaxAskPrice() {
        int maxAskPrice = 0;

        for (Map.Entry<Integer, Order> item : orderBook.entrySet()) {
            if (item.getValue().getTypeOrder() == TypeOrder.ASK && item.getKey() > maxAskPrice)
                maxAskPrice = item.getKey();
        }

        return maxAskPrice;
    }

    public String printBestBidPriceSize() {
        Integer price = getBestBidPrice();

        Order order = orderBook.getOrDefault(price, new Order(0, TypeOrder.SPREAD));
        return price + "," + order.getSize();
    }

    public String printBestAskPriceSize() {
        Integer price = getBestAskPrice();

        Order order = orderBook.getOrDefault(price, new Order(0, TypeOrder.SPREAD));
        return price + "," + order.getSize();
    }

    public String printSizeForPrice(Integer price) {
        Order order = orderBook.getOrDefault(price, new Order(0, TypeOrder.SPREAD));
        return order.getSize().toString();
    }
}
