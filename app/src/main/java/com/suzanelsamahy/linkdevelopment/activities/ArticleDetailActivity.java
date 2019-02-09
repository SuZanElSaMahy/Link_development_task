package com.suzanelsamahy.linkdevelopment.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.suzanelsamahy.linkdevelopment.R;
import com.suzanelsamahy.linkdevelopment.utils.Utilities;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ArticleDetailActivity extends AppCompatActivity {

    @BindView(R.id.article_title)
    TextView titleTv;

    @BindView(R.id.article_subtitle)
    TextView subTitleTv;

    @BindView(R.id.date_tv)
    TextView dateTv;

    @BindView(R.id.article_body)
    TextView articleBody;

    @BindView(R.id.news_iv)
    ImageView newsIv;

    @BindView(R.id.article_layout)
    RelativeLayout articleLayout;

    private Unbinder unbinder;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        unbinder = ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Intent intent = getIntent();
        url = intent.getStringExtra(getString(R.string.url_str));

        titleTv.setText(intent.getStringExtra(getString(R.string.title_str)));
        subTitleTv.setText("By " + intent.getStringExtra(getString(R.string.subtitle_str)));
        articleBody.setText(intent.getStringExtra(getString(R.string.desc_str)));

        if (intent.getStringExtra(getString(R.string.image_str)) != null) {
            Glide.with(this).load(intent.getStringExtra(getString(R.string.image_str))).into(newsIv);
        } else {
            Glide.with(this).load(R.drawable.ic_placeholder).into(newsIv);
        }

        dateTv.setText(Utilities.parsePublishedDate(intent.getStringExtra(getString(R.string.date_str))));
    }


    @OnClick(R.id.go_to_url_tv)
    public void openWebsite() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
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
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


}
