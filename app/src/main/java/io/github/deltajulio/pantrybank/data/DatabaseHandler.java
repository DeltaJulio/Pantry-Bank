package io.github.deltajulio.pantrybank.data;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for dealing with the Firebase database
 */
public class DatabaseHandler
{
    private static final String TAG = "DatabaseHandler";

    // db name constants
    public static final String USER_PATH = "users";
    public static final String QUANTITY_TYPE = "quantityType";
    public static final String QUANTITY = "quantity";
    public static final String IS_PINNED = "isPinned";
    public static final String ITEMS = "items";
    public static final String CATEGORIES = "categories";

    private DatabaseReference databaseReference;
    private final String userId;

    public DatabaseHandler(String userId)
    {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        this.userId = userId;
    }

    public String GetUserId()
    {
        return userId;
    }

    public void AddItem(FoodItem foodItem)
    {
        String foodId = databaseReference
                .child(USER_PATH)
                .child(userId)
                .child(ITEMS)
                .push().getKey();
        
        foodItem.setFoodId(foodId);

        databaseReference
                .child(USER_PATH)
                .child(userId)
                .child(ITEMS)
                .child(foodId).setValue(foodItem);
    }

    public void DeleteItem(String foodId)
    {
        databaseReference
                .child(USER_PATH)
                .child(userId)
                .child(ITEMS)
                .child(foodId).removeValue();
    }

    public void UpdateQuantityType(String foodId, FoodItem.QuantityType quantityType)
    {
        databaseReference
                .child(USER_PATH)
                .child(userId)
                .child(ITEMS)
                .child(foodId)
                .child(QUANTITY_TYPE).setValue(quantityType);
    }

    private void UpdateQuantity(String foodId, String quantity)
    {
        databaseReference
                .child(USER_PATH)
                .child(userId)
                .child(ITEMS)
                .child(foodId)
                .child(QUANTITY).setValue(quantity);
    }

    public void UpdateQuantityLong(String foodId, long quantity)
    {
        UpdateQuantity(foodId, String.valueOf(quantity));
    }

    public void UpdateQuantityEnum(String foodId, FoodItem.QuantityApprox quantity)
    {
        UpdateQuantity(foodId, quantity.toString());
    }

    public void UpdateIsPinned(String foodId, boolean isPinned)
    {
        databaseReference
                .child(USER_PATH)
                .child(userId)
                .child(ITEMS)
                .child(foodId)
                .child(IS_PINNED).setValue(isPinned);
    }

    public DatabaseReference GetDatabaseReference() { return databaseReference; }
}
