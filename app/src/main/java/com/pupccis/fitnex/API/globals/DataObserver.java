package com.pupccis.fitnex.API.globals;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.pupccis.fitnex.Utilities.Constants.GlobalConstants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataObserver {

    private MutableLiveData<HashMap<String, Object>> mutableLiveData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Object>> objects = new MutableLiveData<>();

    private ArrayList<Object> objectModels = new ArrayList<>();
    private Query query;

    public  MutableLiveData<ArrayList<Object>> getObjects(Query query, Object obj){
        this.query = query;
        loadObjects(obj);
        objects.setValue(objectModels);
        return objects;
    }

    private void loadObjects(Object obj) {
        this.query.get().addOnCompleteListener(task -> {
            objectModels.clear();

            Observer observer = (Observer) obj;
            Map<String, Object> map = observer.toMap();
            HashMap<String, Object> documentData = new HashMap<>();

            for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                for(Map.Entry<String, Object> entry: map.entrySet()){
                    documentData.put(entry.getKey(), documentSnapshot.get(entry.getKey()));
                }
                documentData.put(observer.getKey(), documentSnapshot.getId());
                objectModels.add(observer.map(documentData));
                Log.d("Document ID", documentSnapshot.getId());
            }
            bindDataObserver(query, obj);
            objects.postValue(objectModels);
        });
    }

    public MutableLiveData<HashMap<String, Object>> getLiveData(Query query, Object obj){
        HashMap<String, Object> data = new HashMap<>();
        data.put("updateType", "");
        mutableLiveData.setValue(data);

        return mutableLiveData;
    }

    private void bindDataObserver(Query query, Object obj){
        HashMap<String, Object> data = new HashMap<>();

        Observer observer = (Observer) obj;
        Map<String, Object> map = observer.toMap();
        HashMap<String, Object> documentData = new HashMap<>();

        query.addSnapshotListener((snapshot, error) -> {
            boolean flag = true;
            for(DocumentChange dc : snapshot.getDocumentChanges()){
                for(Map.Entry<String, Object> entry: map.entrySet()){
                    documentData.put(entry.getKey(), dc.getDocument().get(entry.getKey()));
                }
                Log.d(observer.getKey(), dc.getDocument().getId());
                documentData.put(observer.getKey(), dc.getDocument().getId());
                switch (dc.getType()){
                    case ADDED:
                        Log.d("ADDED Raised", "triggered");
                        for(Object item : objectModels){
                            Observer mappedItem = (Observer) item;
                            Log.d("mappped item ID", mappedItem.getId());
                            Log.d("dc item iD", dc.getDocument().getId());
                            if(mappedItem.getId().equals(dc.getDocument().getId()))
                                flag = false;
                        }

                        if (flag){
                            Log.d("Flag entry", "entered flag");
                            objectModels.add(dc.getNewIndex(), observer.map(documentData));
                            data.put(GlobalConstants.KEY_UPDATE_TYPE, GlobalConstants.KEY_UPDATE_TYPE_INSERT);
                            data.put("index", dc.getNewIndex());
                            mutableLiveData.postValue(data);
                        }
                        break;
                    case MODIFIED:
                        Log.d("Modify", "Modify Triggered");
                        data.put(GlobalConstants.KEY_UPDATE_TYPE, GlobalConstants.KEY_UPDATE_TYPE_UPDATE);
                        data.put("index", dc.getNewIndex());
                        objectModels.set(dc.getNewIndex(), observer.map(documentData));
                        mutableLiveData.postValue(data);
                        break;
                    case REMOVED:
                        for(Object item : objectModels){
                            Log.d("Delete", "Delete Triggered");
                            Observer mappedItem = (Observer) item;
                            if(mappedItem.getId().equals(dc.getDocument().getId())){
                                objectModels.remove(dc.getOldIndex());
                                data.put(GlobalConstants.KEY_UPDATE_TYPE, GlobalConstants.KEY_UPDATE_TYPE_DELETE);
                                data.put("index", dc.getOldIndex());
                                mutableLiveData.postValue(data);
                                break;
                            }
                        }
                        break;
                }
            }
        });

    }
}
