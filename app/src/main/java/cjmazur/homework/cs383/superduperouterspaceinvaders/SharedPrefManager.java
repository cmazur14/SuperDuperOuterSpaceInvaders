package cjmazur.homework.cs383.superduperouterspaceinvaders;

import android.content.Context;

/**
 * Created by CJ on 5/9/2018.
 */

public class SharedPrefManager {
    private static final String SHARED_PREFERENCES_NAME = "space_shared_preferences";
    private static final String SCORE_1 = "score1";
    private static final String SCORE_2 = "score2";
    private static final String SCORE_3 = "score3";

    private static SharedPrefManager mInstance;
    private static Context mContext;

    //the following two methods are the singleton pattern for hte class
    private SharedPrefManager(Context context) {
        mContext = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null)
            mInstance = new SharedPrefManager(context);
        return mInstance;
    }

}
