package cm.clear.qmerchant.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import cm.clear.qmerchant.database.daos.ServerInfoDao;
import cm.clear.qmerchant.database.entities.ServerInfo;

@Database(entities = {ServerInfo.class}, version = 1)
public abstract class QDatabase extends RoomDatabase {
    private static Context context;
    private static QDatabase INSTANCE = null;

    public static QDatabase getInstance() {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, QDatabase.class, "merchant.db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries().build();
            //INSTANCE = Room.databaseBuilder(context, HelepDatabase.class, "helep.db").build();
        }
        return(INSTANCE);
    }

    public static void setContext(@NonNull Context ctx) {
        Log.d("J-Purple", "setContext() called with: ctx = [" + ctx + "]");
        context = ctx;
    }

    @NonNull
    public abstract ServerInfoDao ServerInfoDao();
}
