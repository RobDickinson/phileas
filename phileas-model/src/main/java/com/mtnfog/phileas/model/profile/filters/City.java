package com.mtnfog.phileas.model.profile.filters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mtnfog.phileas.model.enums.SensitivityLevel;
import com.mtnfog.phileas.model.profile.filters.strategies.dynamic.CityFilterStrategy;

import java.util.List;

public class City {

    @SerializedName("cityFilterStrategies")
    @Expose
    private List<CityFilterStrategy> cityFilterStrategies;

    @SerializedName("sensitivity")
    @Expose
    private String sensitivity = SensitivityLevel.MEDIUM.getName();

    public List<CityFilterStrategy> getCityFilterStrategies() {
        return cityFilterStrategies;
    }

    public void setCityFilterStrategies(List<CityFilterStrategy> cityFilterStrategies) {
        this.cityFilterStrategies = cityFilterStrategies;
    }

    public SensitivityLevel getSensitivityLevel() {
        return SensitivityLevel.fromName(sensitivity);
    }

    public String getSensitivity() {
        return sensitivity;
    }

    public void setSensitivity(String sensitivity) {
        this.sensitivity = sensitivity;
    }

}