package cjmazur.homework.cs383.superduperouterspaceinvaders;

import android.content.Context;
import android.content.SharedPreferences;

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

    public void setScores(int s1, int s2, int s3) {
        SharedPreferences sp = mContext.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(SCORE_1, s1);
        editor.putInt(SCORE_2, s2);
        editor.putInt(SCORE_3, s3);
        editor.apply();
    }

    public int getScore1() {
        SharedPreferences sp = mContext.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sp.getInt(SCORE_1, 0);
    }

    public int getScore2() {
        SharedPreferences sp = mContext.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sp.getInt(SCORE_2, 0);
    }

    public int getScore3() {
        SharedPreferences sp = mContext.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sp.getInt(SCORE_3, 0);
    }

}
