package com.drm.vtracker;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Parse_Adapter adapter;
    private ArrayList<ParseItem> parseItems = new ArrayList<>();
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        FloatingActionButton myFab = findViewById(R.id.myFa);
        myFab.setOnClickListener(v -> CreateAlertDialog());


        recyclerView.setHasFixedSize(true);

        adapter = new Parse_Adapter(parseItems, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        Content content = new Content();
        content.execute();
    }
    private void CreateAlertDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Hello There, Hope you are doing well.\n" +
                "Data from this application is used from Worldometers and an Indian Website(https://covid19stats.in/#india by Aravind NC.)\n"
                +"This app displays all the COVID cases aroud the world, along with state-wise details for USA and India only."
                +"This works fine, until there will be no changes from above mentioned websites.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Thank you.",Toast.LENGTH_SHORT).show();
            }
        });
        builder.create();
        builder.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_item, menu);

        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        // Get the search view and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) searchViewItem.getActionView();
        searchView.setQueryHint("Search...");
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); //Do not iconfy the widget; expand it by default

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                newText = newText.toLowerCase();
                ArrayList<ParseItem> newList = new ArrayList<>();
                for (ParseItem parseItem : parseItems) {
                    String title = parseItem.getTitle().toLowerCase();

                    // you can specify as many conditions as you like
                    if (title.contains(newText)) {
                        newList.add(parseItem);
                    }
                }
                // create method in adapter
                adapter.setFilter(newList);

                return true;
            }
        };

        searchView.setOnQueryTextListener(queryTextListener);

        return true;

    }

    private class Content extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_in));
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_out));
            adapter.notifyDataSetChanged();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                String url = "https://www.worldometers.info/coronavirus/";

                    int i1=0;
                    final Document document=Jsoup.connect(url).get();
                    for(Element row:document.select(
                            "table tr"
                    )){
                        Log.d("here","here");
                        //Log.d("t",tick);
                        final String tick=row.select("td:nth-of-type(1)").text();
                        if(tick.equals("Total:")){
                            break;
                        }
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
                        ++i1;
                        //}
                    }

                //Collections.sort(parseItems, new Comparator<ArrayList<ParseItem>>() {
                  //  @Override
                    //public int compare(ArrayList<ParseItem> o1, ArrayList<ParseItem> o2) {
                        //return o1.get(1)-(o2.get(1));
                    //}
                //});

                //for(int i=0; i < parseItems.size(); i++){
                  //  Collections.swap(parseItems, 2, parseItems.size()-1);
                    //int i2=parseItems.size()-1;
                    //ParseItem p=parseItems.get(i2);
                    //if(Double.parseDouble(parseItems.get(i2).getDetailUrl())>Double.parseDouble(parseItems.get(i).getDetailUrl())){
                      //  for(int j=parseItems.size()-2; j > i; j--){
                        //    int j1=j+1;
                          //  parseItems.set(j1, parseItems.get(j));

                            //String s=String.valueOf(parseItems.get(i).getTitle());

                            //for(int j=0;j<s.length();++j){
                            //Log.d("sss", s);
                        //}
                        //parseItems.set(i, p);
                        //break;
                    //}
                    //String s=String.valueOf(parseItems.get(i).getTitle());

                    //for(int j=0;j<s.length();++j){
                      // Log.d("sss", s);
                    //}

                //}

                //------------------------------------------------------------

            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }
    }
}
