package configuration.entity;

import java.util.Collection;

public class EStaticPage {
    private String url;
    private Collection<String> keywords;

    public EStaticPage(String url, Collection<String> keywords) {
        this.url = url;
        this.keywords = keywords;
    }

    public EStaticPage() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Collection<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(Collection<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public String toString() {
        return "EStaticPage{" +
                "url='" + url + '\'' +
                ", keywords=" + keywords +
                '}';
    }
}
