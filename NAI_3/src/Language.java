import java.util.List;

public class Language {
    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public List<String> getText() {
        return text;
    }

    public void setText(List<String> text) {
        this.text = text;
    }

    private String languageName;
    private List<String> text;

    public Language(String languageName, List<String> text) {
        this.languageName = languageName;
        this.text = text;
    }



}
