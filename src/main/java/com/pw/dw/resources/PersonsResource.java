package com.pw.dw.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.Timer;
import com.codahale.metrics.annotation.Timed;
import com.pw.dw.generator.ModelGenerator;
import com.pw.dw.model.Person;


@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
public class PersonsResource {

    private ModelGenerator<Person> personGenerator = new ModelGenerator<Person>(Person.class);
    private Counter retrievedPersonsCounter;
    private Meter meter;
    private Timer timer;

    public PersonsResource(final Counter counter, final Meter meter, final Timer timer){
        this.retrievedPersonsCounter = counter;
        this.meter = meter;
        this.timer = timer;
    }

    @GET
    @Timed
    @Path("/persons/{id}")
    public Person getPerson(@PathParam("id") final int id) {
        final Timer.Context context = timer.time();

        retrievedPersonsCounter.inc();
        meter.mark();

        context.stop();

        final Person person = personGenerator.generate();

        return person;
    }
}