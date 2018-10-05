package dylantrebilcock.com.carupkeep;

import java.io.Serializable;

public class Vehicle implements Serializable {

    private long m_Id;
    private final String mVehicleName;
    private final int mAverageDist;
    private final int mCurrentDist;

    public Vehicle(long id, String name, int avgDist, int currentDist) {
        this.m_Id = id;
        mVehicleName = name;
        mAverageDist = avgDist;
        mCurrentDist = currentDist;
    }

    long getId() {
        return m_Id;
    }

    String getName() {
        return mVehicleName;
    }

    int getAvgDist() {
        return mAverageDist;
    }

    int getCurrentDist() { return mCurrentDist; }

    void setId(long id) {
        this.m_Id = id;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "m_Id=" + m_Id +
                ", mVehicleName='" + mVehicleName + '\'' +
                ", mAverageDist='" + mAverageDist + '\'' +
                ", mCurrentDist=" + mCurrentDist +
                '}';
    }
}