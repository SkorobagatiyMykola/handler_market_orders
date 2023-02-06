package ua.skorobahatyi.model;

import java.util.*;

public class OrderBook {
    private SortedMap<Integer, Order> askOrderBook;
    private SortedMap<Integer, Order> bidOrderBook;

    public OrderBook() {
        askOrderBook = new TreeMap<>();
        bidOrderBook = new TreeMap<>(Collections.reverseOrder());
    }

    public SortedMap<Integer, Order> getAskOrderBook() {
        return askOrderBook;
    }

    public SortedMap<Integer, Order> getBidOrderBook() {
        return bidOrderBook;
    }

    public void updateOrderBook(Integer price, Order order) {
        if (order.getTypeOrder().equals(TypeOrder.ASK)) {
            if (order.getSize() == 0) {
                askOrderBook.remove(price);
                return;
            }
            askOrderBook.put(price, order);
        } else if (order.getTypeOrder().equals(TypeOrder.BID)) {
            if (order.getSize() == 0) {
                bidOrderBook.remove(price);
                return;
            }
            bidOrderBook.put(price, order);
        } else {
            return;
        }
    }

    public void removeAskOrders(int size) {
        Iterator<Map.Entry<Integer, Order>> itr = askOrderBook.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<Integer, Order> item = itr.next();
            Order order = item.getValue();

            if (order.getSize() > size) {
                order.setSize(order.getSize() - size);
                askOrderBook.put(item.getKey(), order);
                break;
            } else {
                size -= order.getSize();
                itr.remove();
            }
        }
    }

    public void removeBidOrders(int size) {
        Iterator<Map.Entry<Integer, Order>> itr = bidOrderBook.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<Integer, Order> item = itr.next();
            Order order = item.getValue();

            if (order.getSize() > size) {
                order.setSize(order.getSize() - size);
                bidOrderBook.put(item.getKey(), order);
                break;
            } else {
                size -= order.getSize();
                itr.remove();
            }
        }
    }

    public int getBestBidPrice() {
        return bidOrderBook.firstKey() != null ? bidOrderBook.firstKey() : 0;
    }

    public int getBestAskPrice() {
        return askOrderBook.firstKey() != null ? askOrderBook.firstKey() : 0;
    }


    public String printBestBidPriceSize() {
        Integer price = getBestBidPrice();
        Order order = bidOrderBook.getOrDefault(price, new Order(0, TypeOrder.SPREAD));
        return price + "," + order.getSize();
    }

    public String printBestAskPriceSize() {
        Integer price = getBestAskPrice();
        Order order = askOrderBook.getOrDefault(price, new Order(0, TypeOrder.SPREAD));
        return price + "," + order.getSize();
    }

    public String printSizeForPrice(Integer price) {
        Order askOrder = askOrderBook.get(price);
        if (askOrder != null) {
            return askOrder.getSize().toString();
        } else {
            Order bidOrder = bidOrderBook.get(price);
            return bidOrder != null ? bidOrder.getSize().toString() : "0";
        }
    }
}
