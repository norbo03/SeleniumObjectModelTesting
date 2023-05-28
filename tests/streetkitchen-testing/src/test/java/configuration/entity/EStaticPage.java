package configuration.entity;

import util.Util;

import java.util.Collection;
import java.util.stream.Collectors;

public class EStaticPage {
    private String url;
    private String title;
    private Collection<String> keywords;

    public EStaticPage(String url, Collection<String> keywords) {
        this.url = url;
        this.keywords = keywords;
    }

    public EStaticPage() {
    }

    public void sanitizeKeywords() {
        this.keywords = this.keywords.stream().map(Util::removeHungarianChars).collect(Collectors.toList());
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Collection<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(Collection<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public String toString() {
        return "EStaticPage{" + "url='" + url + '\'' + ", title='" + title + '\'' + ", keywords=" + keywords + '}';
    }
}
