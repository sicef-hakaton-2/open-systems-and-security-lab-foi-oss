package party.sicef.borderless.api.data;

import com.google.gson.annotations.Expose;

/**
 * Created by ahuskano on 15.11.2015..
 */
public class Config {

    @Expose
    private Category[] post_categories;

    public Category[] getPost_categories() {
        return post_categories;
    }

    public void setPost_categories(Category[] post_categories) {
        this.post_categories = post_categories;
    }
}
