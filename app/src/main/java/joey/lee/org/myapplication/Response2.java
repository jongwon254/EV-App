package joey.lee.org.myapplication;

import java.util.List;

public class Response2 {

    private List<Data> data;


    public Response2(List<Data> data) {
        this.data = data;

    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

}
