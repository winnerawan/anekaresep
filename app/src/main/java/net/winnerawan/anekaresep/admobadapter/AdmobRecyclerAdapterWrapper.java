/*
 *  Copyright 2015 Yahoo Inc. All rights reserved.
 * Copyright 2015 Clockbyte LLC. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.winnerawan.anekaresep.admobadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.ViewGroup;

import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.NativeAdView;
import com.google.android.gms.ads.formats.NativeAppInstallAd;
import com.google.android.gms.ads.formats.NativeAppInstallAdView;
import com.google.android.gms.ads.formats.NativeContentAd;
import com.google.android.gms.ads.formats.NativeContentAdView;

import java.util.EnumSet;

/**
 * Adapter that has common functionality for any adapters that need to show ads in-between
 * other data.
 */
public class AdmobRecyclerAdapterWrapper
        extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements AdmobFetcherBase.AdmobListener {

    private final String TAG = AdmobRecyclerAdapterWrapper.class.getCanonicalName();

    private RecyclerView.Adapter<RecyclerView.ViewHolder> mAdapter;

    public RecyclerView.Adapter<RecyclerView.ViewHolder> getAdapter() {
        return mAdapter;
    }

    public void setAdapter(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        mAdapter = adapter;
        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                notifyItemRangeChanged(positionStart, itemCount);
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                notifyItemRangeInserted(positionStart, itemCount);
            }

            @Override
            public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                for(int i = 0; i<itemCount; itemCount++)
                    notifyItemMoved(fromPosition+i, toPosition+i);
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                notifyItemRangeRemoved(positionStart, itemCount);
            }

        });
    }

    private AdmobFetcher adFetcher;
    private Context mContext;
    private AdmobAdapterCalculator AdapterCalculator = new AdmobAdapterCalculator();
    /*
    * Gets an object which incapsulates transformation of the source and ad blocks indices
    */
    public AdmobAdapterCalculator getAdapterCalculator(){return AdapterCalculator;}
    /*
* Injects an object which incapsulates transformation of the source and ad blocks indices. You could override calculations
* by inheritance of AdmobAdapterCalculator class
*/
    public void setAdapterCalculator(AdmobAdapterCalculator adapterCalculatordmob){AdapterCalculator = adapterCalculatordmob;}


    private static final int VIEW_TYPE_COUNT = 2;
    private static final int VIEW_TYPE_AD_CONTENT = 1;
    private static final int VIEW_TYPE_AD_INSTALL = 2;

    private final static int DEFAULT_NO_OF_DATA_BETWEEN_ADS = 10;
    private final static int DEFAULT_LIMIT_OF_ADS = 3;

    /*
   * Gets the number of your data items between ad blocks, by default it equals to 10.
   * You should set it according to the Admob's policies and rules which says not to
   * display more than one ad block at the visible part of the screen
   * so you should choose this parameter carefully and according to your item's height and screen resolution of a target devices
   */
    public int getNoOfDataBetweenAds() {
        return AdapterCalculator.getNoOfDataBetweenAds();
    }
    /*
    * Sets the number of your data items between ad blocks, by default it equals to 10.
    * You should set it according to the Admob's policies and rules which says not to
    * display more than one ad block at the visible part of the screen
    * so you should choose this parameter carefully and according to your item's height and screen resolution of a target devices
    */
    public void setNoOfDataBetweenAds(int mNoOfDataBetweenAds) {
        AdapterCalculator.setNoOfDataBetweenAds(mNoOfDataBetweenAds);
    }

    public int getFirstAdIndex() {
        return AdapterCalculator.getFirstAdIndex();
    }
    /*
    * Sets the first ad block index (zero-based) in the adapter, by default it equals to 0
    */
    public void setFirstAdIndex(int firstAdIndex) {
        AdapterCalculator.setFirstAdIndex(firstAdIndex);
    }

    /*
    * Gets the max count of ad blocks per dataset, by default it equals to 3 (according to the Admob's policies and rules)
    */
    public int getLimitOfAds() {
        return AdapterCalculator.getLimitOfAds();
    }

    /*
    * Sets the max count of ad blocks per dataset, by default it equals to 3 (according to the Admob's policies and rules)
    */
    public void setLimitOfAds(int mLimitOfAds) {
        AdapterCalculator.setLimitOfAds(mLimitOfAds);
    }

    private NativeAdLayoutContext mContentAdsLayoutContext;

    /*
    * Gets the context (the res layout id and a strategy of inflating and binding) for published content ads {@link https://support.google.com/admob/answer/6240809}
    */
    public NativeAdLayoutContext getContentAdsLayoutContext() {
        return mContentAdsLayoutContext;
    }

    /*
    * Sets the context (the res layout id and a strategy of inflating and binding) for published content ads {@link https://support.google.com/admob/answer/6240809}
    */
    public void setContentAdsLayoutContext(NativeAdLayoutContext mContentAdsLayoutContext) {
        this.mContentAdsLayoutContext = mContentAdsLayoutContext;
    }

    private NativeAdLayoutContext mInstallAdsLayoutContext;

    /*
    * Gets the context (the res layout id and a strategy of inflating and binding) for published install app ads {@link https://support.google.com/admob/answer/6240809}
    */
    public NativeAdLayoutContext getInstallAdsLayoutContext() {
        return mInstallAdsLayoutContext;
    }

    /*
    * Sets the context (the res layout id and a strategy of inflating and binding) for published install app ads {@link https://support.google.com/admob/answer/6240809}
    */
    public void setInstallAdsLayoutContext(NativeAdLayoutContext mInstallAdsLayoutContext) {
        this.mInstallAdsLayoutContext = mInstallAdsLayoutContext;
    }

    /**
     * Use this constructor for test purposes. if you are going to release the live version
     * please use the appropriate constructor
     * @see #AdmobRecyclerAdapterWrapper(Context, String)
     * @param testDevicesId sets a devices ID to test ads interaction.
     * You could pass null but it's better to set ids for all your test devices
     * including emulators. for emulator just use the
     * @see {AdRequest.DEVICE_ID_EMULATOR}
     */
    public AdmobRecyclerAdapterWrapper(Context context, String[] testDevicesId) {
        this(context, testDevicesId, EnumSet.allOf(EAdType.class));
    }
    /**
    * @param admobReleaseUnitId sets a release unit ID for admob banners.
    * If you are testing the ads please pass null
    * ID should be active, please check it in your Admob's account.
    * Be careful: don't set it or set to null if you still haven't deployed a Release.
    * Otherwise your Admob account could be banned
    */
    public AdmobRecyclerAdapterWrapper(Context context, String admobReleaseUnitId) {
        this(context, admobReleaseUnitId, EnumSet.allOf(EAdType.class));
    }

    /**
     * Use this constructor for test purposes. if you are going to release the live version
     * please use the appropriate constructor
     * @see #AdmobRecyclerAdapterWrapper(Context, String)
     * @param testDevicesId sets a devices ID to test ads interaction.
     * You could pass null but it's better to set ids for all your test devices
     * including emulators. for emulator just use the
     * @see {AdRequest.DEVICE_ID_EMULATOR}
    * @param adTypesToShow sets the types of ads to show in the list.
    * By default all types are loaded by wrapper.
    * i.e. pass EnumSet.of(EAdType.ADVANCED_INSTALLAPP) to show only install app ads
    */
    public AdmobRecyclerAdapterWrapper(Context context, String[] testDevicesId, EnumSet<EAdType> adTypesToShow) {
        init(context, null, testDevicesId, adTypesToShow);
    }

    /**
     * @param admobReleaseUnitId sets a release unit ID for admob banners.
     * If you are testing the ads please use constructor for tests
     * @see #AdmobRecyclerAdapterWrapper(Context, String[])
     * ID should be active, please check it in your Admob's account.
     * Be careful: don't set it or set to null if you still haven't deployed a Release.
     * Otherwise your Admob account could be banned
    * @param adTypesToShow sets the types of ads to show in the list.
    * By default all types are loaded by wrapper.
    * i.e. pass EnumSet.of(EAdType.ADVANCED_INSTALLAPP) to show only install app ads
    */
    public AdmobRecyclerAdapterWrapper(Context context, String admobReleaseUnitId, EnumSet<EAdType> adTypesToShow) {
        init(context, admobReleaseUnitId, null, adTypesToShow);
    }

    private void init(Context context, String admobReleaseUnitId, String[] testDevicesId, EnumSet<EAdType> adTypesToShow){
        setNoOfDataBetweenAds(DEFAULT_NO_OF_DATA_BETWEEN_ADS);
        setLimitOfAds(DEFAULT_LIMIT_OF_ADS);
        setContentAdsLayoutContext(ContentAdLayoutContext.getDefault());
        setInstallAdsLayoutContext(InstallAppAdLayoutContext.getDefault());
        mContext = context;

        adFetcher = new AdmobFetcher();
        if(!TextUtils.isEmpty(admobReleaseUnitId))
            adFetcher.setAdmobReleaseUnitId(admobReleaseUnitId);
        if(testDevicesId!=null)
            for (String testId: testDevicesId)
                adFetcher.addTestDeviceId(testId);
        adFetcher.setAdTypeToFetch(adTypesToShow == null || adTypesToShow.isEmpty()
                ?  EnumSet.allOf(EAdType.class): adTypesToShow);
        adFetcher.addListener(this);
        // Start prefetching ads
        adFetcher.prefetchAds(context.getApplicationContext());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder==null)
            return;

        switch (viewHolder.getItemViewType()) {
            case VIEW_TYPE_AD_INSTALL:
                NativeAppInstallAdView lvi1 = (NativeAppInstallAdView) viewHolder.itemView;
                NativeAppInstallAd ad1 = (NativeAppInstallAd) getItem(position);
                getInstallAdsLayoutContext().bind(lvi1, ad1);
                break;
            case VIEW_TYPE_AD_CONTENT:
                NativeContentAdView lvi2 = (NativeContentAdView) viewHolder.itemView;
                NativeContentAd ad2 = (NativeContentAd) getItem(position);
                getContentAdsLayoutContext().bind(lvi2, ad2);
                break;
            default:
                int origPos = AdapterCalculator.getOriginalContentPosition(position,
                        adFetcher.getFetchedAdsCount(), mAdapter.getItemCount());
                mAdapter.onBindViewHolder(viewHolder, origPos);
        }
    }

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_AD_INSTALL:
            case VIEW_TYPE_AD_CONTENT:
                return new ViewWrapper<NativeAdView>(onCreateItemView(parent, viewType));
            default:
                return mAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    private NativeAdView onCreateItemView(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_AD_INSTALL:
                return getInstallAdsLayoutContext().inflateView(parent);
            case VIEW_TYPE_AD_CONTENT:
                return getContentAdsLayoutContext().inflateView(parent);
            default:
                return null;
        }
    }

    /**
     * <p>Gets the count of all data, including interspersed ads.</p>
     * <p/>
     * <p>If data size is 10 and an ad is to be showed after every 5 items starting at the index 0, this method
     * will return 12.</p>
     *
     * @return the total number of items this adapter can show, including ads.
     * @see AdmobRecyclerAdapterWrapper#setNoOfDataBetweenAds(int)
     * @see AdmobRecyclerAdapterWrapper#getNoOfDataBetweenAds()
     */
    @Override
    public int getItemCount() {

        if (mAdapter != null) {
            /*
            No of currently fetched ads, as long as it isn't more than no of max ads that can
            fit dataset.
             */
            int noOfAds = AdapterCalculator.getAdsCountToPublish(adFetcher.getFetchedAdsCount(), mAdapter.getItemCount());
            return mAdapter.getItemCount() > 0 ? mAdapter.getItemCount() + noOfAds : 0;
        } else {
            return 0;
        }
    }

    /**
     * Gets the item in a given position in the dataset. If an ad is to be returned,
     * a {@link NativeAd} object is returned.
     *
     *
     * @return the object or ad contained in this adapter position
     */
    public Object getItem(int position) {

        if (AdapterCalculator.canShowAdAtPosition(position, adFetcher.getFetchedAdsCount())) {
            int adPos = AdapterCalculator.getAdIndex(position);
            return adFetcher.getAdForIndex(adPos);
        }
        else return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (AdapterCalculator.canShowAdAtPosition(position, adFetcher.getFetchedAdsCount())) {
            int adPos = AdapterCalculator.getAdIndex(position);
            NativeAd ad = adFetcher.getAdForIndex(adPos);
            return ad instanceof NativeAppInstallAd ? VIEW_TYPE_AD_INSTALL : VIEW_TYPE_AD_CONTENT;
        } else {
            int origPos = AdapterCalculator.getOriginalContentPosition(position,
                    adFetcher.getFetchedAdsCount(), mAdapter.getItemCount());
            return mAdapter.getItemViewType(origPos);
        }
    }

    /**
     * Destroys all currently fetched ads
     */
    public void destroyAds() {
        adFetcher.destroyAllAds();
    }

    /**
     * Clears all currently displaying ads to update them
     */
    public void requestUpdateAd() {
        adFetcher.clearMapAds();
    }

    @Override
    public void onAdChanged(int adIdx) {
        notifyDataSetChanged();
    }

    /**
     * Raised when the number of ads have changed. Adapters that implement this class
     * should notify their data views that the dataset has changed.
     */
    @Override
    public void onAdChanged() {
        notifyDataSetChanged();
    }

}
