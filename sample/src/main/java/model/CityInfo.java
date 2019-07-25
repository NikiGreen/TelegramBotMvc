package model;


import javax.persistence.*;

@Entity
public class CityInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String cityName;
/*    @Column(name="cityesAbout")*/
    private String infoAboutCity;

    public CityInfo() {

    }

    public CityInfo(String cityName, String infoAboutCity) {
        this.cityName = cityName;
        this.infoAboutCity = infoAboutCity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInfoAboutCityes() {
        return infoAboutCity;
    }

    public void setInfoAboutCityes(String infoAboutCityes) {
        this.infoAboutCity = infoAboutCityes;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

}
