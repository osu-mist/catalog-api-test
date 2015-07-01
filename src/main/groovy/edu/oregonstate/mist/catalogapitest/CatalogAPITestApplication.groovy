package edu.oregonstate.mist.catalogapitest

import edu.oregonstate.mist.catalogapitest.auth.*
import edu.oregonstate.mist.catalogapitest.core.*
import edu.oregonstate.mist.catalogapitest.db.*
import edu.oregonstate.mist.catalogapitest.health.*
import edu.oregonstate.mist.catalogapitest.resources.*

import io.dropwizard.Application
import io.dropwizard.auth.basic.BasicAuthProvider
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment
import io.dropwizard.hibernate.HibernateBundle
import io.dropwizard.db.DataSourceFactory
import io.dropwizard.jdbi.DBIFactory
import org.skife.jdbi.v2.DBI

/* application class */
public class CatalogAPITestApplication extends Application<CatalogAPITestApplicationConfiguration> {

    /* create managed database connection pool
       per http://dropwizard.io/manual/hibernate.html#configuration */
    private final HibernateBundle<CatalogAPITestApplicationConfiguration> hibernate =
        new HibernateBundle<CatalogAPITestApplicationConfiguration>(Course) {
            @Override
            public DataSourceFactory getDataSourceFactory(CatalogAPITestApplicationConfiguration configuration) {
                return configuration.dataSourceFactory
            }
        }

    @Override
    public void initialize(Bootstrap<CatalogAPITestApplicationConfiguration> bootstrap) {
        bootstrap.addBundle(hibernate)
    }

    /* run method */
    @Override
    public void run(CatalogAPITestApplicationConfiguration configuration, Environment environment) {

        /* create new Hibernate SessionFactory instance */
        final CourseDAO edao = new CourseDAO(hibernate.sessionFactory)
        environment.jersey().register(new CourseResource(edao))

        /* create managed database connection pool and a new DBI instance
           per http://dropwizard.io/manual/jdbi.html#configuration */
        final DBIFactory factory = new DBIFactory()
        final DBI jdbi = factory.build(environment, configuration.dataSourceFactory, 'jdbi')
        final InstructorDAO idao = jdbi.onDemand(InstructorDAO)
        environment.jersey().register(new InstructorResource(idao))

        /* hard code root API URL */
        environment.jersey().setUrlPattern('/api/v1/*')

        /* enable HTTP basic authentication
           per http://dropwizard.io/manual/auth.html#basic-authentication */
        environment.jersey().register(new BasicAuthProvider<AuthenticatedUser>(new SimpleAuthenticator(),'CatalogAPITestApplication'))
    }

    /* main method */
    public static void main(String[] args) throws Exception {
        new CatalogAPITestApplication().run(args)
    }
}
