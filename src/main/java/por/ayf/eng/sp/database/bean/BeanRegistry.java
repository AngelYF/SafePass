package por.ayf.eng.sp.database.bean;

/**
 * Object contains the data of a registry of database.
 *
 * @author: Ángel Yagüe Flor.
 * @version: 3.0.
 */

public class BeanRegistry {
    private String name;
    private String username;
    private String email;
    private String password;
    private String question;
    private String answerQuestion;
    private String dateCreate;
    private String dateUpdate;

    public BeanRegistry(String name, String username, String email, String password, String question, String answerQuestion, String dateCreate, String dateUpdate) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.question = question;
        this.answerQuestion = answerQuestion;
        this.dateCreate = dateCreate;
        this.dateUpdate = dateUpdate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswerQuestion() {
        return answerQuestion;
    }

    public void setAnswerQuestion(String answerQuestion) {
        this.answerQuestion = answerQuestion;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(String dateUpdate) {
        this.dateUpdate = dateUpdate;
    }
}
