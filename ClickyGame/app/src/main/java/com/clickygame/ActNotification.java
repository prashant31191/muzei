package com.clickygame;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.clickygame.db.DLocationModel;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;


public class ActNotification extends Activity {

    String TAG = "=ActNotification=";

    RecyclerView recyclerView;
    MaterialRefreshLayout materialRefreshLayout;
    NotificationAdapter notificationAdapter;
    TextView tvNodataTag;
    LinearLayout llNodataTag;


    String strFrom = "", strTitle = "Notifications";
    int page = 0;
    String strTotalResult = "0";
    public ArrayList<DLocationModel> arrayListAllDLocationModel;

    private Paint p = new Paint();
    Realm realm;


    @SuppressLint("InlinedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_common_list);

        try {


            getIntentData();
            initialization();
            setClickEvent();

            RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
            realm = Realm.getInstance(realmConfiguration);



            /*if (App.isInternetAvail(ActNotification.this))
            {

                page = 0;
                arrayListAllDLocationModel = new ArrayList<>();

               } else {
                App.showSnackBar(tvNodataTag, "We can't detect an internet connection. please check and try again.");
            }*/

            page = 0;
            arrayListAllDLocationModel = new ArrayList<>();
            getAllRecords();

        } catch (Exception e) {
            // TODO: handle exceptione.
            e.printStackTrace();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close(); // Remember to close Realm when done.
    }

    private void getAllRecords(){
        try{

            RealmResults<DLocationModel> arrDLocationModel = realm.where(DLocationModel.class).findAll();

            App.sLog("===arrDLocationModel=="+arrDLocationModel);

            List<DLocationModel> arraDLocationModel = arrDLocationModel;

            for(int k=0;k<arraDLocationModel.size();k++)
            {
                App.sLog(k+"===arraDLocationModel=="+arraDLocationModel.get(k).getImage_URL());
            }

            arrayListAllDLocationModel = new ArrayList<DLocationModel>(arraDLocationModel);



           // RealmQuery<DLocationModel> query = realm.where(DLocationModel.class);
            /*
            for (String id : ids) {
                query.or().equalTo("myField", id);
            }*/

/*
            RealmResults<DLocationModel> results = query.findAll();
            App.sLog("===results=="+results);
            */


            setViewData();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void initialization() {
        try {

            recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            tvNodataTag = (TextView) findViewById(R.id.tvNodataTag);
            llNodataTag = (LinearLayout) findViewById(R.id.llNodataTag);
            materialRefreshLayout = (MaterialRefreshLayout) findViewById(R.id.refresh);
            materialRefreshLayout.setIsOverLay(true);
            materialRefreshLayout.setWaveShow(true);
            materialRefreshLayout.setWaveColor(0x55ffffff);

            //tvNodataTag.setVisibility(View.GONE);
            llNodataTag.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            materialRefreshLayout.setLoadMore(false);


            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActNotification.this);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(true);


            initSwipe();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initSwipe() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT) {
                    notificationAdapter.removeItem(position);
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if (dX < 0) {


                        /*p.setColor(Color.RED);
                        c.drawRect(background,p);*/

                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        p.setColor(Color.GRAY);
                        p.setTextSize(35);
                        c.drawText("  Delete  ", background.centerX(), background.centerY(), p);
                        //versionViewHolder.tvName.setTypeface(App.getFont_Regular());

                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }


    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getString("title") != null && bundle.getString("title").length() > 0) {
                strTitle = bundle.getString("title");
            }
            if (bundle.getString("from") != null && bundle.getString("from").length() > 0) {
                strFrom = bundle.getString("from");
            }
        }

    }


    private void setClickEvent() {


        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                //refreshing...
                if (App.isInternetAvail(ActNotification.this)) {
                    //asyncGetDataList();

                    page = 0;
                    arrayListAllDLocationModel = new ArrayList<>();



                } else {

                    App.showSnackBar(tvNodataTag, ("We can't detect an internet connection. please check and try again."));

                }
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                try {


                    if (App.isInternetAvail(ActNotification.this)) {

                        if (arrayListAllDLocationModel != null && strTotalResult.equalsIgnoreCase("" + arrayListAllDLocationModel.size())) {
                            if (arrayListAllDLocationModel.size() >= 20) {
                                App.showSnackBar(tvNodataTag, "No more notification found.");
                            }
                            materialRefreshLayout.finishRefresh();
                            // load more refresh complete
                            materialRefreshLayout.finishRefreshLoadMore();
                        } else {
                            page = page + 1;

                        }
                    } else {
                        App.showSnackBar(tvNodataTag, ("We can't detect an internet connection. please check and try again."));


                        materialRefreshLayout.finishRefresh();
                        // load more refresh complete
                        materialRefreshLayout.finishRefreshLoadMore();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setViewData() {
        try {
            // refresh complete
            materialRefreshLayout.finishRefresh();
            // load more refresh complete
            materialRefreshLayout.finishRefreshLoadMore();


            if (arrayListAllDLocationModel != null && arrayListAllDLocationModel.size() > 0) {
                //arrayListAllDLocationModel.addAll(model.arrayListDLocationModel);


                if (page == 0) {
                    notificationAdapter = new NotificationAdapter(ActNotification.this, arrayListAllDLocationModel);
                    recyclerView.setAdapter(notificationAdapter);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setVisibility(View.VISIBLE);
                    //tvNodataTag.setVisibility(View.GONE);
                    llNodataTag.setVisibility(View.GONE);
                } else {
                    if (notificationAdapter != null) {
                        notificationAdapter.notifyDataSetChanged();
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.VersionViewHolder> {
        ArrayList<DLocationModel> mArrListDLocationModel;
        Context mContext;


        public NotificationAdapter(Context context, ArrayList<DLocationModel> arrayListFollowers) {
            mArrListDLocationModel = arrayListFollowers;
            mContext = context;
        }

        @Override
        public VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_notification, viewGroup, false);
            VersionViewHolder viewHolder = new VersionViewHolder(view);
            return viewHolder;
        }


        @Override
        public void onBindViewHolder(final VersionViewHolder versionViewHolder, final int i) {
            try {
                DLocationModel notificationListModel = mArrListDLocationModel.get(i);

                versionViewHolder.tvName.setTypeface(App.getFont_Regular());
                versionViewHolder.tvData.setTypeface(App.getFont_Regular());

                versionViewHolder.cardItemLayout.setCardBackgroundColor(App.getMatColor("500"));

                if (notificationListModel.getCountry() != null && notificationListModel.getRegion() != null && notificationListModel.getRegion().length() > 0) {
                    versionViewHolder.tvName.setText(Html.fromHtml("<b>" + notificationListModel.getCountry() + " # </b>" + notificationListModel.getRegion() ));
                    versionViewHolder.tvName.setTextColor(Color.parseColor("#111111"));
                    int color = versionViewHolder.cardItemLayout.getContext().getResources().getColor(R.color.clrCardbgUnRead);
                    //versionViewHolder.cardItemLayout.setCardBackgroundColor(color);
                } else {
                    versionViewHolder.tvName.setText(Html.fromHtml("<b>" + notificationListModel.getCountry() + "</b>"));
                    versionViewHolder.tvName.setTextColor(Color.parseColor("#111111"));

                    int color = versionViewHolder.cardItemLayout.getContext().getResources().getColor(R.color.clrCardbgRead);
                    //versionViewHolder.cardItemLayout.setCardBackgroundColor(color);
                   // versionViewHolder.rlMain.setAlpha(0.6f);
                }

                if (notificationListModel.getGoogle_Maps_URL() != null && notificationListModel.getGoogle_Maps_URL().length() > 1) {
                    versionViewHolder.tvData.setText(notificationListModel.getGoogle_Maps_URL());
                }

                if (notificationListModel.getImage_URL() != null && notificationListModel.getImage_URL().length() > 1) {
                    App.showLog("===111===="+notificationListModel.getImage_URL() );
                   /* Glide.with(getApplicationContext())
                            .load(notificationListModel.getImage_URL())
                            .placeholder(R.color.colorPrimaryDark)
                            .into(versionViewHolder.ivUserPhoto);*/

                    versionViewHolder.progressBar.setVisibility(View.VISIBLE);
                    //Glide.with(getApplicationContext()).load("http://"+notificationListModel.getImage_URL()).asGif().listener(new RequestListener<String, GifDrawable>() {

                    if(notificationListModel.getImage_URL().contains(".gif")) {
                        Glide.with(getApplicationContext()).load( "http://"+notificationListModel.getImage_URL()).asGif().listener(new RequestListener<String, GifDrawable>() {
                            //new function maybe
                       /* @Override
                        public boolean onDownload(Progress currentProgress) {
                            return false;
                        }*/


                            @Override
                            public boolean onException(Exception e, String model, Target<GifDrawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GifDrawable resource, String model, Target<GifDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {

                                versionViewHolder.progressBar.setVisibility(View.GONE);
                                return false;
                            }
                        }).into(versionViewHolder.ivUserPhoto);
                    }
                    else {

                        Glide.with(mContext)
                                .load( "http://"+notificationListModel.getImage_URL())
                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                .placeholder(R.color.colorPrimaryDark)
                                .dontAnimate()
                                .into(new GlideDrawableImageViewTarget(versionViewHolder.ivUserPhoto) {
                                    @Override
                                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                                        versionViewHolder.progressBar.setVisibility(View.GONE);
                                        super.onResourceReady(resource, animation);
                                        //never called
                                    }

                                    @Override
                                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                                        super.onLoadFailed(e, errorDrawable);
                                        //never called
                                    }
                                });
                    }
                }


                versionViewHolder.cardItemLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            App.showLog("===111=click==="+ mArrListDLocationModel.get(i).getImage_URL() );
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return mArrListDLocationModel.size();
        }


        public void removeItem(int position) {



                mArrListDLocationModel.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mArrListDLocationModel.size());
        }


        class VersionViewHolder extends RecyclerView.ViewHolder {
            CardView cardItemLayout;
            TextView tvName, tvData;
            ImageView ivUserPhoto;
            RelativeLayout rlMain;
            ProgressBar progressBar;


            public VersionViewHolder(View itemView) {
                super(itemView);

                cardItemLayout = (CardView) itemView.findViewById(R.id.cardlist_item);
                rlMain = (RelativeLayout) itemView.findViewById(R.id.rlMain);
                tvName = (TextView) itemView.findViewById(R.id.tvName);
                tvData = (TextView) itemView.findViewById(R.id.tvData);
                ivUserPhoto = (ImageView) itemView.findViewById(R.id.ivUserPhoto);
                progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);

            }

        }
    }


    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();

    }

}