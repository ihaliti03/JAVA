/*

MySQL

CREATE DATABASE IF NOT EXISTS JobFinderJava;

TRUNCATE TABLE <table_name>;

CREATE TABLE `address` (
  `id` int NOT NULL,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `bid` (
  `id` int NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `work_id` int DEFAULT NULL,
  `worker_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK502yifauw1k9gljq9bi5eyp77` (`work_id`),
  KEY `FK2irkyepplf6f59v8dw9kefrfj` (`worker_id`),
  CONSTRAINT `FK2irkyepplf6f59v8dw9kefrfj` FOREIGN KEY (`worker_id`) REFERENCES `worker` (`id`),
  CONSTRAINT `FK502yifauw1k9gljq9bi5eyp77` FOREIGN KEY (`work_id`) REFERENCES `work` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user` (
  `id` int NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `address_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKddefmvbrws3hvl5t0hnnsv8ox` (`address_id`),
  CONSTRAINT `FKddefmvbrws3hvl5t0hnnsv8ox` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `work` (
  `id` int NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `available` bit(1) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `address_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `worker_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmdlb8y8c3vrxv4am77t68amwe` (`address_id`),
  KEY `FKcjdv2ub5hdscnv52kyg4hnwb9` (`user_id`),
  KEY `FK471hhpsjfffxrw2iittu93909` (`worker_id`),
  CONSTRAINT `FK471hhpsjfffxrw2iittu93909` FOREIGN KEY (`worker_id`) REFERENCES `worker` (`id`),
  CONSTRAINT `FKcjdv2ub5hdscnv52kyg4hnwb9` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKmdlb8y8c3vrxv4am77t68amwe` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `worker` (
  `id` int NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `address_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKm60iyfk5jne6qi8gwgp74e493` (`address_id`),
  CONSTRAINT `FKm60iyfk5jne6qi8gwgp74e493` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


//address
{
  "id": 1,
  "city": "Negotino-Poloshko",
  "country": "North Macedonia"
}
{
  "id": 2,
  "city": "Pirok",
  "country": "North Macedonia"
}
{
  "id": 3,
  "city": "Tetovo",
  "country": "North Macedonia"
}
{
  "id": 4,
  "city": "Skopje",
  "country": "North Macedonia"
}INSERT INTO address (id, city, country) VALUES (4, 'Skopje', 'North Macedonia');



//user
{
  "id": 1,          Aint working
  "username": "Inspire11",
  "address": {
    "id": 3,
    "city": "Tetovo",
    "country": "North Macedonia"
  }
}
{
  "id": 2,          Aint working
  "username": "HotelKey",
  "address": {
    "id": 3,
    "city": "Tetovo",
    "country": "North Macedonia"
  }
}
{
  "id": 3,          Aint working
  "username": "AdaptiveScale",
  "address": {
    "id": 3,
    "city": "Tetovo",
    "country": "North Macedonia"
  }
}
{
  "id": 4,          Aint working
  "username": "Bridge",
  "address": {
    "id": 3,
    "city": "Tetovo",
    "country": "North Macedonia"
  }
}


//worker
{
  "id": 1,
  "username": "ibrahim_worker",
  "address": {
    "id": 1,
    "city": "Negotino-Poloshko",
    "country": "North Macedonia"
  }
}
{
  "id": 2,
  "username": "Alban_worker",
  "address": {
    "city": "Pirok",
    "country": "North Macedonia"
  }
}
{
  "id": 3,
  "username": "Bekim_worker",
  "address": {
    "city": "Tetovo",
    "country": "North Macedonia"
  }
}
{
  "username": "Drin_worker",
  "address": {
    "city": "Tetovo",
    "country": "North Macedonia"
  }
}




//work
{
  "id": 1,
  "title": "DevOps",
  "description": "A combination of software development (dev) and operations (ops)",
  "available": 1,
  "address": {
    "city": "Tetovo",
    "country": "North Macedonia"
  },
  "user_id": 3,
  "worker_id": 1
}
{
  "title": "FrontEnd",
  "description": "Creating a user interface website or application",
  "available": 1,
  "address": {
    "city": "Tetovo",
    "country": "North Macedonia"
  },
  "user_id": 1,
  "worker_id": 2
}
{
  "title": "Data Engineer",
  "description": "Lead the design, development, and implementation of data pipelines and data warehouses",
  "available": 1,
  "address": {
    "city": "Tetovo",
    "country": "North Macedonia"
  },
  "user_id": 2,
  "worker_id": 3
}
{
  "title": "BackEnd",
  "description": "Creating, maintaining, testing, and debugging the entire back end of an application or system",
  "available": 1,
  "address": {
    "city": "Tetovo",
    "country": "North Macedonia"
  },
  "user_id": 4,
  "worker_id": 4
}




//bid
{/22/1
    "price": 1000.0,
  "created_at": "2025-02-01T14:00:00"
}
{/24/2
  "price": 1000.0,
  "created_at": "2025-02-01T09:00:00.000"
}
{/26/3
  "price": 1000.0,
  "created_at": "2025-01-01T11:00:00.000"
}
{/28/4
  "price": 1000.0,
  "created_at": "2025-01-01T17:00:00.000"
}






 */