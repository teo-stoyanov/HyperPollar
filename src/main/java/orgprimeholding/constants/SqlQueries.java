package orgprimeholding.constants;

import java.util.ArrayList;
import java.util.List;

public final class SqlQueries {
    private static List<String> allQueries = new ArrayList<>();

    private static String CREATE_COMPANY_TABLE = "CREATE TABLE IF NOT EXISTS `company` (\n" +
            "  `company_id` int(11) NOT NULL AUTO_INCREMENT,\n" +
            "  `name` varchar(100) DEFAULT NULL,\n" +
            "  `address` varchar(200) DEFAULT NULL,\n" +
            "  `uuid` varchar(50) DEFAULT NULL,\n" +
            "  PRIMARY KEY (`company_id`)\n" +
            ") ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";

    private static String CREATE_CARD_DETAILS_TABLE = "CREATE TABLE IF NOT EXISTS `card_details` (\n" +
            "  `card_id` int(11) NOT NULL AUTO_INCREMENT,\n" +
            "  `cardtype` varchar(50) DEFAULT NULL,\n" +
            "  `number` varchar(250) DEFAULT NULL,\n" +
            "  `contactless` tinyint(1) DEFAULT NULL,\n" +
            "  PRIMARY KEY (`card_id`)\n" +
            ") ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";

    private static String CREATE_CUSTOMER_TABLE = "CREATE TABLE IF NOT EXISTS `customer` (\n" +
            "  `customer_id` int(11) NOT NULL AUTO_INCREMENT,\n" +
            "  `name` varchar(50) DEFAULT NULL,\n" +
            "  `address` varchar(200) DEFAULT NULL,\n" +
            "  `uuid` varchar(50) DEFAULT NULL,\n" +
            "  PRIMARY KEY (`customer_id`)\n" +
            ") ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";

    private static String CREATE_INVOICE_TABLE = "CREATE TABLE IF NOT EXISTS `invoice` (\n" +
            "  `invoice_id` int(11) NOT NULL AUTO_INCREMENT,\n" +
            "  `total` decimal(10,2) DEFAULT NULL,\n" +
            "  `datetime` datetime DEFAULT NULL,\n" +
            "  `payment` varchar(50) DEFAULT NULL,\n" +
            "  `customer_id` int(11) DEFAULT NULL,\n" +
            "  `store_id` int(11) DEFAULT NULL,\n" +
            "  `card_id` int(11) DEFAULT NULL,\n" +
            "  PRIMARY KEY (`invoice_id`),\n" +
            "  KEY `fk_invoice_customer` (`customer_id`),\n" +
            "  KEY `fk_invoice_store` (`store_id`),\n" +
            "  KEY `fk_invoice_card` (`card_id`),\n" +
            "  CONSTRAINT `fk_invoice_card` FOREIGN KEY (`card_id`) REFERENCES `card_details` (`card_id`),\n" +
            "  CONSTRAINT `fk_invoice_customer` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`),\n" +
            "  CONSTRAINT `fk_invoice_store` FOREIGN KEY (`store_id`) REFERENCES `store` (`store_id`)\n" +
            ") ENGINE=InnoDB AUTO_INCREMENT=3675 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";

    private static String CREATE_TABLE_RECEIPT = "CREATE TABLE IF NOT EXISTS `receipt` (\n" +
            "  `receipt_id` int(11) NOT NULL AUTO_INCREMENT,\n" +
            "  `total` decimal(10,2) DEFAULT NULL,\n" +
            "  `datetime` datetime DEFAULT NULL,\n" +
            "  `payment` varchar(50) DEFAULT NULL,\n" +
            "  `card_id` int(11) DEFAULT NULL,\n" +
            "  `store_id` int(11) DEFAULT NULL,\n" +
            "  PRIMARY KEY (`receipt_id`),\n" +
            "  KEY `fk_receipt_card` (`card_id`),\n" +
            "  KEY `fk_receips_stores` (`store_id`),\n" +
            "  CONSTRAINT `fk_receips_stores` FOREIGN KEY (`store_id`) REFERENCES `store` (`store_id`),\n" +
            "  CONSTRAINT `fk_receipt_card` FOREIGN KEY (`card_id`) REFERENCES `card_details` (`card_id`)\n" +
            ") ENGINE=InnoDB AUTO_INCREMENT=6233 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";

    private static String CREATE_TABLE_STORE = "CREATE TABLE IF NOT EXISTS `store` (\n" +
            "  `store_id` int(11) NOT NULL AUTO_INCREMENT,\n" +
            "  `name` varchar(50) DEFAULT NULL,\n" +
            "  `address` varchar(200) DEFAULT NULL,\n" +
            "  `company_id` int(11) DEFAULT NULL,\n" +
            "  PRIMARY KEY (`store_id`),\n" +
            "  KEY `fk_store_company` (`company_id`),\n" +
            "  CONSTRAINT `fk_store_company` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`)\n" +
            ") ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";

    private static void updateList(){
        allQueries.add(CREATE_COMPANY_TABLE);
        allQueries.add(CREATE_CARD_DETAILS_TABLE);
        allQueries.add(CREATE_CUSTOMER_TABLE);
        allQueries.add(CREATE_TABLE_STORE);
        allQueries.add(CREATE_INVOICE_TABLE);
        allQueries.add(CREATE_TABLE_RECEIPT);
    }

    public static List<String> getAllQueries(){
        updateList();
        return allQueries;
    }
}
