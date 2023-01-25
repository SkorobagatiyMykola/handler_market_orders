package ua.skorobahatyi.utils;

import ua.skorobahatyi.model.Order;
import ua.skorobahatyi.model.OrderBook;
import ua.skorobahatyi.model.TypeOrder;

import java.io.PrintWriter;

public class Handler {

    private OrderBook orderBook;

    public OrderBook getOrderBook() {
        return orderBook;
    }

    public Handler() {
        orderBook = new OrderBook();
    }

    public Handler(OrderBook orderBook) {
        this.orderBook = orderBook;
    }

    public void handlerLine(String[] line, PrintWriter printWriter) throws Exception {
        if (line[0].equals("u")) {
            if (line.length != 4) {
                throw new Exception("A possible error in the line is an extra or missing comma");
            }
            updateLimitOrderBook(line);
        } else if (line[0].equals("q")) {
            printQuery(line, printWriter);
        } else if (line[0].equals("o")) {
            if (line.length != 3) {
                throw new Exception("A possible error in the line is an extra or missing comma");
            }
            doMarketOrder(line);
        } else if (line[0].startsWith("//")) {
            return; // todo This is comment in input.txt (my trick)
        } else {
            throw new Exception("Please check the first character, the correct values should be: \"u\",\"q\" or \"o\".");
        }
    }

    private void updateLimitOrderBook(String[] line) throws Exception {

        Integer price = Integer.valueOf(line[1]);
        Integer size = Integer.valueOf(line[2]);
        TypeOrder typeOrder = line[3].equals("ask") ? TypeOrder.ASK :
                line[3].equals("bid") ? TypeOrder.BID : TypeOrder.ERROR;

        if (typeOrder.equals(TypeOrder.ERROR)) {
            throw new Exception("Please check the type order, the correct values should be: \"ask\" or \"bid\".");
        }

        Order order = new Order(size, typeOrder);
        orderBook.updateOrderBook(price, order);
    }

    private void printQuery(String[] line, PrintWriter printWriter) throws Exception {
        if (line[1].equals("best_bid")) {
            if (line.length != 2) {
                throw new Exception("A possible error in the line is an extra or missing comma");
            }
            printWriter.println(orderBook.printBestBidPriceSize());
        } else if (line[1].equals("best_ask")) {
            if (line.length != 2) {
                throw new Exception("A possible error in the line is an extra or missing comma");
            }
            printWriter.println(orderBook.printBestAskPriceSize());
        } else if (line[1].equals("size")) {
            if (line.length != 3) {
                throw new Exception("A possible error in the line is an extra or missing comma");
            }
            int price = Integer.valueOf(line[2]);
            printWriter.println(orderBook.printSizeForPrice(price));
        } else {
            throw new Exception("Please check the request text, the correct values should be: \"best_bid\",\"best_ask\" or \"size\".");
        }
    }

    private void doMarketOrder(String[] line) throws Exception {
        int size = Integer.valueOf(line[2]);
        if (line[1].equals("buy")) {
            orderBook.removeAskOrders(size);
        } else if (line[1].equals("sell")) {
            orderBook.removeBidOrders(size);
        } else {
            throw new Exception("Please check the text of the market order, the correct values should be: \"buy\" or \"sell\".");
        }
    }
}
