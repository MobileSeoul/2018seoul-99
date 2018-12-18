package com.example.jinyoungkim.teamgung.ui.gung_tour.learning_palace.fragments_learing;

import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.jinyoungkim.teamgung.R;
import com.example.jinyoungkim.teamgung.model.ShowPhotoGet;
import com.example.jinyoungkim.teamgung.network.NetworkService;
import com.example.jinyoungkim.teamgung.ui.gung_tour.learning_palace.fragments_learing.photo_dialog.PhotoDialog;
import com.example.jinyoungkim.teamgung.util.GlobalApplication;
import com.example.jinyoungkim.teamgung.util.SharePreferenceController;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LearningChanggyong extends Fragment {
    View view;
    Document doc;
    TextView explain_changgyeong;

    ImageView first;
    ImageView second;
    ImageView third;

    NetworkService networkService;

    String[] images;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_changgyong_learning,container,false);
        explain_changgyeong = (TextView)view.findViewById(R.id.explain_changgyeong);

        first = (ImageView)view.findViewById(R.id.first_img_changgyeong);
        second = (ImageView)view.findViewById(R.id.second_img_changgyeong);
        third = (ImageView)view.findViewById(R.id.third_img_changgyeong);

        networkService = GlobalApplication.getGlobalApplicationContext().getNetworkService();

        getPhoto();

        new GetXMLTask().execute();
        return view;
    }


    public void getPhoto(){
        Call<ShowPhotoGet>showPhotoGetCall = networkService.showPhoto(SharePreferenceController.getTokenHeader(getContext())
                ,2);
        showPhotoGetCall.enqueue(new Callback<ShowPhotoGet>() {
            @Override
            public void onResponse(Call<ShowPhotoGet> call, Response<ShowPhotoGet> response) {
                if(response.isSuccessful()){
                    images = response.body().result.images;


                    Glide.with(getContext())
                            .load(images[0])
                            .apply(new RequestOptions().centerCrop())
                            .into(first);
                    Glide.with(getContext())
                            .load(images[1])
                            .apply(new RequestOptions().centerCrop())
                            .into(second);
                    Glide.with(getContext())
                            .load(images[2])
                            .apply(new RequestOptions().centerCrop())
                            .into(third);

                    first.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            SharePreferenceController.setPhotoURL(getContext(),images[0]);
                            PhotoDialog dl1= new PhotoDialog(getContext());
                            dl1.show();
                        }
                    });

                    second.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            SharePreferenceController.setPhotoURL(getContext(),images[1]);
                            PhotoDialog dl2= new PhotoDialog(getContext());
                            dl2.show();

                        }
                    });

                    third.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            SharePreferenceController.setPhotoURL(getContext(),images[2]);
                            PhotoDialog dl3= new PhotoDialog(getContext());
                            dl3.show();

                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<ShowPhotoGet> call, Throwable t) {

            }
        });

    }
    //    공공 API 정보 가져오는 부분
    private class GetXMLTask extends AsyncTask<String, Void, Document> {
        @Override
        protected Document doInBackground(String... urls) {
            URL url;
            try {
                url = new URL("http://www.heritage.go.kr/heri/gungDetail/gogungListOpenApi.do");
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                doc = db.parse(new InputSource(url.openStream()));
                doc.getDocumentElement().normalize();

            } catch (Exception e) {
                Toast.makeText(view.getContext(), "Parsing Error", Toast.LENGTH_SHORT).show();
            }
            return doc;
        }

        @Override
        protected void onPostExecute(Document doc) {

            String s = "";
            NodeList nodeList = doc.getElementsByTagName("list");


            Node node = nodeList.item(0);
            Element fstElmnt = (Element) node;

            NodeList idx = fstElmnt.getElementsByTagName("explanation_kor");
            s =idx.item(0).getChildNodes().item(0).getNodeValue() +"\n";
            s=s.replace("<br />","");

            explain_changgyeong.setText(s);

            super.onPostExecute(doc);
        }
    }
}
