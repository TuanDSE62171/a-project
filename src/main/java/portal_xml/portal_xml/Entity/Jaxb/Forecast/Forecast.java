package portal_xml.portal_xml.Entity.Jaxb.Forecast;

import portal_xml.portal_xml.Utility.Mergeable;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "forecast", propOrder = {
        "forecastDayOfWeek",
        "forecastDay",
        "forecastDescription",
        "forecastWind",
        "forecastTemp",
        "forecastIcon"
})
public class Forecast implements Mergeable<Forecast> {

    @XmlTransient
    private long id;
    @XmlTransient
    private String capitalIso2Code;
    @XmlTransient
    private Date forecastDate;

    @XmlElement(required = true)
    private Integer forecastDay;

    @XmlTransient
    private Integer forecastMonth;
    @XmlTransient
    private Integer forecastYear;

    @XmlElement(required = true)
    private String forecastDayOfWeek;
    @XmlElement(required = true)
    private String forecastDescription;
    @XmlElement(required = true)
    private String forecastWind;
    @XmlElement(required = true)
    private int forecastTemp;
    @XmlElement(required = true)
    private String forecastIcon;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "CapitalISO2Code")
    public String getCapitalIso2Code() {
        return capitalIso2Code;
    }

    public void setCapitalIso2Code(String capitalIso2Code) {
        this.capitalIso2Code = capitalIso2Code;
    }

    @Basic
    @Column(name = "ForecastDate")
    public Date getForecastDate() {
        return forecastDate;
    }

    public void setForecastDate(Date forecastDate) {
        this.forecastDate = forecastDate;
    }

    @Basic
    @Column(name = "ForecastDay")
    public Integer getForecastDay() {
        return forecastDay;
    }

    public void setForecastDay(Integer forecastDay) {
        this.forecastDay = forecastDay;
    }

    @Basic
    @Column(name = "ForecastMonth")
    public Integer getForecastMonth() {
        return forecastMonth;
    }

    public void setForecastMonth(Integer forecastMonth) {
        this.forecastMonth = forecastMonth;
    }

    @Basic
    @Column(name = "ForecastYear")
    public Integer getForecastYear() {
        return forecastYear;
    }

    public void setForecastYear(Integer forecastYear) {
        this.forecastYear = forecastYear;
    }

    @Basic
    @Column(name = "ForecastDayOfWeek")
    public String getForecastDayOfWeek() {
        return forecastDayOfWeek;
    }

    public void setForecastDayOfWeek(String forecastDayOfWeek) {
        this.forecastDayOfWeek = forecastDayOfWeek;
    }

    @Basic
    @Column(name = "ForecastDescription")
    public String getForecastDescription() {
        return forecastDescription;
    }

    public void setForecastDescription(String forecastDescription) {
        this.forecastDescription = forecastDescription;
    }

    @Basic
    @Column(name = "ForecastWind")
    public String getForecastWind() {
        return forecastWind;
    }

    public void setForecastWind(String forecastWind) {
        this.forecastWind = forecastWind;
    }

    @Basic
    @Column(name = "ForecastTemp")
    public int getForecastTemp() {
        return forecastTemp;
    }

    public void setForecastTemp(int forecastTemp) {
        this.forecastTemp = forecastTemp;
    }

    @Basic
    @Column(name = "ForecastIcon")
    public String getForecastIcon() {
        return forecastIcon;
    }

    public void setForecastIcon(String forecastIcon) {
        this.forecastIcon = forecastIcon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Forecast forecast = (Forecast) o;
        return id == forecast.id &&
                forecastTemp == forecast.forecastTemp &&
                Objects.equals(capitalIso2Code, forecast.capitalIso2Code) &&
                Objects.equals(forecastDate, forecast.forecastDate) &&
                Objects.equals(forecastDay, forecast.forecastDay) &&
                Objects.equals(forecastMonth, forecast.forecastMonth) &&
                Objects.equals(forecastYear, forecast.forecastYear) &&
                Objects.equals(forecastDayOfWeek, forecast.forecastDayOfWeek) &&
                Objects.equals(forecastDescription, forecast.forecastDescription) &&
                Objects.equals(forecastWind, forecast.forecastWind) &&
                Objects.equals(forecastIcon, forecast.forecastIcon);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, capitalIso2Code, forecastDate, forecastDay, forecastMonth, forecastYear, forecastDayOfWeek, forecastDescription, forecastWind, forecastTemp, forecastIcon);
    }

    @Override
    public void merge(Forecast newEntity) {
        this.forecastDescription = newEntity.forecastDescription;
        this.forecastWind = newEntity.forecastWind;
        this.forecastTemp = newEntity.forecastTemp;
        this.forecastIcon = newEntity.forecastIcon;
    }
}
