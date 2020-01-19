DROP TABLE IF EXISTS product;

CREATE TABLE product (
  id VARCHAR(255) PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  description VARCHAR(255),
  brand VARCHAR(255) NOT NULL,
  tags VARCHAR(255),
  category VARCHAR(255) NOT NULL,
  created_at TIMESTAMP NOT NULL
);

INSERT INTO product (id, name, description, brand, tags, category, created_at) VALUES ('b6afac37-cf9a-4fd4-8257-f096dbb5d34d','Red Shirt', 'Red hugo boss shirt', 'Hugo Boss', '"red,shirt,slim fit"', 'apparel', '2017-04-14T01:02:03Z');
INSERT INTO product (id, name, description, brand, tags, category, created_at) VALUES ('357cd2c8-6f69-4bea-a6fa-86e40af0d867','Blue Shirt', 'Blue hugo boss shirt', 'Hugo Boss', '"blue,shirt"', 'apparel', '2017-04-14T01:02:03Z');