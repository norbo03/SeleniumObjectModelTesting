package configuration;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import configuration.entity.Credential;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

public class Configuration {

    private static Configuration instance = null;
    private final ObjectMapper objectMapper;
    private Credential credentials;

    private Configuration() {
        this.objectMapper = new ObjectMapper();
        readCredentials();
    }

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }

        return instance;
    }

    public Credential getCredentials() {
        return credentials;
    }

    private void readCredentials() {
        try {
            URI credURI = Objects.requireNonNull(getClass().getResource("credentials.json")).toURI();
            this.credentials = objectMapper.readValue(new File(credURI), Credential.class);
        } catch (StreamReadException e) {
            System.err.println("Could not read credentials.json file");
        } catch (DatabindException e) {
            System.err.println("Could not map credentials.json file to Credential class");
        } catch (IOException e) {
            System.err.println("Could not find credentials.json file");
        } catch (URISyntaxException e) {
            System.err.println("Invalid URI");
        }
    }

}
