package com.example.supplychainmanagement;

public class order {
    public static boolean orderProduct(int productId,String customerEmail){

        String query = String.format("INSERT INTO orders (quantity, customer_id, product_id) VALUES(1,(SELECT cid FROM customer WHERE email = '%s'),'%s')",customerEmail,productId);
                DatabaseConnection dbcon = new DatabaseConnection();
        System.out.println(dbcon.executeQuery(query));
        return true;
    }

    public static void main(String[] args) {
        order.orderProduct(5,"saiprasad@gmail.com");
    }
}
