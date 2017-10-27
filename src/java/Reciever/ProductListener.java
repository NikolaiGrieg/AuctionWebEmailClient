/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reciever;

import Entities.AuctionUser;
import Entities.Bid;
import Entities.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.*;  
public class ProductListener implements MessageListener {  
  
    /**
     * Parses products, TODO link to product
     * @param m 
     */
    @Override
    public void onMessage(Message m) { 
        if (m instanceof ObjectMessage) {
            ObjectMessage objectMessage = (ObjectMessage) m;
            Product p;
            try {
                p = (Product) objectMessage.getObject();
                //System.out.println("following product is received: " + p.getName());
                printWonMessage(p);
            } catch (JMSException ex) {
                Logger.getLogger(ProductListener.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
        else{
            System.out.println("Error recieving product:" + m.toString());
        }
    }

    private void printWonMessage(Product p) {
        AuctionUser winner = p.getHighestBidder();
        if (winner != null) {
            System.out.println("---- START EMAIL to customer " + winner.getName() + " ----");
            System.out.println("Dear " + winner.getName());
            System.out.println("Congratulations! You have won in bidding for product " + p.getName());
            System.out.println("You can access the product using the following link:");
            System.out.println("http://localhost:8080/AuctionWeb/faces/search?productID=" + p.getId()); // change for https?
            System.out.println("---- END EMAIL to customer " + winner.getName() + " ----");
        } else {
            System.out.println("Error processing bids");
        }
    }
    

}
