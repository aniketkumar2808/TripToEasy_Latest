package com.TripToEasy.model.parsingModel.landingPage;

public class TopAirlinesModel {

   public  TopAirlinesModel(String airline_name,String logo)
    {
        this.airline_name=airline_name;
        this.logo=logo;

    }

    private String airline_name;
        private String logo;

        public String getAirline_name() {
            return airline_name;
        }

        public void setAirline_name(String airline_name) {
            this.airline_name = airline_name;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

}
