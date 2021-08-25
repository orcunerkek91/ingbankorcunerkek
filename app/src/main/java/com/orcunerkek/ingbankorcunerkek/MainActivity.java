package com.orcunerkek.ingbankorcunerkek;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.stetho.Stetho;
import com.orcunerkek.ingbankorcunerkek.adapter.RepoListAdapter;
import com.orcunerkek.ingbankorcunerkek.db.DB;
import com.orcunerkek.ingbankorcunerkek.model.GithubModel;
import com.orcunerkek.ingbankorcunerkek.viewmodel.GithubViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView_repo_list;
    private EditText editText_username;
    private Button button_search;

    private RepoListAdapter repoListAdapter;
    private LinearLayoutManager linearLayoutManager;
    private GithubViewModel githubViewModel;
    private DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        db = new DB(this);

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());

        recyclerView_repo_list = findViewById(R.id.recyclerView_repo_list);
        editText_username = findViewById(R.id.editText_username);
        button_search = findViewById(R.id.button_search);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        githubViewModel = new ViewModelProvider(this).get(GithubViewModel.class);

        linearLayoutManager =new LinearLayoutManager(this.getApplicationContext());

        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                githubViewModel.makeApiCall(editText_username.getText().toString());
            }
        });

        githubViewModel.getGithubListObserver().observe(this, new Observer<List<GithubModel>>() {
            @Override
            public void onChanged(List<GithubModel> githubModels) {
                if(githubModels != null) {
                    for(int i=0; i<githubModels.size(); i++){
                        boolean val = db.getRepoInfo(githubModels.get(i).getName());
                        if(val){
                            githubModels.get(i).setStarFlag(true);}
                        else {
                            githubModels.get(i).setStarFlag(false);
                        }
                    }



                    repoListAdapter = new RepoListAdapter(githubModels);
                    recyclerView_repo_list.setLayoutManager(linearLayoutManager);
                    recyclerView_repo_list.setAdapter(repoListAdapter);
                    repoListAdapter.notifyDataSetChanged();

                    repoListAdapter.setOnItemClickListener(new RepoListAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            Intent intent = new Intent(MainActivity.this, RepoDetailActivity.class);
                            intent.putExtra("githubModel", githubModels.get(position));
                            intent.putExtra("ownerModel", githubModels.get(position).getOwner());
                            startActivity(intent);
                        }
                    });
                }
            }
        });




    }
}