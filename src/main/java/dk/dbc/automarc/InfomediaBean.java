package dk.dbc.automarc;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import dk.dbc.automarc.dto.InfoMediaDTO;
import dk.dbc.jsonb.JSONBContext;
import dk.dbc.jsonb.JSONBException;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.GenericType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

@Stateless
public class InfomediaBean {
    private static final XLogger LOGGER = XLoggerFactory.getXLogger(InfomediaBean.class);

    private final JSONBContext jsonbContext = new JSONBContext();

    @Inject
    @ConfigProperty(name = "INFOMEDIA_FETCHER_URL", defaultValue = "INFOMEDIA_FETCHER_URL not set")
    private String INFOMEDIA_FETCHER_URL;

    public void getArticlesSinceMidnight() throws IOException, JSONBException {
        StringBuilder urlString = new StringBuilder();
        urlString.append(INFOMEDIA_FETCHER_URL);
        urlString.append("/api/articles/search");
        urlString.append("?search_from=");
        urlString.append("2018-11-14"); // TODO should be dynamic date

        URL url = new URL(urlString.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "text/plain");

        String data = "sourcecode:pol";
        OutputStream os = conn.getOutputStream();
        os.write(data.getBytes());
        os.flush();
        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));
        String output;
        StringBuilder infomediaData = new StringBuilder();
        LOGGER.info("Output from Server .... ");
        while ((output = br.readLine()) != null) {
            infomediaData.append(output);
        }
        LOGGER.info(infomediaData.toString());
        conn.disconnect();

        String blag = infomediaData.toString();

        //final InfoMediaDTO dto = jsonbContext.unmarshall(blag, new GenericType<List<InfoMediaDTO>>() );

        final CollectionType javaType = jsonbContext.getTypeFactory().constructCollectionType(List.class, InfoMediaDTO.class);
        final List<InfoMediaDTO> dto = jsonbContext.unmarshall(blag, javaType);

    }

}
