package com.mtnfog.phileas.model.metadata.zipcode;

import com.mtnfog.phileas.model.metadata.MetadataResponse;

public class ZipCodeMetadataResponse extends MetadataResponse {

    private int population;

    public ZipCodeMetadataResponse(int population) {
        this.population = population;
    }

    public int getPopulation() {
        return population;
    }

}
