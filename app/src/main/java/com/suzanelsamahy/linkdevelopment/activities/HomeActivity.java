package com.suzanelsamahy.linkdevelopment.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.suzanelsamahy.linkdevelopment.R;
import com.suzanelsamahy.linkdevelopment.adapters.ArticlesRecyclerAdapter;
import com.suzanelsamahy.linkdevelopment.models.Article;
import com.suzanelsamahy.linkdevelopment.models.NewsResponse;
import com.suzanelsamahy.linkdevelopment.presenter.INewsPresenter;
import com.suzanelsamahy.linkdevelopment.presenter.NewsPresenter;
import com.suzanelsamahy.linkdevelopment.utils.Constants;
import com.suzanelsamahy.linkdevelopment.view.INewsView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, INewsView {

    @BindView(R.id.locations_rv)
    RecyclerView recyclerView;


    private Unbinder unbinder;
    private ArticlesRecyclerAdapter recyclerAdapter;
    private INewsPresenter newsPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        unbinder = ButterKnife.bind(this);

        showProgressDialog();
        newsPresenter = new NewsPresenter(this);
        newsPresenter.getNewsData(Constants.API_KEY, Constants.SOURCE);
        initRecycler(new ArrayList<Article>());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initRecycler(final List<Article> news) {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerAdapter = new ArticlesRecyclerAdapter(news, this, new ArticlesRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Article pos) {
                Intent intent = new Intent(HomeActivity.this,ArticleDetailActivity.class);
                //intent.putExtra("article",pos);

                intent.putExtra("title",pos.getTitle());
                intent.putExtra("subtitle",pos.getAuthor());
                intent.putExtra("desc",pos.getDescription());
                intent.putExtra("date",pos.getPublishedAt());
                intent.putExtra("image",pos.getUrlToImage());
                intent.putExtra("url",pos.getUrl());

                startActivity(intent);
            }
        });
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_explore) {
          Toast.makeText(HomeActivity.this,"Explore",Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_gallery) {
            Toast.makeText(HomeActivity.this,"Gallery",Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_live) {
            Toast.makeText(HomeActivity.this,"Live Chat",Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_wishlist) {
            Toast.makeText(HomeActivity.this,"Wish List",Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_magazine) {
            Toast.makeText(HomeActivity.this,"E-Magazine",Toast.LENGTH_LONG).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onNewsDataRetrieved(NewsResponse news) {
        hideProgressDialog();
        if (news != null) {
            initRecycler(news.getArticles());
        }
    }

    @Override
    public void onNewsDataRetreieveError(String Message) {
        hideProgressDialog();
        Toast.makeText(HomeActivity.this, Message, Toast.LENGTH_LONG).show();
    }


    public ProgressDialog mProgressDialog;

    public void showProgressDialog() {
        ProgressBar progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyle);
        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), android.graphics.PorterDuff.Mode.SRC_IN);

        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mProgressDialog.setContentView(progressBar);
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        newsPresenter.onDestroy();
    }

}
