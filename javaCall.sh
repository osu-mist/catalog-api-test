#!/bin/bash
java -classpath bin/ojdbc6_g.jar:build/libs/catalog-api-test-all.jar edu.oregonstate.mist.catalogapitest.CatalogAPITestApplication server configuration.yaml
