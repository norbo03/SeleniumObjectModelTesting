package configuration;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import configuration.entity.ECredential;
import configuration.entity.EStaticPage;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Configuration {

    private static Configuration instance = null;
    private static Collection<EStaticPage> staticPages;
    private final ObjectMapper objectMapper;
    private ECredential credentials;

    private Configuration() {
        this.objectMapper = new ObjectMapper();
        readCredentials();
        readStaticPages();
        System.out.println("Configuration initialized");
    }

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }

        return instance;
    }

    public static Stream<EStaticPage> staticPages() {
        return staticPages.stream();
    }

    public ECredential getCredentials() {
        return credentials;
    }

    private void readCredentials() {
        try {
            URI credURI = Objects.requireNonNull(getClass().getResource("credentials.json")).toURI();
            this.credentials = objectMapper.readValue(new File(credURI), ECredential.class);
        } catch (StreamReadException e) {
            System.err.println("Could not read credentials.json file");
        } catch (DatabindException e) {
            System.err.println("Could not map credentials.json file to ECredential class");
        } catch (IOException e) {
            System.err.println("Could not find credentials.json file");
        } catch (URISyntaxException e) {
            System.err.println("Invalid URI");
        }
    }

    private void readStaticPages() {
        try {
            URL staticPagesUri = Objects.requireNonNull(getClass().getResource("staticPages.json"));
            staticPages = objectMapper.readValue(staticPagesUri, new TypeReference<List<EStaticPage>>() {
            });
        } catch (StreamReadException e) {
            System.err.println("Could not read staticPages.json file");
        } catch (DatabindException e) {
            System.err.println("Could not map staticPages.json file to EStaticPages class");
            e.printStackTrace(System.err);
        } catch (IOException e) {
            System.err.println("Could not find staticPages.json file");
        }
    }

    @Override
    public String toString() {
        return "Configuration{" + ", credentials=" + credentials + ", staticPages=" + staticPages + '}';
    }
}
