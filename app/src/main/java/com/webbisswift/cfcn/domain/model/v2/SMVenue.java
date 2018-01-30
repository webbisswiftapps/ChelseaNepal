package com.webbisswift.cfcn.domain.model.v2;

/**
 * Created by biswas on 29/01/2018.
 */

public class SMVenue {

    VenueData data;

    public SMVenue() {
    }

    public VenueData getData() {
        return data;
    }

    public void setData(VenueData data) {
        this.data = data;
    }

    public static  class VenueData{

        public VenueData() {
        }

        String address, city, image_path, name, surface;
        int id, capacity;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getImage_path() {
            return image_path;
        }

        public void setImage_path(String image_path) {
            this.image_path = image_path;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSurface() {
            return surface;
        }

        public void setSurface(String surface) {
            this.surface = surface;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCapacity() {
            return capacity;
        }

        public void setCapacity(int capacity) {
            this.capacity = capacity;
        }
    }
}
