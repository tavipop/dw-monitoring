package com.pw.dw;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.pw.dw.monitoring.DemoHealthCheck;
import com.pw.dw.monitoring.PendingJobsMetric;
import com.pw.dw.resources.PersonsResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class DemoApplication extends Application<AppConfiguration> {

    public static void main(String[] args) throws Exception {
        new DemoApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<AppConfiguration> bootstrap) {
    }

    @Override
    public void run(AppConfiguration configuration, Environment environment) {


        final MetricRegistry metricRegistry = environment.metrics();

        final DemoHealthCheck healthCheck = new DemoHealthCheck();
        environment.healthChecks().register("demo-check", healthCheck);

        final PendingJobsMetric pendingJobsMetric = new PendingJobsMetric();
        metricRegistry.register(MetricRegistry.name(PendingJobsMetric.class, "jobs", "size"), pendingJobsMetric);

        final String retrievedPersonsCounterName = MetricRegistry.name("api.persons", "retrieved");
        final Counter counter = metricRegistry.counter(retrievedPersonsCounterName);

        final Meter meter = metricRegistry.meter(MetricRegistry.name("api.persons", "invoked"));
        final Timer timer = metricRegistry.timer(MetricRegistry.name("api.persons", "timer", "get"));

        final PersonsResource resource = new PersonsResource(counter, meter, timer);

        //graphiteSetup(metricRegistry);

        environment.jersey().register(resource);
    }

    /*private void graphiteSetup(final MetricRegistry metricRegistry){
        final Graphite graphite = new Graphite(new InetSocketAddress("172.16.97.85", 3001));
        final GraphiteReporter reporter = GraphiteReporter.forRegistry(metricRegistry)
                .prefixedWith("com.pw.demo")
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .filter(MetricFilter.ALL)
                .build(graphite);
        reporter.start(1, TimeUnit.MINUTES);
    }*/

}
