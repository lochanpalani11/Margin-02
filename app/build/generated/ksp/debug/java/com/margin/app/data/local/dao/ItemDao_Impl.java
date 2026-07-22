package com.margin.app.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.margin.app.data.local.Converters;
import com.margin.app.data.local.entity.ItemEntity;
import com.margin.app.domain.model.ItemStatus;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ItemDao_Impl implements ItemDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ItemEntity> __insertionAdapterOfItemEntity;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<ItemEntity> __updateAdapterOfItemEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public ItemDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfItemEntity = new EntityInsertionAdapter<ItemEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `items` (`id`,`name`,`category`,`purchasePrice`,`purchaseDate`,`purchasePlatform`,`status`,`salePrice`,`saleDate`,`salePlatform`,`saleFees`,`notes`,`imageUri`,`haulId`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ItemEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getCategory());
        statement.bindDouble(4, entity.getPurchasePrice());
        statement.bindLong(5, entity.getPurchaseDate());
        statement.bindString(6, entity.getPurchasePlatform());
        final String _tmp = __converters.fromStatus(entity.getStatus());
        statement.bindString(7, _tmp);
        if (entity.getSalePrice() == null) {
          statement.bindNull(8);
        } else {
          statement.bindDouble(8, entity.getSalePrice());
        }
        if (entity.getSaleDate() == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, entity.getSaleDate());
        }
        if (entity.getSalePlatform() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getSalePlatform());
        }
        statement.bindDouble(11, entity.getSaleFees());
        statement.bindString(12, entity.getNotes());
        if (entity.getImageUri() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getImageUri());
        }
        if (entity.getHaulId() == null) {
          statement.bindNull(14);
        } else {
          statement.bindLong(14, entity.getHaulId());
        }
      }
    };
    this.__updateAdapterOfItemEntity = new EntityDeletionOrUpdateAdapter<ItemEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `items` SET `id` = ?,`name` = ?,`category` = ?,`purchasePrice` = ?,`purchaseDate` = ?,`purchasePlatform` = ?,`status` = ?,`salePrice` = ?,`saleDate` = ?,`salePlatform` = ?,`saleFees` = ?,`notes` = ?,`imageUri` = ?,`haulId` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ItemEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getCategory());
        statement.bindDouble(4, entity.getPurchasePrice());
        statement.bindLong(5, entity.getPurchaseDate());
        statement.bindString(6, entity.getPurchasePlatform());
        final String _tmp = __converters.fromStatus(entity.getStatus());
        statement.bindString(7, _tmp);
        if (entity.getSalePrice() == null) {
          statement.bindNull(8);
        } else {
          statement.bindDouble(8, entity.getSalePrice());
        }
        if (entity.getSaleDate() == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, entity.getSaleDate());
        }
        if (entity.getSalePlatform() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getSalePlatform());
        }
        statement.bindDouble(11, entity.getSaleFees());
        statement.bindString(12, entity.getNotes());
        if (entity.getImageUri() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getImageUri());
        }
        if (entity.getHaulId() == null) {
          statement.bindNull(14);
        } else {
          statement.bindLong(14, entity.getHaulId());
        }
        statement.bindLong(15, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM items WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM items";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final ItemEntity item, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfItemEntity.insertAndReturnId(item);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAll(final List<ItemEntity> items,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfItemEntity.insert(items);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final ItemEntity item, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfItemEntity.handle(item);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteById(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAll(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteAll.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<ItemEntity>> observeItems(final String query, final String status) {
    final String _sql = "\n"
            + "        SELECT * FROM items\n"
            + "        WHERE (? IS NULL OR status = ?)\n"
            + "        AND (? = '' OR name LIKE '%' || ? || '%' OR category LIKE '%' || ? || '%')\n"
            + "        ORDER BY\n"
            + "            CASE WHEN status = 'SOLD' THEN saleDate ELSE purchaseDate END DESC\n"
            + "        ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 5);
    int _argIndex = 1;
    if (status == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, status);
    }
    _argIndex = 2;
    if (status == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, status);
    }
    _argIndex = 3;
    _statement.bindString(_argIndex, query);
    _argIndex = 4;
    _statement.bindString(_argIndex, query);
    _argIndex = 5;
    _statement.bindString(_argIndex, query);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"items"}, new Callable<List<ItemEntity>>() {
      @Override
      @NonNull
      public List<ItemEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfPurchasePrice = CursorUtil.getColumnIndexOrThrow(_cursor, "purchasePrice");
          final int _cursorIndexOfPurchaseDate = CursorUtil.getColumnIndexOrThrow(_cursor, "purchaseDate");
          final int _cursorIndexOfPurchasePlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "purchasePlatform");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfSalePrice = CursorUtil.getColumnIndexOrThrow(_cursor, "salePrice");
          final int _cursorIndexOfSaleDate = CursorUtil.getColumnIndexOrThrow(_cursor, "saleDate");
          final int _cursorIndexOfSalePlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "salePlatform");
          final int _cursorIndexOfSaleFees = CursorUtil.getColumnIndexOrThrow(_cursor, "saleFees");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfImageUri = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUri");
          final int _cursorIndexOfHaulId = CursorUtil.getColumnIndexOrThrow(_cursor, "haulId");
          final List<ItemEntity> _result = new ArrayList<ItemEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ItemEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final double _tmpPurchasePrice;
            _tmpPurchasePrice = _cursor.getDouble(_cursorIndexOfPurchasePrice);
            final long _tmpPurchaseDate;
            _tmpPurchaseDate = _cursor.getLong(_cursorIndexOfPurchaseDate);
            final String _tmpPurchasePlatform;
            _tmpPurchasePlatform = _cursor.getString(_cursorIndexOfPurchasePlatform);
            final ItemStatus _tmpStatus;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __converters.toStatus(_tmp);
            final Double _tmpSalePrice;
            if (_cursor.isNull(_cursorIndexOfSalePrice)) {
              _tmpSalePrice = null;
            } else {
              _tmpSalePrice = _cursor.getDouble(_cursorIndexOfSalePrice);
            }
            final Long _tmpSaleDate;
            if (_cursor.isNull(_cursorIndexOfSaleDate)) {
              _tmpSaleDate = null;
            } else {
              _tmpSaleDate = _cursor.getLong(_cursorIndexOfSaleDate);
            }
            final String _tmpSalePlatform;
            if (_cursor.isNull(_cursorIndexOfSalePlatform)) {
              _tmpSalePlatform = null;
            } else {
              _tmpSalePlatform = _cursor.getString(_cursorIndexOfSalePlatform);
            }
            final double _tmpSaleFees;
            _tmpSaleFees = _cursor.getDouble(_cursorIndexOfSaleFees);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final String _tmpImageUri;
            if (_cursor.isNull(_cursorIndexOfImageUri)) {
              _tmpImageUri = null;
            } else {
              _tmpImageUri = _cursor.getString(_cursorIndexOfImageUri);
            }
            final Long _tmpHaulId;
            if (_cursor.isNull(_cursorIndexOfHaulId)) {
              _tmpHaulId = null;
            } else {
              _tmpHaulId = _cursor.getLong(_cursorIndexOfHaulId);
            }
            _item = new ItemEntity(_tmpId,_tmpName,_tmpCategory,_tmpPurchasePrice,_tmpPurchaseDate,_tmpPurchasePlatform,_tmpStatus,_tmpSalePrice,_tmpSaleDate,_tmpSalePlatform,_tmpSaleFees,_tmpNotes,_tmpImageUri,_tmpHaulId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<ItemEntity> observeItem(final long id) {
    final String _sql = "SELECT * FROM items WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"items"}, new Callable<ItemEntity>() {
      @Override
      @Nullable
      public ItemEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfPurchasePrice = CursorUtil.getColumnIndexOrThrow(_cursor, "purchasePrice");
          final int _cursorIndexOfPurchaseDate = CursorUtil.getColumnIndexOrThrow(_cursor, "purchaseDate");
          final int _cursorIndexOfPurchasePlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "purchasePlatform");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfSalePrice = CursorUtil.getColumnIndexOrThrow(_cursor, "salePrice");
          final int _cursorIndexOfSaleDate = CursorUtil.getColumnIndexOrThrow(_cursor, "saleDate");
          final int _cursorIndexOfSalePlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "salePlatform");
          final int _cursorIndexOfSaleFees = CursorUtil.getColumnIndexOrThrow(_cursor, "saleFees");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfImageUri = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUri");
          final int _cursorIndexOfHaulId = CursorUtil.getColumnIndexOrThrow(_cursor, "haulId");
          final ItemEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final double _tmpPurchasePrice;
            _tmpPurchasePrice = _cursor.getDouble(_cursorIndexOfPurchasePrice);
            final long _tmpPurchaseDate;
            _tmpPurchaseDate = _cursor.getLong(_cursorIndexOfPurchaseDate);
            final String _tmpPurchasePlatform;
            _tmpPurchasePlatform = _cursor.getString(_cursorIndexOfPurchasePlatform);
            final ItemStatus _tmpStatus;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __converters.toStatus(_tmp);
            final Double _tmpSalePrice;
            if (_cursor.isNull(_cursorIndexOfSalePrice)) {
              _tmpSalePrice = null;
            } else {
              _tmpSalePrice = _cursor.getDouble(_cursorIndexOfSalePrice);
            }
            final Long _tmpSaleDate;
            if (_cursor.isNull(_cursorIndexOfSaleDate)) {
              _tmpSaleDate = null;
            } else {
              _tmpSaleDate = _cursor.getLong(_cursorIndexOfSaleDate);
            }
            final String _tmpSalePlatform;
            if (_cursor.isNull(_cursorIndexOfSalePlatform)) {
              _tmpSalePlatform = null;
            } else {
              _tmpSalePlatform = _cursor.getString(_cursorIndexOfSalePlatform);
            }
            final double _tmpSaleFees;
            _tmpSaleFees = _cursor.getDouble(_cursorIndexOfSaleFees);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final String _tmpImageUri;
            if (_cursor.isNull(_cursorIndexOfImageUri)) {
              _tmpImageUri = null;
            } else {
              _tmpImageUri = _cursor.getString(_cursorIndexOfImageUri);
            }
            final Long _tmpHaulId;
            if (_cursor.isNull(_cursorIndexOfHaulId)) {
              _tmpHaulId = null;
            } else {
              _tmpHaulId = _cursor.getLong(_cursorIndexOfHaulId);
            }
            _result = new ItemEntity(_tmpId,_tmpName,_tmpCategory,_tmpPurchasePrice,_tmpPurchaseDate,_tmpPurchasePlatform,_tmpStatus,_tmpSalePrice,_tmpSaleDate,_tmpSalePlatform,_tmpSaleFees,_tmpNotes,_tmpImageUri,_tmpHaulId);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getItem(final long id, final Continuation<? super ItemEntity> $completion) {
    final String _sql = "SELECT * FROM items WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<ItemEntity>() {
      @Override
      @Nullable
      public ItemEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfPurchasePrice = CursorUtil.getColumnIndexOrThrow(_cursor, "purchasePrice");
          final int _cursorIndexOfPurchaseDate = CursorUtil.getColumnIndexOrThrow(_cursor, "purchaseDate");
          final int _cursorIndexOfPurchasePlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "purchasePlatform");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfSalePrice = CursorUtil.getColumnIndexOrThrow(_cursor, "salePrice");
          final int _cursorIndexOfSaleDate = CursorUtil.getColumnIndexOrThrow(_cursor, "saleDate");
          final int _cursorIndexOfSalePlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "salePlatform");
          final int _cursorIndexOfSaleFees = CursorUtil.getColumnIndexOrThrow(_cursor, "saleFees");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfImageUri = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUri");
          final int _cursorIndexOfHaulId = CursorUtil.getColumnIndexOrThrow(_cursor, "haulId");
          final ItemEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final double _tmpPurchasePrice;
            _tmpPurchasePrice = _cursor.getDouble(_cursorIndexOfPurchasePrice);
            final long _tmpPurchaseDate;
            _tmpPurchaseDate = _cursor.getLong(_cursorIndexOfPurchaseDate);
            final String _tmpPurchasePlatform;
            _tmpPurchasePlatform = _cursor.getString(_cursorIndexOfPurchasePlatform);
            final ItemStatus _tmpStatus;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __converters.toStatus(_tmp);
            final Double _tmpSalePrice;
            if (_cursor.isNull(_cursorIndexOfSalePrice)) {
              _tmpSalePrice = null;
            } else {
              _tmpSalePrice = _cursor.getDouble(_cursorIndexOfSalePrice);
            }
            final Long _tmpSaleDate;
            if (_cursor.isNull(_cursorIndexOfSaleDate)) {
              _tmpSaleDate = null;
            } else {
              _tmpSaleDate = _cursor.getLong(_cursorIndexOfSaleDate);
            }
            final String _tmpSalePlatform;
            if (_cursor.isNull(_cursorIndexOfSalePlatform)) {
              _tmpSalePlatform = null;
            } else {
              _tmpSalePlatform = _cursor.getString(_cursorIndexOfSalePlatform);
            }
            final double _tmpSaleFees;
            _tmpSaleFees = _cursor.getDouble(_cursorIndexOfSaleFees);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final String _tmpImageUri;
            if (_cursor.isNull(_cursorIndexOfImageUri)) {
              _tmpImageUri = null;
            } else {
              _tmpImageUri = _cursor.getString(_cursorIndexOfImageUri);
            }
            final Long _tmpHaulId;
            if (_cursor.isNull(_cursorIndexOfHaulId)) {
              _tmpHaulId = null;
            } else {
              _tmpHaulId = _cursor.getLong(_cursorIndexOfHaulId);
            }
            _result = new ItemEntity(_tmpId,_tmpName,_tmpCategory,_tmpPurchasePrice,_tmpPurchaseDate,_tmpPurchasePlatform,_tmpStatus,_tmpSalePrice,_tmpSaleDate,_tmpSalePlatform,_tmpSaleFees,_tmpNotes,_tmpImageUri,_tmpHaulId);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAllItems(final Continuation<? super List<ItemEntity>> $completion) {
    final String _sql = "SELECT * FROM items ORDER BY purchaseDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ItemEntity>>() {
      @Override
      @NonNull
      public List<ItemEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfPurchasePrice = CursorUtil.getColumnIndexOrThrow(_cursor, "purchasePrice");
          final int _cursorIndexOfPurchaseDate = CursorUtil.getColumnIndexOrThrow(_cursor, "purchaseDate");
          final int _cursorIndexOfPurchasePlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "purchasePlatform");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfSalePrice = CursorUtil.getColumnIndexOrThrow(_cursor, "salePrice");
          final int _cursorIndexOfSaleDate = CursorUtil.getColumnIndexOrThrow(_cursor, "saleDate");
          final int _cursorIndexOfSalePlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "salePlatform");
          final int _cursorIndexOfSaleFees = CursorUtil.getColumnIndexOrThrow(_cursor, "saleFees");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfImageUri = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUri");
          final int _cursorIndexOfHaulId = CursorUtil.getColumnIndexOrThrow(_cursor, "haulId");
          final List<ItemEntity> _result = new ArrayList<ItemEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ItemEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final double _tmpPurchasePrice;
            _tmpPurchasePrice = _cursor.getDouble(_cursorIndexOfPurchasePrice);
            final long _tmpPurchaseDate;
            _tmpPurchaseDate = _cursor.getLong(_cursorIndexOfPurchaseDate);
            final String _tmpPurchasePlatform;
            _tmpPurchasePlatform = _cursor.getString(_cursorIndexOfPurchasePlatform);
            final ItemStatus _tmpStatus;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __converters.toStatus(_tmp);
            final Double _tmpSalePrice;
            if (_cursor.isNull(_cursorIndexOfSalePrice)) {
              _tmpSalePrice = null;
            } else {
              _tmpSalePrice = _cursor.getDouble(_cursorIndexOfSalePrice);
            }
            final Long _tmpSaleDate;
            if (_cursor.isNull(_cursorIndexOfSaleDate)) {
              _tmpSaleDate = null;
            } else {
              _tmpSaleDate = _cursor.getLong(_cursorIndexOfSaleDate);
            }
            final String _tmpSalePlatform;
            if (_cursor.isNull(_cursorIndexOfSalePlatform)) {
              _tmpSalePlatform = null;
            } else {
              _tmpSalePlatform = _cursor.getString(_cursorIndexOfSalePlatform);
            }
            final double _tmpSaleFees;
            _tmpSaleFees = _cursor.getDouble(_cursorIndexOfSaleFees);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final String _tmpImageUri;
            if (_cursor.isNull(_cursorIndexOfImageUri)) {
              _tmpImageUri = null;
            } else {
              _tmpImageUri = _cursor.getString(_cursorIndexOfImageUri);
            }
            final Long _tmpHaulId;
            if (_cursor.isNull(_cursorIndexOfHaulId)) {
              _tmpHaulId = null;
            } else {
              _tmpHaulId = _cursor.getLong(_cursorIndexOfHaulId);
            }
            _item = new ItemEntity(_tmpId,_tmpName,_tmpCategory,_tmpPurchasePrice,_tmpPurchaseDate,_tmpPurchasePlatform,_tmpStatus,_tmpSalePrice,_tmpSaleDate,_tmpSalePlatform,_tmpSaleFees,_tmpNotes,_tmpImageUri,_tmpHaulId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<String>> observeCategories() {
    final String _sql = "SELECT DISTINCT category FROM items ORDER BY category ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"items"}, new Callable<List<String>>() {
      @Override
      @NonNull
      public List<String> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final List<String> _result = new ArrayList<String>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final String _item;
            _item = _cursor.getString(0);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<DashboardRow> observeDashboardStats() {
    final String _sql = "\n"
            + "        SELECT\n"
            + "            COUNT(*) AS totalItems,\n"
            + "            SUM(CASE WHEN status = 'IN_STOCK' THEN 1 ELSE 0 END) AS inStockCount,\n"
            + "            SUM(CASE WHEN status = 'LISTED' THEN 1 ELSE 0 END) AS listedCount,\n"
            + "            SUM(CASE WHEN status = 'SOLD' THEN 1 ELSE 0 END) AS soldCount,\n"
            + "            COALESCE(SUM(CASE WHEN status != 'SOLD' THEN purchasePrice ELSE 0 END), 0.0) AS totalInvested,\n"
            + "            COALESCE(SUM(CASE WHEN status = 'SOLD' THEN salePrice ELSE 0 END), 0.0) AS totalRevenue,\n"
            + "            COALESCE(SUM(CASE WHEN status = 'SOLD' THEN (salePrice - purchasePrice - saleFees) ELSE 0 END), 0.0) AS totalProfit,\n"
            + "            COALESCE(AVG(CASE WHEN status = 'SOLD' THEN (saleDate - purchaseDate) / 86400000.0 ELSE NULL END), 0.0) AS averageDaysToSell\n"
            + "        FROM items\n"
            + "        ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"items"}, new Callable<DashboardRow>() {
      @Override
      @NonNull
      public DashboardRow call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfTotalItems = 0;
          final int _cursorIndexOfInStockCount = 1;
          final int _cursorIndexOfListedCount = 2;
          final int _cursorIndexOfSoldCount = 3;
          final int _cursorIndexOfTotalInvested = 4;
          final int _cursorIndexOfTotalRevenue = 5;
          final int _cursorIndexOfTotalProfit = 6;
          final int _cursorIndexOfAverageDaysToSell = 7;
          final DashboardRow _result;
          if (_cursor.moveToFirst()) {
            final int _tmpTotalItems;
            _tmpTotalItems = _cursor.getInt(_cursorIndexOfTotalItems);
            final int _tmpInStockCount;
            _tmpInStockCount = _cursor.getInt(_cursorIndexOfInStockCount);
            final int _tmpListedCount;
            _tmpListedCount = _cursor.getInt(_cursorIndexOfListedCount);
            final int _tmpSoldCount;
            _tmpSoldCount = _cursor.getInt(_cursorIndexOfSoldCount);
            final double _tmpTotalInvested;
            _tmpTotalInvested = _cursor.getDouble(_cursorIndexOfTotalInvested);
            final double _tmpTotalRevenue;
            _tmpTotalRevenue = _cursor.getDouble(_cursorIndexOfTotalRevenue);
            final double _tmpTotalProfit;
            _tmpTotalProfit = _cursor.getDouble(_cursorIndexOfTotalProfit);
            final double _tmpAverageDaysToSell;
            _tmpAverageDaysToSell = _cursor.getDouble(_cursorIndexOfAverageDaysToSell);
            _result = new DashboardRow(_tmpTotalItems,_tmpInStockCount,_tmpListedCount,_tmpSoldCount,_tmpTotalInvested,_tmpTotalRevenue,_tmpTotalProfit,_tmpAverageDaysToSell);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<ProfitPointRow>> observeProfitOverTime(final long sinceMillis) {
    final String _sql = "\n"
            + "        SELECT\n"
            + "            strftime('%Y-%m', saleDate / 1000, 'unixepoch') AS periodLabel,\n"
            + "            CAST(strftime('%s', strftime('%Y-%m-01', saleDate / 1000, 'unixepoch')) AS INTEGER) * 1000 AS periodStartMillis,\n"
            + "            SUM(salePrice - purchasePrice - saleFees) AS profit\n"
            + "        FROM items\n"
            + "        WHERE status = 'SOLD' AND saleDate >= ?\n"
            + "        GROUP BY periodLabel\n"
            + "        ORDER BY periodStartMillis ASC\n"
            + "        ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sinceMillis);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"items"}, new Callable<List<ProfitPointRow>>() {
      @Override
      @NonNull
      public List<ProfitPointRow> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfPeriodLabel = 0;
          final int _cursorIndexOfPeriodStartMillis = 1;
          final int _cursorIndexOfProfit = 2;
          final List<ProfitPointRow> _result = new ArrayList<ProfitPointRow>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ProfitPointRow _item;
            final String _tmpPeriodLabel;
            _tmpPeriodLabel = _cursor.getString(_cursorIndexOfPeriodLabel);
            final long _tmpPeriodStartMillis;
            _tmpPeriodStartMillis = _cursor.getLong(_cursorIndexOfPeriodStartMillis);
            final double _tmpProfit;
            _tmpProfit = _cursor.getDouble(_cursorIndexOfProfit);
            _item = new ProfitPointRow(_tmpPeriodLabel,_tmpPeriodStartMillis,_tmpProfit);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<ProfitPointRow>> observeProfitByDay(final long sinceMillis) {
    final String _sql = "\n"
            + "        SELECT\n"
            + "            strftime('%Y-%m-%d', saleDate / 1000, 'unixepoch') AS periodLabel,\n"
            + "            CAST(strftime('%s', strftime('%Y-%m-%d', saleDate / 1000, 'unixepoch')) AS INTEGER) * 1000 AS periodStartMillis,\n"
            + "            SUM(salePrice - purchasePrice - saleFees) AS profit\n"
            + "        FROM items\n"
            + "        WHERE status = 'SOLD' AND saleDate >= ?\n"
            + "        GROUP BY periodLabel\n"
            + "        ORDER BY periodStartMillis ASC\n"
            + "        ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sinceMillis);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"items"}, new Callable<List<ProfitPointRow>>() {
      @Override
      @NonNull
      public List<ProfitPointRow> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfPeriodLabel = 0;
          final int _cursorIndexOfPeriodStartMillis = 1;
          final int _cursorIndexOfProfit = 2;
          final List<ProfitPointRow> _result = new ArrayList<ProfitPointRow>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ProfitPointRow _item;
            final String _tmpPeriodLabel;
            _tmpPeriodLabel = _cursor.getString(_cursorIndexOfPeriodLabel);
            final long _tmpPeriodStartMillis;
            _tmpPeriodStartMillis = _cursor.getLong(_cursorIndexOfPeriodStartMillis);
            final double _tmpProfit;
            _tmpProfit = _cursor.getDouble(_cursorIndexOfProfit);
            _item = new ProfitPointRow(_tmpPeriodLabel,_tmpPeriodStartMillis,_tmpProfit);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<ProfitPointRow>> observeProfitByWeek(final long sinceMillis) {
    final String _sql = "\n"
            + "        SELECT\n"
            + "            strftime('%Y-%W', saleDate / 1000, 'unixepoch') AS periodLabel,\n"
            + "            CAST(strftime('%s', strftime('%Y-%m-%d', saleDate / 1000, 'unixepoch', 'weekday 1')) AS INTEGER) * 1000 AS periodStartMillis,\n"
            + "            SUM(salePrice - purchasePrice - saleFees) AS profit\n"
            + "        FROM items\n"
            + "        WHERE status = 'SOLD' AND saleDate >= ?\n"
            + "        GROUP BY periodLabel\n"
            + "        ORDER BY periodStartMillis ASC\n"
            + "        ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sinceMillis);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"items"}, new Callable<List<ProfitPointRow>>() {
      @Override
      @NonNull
      public List<ProfitPointRow> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfPeriodLabel = 0;
          final int _cursorIndexOfPeriodStartMillis = 1;
          final int _cursorIndexOfProfit = 2;
          final List<ProfitPointRow> _result = new ArrayList<ProfitPointRow>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ProfitPointRow _item;
            final String _tmpPeriodLabel;
            _tmpPeriodLabel = _cursor.getString(_cursorIndexOfPeriodLabel);
            final long _tmpPeriodStartMillis;
            _tmpPeriodStartMillis = _cursor.getLong(_cursorIndexOfPeriodStartMillis);
            final double _tmpProfit;
            _tmpProfit = _cursor.getDouble(_cursorIndexOfProfit);
            _item = new ProfitPointRow(_tmpPeriodLabel,_tmpPeriodStartMillis,_tmpProfit);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<CategoryBreakdownRow>> observeCategoryBreakdown() {
    final String _sql = "\n"
            + "        SELECT\n"
            + "            category,\n"
            + "            SUM(salePrice - purchasePrice - saleFees) AS totalProfit,\n"
            + "            COUNT(*) AS itemCount\n"
            + "        FROM items\n"
            + "        WHERE status = 'SOLD'\n"
            + "        GROUP BY category\n"
            + "        ORDER BY totalProfit DESC\n"
            + "        ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"items"}, new Callable<List<CategoryBreakdownRow>>() {
      @Override
      @NonNull
      public List<CategoryBreakdownRow> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfCategory = 0;
          final int _cursorIndexOfTotalProfit = 1;
          final int _cursorIndexOfItemCount = 2;
          final List<CategoryBreakdownRow> _result = new ArrayList<CategoryBreakdownRow>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CategoryBreakdownRow _item;
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final double _tmpTotalProfit;
            _tmpTotalProfit = _cursor.getDouble(_cursorIndexOfTotalProfit);
            final int _tmpItemCount;
            _tmpItemCount = _cursor.getInt(_cursorIndexOfItemCount);
            _item = new CategoryBreakdownRow(_tmpCategory,_tmpTotalProfit,_tmpItemCount);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
