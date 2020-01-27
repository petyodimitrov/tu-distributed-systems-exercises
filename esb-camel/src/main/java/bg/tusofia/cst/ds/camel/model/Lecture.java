package bg.tusofia.cst.ds.camel.model;

import java.util.Date;

public class Lecture {

    private String id;
    private String title;
    private int slideCount;
    private Date createdOn;

    public Lecture() {
    }

    public Lecture(String title, int slideCount, Date createdOn) {
        this.id = id;
        this.title = title;
        this.slideCount = slideCount;
        this.createdOn = createdOn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSlideCount() {
        return slideCount;
    }

    public void setSlideCount(int slideCount) {
        this.slideCount = slideCount;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }
}
