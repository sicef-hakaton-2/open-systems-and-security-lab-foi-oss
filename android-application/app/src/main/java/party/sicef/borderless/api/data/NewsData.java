package party.sicef.borderless.api.data;

import com.google.gson.annotations.Expose;

/**
 * Created by ahuskano on 15.11.2015..
 */
public class NewsData extends BaseData {

    @Expose
    private int id;

    @Expose
    private String title;

    @Expose
    private String text;

    @Expose
    private String image;

    public NewsData(int id, String title, String text, String image) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
