package dk.dbc.automarc.rest;

import dk.dbc.automarc.InfomediaBean;
import dk.dbc.jsonb.JSONBException;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Stateless
@Path("/api")
public class Trigger {
    private static final XLogger LOGGER = XLoggerFactory.getXLogger(Trigger.class);

    @EJB
    private InfomediaBean infomediaBean;

    @GET
    @Path("/trigger")
    public Response trigger() {
        try {
            infomediaBean.getArticlesSinceMidnight();
            return Response.status(Response.Status.OK).build();
        } catch (IOException | JSONBException e) {
            LOGGER.info(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

}
