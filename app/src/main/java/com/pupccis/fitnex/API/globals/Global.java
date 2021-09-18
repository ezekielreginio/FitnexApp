package com.pupccis.fitnex.API.globals;

import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.pupccis.fitnex.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Global{
    public HashMap<String, String> getTextViews(LinearLayout parent, Resources resources){
        HashMap<String, String> hashMap = new HashMap<>();

        for(int i=0; i<parent.getChildCount(); i++){
            TextInputLayout child = (TextInputLayout) parent.getChildAt(i);
            FrameLayout frameLayout = (FrameLayout) child.getChildAt(0);
            TextView textfield = (TextView) frameLayout.getChildAt(0);
            //getResources();
            Log.d("Test Field: ", ""+resources.getResourceEntryName(textfield.getId()));
            //hashMap.put(textfield.getTag().toString(), textfield.getText().toString());
        }
        return hashMap;
    }

    public static void bindDataObserver(Query query, Object obj, ArrayList<Object> arrayList, MutableLiveData<HashMap<String, Object>> liveData){
        Mapper mapper = (Mapper) obj;
        Map<String, Object> map = mapper.toMap();
        Class objClass = obj.getClass();
        Field[] fields = objClass.getFields();

        query.addSnapshotListener((snapshot, error) -> {
            boolean flag = true;
            for(DocumentChange dc : snapshot.getDocumentChanges()){

            }
        });

    }
}
