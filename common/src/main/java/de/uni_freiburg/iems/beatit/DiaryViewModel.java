package de.uni_freiburg.iems.beatit;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class DiaryViewModel extends AndroidViewModel {
    private DiaryDataManager dataManager;
    private LiveData<List<DiaryRecord>> diary;

    public DiaryViewModel(@NonNull Application application) {
        super(application);
        dataManager = DiaryDataManager.getInstance(application);
        diary = dataManager.getDiary();
    }

    public void insert(DiaryRecord record) {
        dataManager.insert(record);
    }

    public void update(DiaryRecord record) {
        dataManager.update(record);
    }

    public void delete(DiaryRecord record) {
        dataManager.delete(record);
    }

    public LiveData<List<DiaryRecord>> getDiary() {
        return diary;
    }
}
