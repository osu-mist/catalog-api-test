package edu.oregonstate.mist.catalogapitest

import io.dropwizard.Configuration
import io.dropwizard.db.DataSourceFactory
import javax.validation.Valid
import javax.validation.constraints.NotNull
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Application configuration class.
 */
public class CatalogAPITestApplicationConfiguration extends Configuration {
    @Valid
    @NotNull
    @JsonProperty('database')
    private DataSourceFactory database = new DataSourceFactory()

    public DataSourceFactory getDataSourceFactory() {
        return database
    }
}
