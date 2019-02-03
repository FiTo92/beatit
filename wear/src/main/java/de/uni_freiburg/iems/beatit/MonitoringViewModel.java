package de.uni_freiburg.iems.beatit;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

public class MonitoringViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> isMonitoringStarted;

    private MutableLiveData<String> startTime;
    private EcologicalMomentaryAssesmentActivity Sensor;
    private ConnectionClass Connector;

    public MonitoringViewModel(@NonNull Application application) {
        super(application);
        isMonitoringStarted = new MutableLiveData<>();
        Sensor = new EcologicalMomentaryAssesmentActivity(application);
        Connector = new ConnectionClass(application);
        //Connector.sendData();
    }

    public LiveData<Boolean> getIsMonitoringStarted() {
        if (isMonitoringStarted == null) {
            isMonitoringStarted = new MutableLiveData<>();
        }
        return isMonitoringStarted;
    }

    public void startMonitoring() {
        isMonitoringStarted.setValue(true);
        Connector.sendData();
    }
}
