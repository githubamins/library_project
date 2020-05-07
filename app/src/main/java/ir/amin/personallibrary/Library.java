package ir.amin.personallibrary;

public class Library {
    private int id;
    private String bookName;
    private String author;
    private String translator;
    private String topic;
    private int cases;
    private String description;
    private int lendCheck;
    private String lendName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTranslator() {
        return translator;
    }

    public void setTranslator(String translator) {
        this.translator = translator;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getCases() {
        return cases;
    }

    public void setCases(int cases) {
        this.cases = cases;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLendCheck() {
        return lendCheck;
    }

    public void setLendCheck(int lendCheck) {
        this.lendCheck = lendCheck;
    }

    public String getLendName() {
        return lendName;
    }

    public void setLendName(String lendName) {
        this.lendName = lendName;
    }
}
