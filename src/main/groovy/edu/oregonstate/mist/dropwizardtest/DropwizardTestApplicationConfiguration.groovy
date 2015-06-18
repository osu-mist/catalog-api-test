package edu.oregonstate.mist.dropwizardtest

import io.dropwizard.Configuration
import io.dropwizard.db.DataSourceFactory
import javax.validation.Valid
import javax.validation.constraints.NotNull
import com.fasterxml.jackson.annotation.JsonProperty

/* configuration class */
public class DropwizardTestApplicationConfiguration extends Configuration {

    /* create a managed, instrumented Hibernate SessionFactory and DBI instance
       per http://dropwizard.io/manual/hibernate.html#configuration
       and http://dropwizard.io/manual/jdbi.html#configuration */
    @Valid
    @NotNull
    @JsonProperty('database')
    private DataSourceFactory database = new DataSourceFactory()

    public DataSourceFactory getDataSourceFactory() {
        return database
    }
}
