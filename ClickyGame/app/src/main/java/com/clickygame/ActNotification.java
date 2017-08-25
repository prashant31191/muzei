package com.clickygame;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
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
import com.clickygame.utils.GridSpacingItemDecoration;
import com.clickygame.utils.RealmBackupRestore;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.OnBoomListener;

import java.util.ArrayList;
import java.util.List;


import google.ads.AdsDisplayUtil;
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;


public class ActNotification extends Activity {

    String TAG = "=ActNotification=";

    ImageView ivListGrid, ivBackup, ivRestore, ivDelete;
    LinearLayout llDashboard;
    RecyclerView recyclerView;
    int intScreenHeight = 500;

    MaterialRefreshLayout materialRefreshLayout;
    NotificationAdapter notificationAdapter;
    TextView tvNodataTag;
    LinearLayout llNodataTag;
    EditText etSearch;


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

    private void getAllRecords() {
        try {

            RealmResults<DLocationModel> arrDLocationModel = realm.where(DLocationModel.class).findAll();

            // and if you want to sort in descending order
            //arrDLocationModel = arrDLocationModel.sort("Country", Sort.DESCENDING);
            arrDLocationModel = arrDLocationModel.sort("Country", Sort.ASCENDING);

            App.sLog("===arrDLocationModel==" + arrDLocationModel);

            List<DLocationModel> arraDLocationModel = arrDLocationModel;

            for (int k = 0; k < arraDLocationModel.size(); k++) {
                App.sLog(k + "===arraDLocationModel==" + arraDLocationModel.get(k).getImage_URL());
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getAllSearchRecords(String strTextCountry) {
        try {

            //RealmResults<DLocationModel> arrDLocationModel = realm.where(DLocationModel.class).findAll();
            //RealmResults<DLocationModel> result = realm.where(DLocationModel.class).equalTo("Image_URL",mArrListDLocationModel.get(position).getImage_URL()).findAll();


            RealmResults<DLocationModel> arrDLocationModel = realm.where(DLocationModel.class)
                    .contains("Country", strTextCountry, Case.INSENSITIVE)
                    //.contains("Country", strTextCountry,false)
                    //.containsIgnoreCase("Country", strTextCountry)
                    .findAll();

            App.sLog("===arrDLocationModel==" + arrDLocationModel);

            List<DLocationModel> arraDLocationModel = arrDLocationModel;

            for (int k = 0; k < arraDLocationModel.size(); k++) {
                App.sLog(k + "===arraDLocationModel==" + arraDLocationModel.get(k).getImage_URL());
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initialization() {
        try {
            etSearch = (EditText) findViewById(R.id.etSearch);
            ivListGrid = (ImageView) findViewById(R.id.ivListGrid);

            ivBackup = (ImageView) findViewById(R.id.ivBackup);
            ivRestore = (ImageView) findViewById(R.id.ivRestore);
            ivDelete = (ImageView) findViewById(R.id.ivDelete);

            llDashboard = (LinearLayout) findViewById(R.id.llDashboard);
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

            llDashboard.post(new Runnable() {
                @Override
                public void run() {
                    //maybe also works height = ll.getLayoutParams().height;

                    intScreenHeight = llDashboard.getHeight();
                    App.showLog("===intScreenHeight=" + intScreenHeight);
                    intScreenHeight = (int) intScreenHeight / 3;
                    App.showLog("===intScreenHeight=" + intScreenHeight);
                    //intScreenHeight = intScreenHeight - ( (int) App.convertDpToPixel(7,ActDashboard.this)); // 50px;
                    intScreenHeight = intScreenHeight - 10; // 50px;
                }
            });

            ivListGrid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ivListGrid.isSelected() == true) {
                        ivListGrid.setSelected(false);
                    } else {
                        ivListGrid.setSelected(true);
                    }

                    setListGridview(ivListGrid.isSelected());
                }
            });

            ivBackup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    App.showLog("=======backup======");
                    RealmBackupRestore realmBackupRestore = new RealmBackupRestore(ActNotification.this);
                    realmBackupRestore.backup();
                }
            });
            ivRestore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    App.showLog("=======restore======");
                    RealmBackupRestore realmBackupRestore = new RealmBackupRestore(ActNotification.this);
                    realmBackupRestore.restore();
                }
            });

            ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    App.showLog("=======delete======");
                    RealmBackupRestore realmBackupRestore = new RealmBackupRestore(ActNotification.this);
                    realmBackupRestore.delete();
                }
            });

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
                page = 0;
                arrayListAllDLocationModel = new ArrayList<>();
                getAllRecords();
                AdsDisplayUtil.openBnrIntAdsScreen(getApplicationContext(),"","");


            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                try {


                    //sdasd
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getAllSearchRecords(s.toString());
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

                /*versionViewHolder.bmb1.clearBuilders();
                for (int i0 = 0; i0 < versionViewHolder.bmb1.getPiecePlaceEnum().pieceNumber(); i0++)
                    versionViewHolder.bmb1.addBuilder(BuilderManager.getSimpleCircleButtonBuilder());
*/
                versionViewHolder.bmb2.clearBuilders();

                for (int j = 0; j < versionViewHolder.bmb2.getPiecePlaceEnum().pieceNumber(); j++) {
                    versionViewHolder.bmb2.addBuilder((HamButton.Builder) getBuilderHamMenu().get(j));
                }


                if (notificationListModel.getIsFavorite() != null && notificationListModel.getIsFavorite().equalsIgnoreCase("1")) {
                    versionViewHolder.ivFav.setVisibility(View.VISIBLE);
                    versionViewHolder.ivFav.setSelected(true);
                    versionViewHolder.ivFav.setAlpha(1f);
                } else {
                    versionViewHolder.ivFav.setVisibility(View.VISIBLE);
                    versionViewHolder.ivFav.setSelected(false);
                    versionViewHolder.ivFav.setAlpha(0.5f);
                }

                versionViewHolder.cardItemLayout.setCardBackgroundColor(App.getMatColor("A100"));

                if (notificationListModel.getCountry() != null && notificationListModel.getRegion() != null && notificationListModel.getRegion().length() > 0) {
                    versionViewHolder.tvName.setText(Html.fromHtml("<b>" + notificationListModel.getCountry() + " # </b>" + notificationListModel.getRegion()));
                    versionViewHolder.tvName.setTextColor(Color.parseColor("#000000"));
                    //int color = versionViewHolder.cardItemLayout.getContext().getResources().getColor(R.color.clrCardbgUnRead);
                    //versionViewHolder.cardItemLayout.setCardBackgroundColor(color);
                } else {
                    versionViewHolder.tvName.setText(Html.fromHtml("<b>" + notificationListModel.getCountry() + "</b>"));
                    versionViewHolder.tvName.setTextColor(Color.parseColor("#111111"));

                    // int color = versionViewHolder.cardItemLayout.getContext().getResources().getColor(R.color.clrCardbgRead);
                    //versionViewHolder.cardItemLayout.setCardBackgroundColor(color);
                    // versionViewHolder.rlMain.setAlpha(0.6f);
                }

                if (notificationListModel.getGoogle_Maps_URL() != null && notificationListModel.getGoogle_Maps_URL().length() > 1) {
                    versionViewHolder.tvData.setText(notificationListModel.getGoogle_Maps_URL());
                }

                if (notificationListModel.getImage_URL() != null && notificationListModel.getImage_URL().length() > 1) {
                    App.showLog("===111====" + notificationListModel.getImage_URL());

                    versionViewHolder.progressBar.setVisibility(View.VISIBLE);

                    if (notificationListModel.getImage_URL().contains(".gif")) {
                        Glide.with(getApplicationContext()).load("http://" + notificationListModel.getImage_URL()).asGif().listener(new RequestListener<String, GifDrawable>() {
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
                    } else {

                        Glide.with(mContext)
                                .load("http://" + notificationListModel.getImage_URL())
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
                            App.showLog("===111=click===" + mArrListDLocationModel.get(i).getImage_URL());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                versionViewHolder.bmb2.setOnBoomListener(new OnBoomListener() {
                    @Override
                    public void onClicked(int iMenu, BoomButton boomButton) {

                        App.showLog("Item Clicked=" + i + "=Menu Clicked=" + iMenu);
                        String strImageUrl = "http://" + mArrListDLocationModel.get(i).getImage_URL();

                        if (iMenu == 0) {
                            Intent intDownloadSong = new Intent(ActNotification.this, DownloadImage.class);
                            intDownloadSong.putExtra("url", strImageUrl);
                            intDownloadSong.putExtra("img_id", strImageUrl);
                            intDownloadSong.putExtra("setwallpaper", "2");
                            startActivityForResult(intDownloadSong, 101);

                            AdsDisplayUtil.openBnrIntAdsScreen(getApplicationContext(),"","");

                        } else if (iMenu == 1) {
                            Intent intDownloadSong = new Intent(ActNotification.this, DownloadImage.class);
                            intDownloadSong.putExtra("url", strImageUrl);
                            intDownloadSong.putExtra("img_id", strImageUrl);
                            intDownloadSong.putExtra("setwallpaper", "1");
                            startActivityForResult(intDownloadSong, 101);

                            AdsDisplayUtil.openBnrIntAdsScreen(getApplicationContext(),"","");

                        } else {
                            realm.beginTransaction();
                            mArrListDLocationModel.get(i).isFavorite = "1";
                            realm.commitTransaction();

                            notificationAdapter.notifyDataSetChanged();


                            App.showSnackBar(recyclerView, "Added to Favorite -->" + i);
                            //updateNewCard(i);
                        }


                    }

                    @Override
                    public void onBackgroundClick() {

                    }

                    @Override
                    public void onBoomWillHide() {

                    }

                    @Override
                    public void onBoomDidHide() {

                    }

                    @Override
                    public void onBoomWillShow() {

                    }

                    @Override
                    public void onBoomDidShow() {

                    }
                });

                versionViewHolder.ivFav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        {
                            realm.beginTransaction();
                            if (mArrListDLocationModel.get(i).isFavorite.equalsIgnoreCase("1")) {
                                mArrListDLocationModel.get(i).isFavorite = "0";
                                App.showSnackBar(recyclerView, "Removed from Favorite");
                            } else {
                                mArrListDLocationModel.get(i).isFavorite = "1";
                                App.showSnackBar(recyclerView, "Added to Favorite");
                            }
                            realm.commitTransaction();
                            notificationAdapter.notifyDataSetChanged();
                        }
                    }
                });

                // Set the view to fade in
                // setFadeAnimation(versionViewHolder.itemView);
                setAnimation(versionViewHolder.itemView, i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * Here is the key method to apply the animation
         */
        // Allows to remember the last item shown on screen
        private int lastPosition = -1;

        private void setAnimation(View viewToAnimate, int position) {
            // If the bound view wasn't previously displayed on screen, it's animated
            if (position > lastPosition) {
                Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_up);
                viewToAnimate.startAnimation(animation);
                lastPosition = position;
            }
        }

        int FADE_DURATION = 300;

        private void setFadeAnimation(View view) {
            AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(FADE_DURATION);
            view.startAnimation(anim);
        }

        @Override
        public int getItemCount() {
            return mArrListDLocationModel.size();
        }


        public void updateNewCard(final int position) {


            // mArrListDLocationModel.get(position).isFavorite = "1";

            DLocationModel toEdit = realm.where(DLocationModel.class)
                    .equalTo("Image_URL", mArrListDLocationModel.get(position).getImage_URL()).findFirst();
            realm.beginTransaction();
            toEdit.setIsFavorite("1");
            realm.commitTransaction();

            // notificationAdapter.notifyDataSetChanged();
        }

        public void removeItem(final int position) {


            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<DLocationModel> result = realm.where(DLocationModel.class).equalTo("Image_URL", mArrListDLocationModel.get(position).getImage_URL()).findAll();
                    result.deleteAllFromRealm();
                }
            });

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

            //BoomMenuButton bmb1;
            BoomMenuButton bmb2;
            //BoomMenuButton bmb3;

            ImageView ivFav;


            public VersionViewHolder(View itemView) {
                super(itemView);

                cardItemLayout = (CardView) itemView.findViewById(R.id.cardlist_item);
                rlMain = (RelativeLayout) itemView.findViewById(R.id.rlMain);
                tvName = (TextView) itemView.findViewById(R.id.tvName);
                tvData = (TextView) itemView.findViewById(R.id.tvData);
                ivUserPhoto = (ImageView) itemView.findViewById(R.id.ivUserPhoto);
                ivFav = (ImageView) itemView.findViewById(R.id.ivFav);
                progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);

                //bmb1 = (BoomMenuButton) itemView.findViewById(R.id.bmb1);
                bmb2 = (BoomMenuButton) itemView.findViewById(R.id.bmb2);
                //bmb3 = (BoomMenuButton) itemView.findViewById(R.id.bmb3);

                tvName.setTypeface(App.getFont_Regular());
                tvData.setTypeface(App.getFont_Bold());

            }

        }
    }


    RecyclerViewGridAdapter recyclerViewGridAdapter;

    private void setListGridview(boolean isGridview) {
        try {

            if (isGridview == true) {
              /*  LinearLayoutManager lLayout;
                lLayout = new LinearLayoutManager(ActNotification.this);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(lLayout);*/

                recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
                int spacingInPixels = getResources().getDimensionPixelSize(R.dimen._1sdp);
                recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, spacingInPixels, true, 0));
                notificationAdapter = new NotificationAdapter(ActNotification.this, arrayListAllDLocationModel);
                recyclerView.setAdapter(notificationAdapter);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setVisibility(View.VISIBLE);
                llNodataTag.setVisibility(View.GONE);
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
                int spacingInPixels = getResources().getDimensionPixelSize(R.dimen._5sdp);
                recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, spacingInPixels, true, 0));
                notificationAdapter = new NotificationAdapter(ActNotification.this, arrayListAllDLocationModel);
                recyclerView.setAdapter(notificationAdapter);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setVisibility(View.VISIBLE);
                llNodataTag.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class RecyclerViewGridAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {

        private ArrayList<DLocationModel> itemList;
        private Context mContext;

        public RecyclerViewGridAdapter(Context context, ArrayList<DLocationModel> itemList) {
            this.itemList = itemList;
            this.mContext = context;
        }

        @Override
        public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_notification_grid, null);
            // or try
            // View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_dashboard, parent, false);
          /*int height = parent.getMeasuredHeight() / 4;
            layoutView.setMinimumHeight(height);
          */
            RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
            return rcv;
        }

        @Override
        public void onBindViewHolder(final RecyclerViewHolders holder, final int position) {
            holder.tvTitleName.setText(itemList.get(position).getCountry() + "#" + itemList.get(position).getRegion());
            if (itemList.get(position).getImage_URL() != null && itemList.get(position).getImage_URL().length() > 1) {
                App.showLog("===111====" + itemList.get(position).getImage_URL());

                holder.progressBar.setVisibility(View.VISIBLE);

                if (itemList.get(position).getImage_URL().contains(".gif")) {
                    Glide.with(getApplicationContext()).load("http://" + itemList.get(position).getImage_URL()).asGif().listener(new RequestListener<String, GifDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GifDrawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GifDrawable resource, String model, Target<GifDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            holder.progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    }).into(holder.ivUserPhoto);
                } else {

                    Glide.with(mContext)
                            .load("http://" + itemList.get(position).getImage_URL())
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .placeholder(R.color.colorPrimaryDark)
                            .dontAnimate()
                            .into(new GlideDrawableImageViewTarget(holder.ivUserPhoto) {
                                @Override
                                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                                    holder.progressBar.setVisibility(View.GONE);
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

            holder.cardlist_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    App.showLog("=====click grid===");
                }
            });
        }

        @Override
        public int getItemCount() {
            return this.itemList.size();
        }
    }

    class RecyclerViewHolders extends RecyclerView.ViewHolder {

        public TextView tvTitleName;
        public ImageView ivUserPhoto, ivFav;
        public LinearLayout llRawItem, llRawMain;
        CardView cardlist_item;
        ProgressBar progressBar;
        //int width = 100;

        public RecyclerViewHolders(View itemView) {
            super(itemView);
            try {
                //   itemView.setOnClickListener(this);
                cardlist_item = (CardView) itemView.findViewById(R.id.cardlist_item);
                tvTitleName = (TextView) itemView.findViewById(R.id.tvTitleName);
                llRawItem = (LinearLayout) itemView.findViewById(R.id.llRawItem);
                ivUserPhoto = (ImageView) itemView.findViewById(R.id.ivUserPhoto);
                ivFav = (ImageView) itemView.findViewById(R.id.ivFav);
                progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);


                //llRawMain = (LinearLayout) itemView.findViewById(R.id.llRawMain);
/*
                ViewTreeObserver vto = llRawItem.getViewTreeObserver();
                vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            llRawItem.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        } else {
                            llRawItem.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                        //width  = llRawItem.getMeasuredWidth();
                        //App.showLog("==111=width==="+width);
                        // int height = llRawItem.getMeasuredHeight();
                        //App.showLog("==111=height==="+height);
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) llRawItem.getLayoutParams();
                        //App.showLog("==222=width==="+width);
                        params.height = intScreenHeight; //llRawItem.getMeasuredWidth();
                        // App.showLog("==222=height==="+params.height);
                        llRawItem.setLayoutParams(params);
                    }
                });

                if(intScreenHeight > 50)
                {

                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) llRawItem.getLayoutParams();
                    //App.showLog("==222=width==="+width);
                    params.height = intScreenHeight; //llRawItem.getMeasuredWidth();
                    // App.showLog("==222=height==="+params.height);
                    llRawItem.setLayoutParams(params);
                }
                else
                {
                    intScreenHeight = 100;

                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) llRawItem.getLayoutParams();
                    //App.showLog("==222=width==="+width);
                    params.height = intScreenHeight; //llRawItem.getMeasuredWidth();
                    // App.showLog("==222=height==="+params.height);
                    llRawItem.setLayoutParams(params);
                }*/


                tvTitleName.setTypeface(App.getFont_Regular());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    private static int[] imageResources = new int[]{
            R.drawable.bat,
            R.drawable.bear,
            R.drawable.bee,
            R.drawable.butterfly,
            R.drawable.cat,
            R.drawable.deer,
            R.drawable.dolphin,
            R.drawable.eagle,
            R.drawable.horse,
            R.drawable.elephant,
            R.drawable.owl,
            R.drawable.peacock,
            R.drawable.pig,
            R.drawable.rat,
            R.drawable.snake,
            R.drawable.squirrel
    };

    private static int imageResourceIndex = 0;

    static int getImageResource() {
        if (imageResourceIndex >= imageResources.length) imageResourceIndex = 0;
        return imageResources[imageResourceIndex++];
    }


    private ArrayList getBuilderHamMenu() {
        ArrayList arrayList = new ArrayList();

        HamButton.Builder builder1 = new HamButton.Builder()
                .normalImageRes(R.drawable.butterfly)
                .normalTextRes(R.string.str_m1_t)
                .subNormalTextRes(R.string.str_m1_d);
               /* .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        // When the boom-button corresponding this builder is clicked.
                        App.showSnackBar(recyclerView, "Item Clicked=" + i+"=Menu Clicked=" + index);

                    }
                });*/

        HamButton.Builder builder2 = new HamButton.Builder()
                .normalImageRes(R.drawable.deer)
                .normalTextRes(R.string.str_m2_t)
                .subNormalTextRes(R.string.str_m2_d);
                /*.listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        // When the boom-button corresponding this builder is clicked.
                        App.showSnackBar(recyclerView, "Item Clicked=" + i+"=Menu Clicked=" + index);

                    }
                });
*/
        HamButton.Builder builder3 = new HamButton.Builder()
                .normalImageRes(R.drawable.bat)
                .normalTextRes(R.string.str_m3_t)
                .subNormalTextRes(R.string.str_m3_d);
                /*.listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        // When the boom-button corresponding this builder is clicked.
                        App.showSnackBar(recyclerView, "Item Clicked=" + i+"=Menu Clicked=" + index);

                    }
                });*/

        arrayList.add(builder1);
        arrayList.add(builder2);
        arrayList.add(builder3);

        return arrayList;
    }


    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();

    }

}