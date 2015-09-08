package edu.oregonstate.mist.catalogapitest

import io.dropwizard.Configuration
import io.dropwizard.db.DataSourceFactory

/**
 * Application configuration class.
 */
public class CatalogAPITestApplicationConfiguration extends Configuration {
    DataSourceFactory database = new DataSourceFactory()
}
