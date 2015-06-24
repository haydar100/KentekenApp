package domain;

/**
 * Created by Haydar on 29-05-15.
 */
public class CarBase {

    private Car d;

    public Car getD() {
        return d;
    }

    public void setD(Car d) {
        this.d = d;
    }

    @Override
    public String toString() {
        return "ClassPojo [d = " + d + "]";
    }


}
