package com.example.tastybites;

public class Repo {

        private String id;
        private String  name;
        private String price;
        private String image;
        private String totalprice;

        private int quantity;

        public Repo(String id, String name, String price, String image, int quantity, String totalprice)  {
                this.id = id;
                this.name = name;
                this.price = price;
                this.image = image;
                this.quantity = quantity;
                this.totalprice = totalprice;

        }

        public String getId() {
                return id;
        }

        public String getTotalprice() {
                return totalprice;
        }

        public void setTotalprice(String totalprice) {
                this.totalprice = totalprice;
        }

        public void setId(String id) {
                this.id = id;
        }

        public String getName() {
                return name;
        }
        public void setName(String name) {
                this.name = name;
        }

        public String getPrice() {
                return price;
        }
        public void setPrice(String price) {
                this.price = price;
        }
        public String getImage() {
                return image;
        }
        public void setImage(String image) {
                this.image = image;
        }

        public int getQuantity() {
                return quantity;
        }
        public void setQuantity(int quantity) {
                this.quantity = quantity;
        }

        @Override
        public String toString() {
                return "Repo {" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        ", price='" + price + '\'' +
                        ", image='" + image + '\'' +
                        '}';

        }
}