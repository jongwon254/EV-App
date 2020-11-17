package joey.lee.org.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class GoingElectric extends AppCompatActivity {

    private TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_going_electric);

        tv_result = findViewById(R.id.tv_result);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.goingelectric.de/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GoingElectricAPI goingElectricAPI = retrofit.create(GoingElectricAPI.class);

        Call<List<Post>> call = goingElectricAPI.getPosts();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                if(!response.isSuccessful()) {
                    tv_result.setText("Code: " + response.code());
                    return;
                }

                List<Post> posts = response.body();

                for(Post post : posts) {
                    String content = "";
                    content += "status: " + post.getStatus() + "\n";
                    content += "startkey: " + post.getStartkey() + "\n";
                    content += "chargelocations: " + post.getChargelocations();

                    tv_result.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                tv_result.setText(t.getMessage());
            }
        });


    }
}