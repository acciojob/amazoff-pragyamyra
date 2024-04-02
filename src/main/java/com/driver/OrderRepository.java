package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {

    private HashMap<String, Order> orderMap;
    private HashMap<String, DeliveryPartner> partnerMap;
    private HashMap<String, HashSet<String>> partnerToOrderMap;
    private HashMap<String, String> orderToPartnerMap;

    public OrderRepository(){
        this.orderMap = new HashMap<String, Order>();
        this.partnerMap = new HashMap<String, DeliveryPartner>();
        this.partnerToOrderMap = new HashMap<String, HashSet<String>>();
        this.orderToPartnerMap = new HashMap<String, String>();
    }

    public void saveOrder(Order order){
        // your code here
        orderMap.put(order.getId(),order);
    }

    public void savePartner(String partnerId){
        // your code here
        // create a new partner with given partnerId and save it
        partnerMap.put(partnerId,new DeliveryPartner(partnerId));
    }

    public void saveOrderPartnerMap(String orderId, String partnerId){
        if(orderMap.containsKey(orderId) && partnerMap.containsKey(partnerId)){
            // your code here

            //add order to given partner's order list
            HashSet<String> currHashSet= new HashSet<>();
            if(partnerToOrderMap.containsKey(partnerId)) {
                currHashSet = partnerToOrderMap.get(partnerId);
            }
            currHashSet.add(orderId);
            partnerToOrderMap.put(partnerId,currHashSet);

            //increase order count of partner
            DeliveryPartner currDelivery =partnerMap.get(partnerId);
            int currOrder= currDelivery.getNumberOfOrders();
            currDelivery.setNumberOfOrders(currOrder+1);

            //assign partner to this order
            orderToPartnerMap.put(orderId,partnerId);

        }
    }

    public Order findOrderById(String orderId){
        // your code here
        return orderMap.get(orderId);
    }

    public DeliveryPartner findPartnerById(String partnerId){
        // your code here
        return partnerMap.get(partnerId);
    }

    public Integer findOrderCountByPartnerId(String partnerId){
        // your code here
        return partnerMap.get(partnerId).getNumberOfOrders();
    }

    public List<String> findOrdersByPartnerId(String partnerId){
        // your code here

        HashSet<String> orderSet = new HashSet<>();
        if(partnerToOrderMap.containsKey(partnerId)){
            orderSet=partnerToOrderMap.get(partnerId);
        }

        return new ArrayList<>(orderSet);

    }

    public List<String> findAllOrders(){
        // your code here
        // return list of all orders
        List<String> allOrderList = new ArrayList<>(orderMap.keySet());
        return allOrderList;
    }

    public void deletePartner(String partnerId){
        // your code here
        // delete partner by ID
        HashSet<String> orderAssign=new HashSet<>();
        if(partnerToOrderMap.containsKey(partnerId)){
            orderAssign=partnerToOrderMap.get(partnerId);
            for(String order: orderAssign){
                orderToPartnerMap.remove(order);
            }
            partnerToOrderMap.remove(partnerId);
        }

        if(partnerMap.containsKey(partnerId)){
            partnerMap.remove(partnerId);
        }
    }

    public void deleteOrder(String orderId){
        // your code here
        // delete order by ID
        if(orderToPartnerMap.containsKey(orderId)){
            String assignDeliveryPartner=orderToPartnerMap.get(orderId);
            orderToPartnerMap.remove(orderId);
            if(partnerToOrderMap.containsKey(assignDeliveryPartner)){
                HashSet<String> orderList= new HashSet<>(partnerToOrderMap.get(assignDeliveryPartner));
                orderList.remove(orderId);
                partnerToOrderMap.put(assignDeliveryPartner,orderList);
            }

        }
        if(orderMap.containsKey(orderId)){
            orderMap.remove(orderId);
        }
    }

    public Integer findCountOfUnassignedOrders(){
        // your code here
        return orderMap.size()-orderToPartnerMap.size();
    }

    public Integer findOrdersLeftAfterGivenTimeByPartnerId(String timeString, String partnerId){
        // your code here
    }

    public String findLastDeliveryTimeByPartnerId(String partnerId){
        // your code here
        // code should return string in format HH:MM
    }
}