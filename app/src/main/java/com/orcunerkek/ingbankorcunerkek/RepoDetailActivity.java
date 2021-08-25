package com.orcunerkek.ingbankorcunerkek;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.orcunerkek.ingbankorcunerkek.db.DB;
import com.orcunerkek.ingbankorcunerkek.model.DbModel;
import com.orcunerkek.ingbankorcunerkek.model.GithubModel;
import com.orcunerkek.ingbankorcunerkek.model.OwnerModel;

public class RepoDetailActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView imageView_avatar, imageView_star;
    TextView textView_owner, textView_stars_content, textView_open_issues_content;
    private DB db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_detail);
        toolbar = findViewById(R.id.toolbar);
        imageView_avatar = findViewById(R.id.imageView_avatar);
        textView_owner = findViewById(R.id.textView_owner);
        textView_stars_content = findViewById(R.id.textView_stars_content);
        textView_open_issues_content = findViewById(R.id.textView_open_issues_content);
        imageView_star = findViewById(R.id.imageView_star);

        db = new DB(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        GithubModel githubModel = getIntent().getExtras().getParcelable("githubModel");
        OwnerModel ownerModel = getIntent().getExtras().getParcelable("ownerModel");

        if(githubModel!=null){

            getSupportActionBar().setTitle(githubModel.getName());
            textView_owner.setText(ownerModel.getLogin());
            textView_stars_content.setText(String.valueOf(githubModel.getStargazers_count()));
            textView_open_issues_content.setText(String.valueOf(githubModel.getOpen_issues_count().toString()));

            Glide.with(this)
                    .load(ownerModel.getAvatar_url())
                    .fitCenter()
                    .into(imageView_avatar);

            if(githubModel.isStarFlag()){
                imageView_star.setBackgroundResource(R.drawable.ic_star);
            }
        }

        imageView_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!db.getRepoInfo(githubModel.getName()))
                    db.addRepo(ownerModel.getLogin(), githubModel.getName());
                else {
                    DbModel dbModel= new DbModel(0,ownerModel.getLogin(),githubModel.getName());
                    db.deleteRepoInfo(dbModel);
                }
                if(imageView_star.getBackground().getConstantState()== getResources().getDrawable(R.drawable.ic_star_unfilled).getConstantState()){
                    imageView_star.setBackgroundResource(R.drawable.ic_star);

                }
                else {
                    imageView_star.setBackgroundResource(R.drawable.ic_star_unfilled);

                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
      /*  Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);*/
    }

    @Override
    public boolean onSupportNavigateUp() {
      /*  Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);*/
        super.onBackPressed();
        return super.onSupportNavigateUp();
    }
}
