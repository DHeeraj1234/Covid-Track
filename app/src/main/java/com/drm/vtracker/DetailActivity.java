package com.drm.vtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

//import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    //private ImageView imageView;
    private TextView titleTExtView, detailTextView;
    private String detailString;

    private RecyclerView recyclerView;
    private Parse_Adapter adapter;
    private ArrayList<ParseItem> parseItems = new ArrayList<>();
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //imageView = findViewById(R.id.imageView);
        //titleTExtView = findViewById(R.id.textView);
        //detailTextView = findViewById(R.id.detailTextView);

        //titleTExtView.setText(getIntent().getStringExtra("title"));

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);

        adapter = new Parse_Adapter(parseItems, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        //MainActivity.Content content = new MainActivity.Content();
        //content.execute();


        //Picasso.get().load(getIntent().getStringExtra("image")).into(imageView);
        Content content = new Content();
        content.execute();
    }

    private class Content extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(DetailActivity.this, android.R.anim.fade_in));

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(DetailActivity.this, android.R.anim.fade_out));
            adapter.notifyDataSetChanged();

        }

        @Override
        protected Void doInBackground(Void... voids) {




            Log.d("b.................n",getIntent().getStringExtra("title"));

                if(getIntent().getStringExtra("title").equals("India")){
                   try{
                       int i1=0;
                    String baseUrl = "https://covid19stats.in/#india/";
                    Document doc = Jsoup.connect(baseUrl).get();
                       //Elements ro=doc.select("div.Home");
                       //Log.d("brandnew", String.valueOf(ro.size()));
                       Log.d("b-------------n","Working fine");
                    for(Element row:doc.select(
                               "table tr"
                       )){
                           Log.d("brandnew","brandnew-INDIA");
                           //Log.d("t",tick);
                           final String tick=row.select("th").text();
                        ParseItem p=new ParseItem();
                           if(tick.equals("Lakshadweep")){
                               break;
                           }
                           else {
                               final String tick1 = row.select("td:nth-of-type(3)").text();
                               final String tick2 = row.select("td:nth-of-type(1)").text();

                               p.setTitle(tick);
                               p.setDetailUrl(tick1);
                               p.setNewCases(tick2);
                               //parseItems.add(new ParseItem(tick, tick1));
                               if(i1!=0)
                                   parseItems.add(p);
                               i1++;
                           }
                            Log.d("tikindia",tick);
                           //if(tick!=null && tick1!=null&&tick2!=null) {

                           //}
                       }



                   } catch (IOException e) {
                       e.printStackTrace();
                   }
                }
                else if(getIntent().getStringExtra("title").equals("USA")){
                    try{
                        int i1=0;
                        Log.d("b-------------n","Working fine with USA");
                        String baseUrl = "https://www.worldometers.info/coronavirus/country/us/";
                        Document doc = Jsoup.connect(baseUrl).get();
                        for(Element row:doc.select(
                                "table.table tr"
                        )){
                            Log.d("here","here");
                            //Log.d("t",tick);
                            final String tick=row.select("td:nth-of-type(1)").text();
                            final String tick1=row.select("td:nth-of-type(2)").text();
                            final String tick2=row.select("td:nth-of-type(3)").text();
                            Log.d("t",tick);
                            //if(tick!=null && tick1!=null&&tick2!=null) {
                            ParseItem p=new ParseItem();
                            p.setTitle(tick);
                            p.setDetailUrl(tick1);
                            p.setNewCases(tick2);
                            //parseItems.add(new ParseItem(tick, tick1));
                            if(i1!=0)
                            parseItems.add(p);
                            i1++;
                            //}
                        }



                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                else{
                    ParseItem p=new ParseItem();
                    p.setTitle("No Details");
                    parseItems.add(p);
                }





            return null;
        }

    }
    private void CreateAlertDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Sorry, No Detailed Information available for the choosen country at this time.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(DetailActivity.this, "Thank you.",Toast.LENGTH_SHORT).show();
            }
        });
        builder.create();
        builder.show();
    }
}