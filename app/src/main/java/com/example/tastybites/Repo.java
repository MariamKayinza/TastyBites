package com.example.tastybites;

public class Repo {

        private String id;
        private String  name;
        private String price;
        private String image;

        public Repo(String id, String name, String price, String image) {
                this.id = id;
                this.name = name;
                this.price = price;
                this.image = image;

        }

        public String getId() {
                return id;
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