package cm.clear.qmerchant.database.daos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cm.clear.qmerchant.database.entities.ServerInfo;

@Dao
public interface ServerInfoDao {
    @Query("SELECT * from serverinfo order by id DESC limit 1")
    ServerInfo getServerInfo();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(@Nullable ServerInfo serverInfo);

    @Insert
    long[] insert(@Nullable ServerInfo... serverInfo);

    @Insert
    long[] insert(@Nullable List<ServerInfo> serverInfo);

    @Update
    int update(@NonNull ServerInfo serverInfo);

    @Delete
    int delete(@NonNull ServerInfo serverInfo);

}
