package joey.lee.org.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GoingElectricAPI {

    @GET("/chargepoints/?key=b1589c7c1140728fedf2649d5f4e82a2")
    Call<List<Post>> getPosts();
}
